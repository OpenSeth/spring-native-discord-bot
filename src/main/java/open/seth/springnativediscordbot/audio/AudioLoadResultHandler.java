package open.seth.springnativediscordbot.audio;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


//I'd really love to make this class a reusable singleton, but JDA makes it difficult to so given its design of this interface that we must implement
public class AudioLoadResultHandler implements com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler {
    private SlashCommandInteractionEvent event;
    private AudioScheduler audioScheduler;
    private VoiceChannel channel;
    private String track;

    public AudioLoadResultHandler(SlashCommandInteractionEvent event, AudioScheduler audioScheduler, VoiceChannel channel, String track){
        this.event = event;
        this.audioScheduler = audioScheduler;
        this.channel = channel;
        this.track = track;
    }
    @Override
    public void trackLoaded(AudioTrack track) {
        event.getChannel().sendMessage("Adding to queue " + track.getInfo().title).submit();
        play(channel.getGuild(), track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        AudioTrack firstTrack = playlist.getSelectedTrack();

        if (firstTrack == null) {
            firstTrack = playlist.getTracks().get(0);
        }

        event.getChannel().sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").submit();

        play(channel.getGuild(), firstTrack);
    }

    @Override
    public void noMatches() {
        event.getChannel().sendMessage("Nothing found by " + track).submit();
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        event.getChannel().sendMessage("Could not play: " + exception.getMessage()).submit();
    }

    private void play(Guild guild, AudioTrack track) {
        audioScheduler.queue(track);
    }
}
