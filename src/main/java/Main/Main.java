package Main;

import Dependencies.*;
import Events.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

/**
 * @author ICAZ117
 * @author Fishpaste
 * @author Maxwellington
 */
public class Main {

	public static PrintWriter out;
	public static HashSet<Long> admins = new HashSet<>();
	public static final Color EMBED_COLOR = new Color(115, 138, 219);
	public static Connection db;
	public static Statement stmt;
	public static AudioPlayerManager playerManager;
	public static Map<Long, GuildAudioManager> audioManagers;
	public static HashSet<Long> activeServers = new HashSet<>();

	public static void main(String[] args) throws FileNotFoundException, LoginException, InterruptedException {
		playerManager = new DefaultAudioPlayerManager();
		audioManagers = new HashMap<>();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);

		// Initializing log file
		out = new PrintWriter(new File("EventLogs.logs"));

		// Open the database connection
		try {
			db = DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch (SQLException e) {
			System.err.println("Failed to open database connection");
		}

		// Initialize statement executor
		try {
			stmt = db.createStatement();
		} catch (SQLException e) {
			System.err.println("Failed to initialize statement executor");
		}

		// Create the channelConfig table if it doesn't exist
		try {
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS channelConfig (serverID LONG PRIMARY KEY, roles TEXT)");
		} catch (SQLException e) {
			System.err.println("Failed to create channelConfig table");
		}

		// Initializing JDA with token and gateway intents
		JDA jda = JDABuilder.create(new Scanner(new File("token.token")).nextLine(),
				GatewayIntent.DIRECT_MESSAGES,
				GatewayIntent.DIRECT_MESSAGE_REACTIONS,
				GatewayIntent.DIRECT_MESSAGE_TYPING,
				GatewayIntent.GUILD_BANS,
				GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
				GatewayIntent.GUILD_INVITES,
				GatewayIntent.GUILD_MEMBERS,
				GatewayIntent.GUILD_MESSAGES,
				GatewayIntent.GUILD_MESSAGE_REACTIONS,
				GatewayIntent.GUILD_MESSAGE_TYPING,
				GatewayIntent.GUILD_PRESENCES,
				GatewayIntent.GUILD_VOICE_STATES,
				GatewayIntent.GUILD_WEBHOOKS,
				GatewayIntent.MESSAGE_CONTENT).setActivity(Activity.playing("Alpha Build 1.0")).build();

		// Waiting for API to connect
		jda.awaitReady();

		// Add bot admins to admin hashset
		admins.add(390633990312427520L); // Ibraheem
		admins.add(429790168883658752L); // Isaac
		admins.add(808511479800004659L); // Maxwell

		jda.updateCommands().addCommands(
				Commands.slash("ping", "Returns the RestAction ping"),
				Commands.slash("help", "Displays a help menu"),
				Commands.slash("vm", "Sends a voice message"),
				Commands.slash("kill", "Kills the bot")
		).queue();

		// Add event listeners
		jda.addEventListener(new Ping());
		jda.addEventListener(new Help());
		jda.addEventListener(new VM());
		jda.addEventListener(new Kill());
	}

	/**
	 * Inserts a new row into the channelConfig table
	 *
	 * @param serverID The server's ID
	 * @param roles    The roles to hide VM channels from
	 * @return True if the row was inserted, false if it was not
	 */
	public static boolean insert(long serverID, String roles) {
		try {
			stmt.executeUpdate(String.format("INSERT INTO channelConfig (serverID, roles) VALUES (%d, '%s')", serverID, roles));
			return true;
		} catch (SQLException e) {
			System.err.println("Failed to insert into channelConfig table");
			return false;
		}
	}

	/**
	 * Updates a row in the channelConfig table
	 *
	 * @param serverID The server's ID
	 * @param roles    The roles to hide VM channels from
	 * @return True if the row was updated, false if it was not
	 */
	public static boolean update(long serverID, String roles) {
		try {
			stmt.executeUpdate(String.format("UPDATE channelConfig SET roles = '%s' WHERE serverID = %d", roles, serverID));
			return true;
		} catch (SQLException e) {
			System.err.println("Failed to update channelConfig table");
			return false;
		}
	}

	/**
	 * Deletes a row from the channelConfig table
	 *
	 * @param serverID The server's ID
	 * @return True if the row was deleted, false if it was not
	 */
	public static boolean delete(long serverID) {
		try {
			stmt.executeUpdate(String.format("DELETE FROM channelConfig WHERE serverID = %d", serverID));
			return true;
		} catch (SQLException e) {
			System.err.println("Failed to delete from channelConfig table");
			return false;
		}
	}

	/**
	 * Gets the roles from the channelConfig table for a specific server
	 *
	 * @param serverID The server's ID
	 * @return The roles, or null if the row was not found
	 */
	public static long[] getRoles(long serverID) {
		try {
			ResultSet rs = stmt.executeQuery(String.format("SELECT roles FROM channelConfig WHERE serverID = %d", serverID));
			if (rs.next()) {
				String[] res = rs.getString("roles").split(",");
				long[] roles = new long[res.length];

				for (int i = 0; i < res.length; i++) {
					roles[i] = Long.parseLong(res[i]);
				}

				return roles;
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			System.err.println("Failed to get roles from channelConfig table");
			return null;
		}
	}

	/**
	 * Method to convert a long array to a CSV string for storage in the database
	 *
	 * @param arr The long array to convert
	 * @return The stringified array
	 */
	private static String stringify(long[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i != arr.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * Method to log events to the EventLogs file using a pseudo-unbuffered stream
	 *
	 * @param message The message to log
	 */
	public static void log(String message) {
		out.println(message);
		out.flush();
	}
}




