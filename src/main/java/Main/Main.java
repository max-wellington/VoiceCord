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
	public static PrintWriter out;
	public static void main(String[] args) throws FileNotFoundException, LoginException, InterruptedException {
		// Initializing log file
		out = new PrintWriter(new File("EventLogs.logs"));

		// Initializing JDA with token and gateway intents
		JDA jda = JDABuilder.create(new Scanner(new File("token.token")).nextLine(),
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

		// Waiting for API to connect
		jda.awaitReady();

	}

}




