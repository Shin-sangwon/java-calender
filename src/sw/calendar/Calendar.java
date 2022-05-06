package sw.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

public class Calendar {
	
	private static final int[] MAX_DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private static final int[] LEAP_MAX_DAYS = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private static final String SAVE_FILE = "calendar.dat";
	
	private HashMap<Date, PlanItem> planMap;
	
	public Calendar() {
		planMap = new HashMap<>();
		File f = new File(SAVE_FILE);
		if(!f.exists()) {
			System.err.println("no save file");
			return;
		}
		try {
			Scanner sc = new Scanner(f);
			while(sc.hasNext()) {
				String line = sc.nextLine();
				String words[] = line.split(",");
				String date = words[0];
				String detail = words[1].replaceAll("\"", "");
				PlanItem p = new PlanItem(date, detail);
				planMap.put(p.getDate(), p);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void registerPlan(String strDate, String plan) throws ParseException {
		
		PlanItem p = new PlanItem(strDate, plan);
		planMap.put(p.getDate(), p);
		
		File f = new File(SAVE_FILE);
		String item = p.saveString();
		try {
			FileWriter fw = new FileWriter(f, true);
			fw.write(item);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public PlanItem searchPlan(String strDate){
		Date date = PlanItem.getDatefromString(strDate);
		return planMap.get(date);
	}
	
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

		final int FLAG_WEEKDAY = 4; //1970. 1. 1 Thursday
		
		int count = 0;
		
		for(int i = flagyear; i < year; i++) {
			int tmp = isLeapYear(i) ? 366 : 365;
			count += tmp;
		}
		
		for(int i = 1; i < month; i++) {
			int tmp = getMaxDaysOfMonth(year, i);
			count += tmp;				
		}
		
		count += day -1 ;
		int weekday = (count + FLAG_WEEKDAY) % 7;
		return weekday;
	}

	public static void main(String[] args) throws ParseException{
		Calendar cal = new Calendar();
		cal.registerPlan("2017-06-03", "gogo");
		
	}

}
