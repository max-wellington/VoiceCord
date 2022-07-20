package Main;

import Events.*;
import java.io.*;
import java.util.*;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

/**
 * @author ICAZ117
 * @author Fishpaste
 * @author Maxwellington
 */
public class Main {

	public static final char PREFIX = '>';
	public static final long SERVER_ID = 938315116561182720L;
	public static final long LOG_CHANNEL_ID = 980697030659805205L;
	public static final long LAUNCH_TIME = System.currentTimeMillis();
	public static final int ID_LENGTH = 18;
	public static Guild SERVER;
	public static TextChannel LOG_CHANNEL;
	public static PrintWriter out;
	public static HashSet<Long> admins = new HashSet<>();

	public static void main(String[] args) throws LoginException, InterruptedException, FileNotFoundException {
		// Initialize PrintWriter for event logs
		out = new PrintWriter(new File("EventLogs.out"));

		// Initialize JDA
//		JDA jda = JDABuilder.create(new Scanner(new File("token.txt")).nextLine(),
//				GatewayIntent.DIRECT_MESSAGES,
//				GatewayIntent.DIRECT_MESSAGE_REACTIONS,
//				GatewayIntent.DIRECT_MESSAGE_TYPING,
//				GatewayIntent.GUILD_BANS,
//				GatewayIntent.GUILD_EMOJIS,
//				GatewayIntent.GUILD_INVITES,
//				GatewayIntent.GUILD_MEMBERS,
//				GatewayIntent.GUILD_MESSAGES,
//				GatewayIntent.GUILD_MESSAGE_REACTIONS,
//				GatewayIntent.GUILD_MESSAGE_TYPING,
//				GatewayIntent.GUILD_PRESENCES,
//				GatewayIntent.GUILD_VOICE_STATES).setActivity(Activity.playing("Pre-Alpha build 0.1")).build();

		JDA jda = JDABuilder.create(new Scanner(new File("token.txt")).nextLine(),
				GatewayIntent.DIRECT_MESSAGES,
				GatewayIntent.DIRECT_MESSAGE_REACTIONS,
				GatewayIntent.DIRECT_MESSAGE_TYPING,
				GatewayIntent.GUILD_BANS,
				GatewayIntent.GUILD_EMOJIS,
				GatewayIntent.GUILD_INVITES,
				GatewayIntent.GUILD_MESSAGES,
				GatewayIntent.GUILD_MESSAGE_REACTIONS,
				GatewayIntent.GUILD_MESSAGE_TYPING,
				GatewayIntent.GUILD_VOICE_STATES).setActivity(Activity.playing("Alpha Build 1.0")).build();

		// Await completion of connection to Discord servers
		jda.awaitReady();

		// Get server object
		SERVER = jda.getGuildById(SERVER_ID);

		// If server is null, print an error message and exit program.
		if (SERVER == null) {
			out.println("\n-------------- SERVER NOT FOUND -------------\n");
			out.flush();
			Main.out.close();
			System.exit(0);
		}
		// Else, print server name
		else {
			out.printf("\n------------------ SUCCESS ------------------\n-> Found Server: %s\n-> Searching for log channel...\n", SERVER.getName());
			out.flush();

			// Get system log channel
			LOG_CHANNEL = SERVER.getTextChannelById(LOG_CHANNEL_ID);

			// Check if log channel is null
			if (LOG_CHANNEL == null) {
				out.println("\n----------- LOG CHANNEL NOT FOUND -----------\n");
				out.flush();
				Main.out.close();
				System.exit(0);
			}
			else {
				out.printf("-> Found log channel: %s\n", LOG_CHANNEL.getName());
				out.flush();
				log("-> Connection status: ONLINE");
				log("-> Loading modules...");

//				Scanner in = new Scanner(new File("ibi.in"));
//
//				while (in.hasNext()) {
//					IBIs.add(in.next());
//				}
				admins.add(390633990312427520L);
				admins.add(819060826740490271L);

				log("-> Initialization complete");
			}
		}

		// Add event listeners
		jda.addEventListener(new Help());
		
	}

	public static void log(String message) {
		LOG_CHANNEL.sendMessage(String.format("```%s```", message)).queue();
		out.println(message);
		out.flush();
	}

	public static void dm(User user, String message) {
		user.openPrivateChannel().flatMap(channel -> channel.sendMessage(message)).queue();
	}
}




