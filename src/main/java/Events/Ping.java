package Events;

import Main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Event to return a ping (in ms) between the bot and the Discord servers.
 */
public class Ping extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		// Check the command type
		if (event.getName().equals("ping")) {
			// Send waiting message
			event.deferReply().queue();

			// Log the ping command
			Main.log("-> Ping command executed by " + event.getUser().getName());

			// Create an embed builder
			EmbedBuilder eb = new EmbedBuilder();

			// Set the embed color
			eb.setColor(Main.EMBED_COLOR);

			try {
				// Attempt to obtain the reset action ping, and send the result to an arrow function
				event.getJDA().getRestPing().queue((time) -> {
					// Set the description of the embed to be the RestAction ping
					eb.setDescription("Ping: " + time + "ms");

					// Build the embed and queue the message to be sent
					MessageEmbed embed = eb.build();

					// Send the message as a response to the slash command
					event.getHook().sendMessageEmbeds(embed).queue();
				});
			} catch (Exception e) {
				// If an exception is thrown, log it and send an error message to the channel
				Main.log("-> Error: " + e.getMessage());
				event.getHook().sendMessage("Error: RestAction ping is currently unavailable").queue();
			}
		}


	}


}