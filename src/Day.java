import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Day {
	private HashMap<Time, Double> day;
	
	/**
	 * constructs a task object 
	 */
	public Day() {
		day = new HashMap<Time, Double>();
	}
	
	/**
	 * sets the index of the day list with a value
	 * used to be setIndex method
	 * @throws Exception 
	 */
	public void addTaskToDay(Time time, double value) throws Exception {
		if (day.size() == 96) {
			throw new Exception("overflow");
		}
		day.put(time, value);
	}
	
	public int getSize() {
		return day.size();
	}
	
	public double getTaskKey(Time time) {
		return day.get(time);
	}
	
	public boolean containsKey(Time time) {
		return day.containsKey(time);
	}
	
	public Collection<Double> getCollection(){
		return day.values();
	}

	public void replace(Time time, double taskKey) {
		day.replace(time, taskKey);
	}
	
	/**
	 * gets the current date as the start date of the task
	 * @return string
	 */
	/*public String toString() {
		Collection<Double> values = day.values();
		String schedule = "";
		for (double v : values) {
			schedule = schedule + " " + Double.toString(v);
		}
		return schedule;
	}*/


}

