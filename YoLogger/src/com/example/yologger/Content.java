package com.example.yologger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Content {

	public static final ArrayList<Event> events = new ArrayList<Event>();
	
	public static final HashMap<String, Category> categories = new HashMap<String, Category>();
	private static long hour = 60 * 60 * 1000;
	private static long day = hour * 24;
	private static long week = day * 7;
	private static Date today = new Date();
	private static Date weekAgo = new Date(today.getTime() - week);
	static{
		categories.put("Exercise", new Category("Exercise", R.drawable.sport_icon, 60 * 6));
		categories.put("Study", new Category("Study", R.drawable.study_icon, 60 * 12));
		categories.put("Freetime", new Category("Freetime", R.drawable.freetime_icon, -1));
		categories.put("Work", new Category("Work", R.drawable.work, 60 * 16));
		categories.put("Sleep", new Category("Sleep", R.drawable.sleep, 40 * 60));
		
		long t = today.getTime();
		addEvent(new Event("Jogging", new Date(t -= hour), 45, categories.get("Exercise")));
		addEvent(new Event("Movie night", new Date(t -= day), 180, categories.get("Freetime")));
		addEvent(new Event("Lecture", new Date(t -= hour), 105, categories.get("Study")));
		addEvent(new Event("Sleep", new Date(t -= hour), 6 * 60, categories.get("Sleep")));
		addEvent(new Event("Studying for finals", new Date(t -= hour), 190, categories.get("Study")));
		addEvent(new Event("Research", new Date(t -= day), 90, categories.get("Work")));
		addEvent(new Event("Lecture", new Date(t -= hour), 105, categories.get("Study")));
		addEvent(new Event("Sleep", new Date(t -= hour), 6 * 60, categories.get("Sleep")));
		addEvent(new Event("Swimming", new Date(t -= day), 90, categories.get("Exercise")));
		addEvent(new Event("Lecture", new Date(t -= hour), 105, categories.get("Study")));
		addEvent(new Event("Sleep", new Date(t -= hour), 6 * 60, categories.get("Sleep")));
		addEvent(new Event("Tennis", new Date(t -= week), 75, categories.get("Exercise")));
		addEvent(new Event("Video Games", new Date(t -= hour), 50, categories.get("Freetime")));
		addEvent(new Event("Meeting", new Date(t -= week), 130, categories.get("Work")));
	}
	

	public static void addEvent(Event e){
		events.add(e);
	}
	
	public static int getCategoryTotal(Category c){
		int time = 0;
		for (Event e : events) {
			if(e.category.name.equals(c.name) && e.date.after(weekAgo)){
				time += e.durationMinutes;
			}
		}
		return time;
	}
	
	public static List<Event> getEventList(){
		List<Event> list = new ArrayList<Event>();
		Date today = new Date();
		Date weekAgo = new Date(today.getTime() - week);
		int c = 0;
		list.add(new Event(c++));
		for (Event e : events) {
			if(e.date.before(weekAgo)){
				list.add(new Event(c++));
				weekAgo = new Date(weekAgo.getTime() - week);
			}
			list.add(e);
		}
		return list;
	}
	
	public static List<Category> getSortedCategories(){
		List<Category> c = Arrays.asList(categories.values().toArray(new Category[0]));
		Collections.sort(c);
		return c;
	}
	
	public static class Event implements Comparable<Event>{
		public String description;
		public Date date;
		public int durationMinutes;
		public Category category;
		public Integer dividerCount;

		public Event(Integer dividerCount){
			this.dividerCount = dividerCount;
		}
		
		public boolean isDivider(){
			return dividerCount != null;
		}
		
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
	
	public static class Category implements Comparable<Category>, Serializable{
		public String name;
		public int resId;
		public int goal = -1;
		
		public Category(String name, int resId) {
			this.name = name;
			this.resId = resId;
		}
		
		public Category(String name, int resId, int goal) {
			this(name, resId);
			this.goal = goal;
		}

		@Override
		public int compareTo(Category another) {
			return this.name.compareTo(another.name);
		}
		
		
	}
}
