# Data Structures final coding projects, New Jersey City University, Fall 2010

# What is this repo?
This repository contains completed Java assignments for my Data Structures class in Fall of 2010. 

- WeatherMapApp.java pulls in temperature info from the [National Weather Service website](http://iwin.nws.noaa.gov/iwin/nj/hourly.html) and does some parsing using a hashmap

- SearchNodeq.java and SearchNode.java incorporate a text file (random_net.txt) and basically perform network searches (randomly choosing the initial node and goal node). The former uses a priority queue data structure (and focuses on best scoring path), while the latter uses a stack

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
						SearchNodeq new_node = new SearchNodeq(current_node,s,cum_val);
						working_pq.offer(new_node);
					}	
				}
				done_list.add(current_node.address);
				current_node = working_pq.poll();
			}


##### I apologize ahead of time for any chaotic or bad code!
