package pl.codeforfun;

import java.sql.Timestamp;

public class Car {
//	private int id;
	private Timestamp timeStamp;
	
	Car() {
		
	}

	Car(Timestamp timeStamp) {
//		this.id = id;
		this.timeStamp = timeStamp;
	}
	
	/*
	 * @return 
	 */
	String parkTime() {
		long parkTime = 1+((new Timestamp(System.currentTimeMillis()).getTime()) - this.timeStamp.getTime())/1000;
		if(parkTime>1) {
			return parkTime + " seconds";
		} else {
			return  parkTime + " second";
		}
	}
}
