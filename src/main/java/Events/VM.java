package Events;

import Main.Main;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.nio.ByteBuffer;

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

			// Create a voice channel and save a reference to it
			VoiceChannel vc = event.getGuild().createVoiceChannel("Recording - " + event.getUser().getName()).complete();

			// Instantiate and audio manager for the voice channel
			AudioManager audioManager = event.getGuild().getAudioManager();

			// Connect to the voice channel
			audioManager.openAudioConnection(vc);

			// Invite the user to the voice channel
			event.getHook().sendMessage("Please join this voice channel: <#" + vc.getId() + ">").queue();

//			AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
//			AudioSourceManagers.registerRemoteSources(playerManager);
//
//			AudioPlayer player = playerManager.createPlayer();
//
//			player.playTrack(playerManager.);
		}
	}


}

