package open.seth.springnativediscordbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AudioConfig {
//    private final AudioScheduler audioScheduler;
    //currently digesting how to use the audio player library
    //https://github.com/lavalink-devs/lavaplayer#usage

    @Bean
    public AudioPlayerManager audioPlayerManager(){
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);//this registers every 3rd party manager. Youtube, spotify, etc with the player manager. From that we create a player.
        return playerManager;
    }

    @Bean
    public AudioPlayer audioPlayer(AudioPlayerManager audioPlayerManager){
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
//        audioPlayer.addListener(audioScheduler);
        return audioPlayer;
    }
}
