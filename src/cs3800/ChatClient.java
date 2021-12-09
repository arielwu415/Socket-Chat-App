package cs3800;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

	private Socket connection;
	private String clientName;
	private PrintWriter out;
	private BufferedReader in;
	
	
	public void initialize(String ip, int port) {
		
		try {
			connection = new Socket(ip, port);
			connection.setKeepAlive(true);
			out = new PrintWriter(connection.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void setName(String name) {
		clientName = name;
	}
	
	public String getName() {
		return clientName;
	}
	
	
	public void shutdown() {
		try {
			in.close();
			out.close();
			connection.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String send(String msg) {
		
		out.println(msg);
		String response = "";
		try {
			response = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
}
