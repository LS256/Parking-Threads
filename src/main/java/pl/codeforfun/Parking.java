package pl.codeforfun;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Parking{
	/* Information about parked cars will be stored in ArrayList */
	List<Car> parkedCars = new ArrayList<Car>();
	int parkingCapacity;
	int freeParkingPlaces;
	boolean availability;
	Random random = new Random();
	private static final Logger logger = LogManager.getLogger(Parking.class);
	
	/* 
	 * Default constructor
	 */
	Parking() {
		
	}
	
	/*
	 * Constructor for parking initialization
	 * @param parkingCapacity - parameter to define how many parking places should have our parking
	 * @param freeParkingPlaces - presents status of free parking places
	 * @param availability - if there is no free places on parking then availability is false, otherwise true
	 */
	Parking(int parkingCapacity) {
		this.parkingCapacity = parkingCapacity;
		this.freeParkingPlaces = parkingCapacity;
		availability = true;

		logger.info("Parking with {} parking places was created.", this.parkingCapacity);
	}
	

	/*
	 *	To avoid variable names conflict, method park is designated for arrivals 
	 *	@param gateName - because our parking can have few access gates this parametr describe name used gate
	 */
	public synchronized void park(String gateName, Timestamp timeStamp) {
		while(!availability) {			
			try{
				logger.info("Awaiting for free parking place");
				wait();
			} catch(InterruptedException ie){
				ie.printStackTrace();
			}		
		}
			freeParkingPlaces--;
			parkedCars.add(new Car(new Timestamp(System.currentTimeMillis())));
			if(freeParkingPlaces > 0) {
				availability = true;
			} else {
				availability = false;
			} 
			notifyAll();
				Thread.currentThread().setName(gateName);
				logger.info("Car arrival through {}. Left: {} free parking places", Thread.currentThread().getName(), freeParkingPlaces);

	}	

	
	/*
	 * To avoid variable names conflict method unPark is designated for departures
	 *	@param gateName - because our parking can have few access gates this parametr describe name used gate
	 */
	public synchronized void unpark(String gateName) {
		
		while(parkingCapacity<=freeParkingPlaces) {
			try{
				logger.info("Parking is empty - no cars");
				wait();
			} catch(InterruptedException ie) {
				logger.error("Error caused by no cars on parking {}", ie);
			}
			notifyAll();	
		}
		
		if(!availability) {
			availability = true;
			notifyAll();
		}
		
		freeParkingPlaces++;
		int idOfParkedCar = random.nextInt(parkedCars.size());
		Car car = parkedCars.get(idOfParkedCar);
		parkedCars.remove(idOfParkedCar);
		String parkTime = car.parkTime();
		Thread.currentThread().setName(gateName);
		logger.info("Car departure through {} - it's parking time was {}.", Thread.currentThread().getName(), parkTime);
		logger.info("Left: {} free parking places.", freeParkingPlaces);
	}
	
	
	
	
	

}
