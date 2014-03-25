package main;

import java.io.InputStream;
import java.net.Socket;

public class Client {
	
	@SuppressWarnings("resource")
	public static void main(final String[] args) throws Exception {
		Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
		
		InputStream inputStream = socket.getInputStream();
		byte[] trash = new byte[1024];
		
		Thread.sleep(2000);
		
		while (!Thread.interrupted()) {
			Long bytesRead = 0l;
			
			Long startTime = System.currentTimeMillis();
			Long elapsedTime = 0l;
			
			do {
				elapsedTime = System.currentTimeMillis() - startTime;
				bytesRead += inputStream.read(trash);
			} while (elapsedTime < 1000);
			
			Float bitsPerSecond = (bytesRead * 8) / (elapsedTime / 1000f);
			
			System.out.println(Math.round(bitsPerSecond / 1024));
		}
	}

}
