import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by User on 06/04/2016.
 */
public class ChatClient {
	
	public static void main(String args[]) throws UnknownHostException, IOException{
		Scanner in = new Scanner(System.in);
		String host = getHost(in);
		String nickname = getNickname(in);
		Socket server = new Socket(host, 6655);
		OutputStream serverOut = server.getOutputStream();
		InputStream serverIn = server.getInputStream();
		
	}
	
	public static String getHost(Scanner in){
		return "loopback"; //todo: user input
	}
	
	public static String getNickname(Scanner in){
		System.out.print("Enter your nickname: ");
		return in.nextLine();
	}
	
	public static boolean handshake(InputStream in, OutputStream out){
		return true;
	}
}
