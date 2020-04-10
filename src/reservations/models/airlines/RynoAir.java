package reservations.models.airlines;

import java.util.function.Predicate;

import reservations.models.Flight;
import reservations.models.seatdata.Seat;
import reservations.models.seatdata.SeatType;
import reservations.models.seatmaps.SeatMap;

/**
 * A RynoAir airline system class with corresponding
 * reservation policies
 * 
 * @author Nikolai Kolbenev 15897074
 *
 */
public class RynoAir extends Airline
{
	/**
	 * Creates a new instance of RynoAir object
	 * @author Nikolai Kolbenev 15897074
	 */
	public RynoAir() 
	{
		super.setAirlineName("RynoAir");
	}

	/** 
	 * All 4 steps are performed to reserve a seat.
	 * The first 2 steps are combined together and described in "applyFirstReservationStage" method of class
	 * "SeatReservationTemplates"
	 * Depending on the result of the first 2 steps, the policy may decide
	 * to go on with seat reservation and perform steps 3 and 4.
	 * Step 3 is specific to the policy of an airline system itself
	 * Step 4 is identical in both cases, which just terminates the process
	 * 
	 * @param seatType The requested seat type
	 * @param flight The Flight object with flight data
	 * @return The reserved seat, if any,
	 * otherwise null
	 * @author Nikolai Kolbenev 15897074
	 */
	@Override
	public Seat reserveFirstClass(Flight flight, SeatType seatType) 
	{
		Seat primarySeat;
		SeatMap seatMap = flight.getSeatMap();
		
		primarySeat = reservationTemplates.applyFirstReservationStage(flight.getSeatMap(), seatType, (requiredSeatType) -> seatMap.queryAvailableFirstClassSeat(requiredSeatType));
		
		if (primarySeat == null)
		{
			Predicate<Seat> searchPolicy = seat -> (!seat.isReserved() && (seat.getSeatType() == SeatType.MIDDLE)
						&& seatMap.getLeft(seat) != null && !seatMap.getLeft(seat).isReserved() 
						&& seatMap.getRight(seat) != null && !seatMap.getRight(seat).isReserved());

			primarySeat = seatMap.findSpecificSeatInRange(seatMap.getNumberOfFirstClassRows(), seatMap.getNumberOfRows() - 1, searchPolicy);
			
			if (primarySeat == null)
			{
				seatMap.printMap();
				reservationTemplates.printFailedReservation();
			}
			else
			{
				primarySeat.setReserved(true);
				seatMap.getLeft(primarySeat).setReserved(true);
				seatMap.getRight(primarySeat).setReserved(true);
				
				String reservationStatus = "All first class seats are reserved. Reserving seats from Economy class...\n\nSeats reserved: \n";
				reservationStatus += primarySeat.getDescription();
				reservationStatus += "\n" + seatMap.getLeft(primarySeat).getDescription();
				reservationStatus += "\n" + seatMap.getRight(primarySeat).getDescription();
				
				seatMap.printMap();
				System.out.println(reservationStatus);
			}
		}
		
		return primarySeat;
	}

	
	/**
	 * All 3 steps are performed to reserve a seat
	 * These are described in "reserveEconomy" method of class 
	 * "SeatReservationTemplates"
	 * 
	 * @param seatType The requested seat type
	 * @param flight The Flight object with flight data
	 * @return The reserved seat, if any,
	 * otherwise null
	 * @author Nikolai Kolbenev 15897074
	 */
	@Override
	public Seat reserveEconomy(Flight flight, SeatType seatType) 
	{
		return reservationTemplates.reserveEconomy(flight.getSeatMap(), seatType);
	}
}
