package Events;

import Main.Main;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Event to send a voice message to the channel.
 */
public class VM extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		// Send waiting message
		event.deferReply(true).queue();

		// Check the command type
		if (event.getName().equals("vm")) {
			// Log the voice message command
			Main.log("-> Voice message command executed by " + event.getUser().getName());

			// Create a voice channel
			event.getGuild().createVoiceChannel(event.getUser().getName()).queue();

			event.getHook().sendMessage("Create channel").queue();
		}
	}


}