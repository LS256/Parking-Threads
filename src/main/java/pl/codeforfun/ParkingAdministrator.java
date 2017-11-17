package pl.codeforfun;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
 * 	Main class used to start of simulation of parking
 */
public class ParkingAdministrator {

	public static void main(String[] args) {

		Parking parking = new Parking(10);
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		es.execute(new Arrivals(parking, 30, "Gate 1"));
		es.execute(new Departures(parking, 30, "Gate 1"));
	
		es.execute(new Arrivals(parking, 30, "Gate 2"));
		es.execute(new Departures(parking, 30, "Gate 2"));
			 
		es.shutdown();

		
	}
}
