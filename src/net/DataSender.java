package net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataSender implements ConnectionHandler {

	private ExecutorService executorService;
	private ConcurrentLinkedQueue<Socket> socketsQueue;
	
	public DataSender(Integer workersCount) {
		this.executorService = Executors.newFixedThreadPool(workersCount);
		this.socketsQueue = new ConcurrentLinkedQueue<>();
	}
	
	@Override
	public void newConnection(Socket socket) {
		socketsQueue.add(socket);
		System.out.println("Socket added to queue. Address: " + socket.getRemoteSocketAddress());
	}
	
	public void sendData(final byte[] data) {
		for (final Socket socket : socketsQueue) {
			
			if (socket.isConnected() && !socket.isOutputShutdown()) {
				executorService.execute(new Runnable() {
					
					@Override
					public void run() {
						try {
							OutputStream outputStream = socket.getOutputStream();
							outputStream.write(data);
							outputStream.flush();
							
						} catch (IOException e) {
							System.err.println("Error while trying to send data to client " + socket.getRemoteSocketAddress());
							socketsQueue.remove(socket);
						}
					}
				});
			}
			else {
				System.err.println("Invalid socket found, removing it from the queue");
				socketsQueue.remove(socket);
			}
		}
	}

}
