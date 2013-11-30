import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class Element {
	
	public static Element SAUNA = new Sauna();
	public static Element ALARM = new Alarm();
	public static Element LIGHTS = new Lights();
	
	private boolean active = true;
	
	private Interaction[] interactions = {Interaction.ON, Interaction.OFF};
	private List<Notification> notifications = new ArrayList<Notification>();
	
	public boolean isActive() {
		return active;
	}
	
	public boolean hasNotifications() {
		return !notifications.isEmpty();
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Interaction> getInteractions(){
		return Arrays.asList(interactions);
	}
	
	public String listNotifications() {
		StringBuilder b = new StringBuilder(getName() + ": ");
		for (Notification n : notifications) {
			b.append(n);
		}
		return b.toString();
	}

	
	public abstract String getName();
	
	public static class Sauna extends Element{
		
		private Sauna(){}
		
		@Override
		public List<Interaction> getInteractions(){
			List<Interaction> i = super.getInteractions();
			i.add(Interaction.SCHEDULE);
			return i;
		}
		
		public String getName() {
			return "Sauna";
		}
		
	}
	
	public static class Alarm extends Element{
		
		private Alarm(){}
		
		public String getName() {
			return "Alarm";
		}
		
	}
	
	public static class Lights extends Element{
		
		private Lights(){}
		
		public String getName() {
			return "Lights";
		}
		
	}
	
	public static enum Interaction {
		ON, OFF, SCHEDULE
	}

	public static class Notification{
		
	}

}