package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener {
	
	private ServerSocket serverSocket;
	private ConnectionHandler handler;
	private Thread thread;
	private Boolean isListening;
	
	public ConnectionListener(Integer port, ConnectionHandler handler) throws IOException {
		this.handler = handler;
		
		serverSocket = new ServerSocket(port);
		isListening = false;
	}
	
	public void startListener() {
		if (isListening) {
			throw new IllegalStateException("Already listening connections");
		}
		
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					try {
						Socket client = serverSocket.accept();
						handler.newConnection(client);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, getClass().getSimpleName());
		
		isListening = true;
		thread.start();
	}
	
	public void stopListener() {
		isListening = false;
		thread.interrupt();
	}
	
}
