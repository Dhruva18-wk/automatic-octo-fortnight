package FreightServicesTest.FreightServcies;

public interface FlightsToSchedule {
	void getInventoryToPrepareFlightSchedule();
	void prepareFlightSchedule(String orderKey, String date);
}
