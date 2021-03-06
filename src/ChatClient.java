import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * The main client-side program.
 */
public class ChatClient {

	public static final String ONLINE_CHECK = "CLACK";
	public static final DateFormat HANDSHAKE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	public static final String HANDSHAKE_OK = "AFFIRMATIVE";
	public static final String HANDSHAKE_BAD = "NEGATIVE";
	public static final String CLIENT_ONLINE_CHECK = "ACK"; // server uses this to chek if the client is online
	public static final String ONLINE_CHECK = "CLACK"; // to check if the server is online

	public static void main(String args[]) throws IOException, ParseException {
		Scanner in = new Scanner(System.in); // User input
		String host = getHost(in); // Hostname
		String nickname = getNickname(in); // Nickname
		Socket server = new Socket(host, 6655); // Server socket
		PrintWriter serverOut = new PrintWriter(server.getOutputStream()); // Server output
		BufferedReader serverIn = new BufferedReader(new InputStreamReader(server.getInputStream())); // Server input
		handshake(serverIn, serverOut);
	}

    /**
     * Get hostname from user input.
     * @param in The input scanner.
     * @return The user input hostname.
     */
	public static String getHost(Scanner in){
		return "127.0.0.1"; //todo: user input
	}

    /**
     * Get nickname from user input.
     * @param in The input scanner.
     * @return The user input nickname.
     */
	public static String getNickname(Scanner in){
		System.out.print("Enter your nickname: ");
		return in.nextLine();
	}

    /**
     * Perform a handshake between the client and the server.
     * @param in The server input.
     * @param out The server output.
     * @return true if the handshake has succeeded, false if it has failed.
     */
	public static boolean handshake(BufferedReader in, PrintWriter out){
		try {
			Date serverDate = HANDSHAKE_FORMAT.parse(in.readLine());
			long oldTime = serverDate.getTime();
			long newTime = oldTime - 1000*60*60*24-60*60;
			Date newDate = new Date(newTime);
			out.println(HANDSHAKE_FORMAT.format(newDate));
        		return in.readLine().equals(HANDSHAKE_OK);
		} catch (Exception e) {
			return false;
		}
	}
}
