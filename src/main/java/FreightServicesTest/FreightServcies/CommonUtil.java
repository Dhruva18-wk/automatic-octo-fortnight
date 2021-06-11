package FreightServicesTest.FreightServcies;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class CommonUtil {

	private final static Logger LOGGER = Logger.getLogger(InventoryManagement.class.getName());

	CommonUtil(){
		LOGGER.setLevel(Level.SEVERE);
	}

	/**
	 * Utility Method to get todays date in format
	 * yyyy-MM-dd 
	 * @return
	 */
	public static String getTodaysDate() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String finalDate = formatDate.format(cal.getTime());
		return finalDate;		
	}

	/**
	 * Utility method to get incremented date
	 * @param numberOfDaysToIncrement
	 * @return String, returns formatted incremented date
	 */
	public static String getIncrementedDate(int numberOfDaysToIncrement) {
		if ( numberOfDaysToIncrement==0 )
			return getTodaysDate();
		Date today = new Date();               
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");            
		Calendar c = Calendar.getInstance();        
		c.add(Calendar.DATE, numberOfDaysToIncrement);  // number of days to add      
		String nextDate = (String)(formattedDate.format(c.getTime()));
		LOGGER.config("===current date=="+nextDate);
		return nextDate;
	}
	/**
	 * Utility method to convert json to map
	 * @param filePath, file path as input
	 * @return
	 */
	public static Map<String, String> convertJSONToMap( String filePath ) {
		Map<String,String> inputOrderMap  = new HashMap<>();
		JsonParser jsonParser    = new JsonParser();
		JsonElement jsonElement = null;
		try{
			FileReader fileReader = new FileReader(filePath);
			jsonElement = jsonParser.parse(fileReader);
		}catch(FileNotFoundException e){
			LOGGER.severe("==FILE NOT FOUND==");
			return null;
		}
		Gson g = new Gson();
		Type mapType = new TypeToken<Map<String,Object>>(){}.getType();
		inputOrderMap = g.fromJson(jsonElement,mapType);
		LOGGER.config("===INPUTORDERMAP=="+inputOrderMap);
		return inputOrderMap;

	}

	/**
	 * Utility method to read json
	 * @param filePath, file path as input
	 * @return
	 */
	public  JsonElement readJson(String filePath) {
		JsonParser jsonParser    = new JsonParser();
		JsonElement jsonElement = null;
		try{
			FileReader fileReader = new FileReader(filePath);
			jsonElement = jsonParser.parse(fileReader);
		}catch(FileNotFoundException e){
			LOGGER.severe("==FILE NOT FOUND==");
		}
		return jsonElement;
	}

}
