package open.seth.springnativediscordbot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

import static open.seth.springnativediscordbot.commands.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class ShuffleMusicRecommendationsRequest implements SlashCommand {
    private final AudioScheduler audioScheduler;
    private final AudioPlayer audioPlayer;

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        //TODO extract to constants
        OptionMapping option = event.getInteraction().getOption("URL");
        if(null != option && StringUtils.isNotBlank(option.getAsString())){//TODO extract as method
             String url = option.getAsString();
            if(!event.getGuild().getAudioManager().isConnected()){

            }
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
        OptionData beanLevelData = new OptionData(OptionType.STRING, "URL", "A URL to YouTube, Soundcloud, Vimeo, Bandcamp, and Twitch");
        return Arrays.asList(beanLevelData);
    }
}
