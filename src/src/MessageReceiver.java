import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.UnknownHostException;

public class MessageReceiver implements Runnable{
	
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private PrintStream userOut;
	private boolean isRunning;
	private OnlineChecker onlineChecker;
	private Thread thread;
		
	public MessageReceiver(BufferedReader socketIn, PrintWriter socketOut, PrintStream userOut) throws UnknownHostException, IOException{
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		this.userOut = userOut;
		this.isRunning = false;
		this.onlineChecker = new OnlineChecker(socketOut);
		this.thread = new Thread(this);
	}

	@Override
	public void run() {
		try {
			while (this.isRunning){
				String message = this.socketIn.readLine();
				if (message.equals(OnlineChecker.ONLINE_CHECK)){
					this.onlineChecker.response();
					continue;
				}
				if (message.equals(OnlineChecker.ONLINE_CHECKED)){
					this.socketOut.println(OnlineChecker.ONLINE_CHECKED);
					continue;
				}
				userOut.println(message);
			}
		} catch (IOException e) {
			this.stop();
		}
	}
	
	public void start(){
		this.isRunning = true;
		this.thread.start();
	}
	
	public void stop(){
		this.isRunning = false;
	}
	
	
	public void close() throws InterruptedException{
		this.stop();
		this.thread.join();
		this.onlineChecker.close();
	}
	
	
	
	
}
