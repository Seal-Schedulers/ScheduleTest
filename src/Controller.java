import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import com.opencsv.CSVWriter;

public class Controller {
	
	File tasksFile = new File("src\\tasks.csv");
	File schedulesFile = new File("src\\schedules.csv");
	File blockedtasksFile = new File("src\\blockedtasks.csv");	
    
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
	 * @  
	 */
	public void createTask(String name, double hrs, int daysTillDue)   {
		LocalDate today = LocalDate.now();
		Task task = new Task(name, hrs, daysTillDue, reference, today);
		tasks.put(reference, task);
		addToDay(task);
        try { 
        	FileWriter outputfile = new FileWriter(tasksFile, true); 
        	BufferedWriter br = new BufferedWriter(outputfile);
            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(br); 
            
            String[] taskData = {name, Double.toString(hrs), Integer.toString(daysTillDue), Double.toString(task.getKey()), Integer.toString(task.getStartDate().getYear()), Integer.toString(task.getStartDate().getMonthValue()), Integer.toString(task.getStartDate().getDayOfMonth())}; 
            writer.writeNext(taskData);
            // closing writer connection 
            writer.close(); 
        } 
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
		System.out.println(reference + " " + tasks.get(reference) + " " + tasks.get(reference).getFifteensPerDay());
		//System.out.println(days.get(date).getCollection());
		//System.out.println(Arrays.toString(days.get(task.getStartDate()).getDay()));
		reference += 0.1;
	}
	
	public void createBlockTask(String name, Time start, Time end)   {
		LocalDate today = LocalDate.now();
		Task task = new Task(name, start, end, reference, today);
		tasks.put(reference, task);
		blockTimeInDay(task);
		System.out.println(reference + " " + tasks.get(reference));
		try { 
        	FileWriter outputfile = new FileWriter(blockedtasksFile, true); 
        	BufferedWriter br = new BufferedWriter(outputfile);
            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(br); 
            
            String[] blockedData = {name, Integer.toString(start.getHour()), Integer.toString(start.getMinute()), Integer.toString(end.getHour()), Integer.toString(end.getMinute()), Double.toString(task.getKey()), Integer.toString(task.getStartDate().getYear()), Integer.toString(task.getStartDate().getMonthValue()), Integer.toString(task.getStartDate().getDayOfMonth())}; 
            writer.writeNext(blockedData);
            // closing writer connection 
            writer.close(); 
        } 
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
		reference += 0.1;
	}
	
	private void blockTimeInDay(Task task) {
		if(!days.containsKey(task.getStartDate())) {
			Day day = new Day();
			Time time = new Time(task.getStart());
			time.increment();
			for (Time t: Day.allTimes) {
				if (t.equals(task.getEnd())) {
					day.replace(t, task.getKey());
					break;
				}
				else if(t.equals(time)) {
					day.replace(t, task.getKey());
					time.increment();
				}
			}
			days.put(task.getStartDate(), day);
		}
		else {
			Day day = days.get(task.getStartDate());
			Time time = new Time(task.getStart());
			for (Time t: Day.allTimes) {
				if (t.equals(time)) {
					
				}
			}
			days.replace(task.getStartDate(), day);
		}
	}
	
	
	private void addToDay(Task task) {
		ArrayList<Double> fifteensPerDay = task.getFifteensPerDay();
		LocalDate startDate = task.getStartDate();
		int daysTillDue = (int) task.getDaysTillDue();
		for (int d = 0; d <= daysTillDue; d++) {
			for (double numFifteens = 0; numFifteens < fifteensPerDay.get(d); numFifteens++) {
				if (days.containsKey(startDate)) {
					Day day = days.get(startDate);
					for(Time t : Day.allTimes) {
						if (day.getTaskKey(t) == 0.0) {
							day.replace(t, task.getKey());
							days.replace(startDate, day);
							days.replace(startDate, priorityReschedule(startDate));
							break;
						}
					}
				}
				else {
					Day day = new Day();
					for(Time t : Day.allTimes) {
						if (day.getTaskKey(t) == 0.0) {
							day.replace(t, task.getKey());
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
	 * prints all the tasks in a day 
	 * @param date
	 */
	
	public void getTaskFromDay(LocalDate date) {
		Day day = days.get(date);
		for (Time time : Day.allTimes) {
			if (time.equals(new Time(23, 45))) {
				System.out.println(time + " - " + tasks.get(day.getTaskKey(time)));
				break;
			}
			else if (day.containsKey(time)) {
				System.out.println(time + " - " + tasks.get(day.getTaskKey(time)));
			}
			else {
				continue;
			}
		}
	}
	
	private Day priorityReschedule(LocalDate today) {
		Day day = days.get(today);
		boolean sorted = false;
	    double temp;
	    while(!sorted) {
	        sorted = true;
	        for (int i = 0; i < day.getSize() - 1; i++) {
	        	if (day.getTaskKey(Day.allTimes.get(i)) == 0.0 || day.getTaskKey(Day.allTimes.get(i+1)) == 0.0) {
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
	
	public void saveSchedule() {
		try { 
        	FileWriter outputfile = new FileWriter(schedulesFile, true); 
        	BufferedWriter br = new BufferedWriter(outputfile);
            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(br); 
            for (LocalDate date: days.keySet()) {
                String[] scheduleData = new String[99];
                scheduleData[0] = Integer.toString(date.getYear());
                scheduleData[1] = Integer.toString(date.getMonthValue());
                scheduleData[2] = Integer.toString(date.getDayOfMonth());
                Day day = days.get(date);
                int index = 3;
                for (Time time : Day.allTimes) {
        			if (time.equals(new Time(23, 45))) {
        				scheduleData[index] = Double.toString(day.getTaskKey(time));
        				break;
        			}
        			else if (day.containsKey(time)) {
        				scheduleData[index] = Double.toString(day.getTaskKey(time));        			}
        			index++;
        		}
                writer.writeNext(scheduleData);
            }
            // closing writer connection 
            writer.close(); 
        } 
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
	}
	
}
