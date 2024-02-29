# Spring Native Discord Bot

This is a passion project to create a discord bot for me and my friends.

## Description

This is an ongoing passion project to provide some extra functionality to a discord server I share with my friends. This bot provides a few fun commands to users.
There's a set of commands devoted to scraping music recommendations in a text channel and automatically playing the recommendations in a voice channel. This way users don't have to go back through the text chat and open links one by one, they can run a command provided by this bot, and the bot can automatically play the latest or earliest recommendations.

This bot also provides a command to easily post our favorite gif. :)

This project is not complete, and as freetime allows I will add more functionality or refine existing functionality.

### Learning Objectives
* Get exposure to Spring Native
* Get exposure to Gradle
* Get exposure to a Discord Library. I ended up picking JDA
* Get more exposure to docker (I love docker) and more specifically in the context of how Spring Native uses it.
* Get exposure to Java 21
* Host this bot on a raspberry pi using Portainer (cheap self hosting)
* If my friends come up with other ideas for the bot, I'll add them!

### Techs Used
Java 21, Spring Boot, Spring Native, Gradle, Docker, JDA, Discord, LavaPlayer

### Lessons Learned
* Spring Native is FAST!
* JDA is a really neat library. Its amazing they can provide so much abstraction. Really everything you do to interact with discord via their REST API, but you never interact with RestTemplate or WebClient. They have clever solutions for blocking and non-blocking calls to the discord API
* Docker is fun. As always.
* Gradle is slick. I like how less verbose it is compared to maven.
* The discord API has its limitations. I originally wanted to create shuffle functionality, but given how their API works, I'd have to parse an entire text channel and then randomly pick what I queried. This is incredibly time consuming and wasteful. Long term solutions would be to log that info in a database or use clever caching to drive shuffle behavior. That's out of scope for me for now.

### Disclaimers
* This will never be a production application or framework. So it is not backed with unit tests or integration tests. If I got serious about what I'm writing here, I would go to that level of effort. This is just for fun.
* This code is highly specific to the structure of my discord server and would need to be heavily modified to use in other discord servers.
* This code is always changing and it is not 100% ironed out. You can probably find a way to break this bot.

Inspiration, code snippets, etc.
* [JDA](https://jda.wiki/introduction/jda/)
* [Spring Native](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html)
* [Discord](https://discord.com/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Lava Player](https://github.com/sedmelluq/lavaplayer/blob/master/demo-jda/src/main/java/com/sedmelluq/discord/lavaplayer/demo/jda/TrackScheduler.java)
