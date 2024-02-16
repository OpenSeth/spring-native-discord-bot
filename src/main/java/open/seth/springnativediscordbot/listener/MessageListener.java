package open.seth.springnativediscordbot.listener;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageListener extends ListenerAdapter {
    private final AudioPlayerManager audioPlayerManager;
    private final AudioScheduler audioScheduler;
    private final AudioPlayer audioPlayer;
    private final AudioSendHandler audioSendingHandler;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getMentions().getUsers().parallelStream().anyMatch(mention -> "This Is Beans Bot".equalsIgnoreCase(mention.getName()))) {
            VoiceChannel musicRecsShuffleChannel = event.getGuild().getVoiceChannelsByName("Music Recs Shuffle", true).get(0);
            event.getGuild().getAudioManager().openAudioConnection(musicRecsShuffleChannel);
            event.getGuild().getAudioManager().setSendingHandler(audioSendingHandler);
            String track = "https://www.youtube.com/watch?v=XOzs1FehYOA";
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
    }

    private void play(Guild guild, AudioTrack track) {
//        connectToFirstVoiceChannel(guild.getAudioManager());
        audioScheduler.queue(track);
//        audioPlayer.playTrack(track);
    }

    private void skipTrack(TextChannel channel) {
//        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        audioScheduler.nextTrack();
        channel.sendMessage("Skipped to next track.").queue();
    }

    private static void connectToFirstVoiceChannel(AudioManager audioManager) {
        if (!audioManager.isConnected()) {
            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                audioManager.openAudioConnection(voiceChannel);
                break;
            }
        }
    }
}

