package FreightServicesTest.FreightServcies;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class schedules flight with given inventory data
 * @author Dhruv
 *
 */
public class FlightsSchedule implements FlightsToSchedule {

	private final static Logger LOGGER = Logger.getLogger(InventoryManagement.class.getName());
	private static int numberOfFlightsToSchedule = 0;
	private static int numberOfFlights = 0;
	private static int numberOfOrdersPerFlight = 0;
	static int numberOFDateToIncrement = 0;
	static StringBuilder ordersToAppend = new StringBuilder();;
	int i =0;
	String orderNumber ="";
	
	public FlightsSchedule(int numberOfFlights, int numberOfOrders){
		numberOfOrdersPerFlight = numberOfOrders;
		numberOfFlightsToSchedule = numberOfFlights;
		LOGGER.setLevel(Level.INFO);
	}

	static Map<String, String> flightScheduleMap = new LinkedHashMap<>();



/**
 * Method to get inventory to schedule flights
 */
	public void getInventoryToPrepareFlightSchedule() {

		InventoryManagement.cityCode.keySet()
		.stream()
		.filter(e -> 
		InventoryManagement.inventoryOrders.get(e)!=null && InventoryManagement.inventoryOrders.get(e)>0)
		.forEach(k ->{
			prepareFlightSchedule( k, CommonUtil.getIncrementedDate( numberOFDateToIncrement ));
		});
	}

	/**
	 * Method to prepare flight schedule based on order key and date
	 */
	@Override
	public void prepareFlightSchedule(String orderKey, String date) {

		InventoryManagement.orderMap.keySet()
		.stream()
		.filter(k-> k.toString()==orderKey)
		.forEach(k->{
			numberOfFlights++;
			if ( InventoryManagement.inventoryOrders.get(k) >= numberOfOrdersPerFlight) {
				InventoryManagement.inventoryOrders.put(k, InventoryManagement.inventoryOrders.get(k)-numberOfOrdersPerFlight);
				String tmpOrderNumber = getOrderNumber(k,20);
				orderNumber = orderNumber +" "+tmpOrderNumber;
				tmpOrderNumber = tmpOrderNumber.replace("Orders:::", "");
				tmpOrderNumber = tmpOrderNumber.replace(":::Orders", "");
				String[] orderNumbers = tmpOrderNumber.split(",");
				LOGGER.config("====THESE orderNumbers**"+Arrays.toString(orderNumbers));

				for (String orderNumber: orderNumbers) {
					InventoryManagement.DestOrders.remove(orderNumber);
					FlightScehedulePrint.updatedDestOrders.put(orderNumber,k);

				}
				FlightsSchedule.flightScheduleMap.put(date+" "+tmpOrderNumber,k+"="+numberOfFlights);
				LOGGER.config("====flightScheduleMap::"+FlightsSchedule.flightScheduleMap);


			}
			else if ( Integer.valueOf(InventoryManagement.inventoryOrders.get(k)) > 0) {
				String tmpOrderNumber = getOrderNumber(k,InventoryManagement.inventoryOrders.get(k));
				orderNumber = orderNumber +" "+tmpOrderNumber;
				InventoryManagement.inventoryOrders.put(k, 0);
				tmpOrderNumber = tmpOrderNumber.replace("Orders:::", "");
				tmpOrderNumber = tmpOrderNumber.replace(":::Orders", "");
				String[] orderNumbers = tmpOrderNumber.split(",");

				for (String orderNumber: orderNumbers) {
					FlightScehedulePrint.updatedDestOrders.put(orderNumber,k);
					InventoryManagement.DestOrders.remove(orderNumber);

				}
				FlightsSchedule.flightScheduleMap.put(date+" "+tmpOrderNumber,k+"="+numberOfFlights);
				LOGGER.config("====flightScheduleMap::"+FlightsSchedule.flightScheduleMap);


			}
			if ( numberOfFlights == numberOfFlightsToSchedule) {
				numberOfFlights=0;
				ordersToAppend= new StringBuilder();
				numberOFDateToIncrement++;
				orderNumber="";
			}



		});
		if ( InventoryManagement.inventoryOrders.get(orderKey) > 0) {
			prepareFlightSchedule( orderKey,  CommonUtil.getIncrementedDate( numberOFDateToIncrement ));
		}
		LOGGER.info("====flightScheduleMap::"+FlightsSchedule.flightScheduleMap);


	}
	
	private String getOrderNumber(String key, int ordersToCount) {
		String orderNumber = InventoryManagement.DestOrders
							.entrySet()
							.stream()
							.filter(entry-> entry.getValue().equals(key))
							.limit(ordersToCount)
							.map(entry-> new String(entry.getKey()))
							.collect(Collectors.joining(",", "Orders:::", ":::Orders"));
		LOGGER.config("====THESE ARE ORDER NUMBERS**"+orderNumber);
		return orderNumber;						

	}

}
