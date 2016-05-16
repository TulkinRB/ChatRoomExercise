import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Nitzan on 15/05/2016.
 */
public class ClientData {
    public String nickname;
    public Date lastChecked;
    public boolean isChecking;
    public Socket socket;
    public PrintWriter out;
    public BufferedReader in;
    public int ID;

    public ClientData(String nickname, Socket socket) {
        this.nickname = nickname;
        this.socket = socket;
    }

    public ClientData(String nickname, Socket socket, BufferedReader in, PrintWriter out) {
        this.nickname = nickname;
        this.socket = socket;
        this.in = in;
        this.out = out;
    }
}
