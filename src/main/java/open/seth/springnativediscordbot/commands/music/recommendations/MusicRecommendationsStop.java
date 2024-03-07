package open.seth.springnativediscordbot.commands.music.recommendations;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import open.seth.springnativediscordbot.commands.SlashCommand;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.stereotype.Component;

import static open.seth.springnativediscordbot.commands.constants.Constants.*;

@Component
@RequiredArgsConstructor
@Reflective
public class MusicRecommendationsStop implements SlashCommand {
    private final AudioPlayer audioPlayer;
    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        audioPlayer.stopTrack();
        event.getGuild().getAudioManager().closeAudioConnection();
        event.reply("~Record scratch~ " + event.getInteraction().getUser().getName() + " turned off the music recs").queue();
    }

    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_STOP_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_STOP_DESC;
    }
}
