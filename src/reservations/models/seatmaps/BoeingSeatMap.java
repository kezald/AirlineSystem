package reservations.models.seatmaps;

import reservations.models.seatdata.Seat;
import reservations.models.seatdata.SeatPosition;
import reservations.models.seatdata.SeatType;

/**
 * A Boeing type that has 10 rows, 7 columns, 4 first class rows
 * 
 * @author Nikolai Kolbenev 15897074
 *
 */
public class BoeingSeatMap extends SeatMap
{
	/**
	 * Creates an instance of BoeingSeatMap
	 * @author Nikolai Kolbenev 15897074
	 */
	public BoeingSeatMap()
	{
		super("Boeing");
		super.numberOfRows = 10;
		super.numberOfColumns = 7;
		super.numberOfFirstClassRows = 4;
		
		initialiseSeatMap();
	}
	
	/**
	 * Initializes current map and layout specific to that
	 * in Boeing types of planes
	 * @author Nikolai Kolbenev 15897074
	 */
	@Override
	public void initialiseSeatMap()
	{
		super.seatLayout = new Seat[numberOfRows][numberOfColumns];
		//Arrays.stream(seatLayout);
	
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
				else if (col == 3)
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
