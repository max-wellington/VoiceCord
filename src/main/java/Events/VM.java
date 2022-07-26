package Events;

import Main.Main;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Event to send a voice message to the channel.
 */
public class VM extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		// Get the message content
		String message = event.getMessage().getContentRaw();

		// If the message is the voice message command, log it and execute the voice message command
		if (message.equalsIgnoreCase(Main.PREFIX + "vm")) {
			// Log the voice message command
			Main.log("-> Voice message command executed by " + event.getAuthor().getName());

			// Create a voice channel
			event.getGuild().createVoiceChannel(event.getAuthor().getName()).queue();

		}
	}


}