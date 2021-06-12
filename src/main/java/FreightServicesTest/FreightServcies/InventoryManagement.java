/**
 * 
 */
package FreightServicesTest.FreightServcies;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;




/**
 * Class for managing inventory of orders
 * 
 * @author Dhruv
 *
 */
public class InventoryManagement implements FreightServicesInventory {

	private final static Logger LOGGER = Logger.getLogger(InventoryManagement.class.getName());
	static protected Map<String,Integer> inventoryOrders  = new HashMap<>();
	static protected Map<String,String> DestOrders   = new LinkedHashMap<>();
	static protected Map<String,String> inputOrderMap  = new HashMap<>();
	static  Map<String,Integer> orderMap  = new HashMap<>();
	static  Map<String,String> cityCode  = new HashMap<>();
	private String orderFilePath = "";




	public InventoryManagement(String orderFile) {

		cityCode = CommonUtil.convertJSONToMap("CityCode.json");	
		cityCode.keySet()
		.stream()
		.forEach( k -> orderMap.put(k.toString(), 0));

		this.orderFilePath = orderFile;
		LOGGER.setLevel(Level.INFO);

	}

	/**
	 * Method to update inventory based on orders
	 */
	@Override
	public void updateInventory() {
		//Read file json and conver to map

		//destination/numberofORders
		try {
			getOrderMap();
			inputOrderMap.entrySet()
			.stream()
			.map(entry -> entry + "=" +entry.getKey())
			.forEach(entry ->{
				String destN = entry;
				LOGGER.config("destn**"+destN);
				String orderNumber = destN.split("=")[0];

				if(destN.contains("=")) {
					destN = destN.split("=")[2];
					LOGGER.config("destn**"+destN);
					destN = destN.split("}")[0];
					LOGGER.config("destn**"+destN);

				}
				if ( cityCode.get(destN) != null) {
					if( inventoryOrders.get(destN) != null)
						inventoryOrders.put(destN, inventoryOrders.get(destN)+1);

					else
						inventoryOrders.put(destN,1);

					DestOrders.put(orderNumber,destN);
					LOGGER.config("desDestOrderstn**"+inventoryOrders.get(destN));
					LOGGER.config("inventoryOrders**"+inventoryOrders);



				}

			});

			LOGGER.config("===inventoryOrders=="+inventoryOrders);
		}
		catch(Exception e) {
			System.out.println("===There was error while udpating inventory please check..."+ExceptionUtils.getStackTrace(e));
		}


	}

	/**
	 * Method to get order from the user
	 */
	@Override
	public  void getOrderMap() {
		//Read Json
		// COnver to MAP
		inputOrderMap = CommonUtil.convertJSONToMap( orderFilePath );
	}
}
