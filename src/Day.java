import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Day {
	private HashMap<Time, Double> day;
	
	static ArrayList<Time> allTimes = new ArrayList<Time>();{
	Time time = new Time(0,0);
	do {
		allTimes.add(new Time(time));
		time.increment();
	}
	while(!time.equals(new Time(0,0)));
	
	}
	
	/**
	 * constructs a day object 
	 */
	public Day() {
		day = new HashMap<Time, Double>();
	}
	
	public void addTaskToDay(Time time, double value) throws Exception {
		day.put(time, value);
	}
	
	public int getSize() {
		return day.size();
	}
	
	public double getTaskKey(Time time) {
		return day.get(time);
	}
	
	public void removeTaskKey(Time time) {
		day.remove(time); 
	}
	
	public boolean containsKey(Time time) {
		for(Time key : day.keySet()) {
			if(key.equals(time))
				return true;
		}
		return false;
	}
	
	public Collection<Double> getCollection(){
		return day.values();
	}

	public void replace(Time time, double taskKey) {
		day.replace(time, taskKey);
	}

	public Set<Time> keySet() {
		return day.keySet();
	}

	

}

