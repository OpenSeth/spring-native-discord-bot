package open.seth.springnativediscordbot.commands.shuffle;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import open.seth.springnativediscordbot.audio.AudioLoadResultHandler;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import open.seth.springnativediscordbot.commands.SlashCommand;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;

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


        TextChannel musicRecsTextChannel = event.getGuild().getTextChannelsByName("music-recs\\uD83C\\uDFB5\\uD83C\\uDFA7\\uD83C\\uDFB7", true).get(0);
        MessageHistory history = MessageHistory.getHistoryFromBeginning(musicRecsTextChannel).complete();
//        history.retrieveFuture(100).complete().stream().filter(message -> {
//            message.getContentRaw().
//        }).collect(Collectors.toList());
        //TODO randomly retrieve messages and use a regex to find youtube links. Add this to the queue.
        audioPlayerManager.loadItem(track, new AudioLoadResultHandler(event, audioScheduler, musicRecsShuffleChannel, track));
    }

    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_SHUFFLE_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_SHUFFLE_DESC;
    }
}
