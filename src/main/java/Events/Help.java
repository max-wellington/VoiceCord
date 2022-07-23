package Events;

import Main.Main;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (message.equalsIgnoreCase(Main.PREFIX + "help") ) {
            EmbedBuilder eb = new EmbedBuilder();

            eb.setColor(new Color(115, 138, 219));

            eb.setTitle("VoiceCord Commands", null);

            eb.addField("help", "Displays a list of all of the commands", false);
            eb.addField("ping", "Returns a RestAction ping between the bot and the Discord servers", false);
            eb.addField("vm", "Records a voice message", false);

            eb.setFooter("Prefix: " + Main.PREFIX);

            MessageEmbed embed = eb.build();

            event.getChannel().sendMessage(embed).queue();

        }
    }


}