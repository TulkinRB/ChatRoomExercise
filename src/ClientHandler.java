import java.util.Date;

/**
 * Created by User on 16/05/2016.
 */
public class ClientHandler implements Runnable {
    public ClientData client;

    public ClientHandler(ClientData client) {
        this.client = client;
    }

    @Override
    public void run() {
        Date now = new Date();
        long timeDiff = now.getTime() - client.lastCheck.getTime();
        if(client.isChecking) {

        }
    }
}
