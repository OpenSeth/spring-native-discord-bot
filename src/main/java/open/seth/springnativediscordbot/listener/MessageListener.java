package open.seth.springnativediscordbot.listener;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageListener extends ListenerAdapter {
    private final AudioPlayerManager audioPlayerManager;
    private final AudioScheduler audioScheduler;
    private final AudioPlayer audioPlayer;
    private final AudioSendHandler audioSendingHandler;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
    }
}

