package pl.codeforfun;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/* 
 *	Class which represents departures from our parking
 */

public class Departures implements Runnable{
	Random random = new Random();
	private Parking parking;
	private int time;
	private String gateName;
	
	/*
	 *	@param parking - parking where car will park
	 *	@param time - time of delay between next departures
	 *	@param gateName - name of gate through which cars are going to departure from our parking
	 */
	public Departures(Parking parking, int time, String gateName) {
		this.parking = parking;
		this.time = time;
		this.gateName = gateName;
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()) {
		
			while(parking.freeParkingPlaces < parking.parkingCapacity){
				try{
					int timeBetweenDepartures = random.nextInt(time)+1;
					TimeUnit.SECONDS.sleep(timeBetweenDepartures);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				
				parking.unpark(gateName);
				if(parking.freeParkingPlaces>0){
					parking.availability = true;
				} else {
					parking.availability = false;
				}			
	
			}
		}
	}
}