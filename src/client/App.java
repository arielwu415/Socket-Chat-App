package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class App {

	private static final int PORT = 60000;
	private static final String host = "127.0.0.1";
	protected Socket connection;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
	    App app = new App();
	    app.initialize(host, PORT);
	}
	
	@SuppressWarnings("deprecation")
	public void initialize(String ip, int port) {
		try {
			connection = new Socket(ip, port);
			PrintStream out = new PrintStream(connection.getOutputStream());
			Scanner in = new Scanner(connection.getInputStream());
			
			System.out.println("\n----- Welcome to the Group Chat App -----\n");
			System.out.println(" > Command instruction: \n"
					+ "\t<user>: print list of current connected users.\n"
					+ "\t<private|user> message: send 'message' only to the user specified.\n"
					+ "\t<bye>: leave chat.\n");
			
			Scanner scan = new Scanner(System.in);
			
			
			String response = "";
			while (!response.equals("Accepted")) {
				System.out.print("Enter to set your user name: ");
				String name = scan.nextLine();
				// send user name to server
				out.println(name);
				response = in.nextLine();
				System.out.println(response);
			}
			
			Thread th = new Thread(new ReceiveServerMsg(connection.getInputStream()));
			th.start();
		    
			System.out.println("You can now start typing");
		    String msg = "";
		    while (scan.hasNextLine()) {
		    	msg = scan.nextLine();
		        out.println(msg);

		        if (msg.equals("<bye>")) {
		        	break;
		        }
		        
		    }
		    scan.close();
		    out.close();
		    connection.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

class ReceiveServerMsg implements Runnable {
	
	private InputStream serverIn;
	
	public ReceiveServerMsg(InputStream serverIn) {
		this.serverIn = serverIn;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(serverIn);
		String msg = "";
		while (!msg.equals("bye") && scan.hasNextLine()) {
			msg = scan.nextLine();
			System.out.println("> " + msg);
		}
		
		scan.close();
		
	}
	
}
