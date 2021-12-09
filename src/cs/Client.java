package cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	private Socket connection;
	private String name;
	private PrintWriter out;
	private BufferedReader in;

	public Client(String name) {
		this.name = name;
	}
	
	public void initialize(String ip, int port) {
		
		try {
			connection = new Socket(ip, port);
			connection.setKeepAlive(true);
			out = new PrintWriter(connection.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			ServerThread client = new ServerThread(connection, name);
			Thread thread = new Thread(client);
			thread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Socket getSocket() {
		return connection;
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
