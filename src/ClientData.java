import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Stack;

/**
 * Created by Nitzan on 15/05/2016.
 */
public class ClientData {
    public String nickname;
    public Date lastCheck;
    public boolean isChecking;
    public Socket socket;
    public PrintWriter out;
    public BufferedReader in;
    public int ID;
    public int numOfMessages;
    public Stack<String> messageQueue;

    public ClientData(int ID, String nickname, Socket socket) {
        this.nickname = nickname;
        this.socket = socket;
        this.ID = ID;
    }

    public ClientData(int ID, String nickname, Socket socket, BufferedReader in, PrintWriter out) {
        this.nickname = nickname;
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.ID = ID;
    }

    public void addMessage(String message) {
        messageQueue.push(message);
    }
}
