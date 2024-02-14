package open.seth.springnativediscordbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.Collection;

public interface SlashCommand {
    void handleSlashCommand(SlashCommandInteractionEvent event);
    String getSlashCommandName();
    String getSlashCommandDescription();
    default Collection<? extends OptionData> getSlashCommandOptions(){
        //not all slash commands need options. We'll default it to providing none and if we need it we'll override the default
        return new ArrayList<>();
    }
}
