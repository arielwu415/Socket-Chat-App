package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

	private static final int PORT = 60000;
	private ServerSocket server;
	private List<Client> clients;
	
	public static void main(String[] args) {
		new Server(PORT).initialize();
	}
	
	public Server(int port) {
		this.clients = new ArrayList<Client>();
	}
	
	
	public void initialize() {
		
		try {
			server = new ServerSocket(PORT) {
				protected void finalize() throws IOException {
					this.close();
			    }
			};
			System.out.println("Server: PORT " + PORT + " started.");
			
			System.out.println("Waiting for clients...");
			acceptClients();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void acceptClients() {
		
		try {
			while (true) {
				Socket socket = server.accept();
				Scanner in = new Scanner(socket.getInputStream());
				PrintStream out = new PrintStream(socket.getOutputStream());
				String name = in.nextLine();
				
				while(isNameTaken(name)) {
					System.out.println("Reject the screen name.");
					out.println("Rejected");
					name = in.nextLine();
				}
				
				Client client = new Client(socket, name);
				System.out.println(name + " connected.");
				out.println("Accepted");
				clients.add(client);

				new Thread(new ServerClientHandler(this, client)).start();
					
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean isNameTaken(String name) {
		
		for (Client client : this.clients) {
			if (client.getName().equals(name)) {
				return true;
			}
		}
		return false;
		
	}
	
	public void broadcastMessages(String msg, Client sender) {
		for (Client client : this.clients) {
			client.getOutStream().println(sender.getName()+ ": " + msg);
		}
	}
	
	public void listAllUsers(Client thisClient){
	    	List<String> name = getClientNames(clients);
	    	thisClient.getOutStream().println(name);
	  }

	public List<String> getClientNames(List<Client> clients){
		List<String> name = new ArrayList<String>();
		for (Client c: clients) {
			name.add(c.getName());
		}
		return name;
	}
	
	public void sendPrivateMsg(String msg, Client sender, String name){
	    boolean find = false;
	    for (Client client : clients) {
	    	if (client != sender && client.getName().equals(name)) {
	    		find = true;
	    		sender.getOutStream().println(sender.getName() + " to " + client.getName() +":" + msg);
	    		client.getOutStream().println(
	    				"<Private> " + sender.getName() + ":" + msg);
	    	}
	    }
	    if (!find) {
	    	sender.getOutStream().println("No user found.");
	    }
	  }
	
	public List<Client> getClients(){
		return this.clients;
	}
	
	public void removeClient(Client user){
	    this.clients.remove(user);
	}
}

class ServerClientHandler implements Runnable {
	
	private Server server;
	private Client client;
	
	public ServerClientHandler(Server server, Client client) {
		this.server = server;
		this.client = client;
	}

	@Override
	public void run() {
		Scanner scan = new Scanner(client.getInputStream());
		String msg = "";
		
		while (scan.hasNextLine() && !msg.equals("bye")) {
			msg = scan.nextLine();

			if (msg.charAt(0) == '<' && msg.indexOf(">") != -1) {
				int endOfCmd = msg.indexOf(">");
				String cmd = msg.substring(1, endOfCmd);
				String p = null;
				String n = null;
				
				int indexOfBar = cmd.indexOf("|");
				if (indexOfBar >= 0) {
					p = cmd.substring(0, indexOfBar);
					n = cmd.substring(indexOfBar + 1, cmd.length());
				}

				if (cmd.equals("user")) {
					server.listAllUsers(client);
				}
				else if (cmd.equals("bye")) {
					System.out.println(client.getName() + " left the chat.");
					client.getOutStream().println(cmd);
				}
				else if (p.equals("private")) {
					String userName = n;
					server.sendPrivateMsg(msg.substring(endOfCmd + 1), client, userName);
				}
				
			} else {
				server.broadcastMessages(msg, client);
			}
						
		}
		
		server.removeClient(this.client);
		scan.close();
		
	}
}

