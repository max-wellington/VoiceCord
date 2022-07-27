package Events;

import Main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

/**
 * Event to terminate execution of the bot
 */
public class Kill extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		// If the message is the kill command AND the message was sent by a bot admin, log it and execute the kill command
		if (event.getName().equals("kill") && (Main.admins.contains(event.getUser().getIdLong()))) {
			// Send waiting message
			event.deferReply().queue();

			// Log the kill command
			Main.log("-> Kill command executed by " + event.getUser().getName());

			// Create an embed builder
			EmbedBuilder eb = new EmbedBuilder();

			// Set the embed color
			eb.setColor(Main.EMBED_COLOR);

			// Set the description of the embed
			eb.setDescription("Terminating program execution...");

			// Build the embed and queue the message to be sent
			event.getHook().sendMessageEmbeds(eb.build()).queue();

			// Close the PrintWriter to the log file
			Main.out.close();

			// Close the database connection and statement executor
			try {
				Main.stmt.close();
				Main.db.close();
			} catch (SQLException e) {
				System.err.println("Failure to close database connection or statement executor");
			}

			// Sleep for half a second to allow the message to be sent
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				Main.out.println("Failed to sleep");
				Main.out.flush();
			}

			// Terminate the program
			System.exit(0);
		}
	}
}
