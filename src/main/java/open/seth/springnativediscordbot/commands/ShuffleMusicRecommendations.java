package open.seth.springnativediscordbot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import org.springframework.stereotype.Component;


import static open.seth.springnativediscordbot.commands.constants.Constants.MUSIC_RECS_SHUFFLE_NAME;
import static open.seth.springnativediscordbot.commands.constants.Constants.MUSIC_RECS_SHUFFLE_DESC;

@Component
@RequiredArgsConstructor
public class ShuffleMusicRecommendations implements SlashCommand {

    private final AudioSendHandler audioSendingHandler;
    private final AudioPlayerManager audioPlayerManager;
    private final AudioScheduler audioScheduler;

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
            VoiceChannel musicRecsShuffleChannel = event.getGuild().getVoiceChannelsByName("Music Recs Shuffle", true).get(0);
            event.getGuild().getAudioManager().openAudioConnection(musicRecsShuffleChannel);
            event.getGuild().getAudioManager().setSendingHandler(audioSendingHandler);
            String track = "https://www.youtube.com/watch?v=z_Y3mnj-8lA";
            audioPlayerManager.loadItem(track, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    event.getChannel().sendMessage("Adding to queue " + track.getInfo().title).queue();
                    play(musicRecsShuffleChannel.getGuild(), track);
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    AudioTrack firstTrack = playlist.getSelectedTrack();

                    if (firstTrack == null) {
                        firstTrack = playlist.getTracks().get(0);
                    }

                    event.getChannel().sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                    play(musicRecsShuffleChannel.getGuild(), firstTrack);
                }

                @Override
                public void noMatches() {
                    event.getChannel().sendMessage("Nothing found by " + track).queue();
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    event.getChannel().sendMessage("Could not play: " + exception.getMessage()).queue();
                }
            });
    }

    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_SHUFFLE_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_SHUFFLE_DESC;
    }

    private void play(Guild guild, AudioTrack track) {
        audioScheduler.queue(track);
    }

    private void skipTrack(TextChannel channel) {
        audioScheduler.nextTrack();
        channel.sendMessage("Skipped to next track.").queue();
    }
}
