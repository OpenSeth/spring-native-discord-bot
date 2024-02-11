package open.seth.springnativediscordbot.listener;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import open.seth.springnativediscordbot.commands.SlashCommand;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SlashCommandListener extends ListenerAdapter {
    private Map<String, SlashCommand> mapOfCommands = new HashMap<>();
    private final List<SlashCommand> slashCommands;

    @PostConstruct
    public void setupCommandMap(){
        //We'll put all of our commands in a map with their name being the key. This will
        //allow us to quickly grab the right command handler for the given user command
        slashCommands.forEach(slashCommand -> {
            mapOfCommands.put(slashCommand.getSlashCommandName(), slashCommand);
        });
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        //grab the right handler for the given slash command name
        SlashCommand slashCommandHandler = mapOfCommands.get(event.getName());
        if(null != slashCommandHandler){
            slashCommandHandler.handleSlashCommand(event);
        }//TODO provide an error message when we can't find a handler
    }
}
