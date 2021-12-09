package cs3800;

import java.util.Scanner;

public class ChatApp {

	private static final int PORT = 60000;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ChatClient client = new ChatClient();
		client.initialize("127.0.0.1", PORT);
		
		Scanner scan = new Scanner(System.in);
		System.out.println("--- Welcome to the Group Chat App ---");
		
		System.out.print("Enter to set your screen name: ");
		String name = scan.nextLine();
		client.setName(name);
		
		String msg = "";
		String response = "";
		
		while (!response.equals("bye")) {
			
			System.out.print(client.getName()+ ": ");
			msg = scan.nextLine();
			response = client.send(msg);
//			System.out.println(response);
			
		}
		System.out.println("You left the chat.");
		client.shutdown();
		
	}
}
