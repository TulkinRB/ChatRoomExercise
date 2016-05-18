import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * The main client-side program.
 */
public class ChatClient {
	
	public static final DateFormat HANDSHAKE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	public static final String HANDSHAKE_OK = "AFFIRMATIVE";
	public static final String HANDSHAKE_BAD = "NEGATIVE";
	
	public static void main(String args[]){
		try {
			Scanner userIn = new Scanner(System.in);
			String[] details = inputConnectionDetails(userIn, System.out);
			Socket socket = new Socket(details[0], 6655);
			ChatClient client = new ChatClient(socket, userIn, System.out);
			client.startConnection(details[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private Socket socket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private Scanner userIn;
	private PrintStream userOut;
	private int clientID;
	private MessageSender sender;
	private MessageReceiver receiver;

	
	public ChatClient(Socket socket, Scanner userIn, PrintStream userOut) throws IOException{
		this.userIn = userIn;
		this.userOut = userOut;
		this.socket = socket;
		this.socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.socketOut = new PrintWriter(socket.getOutputStream());
	}
	
	public boolean startConnection(String nickname){
		if (!this.handshake()){
			return false;
		}
		try {
			this.clientID = Integer.parseInt(this.socketIn.readLine());
			this.sender = new MessageSender(this.userIn, this.socketOut);
			this.receiver = new MessageReceiver(this.socketIn, this.socketOut, this.userOut);
			this.sender.start();
			this.receiver.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean monitorConnection(){
		return false;
		// todo
	}


	/**
     * Perform a handshake between the client and the server.
     * @param in The server input.
     * @param out The server output.
     * @return true if the handshake has succeeded, false if it has failed.
     */
	public boolean handshake(){
		try {
			Date serverDate = HANDSHAKE_FORMAT.parse(this.socketIn.readLine());
			long oldTime = serverDate.getTime();
			long newTime = oldTime - 1000*60*60*24-60*60;
			Date newDate = new Date(newTime);
			this.socketOut.println(HANDSHAKE_FORMAT.format(newDate));
        		return this.socketIn.readLine().equals(HANDSHAKE_OK);
		} catch (Exception e) {
			return false;
		}
	}
	
	
    /**
     * Get host address and nickname from user input.
     * @param in The input scanner.
     * @return The user specified host and nickname.
     */
	public static String[] inputConnectionDetails(Scanner userIn, PrintStream userOut){
		String[] returned = new String[2];
		userOut.print("Enter the server address: ");
		returned[0] = userIn.nextLine();
		System.out.print("Enter your nickname: ");
		returned[1] = userIn.nextLine();
		return returned;
	}
	
}