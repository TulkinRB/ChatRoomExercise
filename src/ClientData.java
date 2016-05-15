import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Nitzan on 15/05/2016.
 */
public class ClientData {
    public String name;
    public Date lastChecked;
    public boolean isChecking;
    public Socket socket;
    public PrintWriter out;
    public BufferedReader in;

    public ClientData(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public ClientData(String name, Socket socket, BufferedReader in, PrintWriter out) {
        this.name = name;
        this.socket = socket;
        this.in = in;
        this.out = out;
    }
}
