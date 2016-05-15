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
	
	public static void main(String args[]) throws IOException, ParseException {
		Scanner in = new Scanner(System.in);
		String host = getHost(in);
		String nickname = getNickname(in);
		Socket server = new Socket(host, 6655);
		PrintWriter serverOut = new PrintWriter(server.getOutputStream());
		BufferedReader serverIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
		handshake(serverIn, serverOut);
	}
	
	public static String getHost(Scanner in){
		return "127.0.0.1"; //todo: user input
	}
	
	public static String getNickname(Scanner in){
		System.out.print("Enter your nickname: ");
		return in.nextLine();
	}
	
	public static boolean handshake(BufferedReader in, PrintWriter out){
		try {
			Date serverDate = ChatServer.FORMAT.parse(in.readLine());
			long oldTime = serverDate.getTime();
			long newTime = oldTime - 1000*60*60*24;
			Date newDate = new Date(newTime);
			out.println(ChatServer.FORMAT.format(newDate));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
