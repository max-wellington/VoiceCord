package Events;

import Main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Event to display an embed listing the bot's commands and their function
 */
public class Help extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        // Get the message content
        String message = event.getMessage().getContentRaw();

        // If the message is the help command, log it and execute the help command
        if (message.equalsIgnoreCase(Main.PREFIX + "help") ) {
            // Log the help command
            Main.log("-> Help command executed by " + event.getAuthor().getName());

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

            // Set the embed footer
            eb.setFooter("Prefix: " + Main.PREFIX);

            // Build the embed
            MessageEmbed embed = eb.build();

            // Send the embed to the channel
            event.getChannel().sendMessage(embed).queue();
        }
    }
}