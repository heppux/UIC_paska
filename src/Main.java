import java.util.Scanner;

public class Main {

	private static Element[] elements = { Element.SAUNA, Element.ALARM,
			Element.LIGHTS };
	private static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Welcome to the Intelligent Home Control System!");

		displayStatus();

		String command = getCommand();
		while (!command.equalsIgnoreCase("exit")) {
			if (command.equalsIgnoreCase("status"))
				displayStatus();
			else if(command.equalsIgnoreCase("reset"))
				System.out.println(clearNotifications());
			else if(command.equalsIgnoreCase("help")){
				displayHelp();
			}else
				System.out.println(execute(command));
			command = getCommand();
		}

		System.out.println("Goodbye!");
	}

	private static void displayHelp() {
		System.out.println("Available commands:");
		System.out.println("status - displays home status");
		System.out.println("reset - resets notifications");
		System.out.println("alarm - controls burglar alarm");
		System.out.println("sauna - controls sauna");
		System.out.println("lights - controls lights");
	}

	private static String clearNotifications() {
		if (!hasNotifications()) {
			return "There are no notifications.";
		}
		for (Element e : elements) {
			e.clearNotifications();
		}
		return "Notifications have been reset.";
	}

	private static void displayStatus() {
		System.out.println("------------------");
		System.out.println("HOME STATUS: ");
		listNotifications();
		listActives();
		System.out.println("------------------");
	}

	private static String execute(String command) {
		String[] split = command.split(" ");
		if (split.length >= 2)
			for (Element e : elements) {
				if (e.getName().equalsIgnoreCase(split[0])) {
					Element.Interaction in = null;
					for (Element.Interaction i : Element.Interaction.values()) {
						if (i.name().equalsIgnoreCase(split[1]))
							in = i;
					}

					if (in == null && !e.getInteractions().contains(in))
						return "Invalid interaction command: " + split[1];
					else {
						if (split.length >= 3) {
							in.setParam(split[2]);
						}
						String ret = e.execute(in);
						in.setParam(null);
						return ret;
					}
				}
		} else if(split.length == 1) {
			for (Element e : elements) {
				if (e.getName().equalsIgnoreCase(split[0])) {
					return e.listHelp();
				}
			}
		}
		return "Invalid command: " + command;
	}

	private static String getCommand() {
		return s.nextLine();
	}

	private static void listActives() {
		boolean anyActive = false;
		for (Element e : elements) {
			if (e.isActive()) {
				System.out.println(e.getName() + " " + e.getStatusVerb()
						+ " ON");
				anyActive = true;
			}
		}
		if (!anyActive) {
			System.out.println("All systems are OFF");
		}
	}

	private static void listNotifications() {
		if (!hasNotifications()) {
			return;
		}
		for (Element e : elements) {
			if (e.hasNotifications())
				System.out.println(e.listNotifications());
		}
	}

	private static boolean hasNotifications() {
		for (Element e : elements) {
			if (e.hasNotifications())
				return true;
		}
		return false;
	}
}
