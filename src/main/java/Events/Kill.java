package Events;

import Main.Main;

import java.awt.*;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Kill extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (message.equalsIgnoreCase(Main.PREFIX + "kill") && (Main.admins.contains(event.getAuthor().getIdLong()))) {
            event.getChannel().sendMessage("```Terminating program execution...```").queue();
            System.exit(0);
        }
    }
}
