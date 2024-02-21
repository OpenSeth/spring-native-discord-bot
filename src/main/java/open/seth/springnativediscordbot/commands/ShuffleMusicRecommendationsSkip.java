package open.seth.springnativediscordbot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import org.springframework.stereotype.Component;

import static open.seth.springnativediscordbot.commands.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class ShuffleMusicRecommendationsSkip implements SlashCommand {
    private final AudioScheduler audioScheduler;

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        audioScheduler.nextTrack();
        event.getInteraction();
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
