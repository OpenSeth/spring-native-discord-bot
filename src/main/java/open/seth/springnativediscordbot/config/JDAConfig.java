package open.seth.springnativediscordbot.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import open.seth.springnativediscordbot.commands.SlashCommand;
import open.seth.springnativediscordbot.listener.MessageListener;
import open.seth.springnativediscordbot.listener.SlashCommandListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Discord interactions are simplified through the JDA library.
 * This configuration class will create the JDA client and
 * register commands, actions, etc with Discord
 */
@Configuration
@RequiredArgsConstructor
public class JDAConfig {
    private final List<SlashCommand> slashCommands;
    private final SlashCommandListener slashCommandListener;
    private final MessageListener messageListener;

    @Bean
    public JDA jdaClient() {
        JDABuilder builder = JDABuilder.createDefault("");
        setCacheOptions(builder);
        setDeleteOptions(builder);
        setBotStatus(builder);
        setListeners(builder);

        JDA jdaClient = builder.build();

        registerSlashCommandsWithDiscord(jdaClient);

        return jdaClient;
    }

    private void setListeners(JDABuilder builder) {
        builder.addEventListeners(slashCommandListener, messageListener);
    }

    private void setBotStatus(JDABuilder builder) {
        // Set activity (like "playing Something")
        builder.setActivity(Activity.customStatus("These are the motherboards?"));
    }

    private void setDeleteOptions(JDABuilder builder) {
        builder.setBulkDeleteSplittingEnabled(false);
    }

    private void setCacheOptions(JDABuilder builder) {
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
    }

    private void registerSlashCommandsWithDiscord(JDA jdaClient) {
        //convert from our command model to the JDA command data model
        ArrayList<CommandData> commandsToRegister = new ArrayList<>();
        slashCommands.forEach(slashCommand -> {
            commandsToRegister.add(Commands.slash(slashCommand.getSlashCommandName(),
                            slashCommand.getSlashCommandDescription())
                    .addOptions(slashCommand.getSlashCommandOptions())
            );
        });

        //register the slash commands with discord
        jdaClient.updateCommands().addCommands(commandsToRegister).queue();
    }
}
