package main;

import java.io.IOException;

import net.ConnectionListener;
import net.DataSender;
import data.TrafficGenerator;

public class Server {
	
	public static void main(String[] args) throws IOException {
		Integer port = Integer.parseInt(args[0]);
		Integer senders = Integer.parseInt(args[1]);
		Integer bitrate = Integer.parseInt(args[2]);
		
		DataSender dataSender = new DataSender(senders);
		TrafficGenerator trafficGenerator = new TrafficGenerator(dataSender);
		ConnectionListener connectionListener = new ConnectionListener(port, dataSender);
		
		connectionListener.startListener();
		trafficGenerator.startTraffic(bitrate);
	}

}
