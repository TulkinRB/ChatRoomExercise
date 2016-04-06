import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Timer;

/**
 * Created by User on 06/04/2016.
 */
public class ChatServer {
    private ServerSocket serverSocket;
    private Timer timeOut;
    private int numOfClients;

    public ChatServer() {
        timeOut = new Timer();
    }

    public void HandleClient() {
        try (
                Socket client = serverSocket.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {
            Date now = Date.from(Instant.now());
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            out.println(format.format(now));

        } catch (IOException e) {

        }
    }
}
