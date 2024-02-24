package open.seth.springnativediscordbot.commands.shuffle;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShuffleHelper {
    public static final String VOICE_CHANNEL_NAME = "Music Recs Shuffle";
    private final AudioSendHandler audioSendingHandler;

    public VoiceChannel setupAudioForVoiceChannel(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        VoiceChannel musicRecsShuffleChannel = guild.getVoiceChannelsByName(VOICE_CHANNEL_NAME, true).getFirst();
        if (!event.getGuild().getAudioManager().isConnected()){
            guild.getAudioManager().openAudioConnection(musicRecsShuffleChannel);
            guild.getAudioManager().setSendingHandler(audioSendingHandler);
        }
        return musicRecsShuffleChannel;
    }

    public void acknowledgeCommand(SlashCommandInteractionEvent event){
        //Discord only gives us three seconds to respond. We get more time from discord if we acknowledge it and send back a "thinking..." reply
        event.deferReply().queue();
    }
}
