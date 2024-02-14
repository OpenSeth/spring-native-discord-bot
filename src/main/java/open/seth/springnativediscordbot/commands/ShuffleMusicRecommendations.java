package open.seth.springnativediscordbot.commands;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;


import static open.seth.springnativediscordbot.commands.constants.Constants.MUSIC_RECS_SHUFFLE_NAME;
import static open.seth.springnativediscordbot.commands.constants.Constants.MUSIC_RECS_SHUFFLE_DESC;

@Component
@NoArgsConstructor
public class ShuffleMusicRecommendations implements SlashCommand {

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        event.getInteraction();
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
