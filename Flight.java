package FlightApp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
public class Flight {

		private String flightNumber;
		private String airliner;
		
		private String source;
		private LocalDateTime flyDateTime;
		
		private String destination;
		private LocalDateTime arrivalDateTime;
		
		private Halt[] hault; // can be null;
		
		private boolean flyType; // domestic (false) or international(true)
		private int internationFlyTax; 
		
		private int baseFare; // always include 5% as airport surcharge and 30% as fuel cost
		private int finalCost;// based on Airliner calendar and other criteria's 
		
		private double surCharge;
		private double fuelCost;
		private double nonFriendlyTax;
		private double timeTax = 0;
		private double calendarTax ;
		
		
		public Flight(String flightNumber, String airliner, String source, LocalDateTime flyDateTime,
				String destination, LocalDateTime arrivalDateTime, boolean flyType, int internationFlyTax,
				int baseFare) {
		
			this.flightNumber = flightNumber;
			this.airliner = airliner;
			this.source = source;
			this.flyDateTime = flyDateTime;
			this.destination = destination;
			this.arrivalDateTime = arrivalDateTime;
			this.flyType = flyType;
			this.internationFlyTax = internationFlyTax;
			this.baseFare = baseFare;
		}


		public String getFlightNumber() {
			return flightNumber;
		}


		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}


		public String getAirliner() {
			return airliner;
		}


		public void setAirliner(String airliner) {
			this.airliner = airliner;
		}


		public String getSource() {
			return source;
		}


		public void setSource(String source) {
			this.source = source;
		}


		public LocalDateTime getFlyDateTime() {
			return flyDateTime;
		}


		public void setFlyDateTime(LocalDateTime flyDateTime) {
			this.flyDateTime = flyDateTime;
		}


		public String getDestination() {
			return destination;
		}


		public void setDestination(String destination) {
			this.destination = destination;
		}


		public LocalDateTime getArrivalDateTime() {
			return arrivalDateTime;
		}


		public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
			this.arrivalDateTime = arrivalDateTime;
		}


		public Halt[] getHault() {
			return hault;
		}


		public void setHault(Halt[] hault) {
			this.hault = hault;
		}


		public boolean isFlyType() {
			return flyType;
		}


		public void setFlyType(boolean flyType) {
			this.flyType = flyType;
		}


		public int getInternationFlyTax() {
			return internationFlyTax;
		}


		public void setInternationFlyTax(int internationFlyTax) {
			this.internationFlyTax = internationFlyTax;
		}


		public int getBaseFare() {
			return baseFare;
		}


		public void setBaseFare(int baseFare) {
			this.baseFare = baseFare;
		}


		public int getFinalCost() {
			return finalCost;
		}


		public void setFinalCost(int finalCost) {
			this.finalCost = finalCost;
		}

        
		public double getSurCharge() {
			return surCharge;
		}


		public void setSurCharge(double surCharge) {
			this.surCharge = surCharge;
		}


		public double getFuelCost() {
			return fuelCost;
		}


		public void setFuelCost(double fuelCost) {
			this.fuelCost = fuelCost;
		}


		public double getNonFriendlyTax() {
			return nonFriendlyTax;
		}


		public void setNonFriendlyTax(double nonFriendlyTax) {
			this.nonFriendlyTax = nonFriendlyTax;
		}


		public double getTimeTax() {
			return timeTax;
		}


		public void setTimeTax(double timeTax) {
			this.timeTax = timeTax;
		}


		public double getHolidayTax() {
			return calendarTax;
		}


		public void setHolidayTax(double holidayTax) {
			this.calendarTax = holidayTax;
		}
        
		public boolean checkFriendlyCountry(){
			
			FriendlyCountry fc=  new FriendlyCountry();
			for(String c :fc.countries) {
				if(c.equals(destination)) {
					return  true;
				}
			}
			return false;
			
		}
		public long checkTime() {
			LocalTime noonTime = LocalTime.parse("12:00:00");
			LocalTime landingTime = arrivalDateTime.toLocalTime();
			long diffsec = ChronoUnit.SECONDS.between(landingTime,noonTime);
			return diffsec;
			
		}
        public double checkAirlineCalander() {
        	double tax = 0;
        	AirLineCalender ac = new AirLineCalender();
        	LocalDate landingDate = arrivalDateTime.toLocalDate();
        	for(LocalDate ld : ac.listOfHolidays) {
        		long diffDays =  ChronoUnit.DAYS.between(landingDate,ld);
        		if(diffDays >=0 && diffDays < 2) {
        			tax = baseFare * 0.5;
        			break;
        		}
        		else if((diffDays >= 2 && diffDays <= 10)) {
    				tax = baseFare * 0.2;
    				break;
    			}
        	}
        	return tax;
        }
		public int calculateCost()
		{
			/*
			 * Cost of the flight will be decided by 
			 * 1. arrival date. :-
			 * 		1.a)  If 10 to 2 days prior to the AirlinerCalendar Date	 :- 20% extra
			 * 		1.b)  If 1 to 0 day prior to the AirlinerCalendar Date 	:-  50% extra	
			 * 2. arrival time.
			 * 		2.a) If flight land time is 2 hr +/- to 12:00 Noon (Any Date)	:- 10% extra
			 * 3. Non free travel countries (List of FriendlyCountry.java)
			 * 		3.a) If destination belongs to non free travel, friendly country :- 30% extra
			 * */
			boolean status = checkFriendlyCountry();
			if(status) {
				nonFriendlyTax = 0;
			}else {
				nonFriendlyTax = baseFare * 0.3;
			}
			long sec = checkTime();
			if(sec <= 7200 && sec >= -7200) {
				timeTax = baseFare * 0.1;
			}
			calendarTax = checkAirlineCalander();
			surCharge = baseFare * 0.05;
			fuelCost = baseFare * 0.3;
			
			finalCost =(int) (baseFare + nonFriendlyTax + timeTax + calendarTax + fuelCost + surCharge+
					internationFlyTax) ;
			return finalCost;
		}

}
