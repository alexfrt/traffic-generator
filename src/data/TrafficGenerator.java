package data;

import java.util.Random;

import net.DataSender;

public class TrafficGenerator {
	
	private static final int SLEEP_TIME = 100;
	
	private DataSender dataSender;
	private Thread thread;
	private Boolean isGeneratingTraffic;
	
	public TrafficGenerator(DataSender dataSender) {
		this.dataSender = dataSender;
		this.isGeneratingTraffic = false;
	}
	
	public void startTraffic(final Integer bitRate) {
		if (isGeneratingTraffic) {
			throw new IllegalStateException("Already generating traffic");
		}
		
		final Integer burstsPerSecond = 1000 / SLEEP_TIME; //one second has 1000 ms
		final Integer blockLength = (bitRate * 1024 / 8) / burstsPerSecond; //one byte has 8 bits and 1kb has 1024 bits
		
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Random random = new Random();
				
				while (!Thread.interrupted()) {
					byte[] data = new byte[blockLength];
					random.nextBytes(data);
					
					dataSender.sendData(data);
					
					sleep();
				}
			}
		}, getClass().getSimpleName());
		
		isGeneratingTraffic = true;
		thread.start();
	}
	
	public void stopTraffic() {
		isGeneratingTraffic = false;
		thread.interrupt();
	}
	
	private static void sleep() {
		try {
			Thread.sleep(SLEEP_TIME);
		} catch (Exception e) {}
	}
	
}
