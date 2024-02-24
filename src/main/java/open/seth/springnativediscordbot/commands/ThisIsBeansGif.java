package open.seth.springnativediscordbot.commands;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static open.seth.springnativediscordbot.commands.constants.Constants.THIS_IS_BEANS_GIF_DESC;
import static open.seth.springnativediscordbot.commands.constants.Constants.THIS_IS_BEANS_GIF_NAME;

@Component
@NoArgsConstructor
public class ThisIsBeansGif implements SlashCommand {

    public static final String BEAN_FRANTIC_MODE = "bean-frantic-mode";
    public static final String BEAN_FRANTIC_MODE_DESC = "Do you need frantic beans?";
    public static final String BEANS_GIF_URL = "https://media.tenor.com/TjX1yORoln0AAAAM/this-is-beans-beans.gif";
    public static final String BEANS_FRANTIC_GIF_URL = "https://gifyu.com/image/SCOEm";

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        boolean isObnoxious = determineHowObnoxious(event);
        String beansGifReply = buildReply(isObnoxious);
        event.reply(beansGifReply).queue();

    }

    private String buildReply(boolean isLoud) {
        return isLoud ? BEANS_FRANTIC_GIF_URL : BEANS_GIF_URL;
    }

    private boolean determineHowObnoxious(SlashCommandInteractionEvent event) {
        //determine if the user wants a huge title to go with their gif post
        OptionMapping option = event.getInteraction().getOption(BEAN_FRANTIC_MODE);
        return option != null ? option.getAsBoolean() : false;
    }

    @Override
    public String getSlashCommandName() {
        return THIS_IS_BEANS_GIF_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return THIS_IS_BEANS_GIF_DESC;
    }

    @Override
    public Collection<? extends OptionData> getSlashCommandOptions() {
        OptionData beanCommandOptions = new OptionData(OptionType.BOOLEAN, BEAN_FRANTIC_MODE, BEAN_FRANTIC_MODE_DESC);
        return List.of(beanCommandOptions);
    }
}
