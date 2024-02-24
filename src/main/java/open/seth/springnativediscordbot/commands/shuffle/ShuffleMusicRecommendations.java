package open.seth.springnativediscordbot.commands.shuffle;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import open.seth.springnativediscordbot.audio.AudioLoadResultHandler;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import open.seth.springnativediscordbot.commands.SlashCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static open.seth.springnativediscordbot.commands.constants.Constants.*;

@Component
@Slf4j
public class ShuffleMusicRecommendations implements SlashCommand {

    private final AudioPlayerManager audioPlayerManager;
    private final AudioScheduler audioScheduler;
    private final ShuffleHelper shuffleHelper;
    private final Pattern pattern;

    @Autowired
    public ShuffleMusicRecommendations(AudioSendHandler audioSendingHandler, AudioPlayerManager audioPlayerManager, AudioScheduler audioScheduler, ShuffleHelper shuffleHelper) {
        this.audioScheduler = audioScheduler;
        this.audioPlayerManager = audioPlayerManager;
        this.shuffleHelper = shuffleHelper;
        String YOUTUBE_URL_REGEX = "http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?";
        pattern = Pattern.compile(YOUTUBE_URL_REGEX);
    }

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (null != guild) {
            shuffleHelper.acknowledgeCommand(event);
            //Open an audio connection in the music recs shuffle voice channel
            VoiceChannel musicRecsShuffleChannel = shuffleHelper.setupAudioForVoiceChannel(event);
            //Grab some random recommendations from the music rec text channel
            List<String> randomMusicRecommendationUrls = grabRandomSongsFromTheMusicRecommendationsTextChannel(guild);
            //Load up those recommendations and play them
            loadRandomMusicRecommendations(randomMusicRecommendationUrls, musicRecsShuffleChannel, event);
            event.getHook().sendMessage("Thanks for taking the plunge " + event.getInteraction().getUser().getName() + ". Let's find some new music!").queue();
        } else {
            event.getChannel().sendMessage(ERROR_GUILD_FAILURE).queue();
        }
    }

    private void loadRandomMusicRecommendations(List<String> randomMusicRecommendationUrls, VoiceChannel musicRecsShuffleChannel, SlashCommandInteractionEvent event) {
        randomMusicRecommendationUrls.forEach(musicUrl -> {
            audioPlayerManager.loadItem(musicUrl, new AudioLoadResultHandler(event, audioScheduler, musicRecsShuffleChannel, musicUrl));
        });
    }

    private List<String> grabRandomSongsFromTheMusicRecommendationsTextChannel(Guild guild) {
        //determine the time the server was created and
        String musicRecsTextChannelName = "music-recs\uD83C\uDFB5\uD83C\uDFA7\uD83C\uDFB7";
        TextChannel musicRecsTextChannel = guild.getTextChannelsByName(musicRecsTextChannelName, true).getFirst();
        MessageHistory history = MessageHistory.getHistoryFromBeginning(musicRecsTextChannel).complete();
        List<String> musicUrls = history.retrieveFuture(25).complete().stream()
                .map(message -> {
                    String rawMessage = message.getContentRaw();
                    Matcher matcher = pattern.matcher(rawMessage);
                    if(matcher.find()){
                        return matcher.group();
                    } else{
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (musicUrls.isEmpty()) {
            //sometimes people blather on about nothing and don't recommend music. If that happens, let's just search a different timeframe and make a recursive call
            grabRandomSongsFromTheMusicRecommendationsTextChannel(guild);
        }
        return musicUrls;
    }


    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_SHUFFLE_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_SHUFFLE_DESC;
    }
}
