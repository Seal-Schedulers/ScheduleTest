import java.time.LocalDate;
import java.util.*;

public class Controller{
	private HashMap<Double, Task> tasks;
	private HashMap<LocalDate, Day> days;
	private static double reference = 0.1;

	
	public Controller() {
		tasks = new HashMap<Double, Task>();
		days = new HashMap<LocalDate, Day>();
	}

	public void createTask(String name, double hrs, int daysTillDue) {
		Task task = new Task(name, hrs, daysTillDue, reference);
		addTask(task);
		addToDay(task);
		System.out.println(reference + " " + tasks.get(reference).toString() + " " + tasks.get(reference).getFifteensPerDay());
		//System.out.println(Arrays.toString(days.get(task.getStartDate()).getDay()));
		reference += 0.1;
	}
	
	private void addTask(Task task) {
		tasks.put(reference, task);
	}
	
	private void addToDay(Task task){
		ArrayList<Double> fifteensPerDay = task.getFifteensPerDay();
		LocalDate startDate = task.getStartDate();
		int daysTillDue = (int) task.getDaysTillDue();
		for (int d = 0; d <= daysTillDue; d++) {
			for (double numFifteens = 0; numFifteens < fifteensPerDay.get(d); numFifteens++) {
				if (days.containsKey(startDate)) {
					Day day = days.get(startDate);
					for (int i = 0; i < day.getDay().length; i++) {
						double[] dayList = day.getDay();
						if (dayList[i] == 0) {
							day.setIndex(i, task.getKey());
							days.replace(startDate, priorityReschedule(day));
							break;
						}
					}
				}
				else {
					Day day = new Day();
					for (int i = 0; i < day.getDay().length; i++) {
						if (day.getDay()[i] == 0) {
							day.setIndex(i, task.getKey());
							break;
						}
					}
					days.put(startDate, day);
				}
			}
			startDate = startDate.plusDays(1);
		}
	}
	
	public Task[] getTaskFromDay(LocalDate date) {
		Day day = days.get(date);
		double[] taskKeysInDay = day.getDay();
		Task[] tasksInDay = new Task[96];
		int index = 0;
		for (double key : taskKeysInDay) {
			tasksInDay[index] = tasks.get(key);
			index++;
		}
		return tasksInDay;
	}
	
	/*public ArrayList<Task> getTaskFromDay(LocalDate date) {
		Day day = days.get(date);
		double[] taskKeysInDay = day.getDay();
		ArrayList<Task> tasksInDay = new ArrayList<Task>();
		for (double key : taskKeysInDay) {
			tasksInDay.add(tasks.get(key));
		}
		return tasksInDay;
	}*/
	
	public void removeTask(Task task) {
		LocalDate startDate = task.getStartDate();
		double daysTillDue = task.getDaysTillDue();
		for (int d = 0; d <= daysTillDue; d++) {
			Day day = days.get(startDate);
			for (int i = 0; i < day.getDay().length; i++) {
				double[] dayList = day.getDay();
				if (dayList[i] == task.getKey()) {
					day.setIndex(i, 0);
					days.replace(startDate, priorityReschedule(day));
				}
			}
			startDate.plusDays(1);
		}
		tasks.remove(task.getKey());
	}
	
	private void checkOverflow () {
		
	}
	
	private Day priorityReschedule(Day day) {
		//Task[] tasksInDay = getTaskFromDay(startDate);
		boolean sorted = false;
	    double temp;
	    while(!sorted) {
	        sorted = true;
	        for (int i = 0; i < day.getDay().length-1; i++) {
	        	/*if (day.getDay()[i+1] == 0) {
	        		//sorted = false;
	        		break;
	        	}*/
	        	if (!tasks.containsKey(day.getDay()[i+1])) {
	        		continue;
	        	}
	        	else if (tasks.get(day.getDay()[i]).getDaysTillDue() > tasks.get(day.getDay()[i+1]).getDaysTillDue()) {
		            temp = day.getDay()[i];
		            day.setIndex(i, day.getDay()[i+1]);
		            day.setIndex(i+1, temp);
		            sorted = false;
		        }
	        }
	    }
	    return day;
	}
	
	/*private int compareTo(Task task1, Task task2) {
		double daysTillDue1 = task1.getDaysTillDue();
		double daysTillDue2 = task2.getDaysTillDue();
		if (daysTillDue1 > daysTillDue2) {
			return 2;
		}
		return 1;
	}*/
}
