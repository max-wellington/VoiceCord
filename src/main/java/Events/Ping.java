package Events;

import Main.Main;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ping extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (message.equalsIgnoreCase(Main.PREFIX + "ping") ) {
            Main.log("-> Ping command executed by " + event.getAuthor().getName());

            EmbedBuilder eb = new EmbedBuilder();

            eb.setColor(new Color(115, 138, 219));

            try {
                event.getJDA().getRestPing().queue((time) -> {
                    eb.setDescription("Ping: " + time + "ms");
                    MessageEmbed embed = eb.build();

                    event.getChannel().sendMessage(embed).queue();
                });
            }
            catch (Exception e) {
                System.out.println("ERRO.RE.");
            }

        }
    }


}