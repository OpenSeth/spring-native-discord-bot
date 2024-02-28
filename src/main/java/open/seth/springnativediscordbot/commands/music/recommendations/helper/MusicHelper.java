package open.seth.springnativediscordbot.commands.music.recommendations.helper;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MusicHelper {
    public static final String MUSIC_RECS_VOICE_CHANNEL_ID = "1207505423486423090";
    private final AudioSendHandler audioSendingHandler;

    public VoiceChannel setupAudioForVoiceChannel(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        VoiceChannel musicRecsVoiceChannel = guild.getVoiceChannelById(MUSIC_RECS_VOICE_CHANNEL_ID);
        if (!event.getGuild().getAudioManager().isConnected()){
            guild.getAudioManager().openAudioConnection(musicRecsVoiceChannel);
            guild.getAudioManager().setSendingHandler(audioSendingHandler);
        }
        return musicRecsVoiceChannel;
    }

    public void acknowledgeCommand(SlashCommandInteractionEvent event){
        //Discord only gives us three seconds to respond. We get more time from discord if we acknowledge it and send back a "thinking..." reply
        event.deferReply().queue();
    }
}
