package FreightServicesTest.FreightServcies;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class FreightServicesTest {


	public static void main( String[] args )
	{


		Scanner readConsole = new Scanner(System.in);
		System.out.println("===Input Order File Name==");

		String orderFileName = readConsole.nextLine();
		System.out.println("===Input Number of Flights to Schedule per day==");
		int numberOfFlights = Integer.valueOf(readConsole.nextLine());
		System.out.println("===Number of Orders Per Flight==");
		int orderPerFlight = Integer.valueOf(readConsole.nextLine());
		readConsole.close();

		InventoryManagement inventoryMgmt = new InventoryManagement( orderFileName );
		inventoryMgmt.updateInventory();
		FlightsSchedule flightsSchedule = new FlightsSchedule(numberOfFlights, orderPerFlight);
		flightsSchedule.getInventoryToPrepareFlightSchedule();
		FlightScehedulePrint flightScehedulePrint = new FlightScehedulePrint();
		flightScehedulePrint.displayFlightSchedule();
	}



}
