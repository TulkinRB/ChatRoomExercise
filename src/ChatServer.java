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

    public static final DateFormat HANDSHAKE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public static final String HANDSHAKE_OK = "AFFIRMATIVE";
    public static final String HANDSHAKE_BAD = "NEGATIVE";

    public ChatServer() {
        timeOut = new Timer();
    }

    public void HandleClient() {
        try {
            Socket client = serverSocket.accept();
            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            handshake(in, out);
        } catch (IOException e) {
        }
    }

    public boolean handshake(BufferedReader in, PrintWriter out) throws IOException {
        try {
            Date now = new Date();
            out.println(HANDSHAKE_FORMAT.format(now));
            Date newDate = HANDSHAKE_FORMAT.parse(in.readLine());
            if(now.getTime() - newDate.getTime() == 1000*60*60*24) {
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

    public void acceptClient(String name, Socket client, BufferedReader in, PrintWriter out) {
        ClientData newClient = new ClientData(name, client, in, out);
        clients.add(newClient);
    }
}
