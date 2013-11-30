import java.util.Scanner;


public class Main {
	
	private static Element[] elements = {Element.SAUNA, Element.ALARM, Element.LIGHTS};
	private static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args){
		
		System.out.println("Welcome!");
		
		displayStatus();
		
		String command = getCommand();
		while(!command.equalsIgnoreCase("exit")) {
			if(command.equalsIgnoreCase("status")) 
				displayStatus();
			else
				System.out.println(execute(command));
			command = getCommand();
		}
		
		System.out.println("Goodbye!");
	}

	private static void displayStatus() {
		listNotifications();
		listActives();
	}

	private static String execute(String command) {
		String[] split = command.split(" ");
		if(split.length >= 2)
		for (Element e : elements) {
			if(e.getName().equalsIgnoreCase(split[0])){
				Element.Interaction in = null;
				for (Element.Interaction i : Element.Interaction.values()) {
					if(i.name().equalsIgnoreCase(split[1]))
						in = i;
				}
				
				if(in == null && !e.getInteractions().contains(in))
					return "Invalid interaction command: " + split[1];
				else {
					if(split.length >= 3){
						in.setParam(split[2]);
					}
					String ret = e.execute(in);
					in.setParam(null);
					return ret;
				}
			}
		}
		return "Invalid command: " + command;
	}

	private static String getCommand() {
		return s.nextLine();
	}

	private static void listActives() {
		for (Element e : elements) {
			if(e.isActive()){
				System.out.println(e.getName() + " " + e.getStatusVerb() + " active.");
			}
		}
	}
	
	private static void listNotifications() {
		if(!hasNotifications()){
			return;
		}
		System.out.println("HOME STATUS: ");
		for (Element e : elements) {
			System.out.println(e.listNotifications());
		}
	}
	
	private static boolean hasNotifications(){
		for (Element e : elements) {
			if(e.hasNotifications())
				return true;
		}
		return false;
	}
}
