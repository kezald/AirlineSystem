package reservations.utilities;

import java.util.function.Function;
import reservations.models.seatdata.Seat;
import reservations.models.seatdata.SeatType;
import reservations.models.seatmaps.SeatMap;

/**
 * This class is a collection of methods that perform
 * various parts of seat reservation process that are similar
 * to both SimpleJet and RynoAir airline systems.
 * 
 * These templates help me share the reservation steps
 * among different airline systems.
 * 
 * @author Nikolai Kolbenev 15897074
 */
public class SeatReservationTemplates 
{
	/**
	 * Prints a string that notifies about failed reservations
	 * @author Nikolai Kolbenev 15897074
	 */
	public void printFailedReservation()
	{
		System.out.println("Failed to reserve a seat: no seat that matches criteria was found.");
	}
	
	/**
	 * This template reflects first two steps that both 
	 * RynoAir and SimpleJet airline systems take to reserve a seat in both
	 * economy and first classes.
	 * 
	 * This template assumes the following:
	 *  1) seatQuerer attempts to return the first seat that matches the requested seat type, 
	 *  2) in case of no match seatQuerer attempts to find any available seat in the same class
	 *     where seat search happens
	 *  3) otherwise seatQuerer returns null
	 *  
	 * The above pattern can be observed in both SimpleJet and RynoAir systems when they
	 * go through the first two steps of reserving a seat either in Economy class or in First class  
	 * 
	 * @param seatType The requested seat type to reserve
	 * @param seatQuerer any function that performs a search for a seat
	 * 		  following exactly same steps as described above 
	 * @return A Seat object that seatQuerer is able to find according to the search rules, otherwise null
	 * @author Nikolai Kolbenev 15897074
	 */
	public Seat applyFirstReservationStage(SeatMap seatMap, SeatType seatType, Function<SeatType, Seat> seatQuerer)
	{
		Seat seat = seatQuerer.apply(seatType);
		if (seat != null)
		{
			seat.setReserved(true);
			
			seatMap.printMap();
			if (seat.getSeatType() == seatType)
			{
				System.out.println("Reservation successful: " + seat.getDescription() + ".");
			}
			else
			{
				System.out.println("Requested type not found. Reserving a different seat in the same class...\n"
						+ "The following seat was reserved: " + seat.getDescription() + ".");
			}
		}
		
		return seat;
	}
	
	/**
	 * This template is an extension to "applyFirstReservationStage" template above.
	 * This is an accomodation for Economy seat reservation, which is identical
	 * in both airlines but needs a notification about falied reservation and an actual
	 * temination of that process
	 * 
	 * If first two steps in any reservation policy return no result, then the process is
	 * terminated. This will not work for first class seats because, in there, additional steps
	 * are involved.
	 * 
	 * @param seatMap The SeatMap object where seats are being reserved
	 * @param seatType The requested type of seat
	 * @return The seat search result
	 * @author Nikolai Kolbenev 15897074
	 */
	public Seat reserveEconomy(SeatMap seatMap, SeatType seatType) 
	{
		Seat seat = applyFirstReservationStage(seatMap, seatType, (requiredSeatType) -> seatMap.queryAvailableEconomyClassSeat(requiredSeatType));
		
		if (seat == null)
		{
			seatMap.printMap();
			printFailedReservation();
		}
		
		return seat;
	}
}
