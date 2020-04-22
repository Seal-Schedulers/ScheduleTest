import java.time.LocalDate;
import java.util.*;

public class Controller{
	// Data
	private HashMap<Double, Task> tasks;
	private HashMap<LocalDate, Day> days;
	private static double reference = 0.1;
	private Time[] times = new Time[96];

	// Constructors
	public Controller() {
		tasks = new HashMap<Double, Task>();
		days = new HashMap<LocalDate, Day>();
		fillTime();
		System.out.println(Arrays.toString(times));
	}
	
	// Methods
	/**
	 * creates a task object using the Task class 
	 * @param name
	 * @param hrs
	 * @param daysTillDue
	 * @throws Exception 
	 */
	public void createTask(String name, double hrs, int daysTillDue) throws Exception {
		Task task = new Task(name, hrs, daysTillDue, reference, false);
		addTask(task);
		addToDay(task);
		System.out.println(reference + " " + tasks.get(reference).toString() + " " + tasks.get(reference).getFifteensPerDay());
		LocalDate date = LocalDate.now();
		//System.out.println(days.get(date).getCollection());
		//System.out.println(Arrays.toString(days.get(task.getStartDate()).getDay()));
		reference += 0.1;
	}
	
	private void addTask(Task task) {
		tasks.put(reference, task);
	}
	
	private void addToDay(Task task) throws Exception {
		ArrayList<Double> fifteensPerDay = task.getFifteensPerDay();
		LocalDate startDate = task.getStartDate();
		int daysTillDue = (int) task.getDaysTillDue();
		for (int d = 0; d <= daysTillDue; d++) {
			for (double numFifteens = 0; numFifteens < fifteensPerDay.get(d); numFifteens++) {
				if (days.containsKey(startDate)) {
					Day day = days.get(startDate);
					for (Time i = new Time(); !i.compareTo(new Time(23, 45)); i.increment()) {
						System.out.println(i);
						if (!day.containsKey(i)) {
							day.addTaskToDay(i, task.getKey());
							days.replace(startDate, day);
							//days.replace(startDate, priorityReschedule(startDate));
							break;
						}
					}
				}
				else {
					Day day = new Day();
					for (Time i = new Time(); !i.compareTo(new Time(23, 45)); i.increment()) {
						if (!day.containsKey(i)) {
							day.addTaskToDay(i, task.getKey());
							days.put(startDate, day);
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
	public Task[] getTaskFromDay(LocalDate date) {
		Day day = days.get(date);
		Task[] tasksInDay = new Task[96];
		int index = 0;
		for (Time time : times) {
			if (day.containsKey(time)) {
				tasksInDay[index] = tasks.get(day.getTaskKey(time));
			}
			else {
				continue;
			}
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
	/*public void removeTask(TaskOld task) {
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
		
	}*/
	
	private void fillTime() {
		Time time = new Time();
		time.increment();
		System.out.println(time);
		for (int i = 0; i < times.length; i++) {
			times[i] = time;
			time.increment();
		}
	}
	
	private Day priorityReschedule(LocalDate today) {
		Day day = days.get(today);
		boolean sorted = false;
	    double temp;
	    Time time1 = new Time();
	    Time time2 = new Time(0, 15);
	    while(!sorted) {
	        sorted = true;
	        System.out.println(tasks.get(day.getTaskKey(time1)));
	        for (int i = 0; i < day.getSize(); i++) {
	        	if (!day.containsKey(time1) || !day.containsKey(time2)) {
	        		continue;
	        	}
	        	else if (tasks.get(day.getTaskKey(time1)).getCurrentDaysTillDue(today) > tasks.get(day.getTaskKey(time2)).getCurrentDaysTillDue(today)) {
	        		temp = day.getTaskKey(time1);
		            day.replace(time1, day.getTaskKey(time2));
		            System.out.println("entered");
		            day.replace(time2, temp);
		            sorted = false;
		        }
	        }
	        time1.increment();
	        time2.increment();
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
