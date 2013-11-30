import java.util.Scanner;


public class Main {
	
	private static Element[] elements = {Element.SAUNA, Element.ALARM, Element.LIGHTS};
	private static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args){
		
		System.out.println("Welcome!");
		
		listNotifications();
		listActives();
		
		String command = getCommand();
		while(!command.equalsIgnoreCase("exit"))
			execute(command);
		
		System.out.println("Goodbye!");
	}

	private static void execute(String command) {
		// TODO Auto-generated method stub
	}

	private static String getCommand() {
		return s.next();
	}

	private static void listActives() {
		for (Element e : elements) {
			if(e.isActive()){
				System.out.println(e.getName() + " is active.");
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
