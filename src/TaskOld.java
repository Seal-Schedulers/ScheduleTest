import java.time.LocalDate;
import java.util.*;

public class TaskOld {
	// Data
	private String name;
	private double hrs;
	private ArrayList<Double> fifteensPerDay = new ArrayList<Double>();
	private double daysTillDue;
	private LocalDate startDate;
	private final double key;
	private boolean block;
	final static double INCREMENT = 1E-4;

	// Constructors
	/**
	 * constructs a task object 
	 * @param name: the name of the task
	 * @param hrs: the total hours required for the task
	 * @param daysTillDue: the number of days until the task is due
	 * @param key: the reference for the task
	 */
	public TaskOld(String name, double hrs, int daysTillDue, double key, boolean block) {
		this.name = name;
		this.hrs = hrs;
		this.daysTillDue = daysTillDue;
		this.key = key;
		this.block = block;
		fifteensPerDay = amountPerDay(fifteens(hrs), daysTillDue, fifteensPerDay);
		startDate = LocalDate.now();
	}
	
	// Methods
	
	/**
	 * gets the current date as the start date of the task
	 * @return the start date / current date
	 */
	public LocalDate getStartDate() {
		return startDate;
	}
	
	/**
	 * gets the number of days until the task is due
	 * @return the number of days until the task is due
	 */
	public double getDaysTillDue() {
		return daysTillDue;
	}
	
	/**
	 * calculates the number of days until the task is due depending on the current day
	 * @return the number of days until the task is due depending on the current day
	 */
	public double getCurrentDaysTillDue(LocalDate today) {
		double days = 0;
		for(LocalDate day = startDate; !day.equals(today); day = day.plusDays(1)) {
			days++;
		}
		return daysTillDue - days;
	}

	/**
	 * gets the list of the fifteen minute segments required to do the task each day
	 * @return ArrayList with the number of fifteen minute segments needed for the task each day
	 */
	public ArrayList<Double> getFifteensPerDay() {
		return fifteensPerDay;
	}
	
	/**
	 * get the reference associated with the task
	 * @return the key for the task
	 */
	public double getKey() {
		return key;
	}
	
	/**
	 * converts to a String
	 */
	public String toString() {
		return name;
	}
	
	private static double fifteens(double hrs) {
    	return hrs*4;
    }
	
	private static ArrayList<Double> amountPerDay(double fifteens, double daysTillDue, ArrayList<Double> fifteensPerDay) {
    	double total = integral(0, 4, x -> {
			return (1/Math.sqrt(2*Math.PI))*(Math.pow(Math.E,-(Math.pow(x-2, 2)/2)));
		});
    	double plusNextDay = 4/(daysTillDue+1);
    	for (int i = 1; i <= daysTillDue+1; i++) {
			double hrsToday = integral(plusNextDay*(i-1), plusNextDay*i, x -> {
				return (1/Math.sqrt(2*Math.PI))*(Math.pow(Math.E,-(Math.pow(x-2, 2)/2)));
			});
			//hrsPerDay.add(hrsToday/total);
			fifteensPerDay.add((double) Math.round(hrsToday/total*fifteens));
			//hrsPerDay.add((hrsToday/total*hrs));
		}
    	double totalTime = 0;
    	while(totalTime != fifteens) {
    		totalTime = 0;
    		for (double time : fifteensPerDay) {
        		totalTime += time;
        	}
    		if (totalTime < fifteens){
        		fifteensPerDay.set((int) Math.floor(fifteensPerDay.size()/2), Math.floor(fifteensPerDay.get(fifteensPerDay.size()/2))+1);
        	}
        	else if (totalTime > fifteens){
        		fifteensPerDay.set((int) Math.floor(fifteensPerDay.size()/2), Math.floor(fifteensPerDay.get(fifteensPerDay.size()/2))-1);
        	}
    	}
		return fifteensPerDay;
	}
   
    
	//Source for integration method: https://gist.github.com/JoseRivas1998/f6642e1e8dcea665b12e0f7264d3e088 -- courtesy to Jose Rivas
    private static double integral(double a, double b, Function function) {
        double area = 0;
        double modifier = 1;
        if(a > b) {
            double tempA = a;
            a = b;
            b = tempA;
            modifier = -1;
        }
        for(double i = a + INCREMENT; i < b; i += INCREMENT) {
            double dFromA = i - a;
            area += (INCREMENT / 2) * (function.f(a + dFromA) + function.f(a + dFromA - INCREMENT));
        }
        return (Math.round(area * 1000.0) / 1000.0) * modifier;
    }

}
