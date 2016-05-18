import java.io.PrintWriter;

public class OnlineChecker implements Runnable {
	
	public static final String ONLINE_CHECKED = "ACK"; // server uses this to check if the client is online
	public static final String ONLINE_CHECK = "CLACK"; // to check if the server is online
	
	private PrintWriter socketOut;
	private boolean gotResponse;
	private boolean isRunning;
	private int interval;
	private int resLimit;
	private Thread thread;
	
	public OnlineChecker(PrintWriter socketOut){
		this(socketOut, 10, 1);
	}
	public OnlineChecker(PrintWriter socketOut, int checkInterval, int responseLimit){
		this.socketOut = socketOut;
		this.isRunning = true;
		this.gotResponse = false;
		this.interval = checkInterval;
		this.resLimit = responseLimit;
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {
		while (this.isRunning){
			this.gotResponse = false;
			this.socketOut.println(ONLINE_CHECK);
			try {
				Thread.sleep(this.resLimit * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!this.gotResponse){
				this.stop();
				break;
			}
			try {
				Thread.sleep((this.interval - this.resLimit) * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void response(){
		this.gotResponse = true;
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
	}

}
