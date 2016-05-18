import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MessageSender implements Runnable {

	private Scanner userIn;
	private PrintWriter socketOut;
	private boolean isRunning;
	private Thread thread;
	
	public MessageSender(Scanner userIn, PrintWriter socketOut) {
		this.userIn = userIn;
		this.socketOut = socketOut;
		this.isRunning = false;;
		this.thread = new Thread(this);
	}
	
	@Override
	public void run() {
		while (this.isRunning){
			String message = this.userIn.nextLine();
			// todo: add -q closes client
			this.socketOut.println(message);
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
	}

}
