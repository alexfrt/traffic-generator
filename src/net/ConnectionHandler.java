package net;

import java.net.Socket;

public interface ConnectionHandler {
	
	void newConnection(Socket socket);

}
