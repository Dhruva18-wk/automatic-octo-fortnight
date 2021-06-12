package FreightServicesTest.FreightServcies;

/**
 * Interface to scheduling flight
 * @author Dhruv
 *
 */
public interface FlightsToSchedule {
	
	void getInventoryToPrepareFlightSchedule();
	void prepareFlightSchedule(String orderKey, String date);
}
