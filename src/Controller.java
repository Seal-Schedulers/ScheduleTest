import java.time.LocalDate;
import java.util.*;

public class Controller{
	// Data
	private HashMap<Double, Task> tasks;
	private HashMap<LocalDate, Day> days;
	private static double reference = 0.1;

	// Constructors
	public Controller() {
		tasks = new HashMap<Double, Task>();
		days = new HashMap<LocalDate, Day>();
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
		System.out.println(reference + " " + tasks.get(reference) + " " + tasks.get(reference).getFifteensPerDay());
		//System.out.println(days.get(date).getCollection());
		//System.out.println(Arrays.toString(days.get(task.getStartDate()).getDay()));
		reference += 0.1;
	}
	
	private void addTask(Task task) {
		tasks.put(reference, task);
	}
	
	public void createBlockTask(String name, Time start, Time end) throws Exception {
		Task task = new Task(name, start, end, reference, true);
		addTask(task);
		blockTimeInDay(task);
		System.out.println(reference + " " + tasks.get(reference));
		reference += 0.1;
	}
	
	private void blockTimeInDay(Task task) throws Exception {
		if(!days.containsKey(task.getStartDate())) {
			Day day = new Day();
			Time time = new Time(task.getStart());
			for (Time t: Day.allTimes) {
				if (t.equals(task.getEnd())) {
					day.addTaskToDay(t, task.getKey());
					break;
				}
				else if(t.equals(time)) {
					day.addTaskToDay(t, task.getKey());
					time.increment();
				}
			}
			days.put(task.getStartDate(), day);
		}
		/*else {
			Day day = days.get(task.getKey());
			Time time = new Time(task.getStart());
			for (Time t: Day.allTimes) {
				if (t.equals(task.getEnd())) {
					if(day.containsKey(time)) {
						double temp = day.getTaskKey(time);
						day.replace(time, task.getKey());
						for(Time k : Day.allTimes) {
							if (!day.containsKey(t)) {
								day.addTaskToDay(k, temp);
							}
						}
					}
					else {
						day.addTaskToDay(t, task.getKey());
					}
					break;
				}
				else if(t.equals(time)) {
					if(day.containsKey(time)) {
						double temp = day.getTaskKey(time);
						day.replace(time, task.getKey());
						for(Time k : Day.allTimes) {
							if (!day.containsKey(t)) {
								day.addTaskToDay(k, temp);
							}
						}
					}
					else {
						day.addTaskToDay(t, task.getKey());
					}
					time.increment();
				}
			}
		}*/
	}
	
	private void addToDay(Task task) throws Exception {
		ArrayList<Double> fifteensPerDay = task.getFifteensPerDay();
		LocalDate startDate = task.getStartDate();
		int daysTillDue = (int) task.getDaysTillDue();
		for (int d = 0; d <= daysTillDue; d++) {
			for (double numFifteens = 0; numFifteens < fifteensPerDay.get(d); numFifteens++) {
				if (days.containsKey(startDate)) {
					Day day = days.get(startDate);
					for(Time t : Day.allTimes) {
						if (!day.containsKey(t)) {
							day.addTaskToDay(t, task.getKey());
							days.replace(startDate, day);
							days.replace(startDate, priorityReschedule(startDate));
							break;
						}
					}
				}
				else {
					Day day = new Day();
					for(Time t : Day.allTimes) {
						if (!day.containsKey(t)) {
							day.addTaskToDay(t, task.getKey());
							break;
						}
					}
					days.put(startDate, day);
				}
			}
			startDate = startDate.plusDays(1);
		}
	}
	
	/*
	public void getTaskFromDay(LocalDate date) {
		Day day = days.get(date);
		for (Time name: day.keySet()){
            String key = name.toString();
            Double value = day.getTaskKey(name);  
            System.out.println(key + " " + value);  
		} 
	}
	
	
	/*public Task[] getTaskFromDay(LocalDate date) {
		Day day = days.get(date);
		Collection<Double> taskKeys = day.getCollection();
		Task[] tasksInDay = new Task[96];
		int index = 0;
		for (double key : taskKeys) {
			tasksInDay[index] = tasks.get(key);
			index++;
		}
		return tasksInDay;
	}*/
	
	/**
	 * gets all the tasks in a day 
	 * @param date
	 */
	
	public void getTaskFromDay(LocalDate date) {
		Day day = days.get(date);
		for (Time time : Day.allTimes) {
			if (day.containsKey(time)) {
				System.out.println(time + " - " + tasks.get(day.getTaskKey(time)));
			}
			else {
				break;
			}
		}
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
	
	private Day priorityReschedule(LocalDate today) {
		Day day = days.get(today);
		boolean sorted = false;
	    double temp;
	    while(!sorted) {
	        sorted = true;
	        for (int i = 0; i < day.getSize() - 1; i++) {
	        	if (!day.containsKey(Day.allTimes.get(i)) || !day.containsKey(Day.allTimes.get(i+1))) {
	        		continue;
	        	}
	        	else if (tasks.get(day.getTaskKey(Day.allTimes.get(i))).getCurrentDaysTillDue(today) > tasks.get(day.getTaskKey(Day.allTimes.get(i+1))).getCurrentDaysTillDue(today)) {
	        		temp = day.getTaskKey(Day.allTimes.get(i));
		            day.replace(Day.allTimes.get(i), day.getTaskKey(Day.allTimes.get(i+1)));
		            day.replace(Day.allTimes.get(i+1), temp);
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
