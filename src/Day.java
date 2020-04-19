import java.util.Arrays;

public class Day {
	private double[] day;
	
	/**
	 * constructs a task object 
	 */
	public Day() {
		day = new double[96];
	}
	
	/**
	 * gets the day list
	 * @return day list
	 */
	public double[] getDay() {
		return day;
	}
	
	/**
	 * sets the index of the day list with a value
	 */
	public void setIndex(int i, double value) {
		day[i] = value;
	}
	
	/**
	 * gets the current date as the start date of the task
	 * @return string
	 */
	public String toString() {
		return Arrays.toString(day);
	}
}

