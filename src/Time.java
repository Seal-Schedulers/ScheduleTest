import java.util.ArrayList;

public class Time {
	
    public static final double INCREMENT = 1E-4;

    public static void dummy(){
		System.out.println("1");
		System.out.println("1");
		System.out.println("2");
		System.out.println("");
		System.out.println("");
		System.out.println("4");
		System.out.println("");
		System.out.println("5");
		System.out.println("");
		System.out.println("");
	}
    
    public static void dummy1(){
		System.out.println("hello");
		System.out.println("hi");
		System.out.println("whats");
		System.out.println("up");
		System.out.println("cool");
		System.out.println("hello");
		System.out.println("hi");
		System.out.println("whats");
		System.out.println("up");
		System.out.println("cool");
		System.out.println("hello");
	}
    
    public static void main(String[] args) {
    	double hrs = 72;
    	double daysTillDue = 30;
    	ArrayList<Double> fifteensPerDay = new ArrayList<Double>();
        System.out.println(amountPerDay(fifteens(hrs), daysTillDue, fifteensPerDay));
    }
    /**
     * converts the number of hours into the number of fifteen minute segments
     * @param hrs: the total number of hours the task requires
     * @return the number of fifteen minute segments the task requires
     */
    public static double fifteens(double hrs) {
    	return hrs*4;
    }
    /**
     * calculates the number of fifteen minute segments needed for the task each day
     * uses a bell curve formula to divide the workload
     * @param fifteens: the number of fifteen minute segments required for the task
     * @param daysTillDue: the number of days until the task is due
     * @param fifteensPerDay: the number of fifteen minute segments to work each day
     * @return ArrayList with the number of fifteen minute segments needed for the task each day
     */
    public static ArrayList<Double> amountPerDay(double fifteens, double daysTillDue, ArrayList<Double> fifteensPerDay) {
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
