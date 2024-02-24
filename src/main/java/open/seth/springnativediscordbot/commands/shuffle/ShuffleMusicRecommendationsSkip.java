package open.seth.springnativediscordbot.commands.shuffle;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import open.seth.springnativediscordbot.commands.SlashCommand;
import org.springframework.stereotype.Component;

import static open.seth.springnativediscordbot.commands.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class ShuffleMusicRecommendationsSkip implements SlashCommand {
    private final AudioScheduler audioScheduler;
    private final ShuffleHelper shuffleHelper;

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        shuffleHelper.acknowledgeCommand(event);
        audioScheduler.nextTrack();
        event.getHook().sendMessage(audioScheduler.getCurrentTrack() + " is now playing").queue();
    }

    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_SHUFFLE_SKIP_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_SHUFFLE_SKIP_DESC;
    }
}
