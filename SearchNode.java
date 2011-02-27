import java.io.*;
import java.util.*;
import java.lang.*;

public class SearchNode {
	
	private SearchNode parent; 
	private String address;
	private int cum_cost;
	
	
	public SearchNode (SearchNode p, String a, int c) {
		parent = p;
		address = a;
		cum_cost = c;	
	}
	
	public static void main(String args[]) {
		Console console = System.console();
		String init = console.readLine("Init Value:").toUpperCase();
		String goal = console.readLine("Goal Value:").toUpperCase();
		
		File allfile = new File("random_net.txt"); 
		ArrayList<String> allthings = new ArrayList<String>();
		try {
		BufferedReader in = new BufferedReader(
		new InputStreamReader(new FileInputStream(allfile))); 
		String line = "";
	   	while ((line = in.readLine() ) != null) {
				String first = line.substring(0,2);		
				String second = line.substring(3,5);
				allthings.add(first);
				allthings.add(second);
	  		} // end loop
	   	in.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(1);	
		}
		
		HashSet<String> hash = new HashSet<String>(allthings);
		ArrayList<String> allvals = new ArrayList<String>(hash) ;
		
		Random generator = new Random();
		int rnd_num_1 = generator.nextInt(allvals.size());
		
		// String init = "F3";
		// String goal = "D4";
		System.out.println("init: " + init);
		System.out.println("goal: " + goal);
		SearchNode final_node = search(init,goal);
		System.out.println("last_node: " + final_node.address);
		
	}
	
	public static SearchNode search(String init, String goal) {
		Map<String,Integer> neighborhood = new HashMap<String,Integer>();
		ArrayList<String> done_list = new ArrayList<String>();
		Stack<SearchNode> working_stack = new Stack<SearchNode>();
		
		ArrayList<String> final_path = new ArrayList<String>();
		
		Map<String[],Integer> network = new HashMap<String[],Integer>();
		network = getHash();
		
		SearchNode current_node = new SearchNode(null,init,0);
		
		//etc vals
		int add_val = 0;
		int cum_val = 0;
		
		outerloop:
		while (current_node != null) {
			final_path.add(current_node.address);
			neighborhood.clear();
			Iterator eachArray = network.keySet().iterator();
			while(eachArray.hasNext()) {
				String[] get_array = (String[])eachArray.next();
				if (get_array[0].equals(current_node.address))
					neighborhood.put(get_array[1],network.get(get_array));
				else 
					continue;	
			}
			
			for (String s : neighborhood.keySet()) {
				if (done_list.contains(s)) {
					continue;
				}
				else {
					add_val = neighborhood.get(s).intValue();
					cum_val = current_node.cum_cost + add_val;
					SearchNode new_node = new SearchNode(current_node,s,cum_val);
					
					if (s.equals(goal)) {
						System.out.println("Success!");
						
						final_path.add(goal);
						System.out.println("Final Path: " + final_path);
						
						System.out.println("Final Cost: " + cum_val);
						break outerloop;
					}
					
					else {
						working_stack.push(new_node);
						continue;
					}	
				}	
			}//end for each loop through neighborhood
			done_list.add(current_node.address);
			current_node = working_stack.pop();	
		}//end while loop through current node
		
		return current_node;
	}//end search method
	
	public static HashMap<String[],Integer> getHash() {
		File inputfile = new File("random_net.txt"); 
		HashMap<String[],Integer> hmap = new HashMap<String[],Integer>();
	
		try {
		BufferedReader in = new BufferedReader(
		new InputStreamReader(new FileInputStream(inputfile))); 
		String line = "";
	   	while ((line = in.readLine() ) != null) {
				String[] quick_array = new String[2];
				String first = line.substring(0,2);		
				String second = line.substring(3,5);
				String third = line.substring(6,7);
				
				quick_array[0] = first;
				quick_array[1] = second;
				hmap.put(quick_array,Integer.parseInt(third));
				
				
	  		} // end loop
	   	in.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(1);	
		}
	
		return hmap;
		
	}//end getHash	
}//end SearchNode class
