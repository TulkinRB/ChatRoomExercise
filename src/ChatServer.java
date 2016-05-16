import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Timer;

/**
 * Created by User on 06/04/2016.
 */
public class ChatServer {
    private ServerSocket serverSocket;
    private Timer timeOut;
    private HashSet<ClientData> clients;
    private int nextID;

    public static final DateFormat HANDSHAKE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public static final String HANDSHAKE_OK = "AFFIRMATIVE";
    public static final String HANDSHAKE_BAD = "NEGATIVE";
    public static final String CLIENT_ONLINE_CHECK = "ACK"; // to check if the client is online
    public static final String SERVER_ONLINE_CHECK = "CLACK"; // client uses this to chek if the server is online

    public ChatServer() {
        timeOut = new Timer();
    }

    public static void main(String[] args) {
        ChatServer s = new ChatServer();

    }

    public void HandleNewClient() {
        try {
            Socket client = serverSocket.accept();
            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            if(handshake(in, out)) {
                String nick = in.readLine();
                acceptClient(nick, client, in, out);
            }

        } catch (IOException e) {
        }
    }

    /**
     * Sends a message to every client in the server except the one with exceptionID.
     * @param message The message to send.
     * @param exceptionID The client not to send to. -1 to send to all clients.
     */
    public void SendMessage(String message, int exceptionID) {
        for (ClientData client : clients) {
            if(client.ID != exceptionID) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientData client) {
        clients.remove(client);
    }

    public boolean handshake(BufferedReader in, PrintWriter out) throws IOException {
        try {
            Date now = new Date();
            out.println(HANDSHAKE_FORMAT.format(now));
            Date newDate = HANDSHAKE_FORMAT.parse(in.readLine());
            if(now.getTime() - newDate.getTime() == 1000*60*60*24+60*60) {
                out.println(HANDSHAKE_OK);
                return true;
            }
            else {
                out.println(HANDSHAKE_BAD);
                return false;
            }
        }
        catch (ParseException e) {
            out.println(HANDSHAKE_BAD);
            return false;
        }
    }

    public void acceptClient(String nickname, Socket client, BufferedReader in, PrintWriter out) {
        ClientData newClient = new ClientData(nextID++, nickname, client, in, out);
        clients.add(newClient);
        Runnable aliveCheck = new ClientHandler(newClient, this);
        Thread t = new Thread(aliveCheck);
        t.start();
    }


}
