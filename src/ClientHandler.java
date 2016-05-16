import java.io.IOException;
import java.util.Date;

/**
 * Runnable that adds messages the client sends to its receiveQueue automatically, and closes it when an IOException occurs.
 */
public class ClientHandler implements Runnable {
    public ClientData client;
    public ChatServer server;

    public ClientHandler(ClientData client, ChatServer server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String clientInput = client.in.readLine();
                client.addReceiveMessage(clientInput);
            }
        } catch (IOException e) {
            closeClient();
        }
    }

    private void closeClient() {
        client.out.close();
        try {
            client.in.close();
        } catch (IOException ee) {
            ee.printStackTrace();
        }
        server.removeClient(client);
    }
}
