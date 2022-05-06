package sw.calendar;


public class Calendar {
	
	private static final int[] MAX_DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private static final int[] LEAP_MAX_DAYS = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	public static boolean isLeapYear(int year) {
		
		if(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) return true;
		
		return false;
	}
	
	public static int getMaxDaysOfMonth(int year, int month) {
		if(isLeapYear(year)) {
			return LEAP_MAX_DAYS[month];
		}
		
		return MAX_DAYS[month];
	}
	
	public void printCalendar(int year, int month) {
		System.out.printf("    <<%d년 %d월>>\n", year, month);
		System.out.println(" SU MO TH WE TH FR SA");
		System.out.println("---------------------");
		int maxDay = getMaxDaysOfMonth(year, month);
		
		
		int weekday = getWeekDay(year, month, 1);
		
		
		for(int i = 0; i < weekday; i++) {
			System.out.print("   ");
		}
		
		int count = 7 - weekday;
		int delim = (count < 7) ? count : 0;
		//첫줄처리
		for(int i = 1; i <= count; i++) {
			System.out.printf("%3d", i);
		}
		
		System.out.println();
		count++;
		
		for(int i = count; i <= maxDay; i++) {
			System.out.printf("%3d", i);
			if(i % 7 == delim) System.out.println();
		}
		System.out.println();
		System.out.println();

	}
	
	private int getWeekDay(int year, int month, int day) {
		
		int flagyear = 1970;

		final int FLAG_WEEKDAY = 3; //1970. 1. 1 Thursday
		
		int count = 0;
		
		for(int i = flagyear; i < year; i++) {
			int tmp = isLeapYear(i) ? 366 : 365;
			count += tmp;
		}
		
		for(int i = 1; i < month; i++) {
			int tmp = getMaxDaysOfMonth(year, i);
			count += tmp;				
		}
		
		count += day;
		int weekday = (count + FLAG_WEEKDAY) % 7;
		return weekday;
	}

	public static void main(String[] args) {
		
		
	}

}
