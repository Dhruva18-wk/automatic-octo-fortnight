package FreightServicesTest.FreightServcies;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Class to print the flight schedule on console
 * @author Dhruv
 *
 */
public class FlightScehedulePrint {
	
	
	static protected Map<String,String> updatedDestOrders   = new LinkedHashMap<>();
	int repeatIteration = 0;	
	

	
	/**
	 * Method displays filght schedule on console
	 */
	public void displayFlightSchedule() {
		
		System.out.println("============================*****");
		updatedDestOrders
		.keySet()
		.stream()
		.forEach(k->{ 
			FlightsSchedule.flightScheduleMap.entrySet()
			.stream()
			.filter(entry -> entry.getKey().contains(k))
			.forEach(kk -> {
				System.out.println("Order: "+k+", Flight"+kk.getValue()+", departure: YUL, arrival: "
						+updatedDestOrders.get(k)
						+", day:"+kk.getKey());
				System.out.println("============================*****");
					
			});
		});


	}


}
