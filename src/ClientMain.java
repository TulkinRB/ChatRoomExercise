import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by User on 06/04/2016.
 */
public class ClientMain {
	
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
		return "loopback"; //todo: user input
	}
	
	public static String getNickname(Scanner in){
		System.out.print("Enter your nickname: ");
		return in.nextLine();
	}
	
	public static boolean handshake(BufferedReader in, PrintWriter out){
		try {
			Date serverDate = ChatServer.format.parse(in.readLine());
			long oldTime = serverDate.getTime();
			long newTime = oldTime - 1000*60*60*24;
			Date newDate = new Date(newTime);
			out.println(ChatServer.format.format(newDate));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
