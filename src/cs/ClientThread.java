package cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Server implements Runnable {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			System.out.println("Client connected.");
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String txt = "";

			while (true) {
				txt = in.readLine();

				if (txt == null || txt.equals("bye")) {
					out.println("bye");
					break;
				}
				
				System.out.println("Message from client: " + txt);
				for (ClientThread client : clients) {
					client.out.write("Message received: " + txt);
				}
				out.println("Message received: " + txt);
			}
			
			in.close();
			out.close();
			socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	
}