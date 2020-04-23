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
	
	public Time(Time time) {
		this.hr = time.getHour();
		this.min = time.getMinute();
	}
	
	public int getHour() {
		return hr;
	}
	
	public int getMinute() {
		return min;
	}
	
	public boolean equals(Time time) {
		return (time.getHour() == hr && time.getMinute() == min);
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
