package Events;

import Main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Event to display an embed listing the bot's commands and their function
 */
public class Help extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // Check the command type
        if (event.getName().equals("help")) {
            // Send waiting message
            event.deferReply().queue();

            // Log the help command
            Main.log("-> Help command executed by " + event.getUser().getName());

            // Create an embed builder
            EmbedBuilder eb = new EmbedBuilder();

            // Set the embed color
            eb.setColor(Main.EMBED_COLOR);

            // Set the embed title
            eb.setTitle("VoiceCord Commands", null);

            // Add fields to the embed for each command
            eb.addField("help", "Displays a list of all of the commands", false);
            eb.addField("ping", "Returns a RestAction ping between the bot and the Discord servers", false);
            eb.addField("vm", "Records a voice message", false);

            // Build the embed
            MessageEmbed embed = eb.build();

            // Send the embed to the channel
            event.getHook().sendMessageEmbeds(embed).queue();
        }
    }
}