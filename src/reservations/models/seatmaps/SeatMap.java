package reservations.models.seatmaps;

import java.util.function.Predicate;

import reservations.models.seatdata.Seat;
import reservations.models.seatdata.SeatType;
import reservations.utilities.PositionTranslation;

/**
 * The class represents a map of seats with data about 
 * seat layout such as number of row, columns and first class rows,
 * and data about each seat in particular.
 * 
 * @author Nikolai Kolbenev 15897074
 *
 */
public abstract class SeatMap 
{
	protected String mapModel;
	protected int numberOfRows;
	protected int numberOfColumns;
	protected int numberOfFirstClassRows;
	protected Seat[][] seatLayout;
	
	/**
	 * @param mapModel The company name (Boeing or AirBus), which
	 * indicates the plane size and hence the size of its map
	 * @author Nikolai Kolbenev 15897074
	 */
	protected SeatMap(String mapModel)
	{
		this.mapModel = mapModel;
	}
	
	/**
	 * This method is required for subclasses to determine
	 * how much space is required for seats in the sense of
	 * rows and columns. Each seat should be marked as of certain type, depending
	 * on its location.
	 * @author Nikolai Kolbenev 15897074
	 */
	public abstract void initialiseSeatMap();
	
	/**
	 * @return The company name which is the type/model of map
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getMapModel()
	{
		return this.mapModel;
	}
	
	/**
	 * Print this map to console
	 * @author Nikolai Kolbenev 15897074
	 */
	public void printMap()
	{
		System.out.println(this);
	}
	
