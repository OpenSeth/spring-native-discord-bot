package open.seth.springnativediscordbot.commands.music.recommendations;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import open.seth.springnativediscordbot.audio.AudioLoadResultHandler;
import open.seth.springnativediscordbot.audio.AudioScheduler;
import open.seth.springnativediscordbot.commands.SlashCommand;
import open.seth.springnativediscordbot.commands.music.recommendations.helper.MusicHelper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static open.seth.springnativediscordbot.commands.constants.Constants.*;

@Component
@Slf4j
public class MusicRecommendations implements SlashCommand {

    private final AudioPlayerManager audioPlayerManager;
    private final AudioScheduler audioScheduler;
    private final MusicHelper musicHelper;
    private final Pattern pattern;

    @Autowired
    public MusicRecommendations(AudioPlayerManager audioPlayerManager, AudioScheduler audioScheduler, MusicHelper musicHelper) {
        this.audioScheduler = audioScheduler;
        this.audioPlayerManager = audioPlayerManager;
        this.musicHelper = musicHelper;
        String YOUTUBE_URL_REGEX = "http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?";
        pattern = Pattern.compile(YOUTUBE_URL_REGEX);
    }

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (null != guild) {
            musicHelper.acknowledgeCommand(event);
            //Open an audio connection in the music recs voice channel
            VoiceChannel musicRecsVoiceChannel = musicHelper.setupAudioForVoiceChannel(event);
            //Grab some random recommendations from the music rec text channel
            List<String> randomMusicRecommendationUrls = grabRandomSongsFromTheMusicRecommendationsTextChannel(event);
            //Load up those recommendations and play them
            loadRandomMusicRecommendations(randomMusicRecommendationUrls, musicRecsVoiceChannel, event);
            event.getHook().sendMessage("Thanks for taking the plunge " + event.getInteraction().getUser().getName() + ". Let's find some new music!").queue();
        } else {
            event.getChannel().sendMessage(ERROR_GUILD_FAILURE).queue();
        }
    }

    private void loadRandomMusicRecommendations(List<String> randomMusicRecommendationUrls, VoiceChannel musicRecsChannel, SlashCommandInteractionEvent event) {
        randomMusicRecommendationUrls.forEach(musicUrl -> {
            audioPlayerManager.loadItem(musicUrl, new AudioLoadResultHandler(event, audioScheduler, musicRecsChannel, musicUrl));
        });
    }

    private List<String> grabRandomSongsFromTheMusicRecommendationsTextChannel(SlashCommandInteractionEvent event) {
        String musicRecsTextChannelName = "694006289797218375";
        TextChannel musicRecsTextChannel = event.getGuild().getTextChannelById(musicRecsTextChannelName);
        List<String> musicUrls = new ArrayList<>();

        OptionMapping option = event.getInteraction().getOption("start-from-the-beginning");
        if(null != option){
            MessageHistory history = MessageHistory.getHistoryFromBeginning(musicRecsTextChannel).complete();
            musicUrls = getFirstMusicRecs(history);
        } else {
            Message latestMessage =  musicRecsTextChannel.getHistory().retrievePast(1).complete().getFirst();
            MessageHistory messageHistory = MessageHistory.getHistoryBefore(musicRecsTextChannel, latestMessage.getId()).complete();
            musicUrls = getLatestMusicRecs(messageHistory);
        }
        return musicUrls;
    }

    @NotNull
    private List<String> getLatestMusicRecs(MessageHistory history) {
        List<String> musicUrls = history.retrievePast(100).complete().stream()
                .map(message -> {
                    log.debug(message.getContentRaw());
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
            getLatestMusicRecs(history);
        }
        return musicUrls;
    }

    @NotNull
    private List<String> getFirstMusicRecs(MessageHistory history) {
        List<String> musicUrls = history.retrieveFuture(100).complete().stream()
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
            getFirstMusicRecs(history);
        }
        return musicUrls;
    }

    @Override
    public String getSlashCommandName() {
        return MUSIC_RECS_NAME;
    }

    @Override
    public String getSlashCommandDescription() {
        return MUSIC_RECS_DESC;
    }
}
