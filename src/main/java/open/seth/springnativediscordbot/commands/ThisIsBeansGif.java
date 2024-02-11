package open.seth.springnativediscordbot.commands;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class ThisIsBeansGif implements SlashCommand {
    public static final String THIS_IS_BEANS_GIF = "thisisbeansgif";

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        event.reply("https://media.tenor.com/TjX1yORoln0AAAAM/this-is-beans-beans.gif").submit();
    }

    @Override
    public String getSlashCommandName() {
        return THIS_IS_BEANS_GIF;
    }

    @Override
    public String getSlashCommandDescription() {
        return "This is food, this is beans";
    }
}
