package pl.codeforfun;

import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Arrivals implements Runnable{
	private Parking parking;
	private int time;
	private String gateName;
	Random random = new Random();
	
	public Arrivals(Parking parking, int time, String gateName) {
		this.parking = parking;
		this.time = time;
		this.gateName = gateName;
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()) {		
			while(parking.freeParkingPlaces <= parking.parkingCapacity){
				try{
					int timeBetweenArrivals = random.nextInt(time)+1; // bylo 10
					TimeUnit.SECONDS.sleep(timeBetweenArrivals);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
					
					parking.park(gateName, new Timestamp(System.currentTimeMillis()));		
			}
		}
	}
}
