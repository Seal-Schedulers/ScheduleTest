import java.util.Arrays;

public class Day {
	private double[] day;
	
	public Day() {
		day = new double[96];
	}
	
	public double[] getDay() {
		return day;
	}
	
	public void setIndex(int i, double value) {
		day[i] = value;
	}
	
	public String toString() {
		return Arrays.toString(day);
	}
}

