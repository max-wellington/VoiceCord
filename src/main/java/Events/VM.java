package Events;

import Main.Main;
import Dependencies.*;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

/**
 * Event to send a voice message to the channel.
 */
public class VM extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		// Check the command type
		if (event.getName().equals("vm")) {
			// Send waiting message
			event.deferReply(true).queue();

			// Log the voice message command
			Main.log("-> Voice message command executed by " + event.getUser().getName());

			// If someone in the same server is actively recording a voice message, send and error message to the user
			if (Main.activeServers.contains(event.getGuild().getIdLong())) {
				// Send an error message to the channel
				event.getHook().sendMessage("Error: A voice message is already in progress").queue();
				return;
			}
			else {
				// Add the current server to the active servers list
				Main.activeServers.add(event.getGuild().getIdLong());

				// Create a voice channel and save a reference to it
				VoiceChannel vc = event.getGuild().createVoiceChannel("Recording - " + event.getUser().getName()).complete();

				// Instantiate and audio manager for the voice channel
				AudioManager audioManager = event.getGuild().getAudioManager();

				// Connect to the voice channel
				audioManager.openAudioConnection(vc);

				// Invite the user to the voice channel
				event.getHook().sendMessage("Please join this voice channel: <#" + vc.getId() + ">").queue();

				loadAndPlay(event.getChannel().asTextChannel(), "https://www.youtube.com/watch?v=13ViHuJzP3g");
			}
		}
	}

	private synchronized GuildAudioManager getGuildAudioPlayer(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		GuildAudioManager audioManager = Main.audioManagers.get(guildId);

		if (audioManager == null) {
			audioManager = new GuildAudioManager(Main.playerManager);
			Main.audioManagers.put(guildId, audioManager);
		}

		guild.getAudioManager().setSendingHandler(audioManager.getSendHandler());

		return audioManager;
	}

	private void loadAndPlay(final TextChannel channel, final String trackUrl) {
		GuildAudioManager audioManager = getGuildAudioPlayer(channel.getGuild());

		Main.playerManager.loadItemOrdered(audioManager, trackUrl, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				channel.sendMessage("Adding to queue " + track.getInfo().title).queue();
				play(channel.getGuild(), audioManager, track);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();

				if (firstTrack == null) {
					firstTrack = playlist.getTracks().get(0);
				}

				channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

				play(channel.getGuild(), audioManager, firstTrack);
			}

			@Override
			public void noMatches() {
				channel.sendMessage("Nothing found by " + trackUrl).queue();
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				channel.sendMessage("Could not play: " + exception.getMessage()).queue();
			}
		});
	}

	private void play(Guild guild, GuildAudioManager audioManager, AudioTrack track) {
		connectToFirstVoiceChannel(guild.getAudioManager());

		audioManager.scheduler.queue(track);
	}

	private void skipTrack(TextChannel channel) {
		GuildAudioManager audioManager = getGuildAudioPlayer(channel.getGuild());
		audioManager.scheduler.nextTrack();

		channel.sendMessage("Skipped to next track.").queue();
	}

	private static void connectToFirstVoiceChannel(AudioManager audioManager) {
		if (!audioManager.isConnected()) {
			for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
				audioManager.openAudioConnection(voiceChannel);
				break;
			}
		}
	}


}

