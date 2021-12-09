package cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {
	
	private Socket client;
	private String name;
	private PrintWriter out;
	private BufferedReader in;
	private BufferedReader uin;
	
	public ServerThread(Socket client, String name) {
		this.client = client;
		this.name = name;
	}
	
	public void run() {
		try {
			System.out.println("Start Running...");
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			uin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Start Running........");
			
			int count = 0;
			while (!client.isClosed()) {
				if (count <= 10) {
					count++;
				} else {
					client.close();
				}
				
				System.out.println("Enter loop");
				
				if (in.ready()) {
					String input = in.readLine();
					if (input != null) {
						System.out.println(input);
					}
				}
				
				if(uin.ready()) {
					out.print(name + ": ");
					uin.readLine();
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public String send(String userInput) {
//		
//		String response = "";
//		try {
//			response = in.readLine();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return response;
//	}
//	
	public void shutdown() {
		
		try {
			out.close();
			in.close();
			uin.close();
			client.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}