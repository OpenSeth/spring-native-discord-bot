package open.seth.springnativediscordbot.commands.shuffle;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import open.seth.springnativediscordbot.commands.SlashCommand;
import org.springframework.stereotype.Component;

import static open.seth.springnativediscordbot.commands.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class ShuffleMusicRecommendationsStop implements SlashCommand {
    private final AudioPlayer audioPlayer;
    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        audioPlayer.stopTrack();
        event.getGuild().getAudioManager().closeAudioConnection();
        event.reply("~Record scratch~ " + event.getInteraction().getUser().getName() + " turned off the music shuffle").queue();
    }

    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_SHUFFLE_STOP_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_SHUFFLE_STOP_DESC;
    }
}
