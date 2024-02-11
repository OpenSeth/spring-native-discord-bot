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
        slashCommands.forEach(slashCommand -> {
            mapOfCommands.put(slashCommand.getSlashCommandName(), slashCommand);
        });
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        SlashCommand slashCommandHandler = mapOfCommands.get(event.getName());
        if(null != slashCommandHandler){
            slashCommandHandler.handleSlashCommand(event);
        }
    }
}
