package cs;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
	
	private static final int PORT = 60000;
	private ServerSocket server;
	public ArrayList<ClientThread> clients = new ArrayList<ClientThread>();

	public static void main(String[] args) {
		Server srv = new Server();
		srv.initialize(PORT);
	}

	public void initialize(int port) {
		try {
			server = new ServerSocket(port);
			
			while(true) {
				ClientThread client = new ClientThread(server.accept());
				Thread thread = new Thread(client);
				thread.start();
				clients.add(client);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
