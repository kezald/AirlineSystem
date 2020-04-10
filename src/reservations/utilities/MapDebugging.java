package reservations.utilities;

import java.util.function.Predicate;

import reservations.models.seatdata.Seat;
import reservations.models.seatmaps.SeatMap;

/**
 * The initial purpose of this class was to
 * aggregate methods useful for debugging a map of seats
 * 
 * @author Nikolai Kolbenev 15897074
 *
 */
public class MapDebugging 
{
	/**
	 * Reserves specific seats on the map. 
	 * Only seats that match a patter, or supplied condition, will be reserved,
	 * others will be freed.  
	 * 
	 * @param seatMap The seatMap object with seats
	 * @param reservationPattern The supplied condition to get 
	 * each seat, test it and decide whether it needs to be reserved
	 * @author Nikolai Kolbenev 15897074
	 */
	public static void reserveSeats(SeatMap seatMap, Predicate<Seat> reservationPattern)
	{
		for (int row = 0; row < seatMap.getNumberOfRows(); row++)
		{
			for (int col = 0; col < seatMap.getNumberOfColumns(); col++)
			{
				int rowNumber = PositionTranslation.mapIndexToRowNumber(row);
				char columnSymbol = PositionTranslation.mapIndexToColumnSymbol(col);
				
				Seat seat = seatMap.getSeat(rowNumber, columnSymbol);
				if (reservationPattern.test(seat))
				{
					seat.setReserved(true);
				}
				else
				{
					seat.setReserved(false);
				}
			}
		}
	}
}