	/** 
	 * The visual map has some characteristics that are common for all subclasses,
	 * including corners with row numbers and column symbols, which must be padded 
	 * accordingly. This is achieved in a single place.
	 * 
	 * @return A string with visual representation of 
	 * current map, seat layout, seat types and seat status
	 * @author Nikolai Kolbenev 15897074
	 */
	@Override
	public String toString()
	{
		int leftPadding = Integer.toString(numberOfRows).length();
		
		StringBuilder map = new StringBuilder(1024);
		
		String firstRowLine = formLineFirstRow(leftPadding);
		map.append(new String(new char[firstRowLine.length()]).replace("\0", "-"));
		map.append("\n" + firstRowLine + "\n");
		
		for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++)
		{
			map.append(String.format("%" + leftPadding + "s", PositionTranslation.mapIndexToRowNumber(rowIndex)));
			for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++)
			{
				map.append(String.format(" %s", seatLayout[rowIndex][columnIndex]));
			}
			map.append(System.lineSeparator());
		}
		
		return map.toString();
	}
	
	/**
	 * This method aids in making a visual map by creating
	 * the first line containing column symbols.
	 * 
	 * @param leftPadding The index at which to
	 * start measuring column symbols
	 * @return A string cotaining the fisrt line
	 * @author Nikolai Kolbenev 15897074
	 */
	private String formLineFirstRow(int leftPadding)
	{
		String firstLine = "";
		
		firstLine += new String(new char[leftPadding]).replace("\0", " ");
		
		for (int col = 0; col < numberOfColumns; col++)
		{
			int seatWidth = seatLayout[0][col].toString().length();
			firstLine += String.format("  %c%" + (seatWidth - 2) + "s", PositionTranslation.mapIndexToColumnSymbol(col), "");
		}
		
		return firstLine;
	}
	
	
	/**
	 * Translates seat markers on map to indices in array
	 * and searches for corresponding seat
	 * 
	 * @param rowNumber The row number
	 * @param columnSymbol The char symbol that denotes a column 
	 * @return The seat at specified position, if any, 
	 * otherwise null
	 * @author Nikolai Kolbenev 15897074
	 */
	public Seat getSeat(int rowNumber, char columnSymbol)
	{
		Integer rowIndex = PositionTranslation.mapRowNumberToIndex(rowNumber);
		Integer columnIndex = PositionTranslation.mapColumnSymbolToIndex(columnSymbol);
		
		if (rowIndex == null || columnIndex == null || !isValidLocation(rowIndex, columnIndex)) 
		{
			return null;
		}
		else
		{
			return seatLayout[rowIndex][columnIndex];
		}
	}
	
	/**
	 * Gets nearby free seat, starting from left and, if
	 * there is none, seat from the right is returned.
	 * 
	 * @param seat The Seat object relative to which
	 * nearby seats are traversed 
	 * @return The seat right next to the specified one
	 * @author Nikolai Kolbenev 15897074
	 */
	public Seat getNearbyFreeSeat(Seat seat)
	{
		Seat nearbySeat = getLeft(seat);
		return (nearbySeat != null) ? nearbySeat : getRight(seat);
	}
	
	/**
	 * @param seat The Seat object relative to which
	 * left seat is checked
	 * @return The left seat, if it exists, otherwise null
	 * @author Nikolai Kolbenev 15897074
	 */
	public Seat getLeft(Seat seat)
	{
		int seatRowIndex = seat.getSeatPosition().getRowIndex();
		int seatColumnIndex = seat.getSeatPosition().getColumnIndex();
		
		if (isValidLocation(seatRowIndex, seatColumnIndex) && seatColumnIndex > 0)
		{
			return seatLayout[seatRowIndex][seatColumnIndex - 1];
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * @param seat The Seat object relative to which
	 * right seat is checked
	 * @return The right seat, if it exists, otherwise null
	 * @author Nikolai Kolbenev 15897074
	 */
	public Seat getRight(Seat seat)
	{
		int seatRowIndex = seat.getSeatPosition().getRowIndex();
		int seatColumnIndex = seat.getSeatPosition().getColumnIndex();
		
		if (isValidLocation(seatRowIndex, seatColumnIndex) && seatColumnIndex < numberOfColumns - 1)
		{
			return seatLayout[seatRowIndex][seatColumnIndex + 1];
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Checks if the row and column indices are
	 * within the bound of current seat map
	 * 
	 * @param rowIndex The row index to check
	 * @param columnIndex The column index to check
	 * @return true if both row and column indices
	 * are within bounds of current seat map
	 * @author Nikolai Kolbenev 15897074
	 */
	private boolean isValidLocation(int rowIndex, int columnIndex)
	{
		boolean isValidColumn = (columnIndex >= 0 && columnIndex <= numberOfColumns - 1);
		boolean isValidRow = (rowIndex >= 0 && rowIndex <= numberOfRows - 1);
		
		if (isValidColumn && isValidRow)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/**
	 * Searches for a seat in first class, first giving priority to seats matching
	 * specified type and then choosing any available seat.
	 * 
	 * @param seatType The type of seat to give priority
	 * @return A seat in first class that has the matching type,
	 * If no such seat exists, first available seat in first class is returned,
	 * If all seats are reserved, null is returned
	 * @author Nikolai Kolbenev 15897074
	 */
	public Seat queryAvailableFirstClassSeat(SeatType seatType)
	{
		Seat seatCandidate = findSpecificSeatInRange(0, numberOfFirstClassRows - 1, seat -> !seat.isReserved() && seat.getSeatType() == seatType);
		if (seatCandidate == null)
		{
			seatCandidate = findSpecificSeatInRange(0, numberOfFirstClassRows - 1, seat -> !seat.isReserved());
		}
		
		return seatCandidate;
	}
	
	/**
	 * Searches for a seat in economy class, first giving priority to seats matching
	 * specified type and then choosing any available seat.
	 * 
	 * @param seatType The type of seat to give priority
	 * @return A seat in economy class that has the matching type,
	 * If no such seat exists, first available seat in economy is returned,
	 * If all seats are reserved, null is returned
	 * @author Nikolai Kolbenev 15897074
	 */
	public Seat queryAvailableEconomyClassSeat(SeatType seatType)
	{
		Seat seatCandidate = findSpecificSeatInRange(numberOfFirstClassRows, numberOfRows - 1, seat -> !seat.isReserved() && seat.getSeatType() == seatType);
		if (seatCandidate == null)
		{
			seatCandidate = findSpecificSeatInRange(numberOfFirstClassRows, numberOfRows - 1, seat -> !seat.isReserved());
		}
		
		return seatCandidate;
	}
	
	/**
	 * Search for a seat located between start and end row indices
	 * and having the properties that match those checked by seatValidityPolicy
	 * 
	 * @param startRowIndex The row index to begin search at
	 * @param endRowIndex The row index to stop search at
	 * @param seatValidityPolicy The predicate that takes a seat and
	 * checks if it meets all specified conditions
	 * @return The Seat object which is either a matching seat
	 * or null if no seat was found at all
	 * @author Nikolai Kolbenev 15897074
	 */
	public Seat findSpecificSeatInRange(int startRowIndex, int endRowIndex, Predicate<Seat> seatValidityPolicy)
	{
		Seat matchingSeat = null;
		
		outerloop:
		for (int row = startRowIndex; row <= endRowIndex; row++)
		{
			for (int col = 0; col < numberOfColumns; col++)
			{
				Seat seat = seatLayout[row][col];
				if (seatValidityPolicy.test(seat))
				{
					matchingSeat = seat;
					break outerloop;
				}
			}
		}
		
		return matchingSeat;
	}
	
	/**
	 * @return The last row number
	 * @author Nikolai Kolbenev 15897074
	 */
	public int getLastRowNumber()
	{
		return PositionTranslation.mapIndexToRowNumber(numberOfRows - 1);
	}

	/**
	 * @return The last column symbol
	 * @author Nikolai Kolbenev 15897074
	 */
	public int getLastColumn()
	{
		return PositionTranslation.mapIndexToColumnSymbol(numberOfColumns - 1);
	}
	
	/**
	 * @return The number of rows
	 * @author Nikolai Kolbenev 15897074
	 */
	public int getNumberOfRows() {
		return numberOfRows;
	}

	/**
	 * @return The number of columns
	 * @author Nikolai Kolbenev 15897074
	 */
	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	/**
	 * @return The number of first class rows
	 * @author Nikolai Kolbenev 15897074
	 */
	public int getNumberOfFirstClassRows() {
		return numberOfFirstClassRows;
	}
}
