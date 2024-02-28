package open.seth.springnativediscordbot.commands.music.recommendations;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import open.seth.springnativediscordbot.audio.AudioLoadResultHandler;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import open.seth.springnativediscordbot.commands.SlashCommand;
import open.seth.springnativediscordbot.commands.music.recommendations.helper.MusicHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static open.seth.springnativediscordbot.commands.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class MusicRecommendationsRequest implements SlashCommand {
    private static final String URL = "url";
    private static final String ERROR_MISSING_OPTION = "Provide a url as an option in your request";
    private static final String COMMAND_OPTION_DESCRIPTION = "A URL to YouTube, Soundcloud, Vimeo, Bandcamp, and Twitch";

    private final AudioScheduler audioScheduler;
    private final AudioPlayerManager audioPlayerManager;
    private final MusicHelper musicHelper;

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        if(null!= event.getGuild()) {
            musicHelper.acknowledgeCommand(event);
            VoiceChannel musicRecsVoiceChannel = musicHelper.setupAudioForVoiceChannel(event);
            OptionMapping option = event.getInteraction().getOption(URL);
            if (isTrackURLProvided(option)) {
                String url = option.getAsString();
                audioPlayerManager.loadItem(url, new AudioLoadResultHandler(event, audioScheduler, musicRecsVoiceChannel, url));
                event.getHook().sendMessage("Oooh that's a good one " + event.getInteraction().getUser().getName()).queue();
            } else {
                event.getChannel().sendMessage(ERROR_MISSING_OPTION).queue();
            }
        } else {
            event.getChannel().sendMessage(ERROR_GUILD_FAILURE).queue();
        }
    }

    private static boolean isTrackURLProvided(OptionMapping option) {
        return null != option && StringUtils.isNotBlank(option.getAsString());
    }

    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_CUSTOM_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_CUSTOM_DESC;
    }

    @Override
    public Collection<? extends OptionData> getSlashCommandOptions() {
        OptionData requestCommandOptions = new OptionData(OptionType.STRING, URL, COMMAND_OPTION_DESCRIPTION);
        requestCommandOptions.setAutoComplete(true);
        return List.of(requestCommandOptions);
    }
}
