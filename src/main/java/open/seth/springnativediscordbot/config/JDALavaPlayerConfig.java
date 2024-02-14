package open.seth.springnativediscordbot.config;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JDALavaPlayerConfig {
    //currently digesting how to use the audio player library
    //https://github.com/lavalink-devs/lavaplayer#usage

    @Bean
    public AudioPlayerManager audioPlayerManager(){
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        return playerManager;
    }

    @Bean
    public AudioPlayer audioPlayer(AudioPlayerManager audioPlayerManager){
        return audioPlayerManager.createPlayer();
    }
}
