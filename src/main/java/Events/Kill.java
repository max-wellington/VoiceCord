package Events;

import Main.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Kill extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (message.equalsIgnoreCase(Main.PREFIX + "kill") && (Main.admins.contains(event.getAuthor().getIdLong()))) {
            Main.log("-> Kill command executed by " + event.getAuthor().getName());

            event.getChannel().sendMessage("```Terminating program execution...```").queue();
            Main.out.close();
            System.exit(0);
        }
    }
}
