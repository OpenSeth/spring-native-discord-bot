package open.seth.springnativediscordbot.commands.music.recommendations;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import open.seth.springnativediscordbot.commands.SlashCommand;
import open.seth.springnativediscordbot.commands.music.recommendations.helper.MusicHelper;
import org.springframework.stereotype.Component;

import static open.seth.springnativediscordbot.commands.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class MusicRecommendationsSkip implements SlashCommand {
    private final AudioScheduler audioScheduler;
    private final MusicHelper musicHelper;

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        musicHelper.acknowledgeCommand(event);
        audioScheduler.nextTrack();
        String currentTrack = audioScheduler.getCurrentTrack();
        if(null !=currentTrack){
            event.getHook().sendMessage(currentTrack + " is now playing").queue();
        } else {
            event.getHook().sendMessage("Nothing is on the queue").queue();
        }
    }

    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_SKIP_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_SKIP_DESC;
    }
}
