package cs3800;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GroupChatServer {
	
	private static final int PORT = 60000;
	private ServerSocket server;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GroupChatServer srv = new GroupChatServer();
		srv.initialize(PORT);
	}

	public void initialize(int port) {
		try {
			server = new ServerSocket(port);
			
			while(true) {
				new SingleChatServer(server.accept()).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void acceptClients() {
		
		while(true) {
			
		}
		
	}
	
	private class SingleChatServer extends Thread {
		private Socket client;
		private PrintWriter out;
		private BufferedReader in;

		public SingleChatServer(Socket client) {
			this.client = client;
		}
		
		public void run() {
			try {
				System.out.println("Client connected.");
				out = new PrintWriter(client.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String text = "";

				while (true) {
					text = in.readLine();

					if (text == null || text.equals("bye")) {
						out.println("bye");
						break;
					}
					
					System.out.println("Message from client: " + text);
					out.println("Message received: " + text);
				}
				
				in.close();
				out.close();
				client.close();
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
}
