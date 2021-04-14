package FlightApp;
import java.time.LocalDateTime;
import java.util.Scanner;
public class TravelApp {
	public static void main(String[] args) {
		Flight flight = new Flight("12345","IndiGo","DEL",LocalDateTime.parse("2021-03-24T07:30:15.40"),"HYD",LocalDateTime.parse("2021-03-24T09:40:20.23"),true,1200,2300);
		System.out.println("Enter the number of Stops:");
		Scanner sc = new Scanner(System.in);
		int stops = Integer.parseInt(sc.nextLine());
		Halt halt[] = new Halt[stops];
		for(int i = 0; i < stops; i++) {
			Halt h = new Halt();
			
			System.out.println("Enter Airport Name");
			String name = sc.nextLine();
			h.setAirportName(name);
			
			System.out.println("Enter duration");
			int dur = Integer.parseInt(sc.nextLine());
			h.setDuration(dur);
			
			halt[i] = h;
		}
		sc.close();
		flight.setHault(halt);
		flight.calculateCost();
		FlightDetails flightDetails = new FlightDetails();
		flightDetails.printFlightDetails(flight);
		
	}

}
