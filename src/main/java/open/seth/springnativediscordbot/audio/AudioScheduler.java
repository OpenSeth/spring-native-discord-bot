package open.seth.springnativediscordbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

//Pulled code from here but modified for Spring. https://github.com/sedmelluq/lavaplayer/blob/master/demo-jda/src/main/java/com/sedmelluq/discord/lavaplayer/demo/jda/TrackScheduler.java
@Component
@RequiredArgsConstructor
public class AudioScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final ArrayBlockingQueue<AudioTrack> queue = new ArrayBlockingQueue<>(20);

    @PostConstruct
    public void postConstruct(){
        player.addListener(this);
    }

    public void queue(AudioTrack track) {
        if(!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        // Player was paused
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        // Player was resumed
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        // A track started playing
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            // Start next track
        }

        // endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
        // endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
        // endReason == STOPPED: The player was stopped.
        // endReason == REPLACED: Another track started playing while this had not finished
        // endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
        //                       clone of this back to your queue
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        // Audio track has been unable to provide us any audio, might want to just start a new track
    }

    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        player.startTrack(queue.poll(), false);
    }
}
