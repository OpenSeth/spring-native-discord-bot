package open.seth.springnativediscordbot.commands;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand {
    void handleSlashCommand(SlashCommandInteractionEvent event);
    String getSlashCommandName();
    String getSlashCommandDescription();
}
