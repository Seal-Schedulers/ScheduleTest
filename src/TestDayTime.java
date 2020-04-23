import java.util.ArrayList;

public class TestDayTime {

	public static double key = 0.1;
	
	public static void main(String[] args) throws Exception {
		Time time = new Time(0,0);
		
		ArrayList<Time> allTimes = new ArrayList<Time>();
		
		// Create an arraylist with all time IN ORDER (make static in day)
		do {
			allTimes.add(new Time(time));
			time.increment();
		}
		while(!time.equals(new Time(0,0)));
		
		
		System.out.println(allTimes.toString());
		
		Day day = new Day();
		
		// Add all times to the new day
		Double count = (double) 0;
		for(Time t : allTimes) {
			day.addTaskToDay(t, count);
			count++;
		}
		
		// Prints :)
		for(Time t : allTimes) {
			System.out.println(t + " - " + Double.toString(day.getTaskKey(t)));
		}
		
		
		
		// Same data, different memory
		System.out.println("dif mem, my method " +day.containsKey(new Time()));
		System.out.println("dif mem " + day.containsKey(new Time()));
		// Same memory
		System.out.println("same mem, my method " + day.containsKey(allTimes.get(0)));
		System.out.println("same mem " + day.containsKey(allTimes.get(0)));
		
	}

}
