import java.time.LocalDate;
import java.util.Arrays;

public class Tester {

	public static void main(String[] args) {
		LocalDate date = LocalDate.now();
		Controller controller = new Controller();
		controller.createTask("Math", 1.5, 3);
		controller.createTask("CS", 1, 0);
		controller.createTask("Spanish", 1, 8);
		controller.createTask("Stem 2", 72, 30);
		controller.createTask("Stem 1", 2, 0);
		//controller.createTask("Stem 3", 25, 0);
		System.out.println(Arrays.toString(controller.getTaskFromDay(date)));
		//System.out.println(Arrays.toString(controller.getTaskFromDay(date)));
		date = date.plusDays(1L);
		//System.out.println(controller.getTaskFromDay(date));
		System.out.println(Arrays.toString(controller.getTaskFromDay(date)));
		date = date.plusDays(1L);
		//System.out.println(controller.getTaskFromDay(date));
		System.out.println(Arrays.toString(controller.getTaskFromDay(date)));
		date = date.plusDays(1L);
		//System.out.println(controller.getTaskFromDay(date));
		System.out.println(Arrays.toString(controller.getTaskFromDay(date)));
		date = date.plusDays(1L);
		//System.out.println(controller.getTaskFromDay(date));
		System.out.println(Arrays.toString(controller.getTaskFromDay(date)));
		/*int[] a = {1,5,4,2,3,6};
		bubbleSort(a);
		System.out.print(Arrays.toString(a));*/
	}

	public static void bubbleSort(int[] a) {
	    boolean sorted = false;
	    int temp;
	    while(!sorted) {
	        sorted = true;
	        for (int i = 0; i < a.length - 1; i++) {
	            if (a[i] > a[i+1]) {
	                temp = a[i];
	                a[i] = a[i+1];
	                a[i+1] = temp;
	                sorted = false;
	            }
	        }
	    }
	}
}
