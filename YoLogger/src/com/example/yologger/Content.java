package com.example.yologger;

import java.util.ArrayList;
import java.util.Date;

public class Content {

	public static final ArrayList<Event> events = new ArrayList<Event>();
	
	public static class Event implements Comparable<Event>{
		public String description;
		public Date date;
		public int durationMinutes;
		public String category;

		public Event(String description, Date date, int durationMinutes, String category){
			this.description = description;
			this.date = date;
			this.durationMinutes = durationMinutes;
			this.category = category;
		}

		@Override
		public int compareTo(Event another) {
			if(this.date.equals(another.date)){
				return 0;
			} else if(this.date.before(another.date)) {
				return 1;
			} else
				return -1;
		}
		
		
	}
}
