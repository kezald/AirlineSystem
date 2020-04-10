package reservations.models.seatmaps;

import reservations.models.seatdata.Seat;
import reservations.models.seatdata.SeatPosition;
import reservations.models.seatdata.SeatType;


/**
 * An AirBusSeatMap type that has 12 rows, 9 columns, 6 first class rows
 * 
 * @author Nikolai Kolbenev 15897074
 *
 */
public class AirBusSeatMap extends SeatMap
{
	/**
	 * Creates an instance of AirBusSeatMap
	 * @author Nikolai Kolbenev 15897074
	 */
	public AirBusSeatMap()
	{
		super("AirBus");
		super.numberOfRows = 12;
		super.numberOfColumns = 9;
		super.numberOfFirstClassRows = 6;
		
		initialiseSeatMap();
	}
	
	/**
	 * Initializes current map and layout specific to that
	 * in AirBus types of planes
	 * @author Nikolai Kolbenev 15897074
	 */
	@Override
	public void initialiseSeatMap()
	{
		super.seatLayout = new Seat[numberOfRows][numberOfColumns];
		
		for (int row = 0; row < numberOfRows; row++)
		{
			for (int col = 0; col < numberOfColumns; col++)
			{
				SeatPosition seatPosition = new SeatPosition(row, col);
				boolean isInFirstClass = (row < numberOfFirstClassRows) ? true : false;
				SeatType seatType;
				
				if (col == 0 || col == numberOfColumns - 1)
				{
					seatType = SeatType.WINDOW;
				}
				else if (col == 1 || col == 4 || col == 7)
				{
					seatType = SeatType.MIDDLE;
				}
				else
				{
					seatType = SeatType.AISLE;
				}
				
				seatLayout[row][col] = new Seat(isInFirstClass, seatType, seatPosition);
			}
		}
	}

}
