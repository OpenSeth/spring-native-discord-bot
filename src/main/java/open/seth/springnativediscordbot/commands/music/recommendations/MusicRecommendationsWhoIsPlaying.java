package open.seth.springnativediscordbot.commands.music.recommendations;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import open.seth.springnativediscordbot.commands.SlashCommand;
import org.springframework.stereotype.Component;

import static open.seth.springnativediscordbot.commands.constants.Constants.MUSIC_RECS_WHO_IS_PLAYING_DESC;
import static open.seth.springnativediscordbot.commands.constants.Constants.MUSIC_RECS_WHO_IS_PLAYING_NAME;


@Component
@RequiredArgsConstructor
public class MusicRecommendationsWhoIsPlaying implements SlashCommand {
    private final AudioScheduler audioScheduler;
    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        event.getInteraction().reply(audioScheduler.getCurrentTrack()).queue();
    }

    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_WHO_IS_PLAYING_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_WHO_IS_PLAYING_DESC;
    }
}
