import java.time.LocalDate;
import java.util.Arrays;

public class Tester {

	public static void main(String[] args) throws Exception {
		/*for (Time i = new Time(); i.equals(new Time(23, 45)); i.increment()) {
			System.out.println(i);
		}
		/*Time time = new Time();
		System.out.println(time);
		time.increment();
		System.out.println(time);*/
		LocalDate date = LocalDate.now();
		Controller controller = new Controller();
		controller.createTask("Math", 1.5, 3);
		controller.createTask("CS", 1, 0);
		controller.createTask("Spanish", 1, 8);
		controller.createTask("Stem 2", 72, 30);
		controller.createTask("Stem 1", 2, 0);
		ControllerOld controller1 = new ControllerOld();
		controller1.createTask("Math", 1.5, 3);
		controller1.createTask("CS", 1, 0);
		controller1.createTask("Spanish", 1, 8);
		controller1.createTask("Stem 2", 72, 30);
		controller1.createTask("Stem 1", 2, 0);
		//controller.createTask("Stem 3", 25, 0);
		controller.getTaskFromDay(date);
		System.out.println(Arrays.toString(controller1.getTaskFromDay(date)));
		//System.out.println(controller.getTaskFromDay(date));
		//System.out.println(Arrays.toString(controller.getTaskFromDay(date)));
		date = date.plusDays(1L);
		//System.out.println(controller.getTaskFromDay(date));
		//System.out.println(Arrays.toString(controller.getTaskFromDay(date)));
		date = date.plusDays(1L);
		//System.out.println(controller.getTaskFromDay(date));
		//System.out.println(Arrays.toString(controller.getTaskFromDay(date)));
		date = date.plusDays(1L);
		//System.out.println(controller.getTaskFromDay(date));
		//System.out.println(Arrays.toString(controller.getTaskFromDay(date)));
		/*int[] a = {1,5,4,2,3,6};
		bubbleSort(a);
		System.out.print(Arrays.toString(a));*/
	}
}
