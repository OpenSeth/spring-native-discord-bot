package open.seth.springnativediscordbot.listener;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageListener extends ListenerAdapter {

    //Listener that can receive all sorts of events in discord.
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
    }
}

