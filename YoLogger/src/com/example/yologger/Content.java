package com.example.yologger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Content {

	public static final ArrayList<Event> events = new ArrayList<Event>();
	
	public static final HashMap<String, Category> categories = new HashMap<String, Category>();
	
	static{
		categories.put("Exercise", new Category("Exercise", R.drawable.sport_icon));
		categories.put("Study", new Category("Study", R.drawable.study_icon));
		categories.put("Freetime", new Category("Freetime", R.drawable.freetime_icon));
	}
	
	public static class Event implements Comparable<Event>{
		public String description;
		public Date date;
		public int durationMinutes;
		public Category category;

		public Event(String description, Date date, int durationMinutes, Category category){
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
	
	public static class Category {
		public String name;
		public int resId;
		public Category(String name, int resId) {
			this.name = name;
			this.resId = resId;
		}
		
	}
}
