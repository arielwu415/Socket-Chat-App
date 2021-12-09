package cs;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatApp {

	private static final int PORT = 60000;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		System.out.println("--- Welcome to the Group Chat App ---");
		
		System.out.print("Enter to set your screen name: ");
		String name = scan.nextLine();
		scan.close();
		
		Socket connection;
		try {
			connection = new Socket("127.0.0.1", PORT);
			connection.setKeepAlive(true);
			
			ServerThread client = new ServerThread(connection, name);
			Thread thread = new Thread(client);
			thread.start();
			
			System.out.println("You left the chat.");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
	}
}
