package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	private PrintStream out;
	private InputStream in;
	private String name;
	private Socket client;
	
	public Client(Socket client, String name) throws IOException {
		
	    this.out = new PrintStream(client.getOutputStream());
	    this.in = client.getInputStream();
	    this.client = client;
	    this.name = name;
	    
	}
	
	public String getName() {
	    return this.name;
	}
	
	public void shutdown() {
		try {
			in.close();
			out.close();
			client.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PrintStream getOutStream(){
	    return this.out;
	}

	public InputStream getInputStream(){
	    return this.in;
	}

}
