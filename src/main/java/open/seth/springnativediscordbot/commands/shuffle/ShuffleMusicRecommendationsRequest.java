package open.seth.springnativediscordbot.commands.shuffle;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import open.seth.springnativediscordbot.audio.AudioLoadResultHandler;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import open.seth.springnativediscordbot.commands.SlashCommand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

import static open.seth.springnativediscordbot.commands.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class ShuffleMusicRecommendationsRequest implements SlashCommand {
    public static final String URL = "url";
    private final AudioScheduler audioScheduler;
    private final AudioPlayer audioPlayer;
    private final AudioPlayerManager audioPlayerManager;
    private final AudioSendHandler audioSendingHandler;

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        //TODO extract to constants
        VoiceChannel musicRecsShuffleChannel = event.getGuild().getVoiceChannelsByName("Music Recs Shuffle", true).get(0);
        OptionMapping option = event.getInteraction().getOption(URL);
        if(null != option && StringUtils.isNotBlank(option.getAsString())){//TODO extract as method
            String url = option.getAsString();
            if(!event.getGuild().getAudioManager().isConnected()){
                //TODO duplicated code. Extract to common
                event.getGuild().getAudioManager().openAudioConnection(musicRecsShuffleChannel);
                event.getGuild().getAudioManager().setSendingHandler(audioSendingHandler);
            }
            audioPlayerManager.loadItem(url, new AudioLoadResultHandler(event, audioScheduler, musicRecsShuffleChannel, url));

        } else{
            //TODO give an error message
        }

    }
    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_SHUFFLE_CUSTOM_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_SHUFFLE_CUSTOM_DESC;
    }

    @Override
    public Collection<? extends OptionData> getSlashCommandOptions() {
        OptionData beanLevelData = new OptionData(OptionType.STRING, URL, "A URL to YouTube, Soundcloud, Vimeo, Bandcamp, and Twitch");
        return Arrays.asList(beanLevelData);
    }
}
