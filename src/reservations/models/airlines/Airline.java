package reservations.models.airlines;

import reservations.models.Flight;
import reservations.models.seatdata.Seat;
import reservations.models.seatdata.SeatType;
import reservations.utilities.SeatReservationTemplates;

/**
 * An airline system class without corresponding
 * reservation policies
 * 
 * @author Nikolai Kolbenev 15897074
 *
 */
public abstract class Airline 
{
	protected SeatReservationTemplates reservationTemplates = new SeatReservationTemplates();
	private String airlineName;

	/**
	 * This method performs steps to reserve a seat in first class.
	 * RynoAir and SimpleJet classes have similar but different actions
	 * for reserving First class seats.
	 * 
	 * @param flight a Flight object with essential data about seats
	 * @param seatType The type of seat to be reserved
	 * @author Nikolai Kolbenev 15897074
	 */
	public abstract Seat reserveFirstClass(Flight flight, SeatType seatType);
	
	/**
	 * This method performs steps to reserve a seat in economy class.
	 * RynoAir and SimpleJet classes have the same implementation of this method
	 * because they use same steps in reserving economy class seats.
	 * 
	 * @param flight a Flight object with essential data about seats
	 * @param seatType The type of seat to be reserved
	 * @author Nikolai Kolbenev 15897074
	 */
	public abstract Seat reserveEconomy(Flight flight, SeatType seatType);
	
	@Override
	public String toString()
	{
		String welcomeMessage = "Welcome to the " + getAirlineName() + " Airline reservation system";
		
		return welcomeMessage;
	}
	
	/**
	 * Have the airline announce a specific flight
	 * 
	 * @param flight The flight to announce
	 * @return A string with flight announcement
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getFlightAnnouncement(Flight flight)
	{
		String announcement = 
				"Booking seats for Flight: " + flight.getFlightNumber() +
				". From \'" + flight.getStartCity() + "\'"+ 
				" to \'" + flight.getDestinationCity() + "\'" +
				". Departing at: " + flight.getDepartureTime() + "\n";
		
		announcement += flight.getSeatMap();
		
		return announcement;
	}
	
	/**
	 * @return A string with name of airline
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getAirlineName() {
		return airlineName;
	}

	/**
	 * @param airlineName A new airline name to pass in
	 * @author Nikolai Kolbenev 15897074
	 */
	protected void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
}
