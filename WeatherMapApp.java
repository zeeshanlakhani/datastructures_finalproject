import java.io.*;
import java.util.*;
import java.net.*;

public class WeatherMapApp {
	static List<String> testlist = new ArrayList<String>();
	static String time = null;
	//declare a static map object, weather_map
	static Map<String,Integer> weather_map = new HashMap<String,Integer>();
	//key set of String objects, entry set of Integer objects
	
	public static void main (String[] args) {
		String base = "http://iwin.nws.noaa.gov/iwin/nj/hourly.html";
		URL u = null;
		URLConnection conn = null;
		Scanner in = null;
		try 
		{
			u = new URL(base);
			conn = u.openConnection();
			in = new Scanner(conn.getInputStream());
			parsePage(in);
			fillMap(testlist);
			in.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
			
		}
		System.out.println("Zeeshan Lakhani LAB 5 CS 209 FAll 2010");
		System.out.println(time);
		//Note: this console output is only the testing code
		/*for (String s:testlist)
			System.out.println(s);*/
			
		//Set<String> cities = weather_map.keySet();
		//Set<String> cities = weather_map.entrySet();
		for (Map.Entry<String, Integer> loc : weather_map.entrySet())
			System.out.println("City:" + loc.getKey() + " Temp:" + loc.getValue());
		
		try {
	    	fileOutput();
      		} 
		catch (IOException e) {
          	System.out.println("Output problem.");
 			}
	} // end main
	
	//This function iterates over the web page, looks for --> National Weather Service, gets it's time (if null), and then finds lines under City that don't start with $$
	//and a space. The lines must be longer than 30 in length. This line is then added to the testlist (when it finds something that contains $$ and a space or less than 30 in
	//length, the while look breaks.
	static void parsePage(Scanner in) { //get weather report
		String line;
		while (in.hasNext()) {
			line = in.nextLine();
			if (time == null && line.startsWith("NATIONAL WEATHER SERVICE"))
				time = in.nextLine();
			if (line.startsWith("CITY"))
				while ((line = in.nextLine()) != null) {
					if (!line.startsWith("$$") && !line.startsWith(" ") && line.length() > 30)
						testlist.add(line);
					else
						break;
				}
			else continue;
		}
	} //end static parsePage
	
	static void fillMap(List<String> testlist) {
		String location = "";
		String tmp_string = ""; 
		Integer tmp;
		for (String s:testlist) {
			//see instruction
			location = s.substring(0,14);
			tmp_string = s.substring(25,27);
			
			location = location.trim();
			
			if (location.contains("*"))
				location = location.replace("*","");
				
			
			try {
		            Integer.parseInt(tmp_string);
				}
			catch (NumberFormatException ex) {
					tmp_string = "-1";
				}
			
			tmp = Integer.valueOf(tmp_string);
			
			if (tmp == -1)
				continue;
			else
				weather_map.put(location,tmp);
		}
	} //end fillMap
	
	
	static void fileOutput() throws IOException  { 
		File outputfile = new File("city_temp.txt"); 
	  		PrintStream out = new PrintStream(
                new FileOutputStream(outputfile));
			out.println("Zeeshan Lakhani LAB 5 CS 209 FAll 2010");
			out.println(time);
			
	    for (Map.Entry<String, Integer> loc : weather_map.entrySet())
			out.println("City:" + loc.getKey() + " Temp:" + loc.getValue());
	    out.flush();
	    out.close();
		
	} //end fileOutput
	
}//end WeatherMapApp