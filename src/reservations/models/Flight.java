package reservations.models;

import reservations.models.seatmaps.SeatMap;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * This class is a representation of a flight.
 * It is the source of the most valuable object - SeatMap 
 * 
 * @author Nikolai Kolbenev 15897074
 *
 */
public class Flight 
{
	private String startCity;
	private String destinationCity;
	private LocalTime departureTime = LocalTime.MIN;
	private String flightNumber;
	private SeatMap seatMap;
	
	/**
	 * Initializes a new Flight object
	 * 
	 * @param startCity The city where the flight will start
	 * @param destinationCity The city where the flight ends
	 * @param departureTime The time when flight begins. Passengers are no longer allowed to board a plane 
	 * @param flightNumber The number of a flight
	 * @param seatMap The map with seats and seat layout
	 * @author Nikolai Kolbenev 15897074
	 */
	public Flight(String startCity, String destinationCity, LocalTime departureTime, 
			String flightNumber, SeatMap seatMap) {
		super();
		this.startCity = startCity;
		this.destinationCity = destinationCity;
		this.departureTime = departureTime;
		this.flightNumber = flightNumber;
		this.seatMap = seatMap;
	}
	
	
	/** 
	 * Provides information about this flight
	 * 
	 * @return A string containing flight details
	 * @author Nikolai Kolbenev 15897074
	 */
	public String toString()
	{
		String flight = 
				flightNumber + " " +seatMap.getMapModel() + " company. Departing at: " + departureTime +
				". From \"" + startCity + "\" to \"" + destinationCity + "\"";
	
		return flight;
	}
	
	/**
	 * @return A string with the name of a 
	 * city where flight begins
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getStartCity() {
		return startCity;
	}
	
	/**
	 * @return A string with destination city
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getDestinationCity() {
		return destinationCity;
	}
	
	/**
	 * @return A string containing departure time, formatted as 12-hour daytime
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getDepartureTime() 
	{	
		return String.format("%tI:%1$tM %1$Tp", departureTime);
	}

	/**
	 * @return A string with flight number
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @return The seat map and seat layout of current flight
	 * @author Nikolai Kolbenev 15897074
	 */
	public SeatMap getSeatMap() {
		return seatMap;
	}
}
