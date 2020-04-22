public class Time {
	private int hr;
	private int min;
	
	public Time() {
		hr = 0;
		min = 0;
	}
	
	public Time(int hr, int min) {
		this.hr = hr;
		this.min = min;
	}
	
	public int getHour() {
		return hr;
	}
	
	public int getMinute() {
		return min;
	}
	
	public void increment() {
		if (min == 45) {
			if (hr == 23) {
				hr = 0;
			}
			else {
				hr++;
			}
			min = 0;
		}
		else {
			min += 15;
		}
	}
	
	public boolean compareTo(Time t) {
		if (t.getHour() < hr) {
			return true;
		}
		else if (t.getHour() <= hr && t.getMinute() < min) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		String minutes = "";
		if (min < 15) {
			minutes = "00";
		}
		else {
			minutes = Integer.toString(min);
		}
		return Integer.toString(hr) + ":" + minutes;
	}
}
