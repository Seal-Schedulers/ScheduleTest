import java.time.LocalDate;
import java.util.*;

public class ControllerOld{
	// Data
	private HashMap<Double, TaskOld> tasks;
	private HashMap<LocalDate, DayOld> days;
	private static double reference = 0.1;

	// Constructors
	public ControllerOld() {
		tasks = new HashMap<Double, TaskOld>();
		days = new HashMap<LocalDate, DayOld>();
	}
	
	// Methods
	/**
	 * creates a task object using the Task class 
	 * @param name
	 * @param hrs
	 * @param daysTillDue
	 */
	public void createTask(String name, double hrs, int daysTillDue) {
		TaskOld task = new TaskOld(name, hrs, daysTillDue, reference, false);
		addTask(task);
		addToDay(task);
		System.out.println(reference + " " + tasks.get(reference).toString() + " " + tasks.get(reference).getFifteensPerDay());
		//System.out.println(Arrays.toString(days.get(task.getStartDate()).getDay()));
		reference += 0.1;
	}
	
	private void addTask(TaskOld task) {
		tasks.put(reference, task);
	}
	
	private void addToDay(TaskOld task){
		ArrayList<Double> fifteensPerDay = task.getFifteensPerDay();
		LocalDate startDate = task.getStartDate();
		int daysTillDue = (int) task.getDaysTillDue();
		for (int d = 0; d <= daysTillDue; d++) {
			for (double numFifteens = 0; numFifteens < fifteensPerDay.get(d); numFifteens++) {
				if (days.containsKey(startDate)) {
					DayOld day = days.get(startDate);
					for (int i = 0; i < day.getDay().length; i++) {
						double[] dayList = day.getDay();
						if (dayList[i] == 0) {
							day.setIndex(i, task.getKey());
							days.replace(startDate, day);
							days.replace(startDate, priorityReschedule(startDate));
							break;
						}
					}
				}
				else {
					DayOld day = new DayOld();
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
	
	/**
	 * gets all the tasks in a day 
	 * @param date
	 */
	public TaskOld[] getTaskFromDay(LocalDate date) {
		DayOld day = days.get(date);
		double[] taskKeysInDay = day.getDay();
		TaskOld[] tasksInDay = new TaskOld[96];
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
	
	/**
	 * removes all instances of the task in a day and the tasks hashmap 
	 * @param task
	 */
	public void removeTask(TaskOld task) {
		LocalDate startDate = task.getStartDate();
		double daysTillDue = task.getDaysTillDue();
		for (int d = 0; d <= daysTillDue; d++) {
			DayOld day = days.get(startDate);
			for (int i = 0; i < day.getDay().length; i++) {
				double[] dayList = day.getDay();
				if (dayList[i] == task.getKey()) {
					day.setIndex(i, 0);
					days.replace(startDate, day);
					days.replace(startDate, priorityReschedule(startDate));
				}
			}
			startDate.plusDays(1);
		}
		tasks.remove(task.getKey());
	}
	
	private void checkOverflow () {
		
	}
	
	private DayOld priorityReschedule(LocalDate today) {
		DayOld day = days.get(today);
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
	        	else if (tasks.get(day.getDay()[i]).getCurrentDaysTillDue(today) > tasks.get(day.getDay()[i+1]).getCurrentDaysTillDue(today)) {
		            temp = day.getDay()[i];
		            day.setIndex(i, day.getDay()[i+1]);
		            day.setIndex(i+1, temp);
		            sorted = false;
		        }
	        }
	    }
	    return day;
	}
	
	/*private boolean compareTo(Task task1, Task task2) {
		double daysTillDue1 = task1.getCurrentDaysTillDue();
		double daysTillDue2 = task2.getCurrentDaysTillDue();
		if (daysTillDue1 > daysTillDue2) {
			return true;
		}
		return false;
	}*/
}
