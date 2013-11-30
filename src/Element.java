import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Element {

	public static Element SAUNA = new Sauna(new Notification("SAUNA IS ON FIRE"));
	public static Element ALARM = new Alarm(new Notification("ALARM TRIGGERED AT 03:00"));
	public static Element LIGHTS = new Lights();
	
	public Element(Notification... n){
		this.notifications.addAll(Arrays.asList(n));
	}

	private boolean active = true;

	private Interaction[] interactions = { Interaction.ON, Interaction.OFF };
	private List<Notification> notifications = new ArrayList<Notification>();

	public boolean isActive() {
		return active;
	}

	public boolean hasNotifications() {
		return !notifications.isEmpty();
	}

	public void addNotification(Notification n) {
		notifications.add(n);
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Interaction> getInteractions() {
		return Arrays.asList(interactions);
	}

	public String listNotifications() {
		StringBuilder b = new StringBuilder("*** ");
		for (Notification n : notifications) {
			b.append(n.message);
		}
		b.append(" ***");
		return b.toString();
	}

	public abstract String getName();

	public String getStatusVerb() {
		return "is";
	}

	public static class Sauna extends Element {
		
		private int hours = 0;
		private int minutes = 0;

		private Sauna(Notification... n) {
			super(n);
		}

		@Override
		public String execute(Interaction in) {
			if (in == Interaction.ON) {
				if(in.param == null){
					super.active = true;
					return this.getName() + " is now " + in.name(); 
				}
				
				String[] split = in.param.split(":");
				if(split.length != 2)
					return "Invalid schedule time: " + in.param;
				
				try{
					hours = Integer.parseInt(split[0]);
					minutes = Integer.parseInt(split[1]);
				}catch(NumberFormatException e){
					return "Invalid schedule time: " + in.param;
				}
				return this.getName() + " scheduled to go ON in " + hours + " hours " + minutes + " minutes";
			} else if (in == Interaction.OFF) {
				super.active = false;
				return this.getName() + " is now " + in.name(); 
			} else {
				return null;
			}
		}

		public String getName() {
			return "Sauna";
		}

	}

	public static class Alarm extends Element {

		private Alarm(Notification... n) {
			super(n);
		}

		public String getName() {
			return "Alarm";
		}


		@Override
		public String execute(Interaction in) {
			if (in == Interaction.ON) {
				super.active = true;
				return this.getName() + " will be " + in.name() + " in 30 seconds."; 
			} else if (in == Interaction.OFF) {
				super.active = false;
				return this.getName() + " " + this.getStatusVerb() + " now " + in.name(); 
			} else {
				return null;
			}
		}
	}

	public static class Lights extends Element {

		private Lights(Notification... n) {
			super(n);
		}

		public String getName() {
			return "Lights";
		}

		@Override
		public String getStatusVerb() {
			return "are";
		}

	}

	public static enum Interaction {
		ON, OFF;
		private String param;
		public void setParam(String p) {
			this.param = p;
		}
	}

	public static class Notification {

		public final String message;

		public Notification(String message) {
			this.message = message;
		}

	}

	public String execute(Interaction in) {
		if (in == Interaction.ON) {
			this.active = true;
			return this.getName() + " " + this.getStatusVerb() + " now " + in.name(); 
		} else if (in == Interaction.OFF) {
			this.active = false;
			return this.getName() + " " + this.getStatusVerb() + " now " + in.name(); 
		} else {
			return null;
		}
	}

	public void clearNotifications() {
		this.notifications.clear();
	}

}