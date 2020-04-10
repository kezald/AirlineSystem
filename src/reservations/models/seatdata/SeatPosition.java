package reservations.models.seatdata;

import reservations.utilities.PositionTranslation;

/**
 * Seat Position class that stores seat location
 * in row index and column index
 * 
 * @author Nikolai Kolbenev 15897074
 *
 */
public class SeatPosition 
{
	private int rowIndex;
	private int columnIndex;
	
	/**
	 * Sets row and column indices of current SeatPosition object
	 * 
	 * @param rowIndex The row index as an integer
	 * @param columnIndex The column index as an integer
	 * @author Nikolai Kolbenev 15897074
	 */
	public SeatPosition(int rowIndex, int columnIndex) {
		super();
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}

	/**
	 * The indices are converted into visual markers
	 * 
	 * @return A compact string with seat location
	 * on map
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getOnBoardPosition()
	{
		int rowNumber = PositionTranslation.mapIndexToRowNumber(rowIndex);
		char columnSymbol = PositionTranslation.mapIndexToColumnSymbol(columnIndex);
		
		return String.format("%d%c", rowNumber, columnSymbol);
	}

	/**
	 * @return The row index as an integer
	 * @author Nikolai Kolbenev 15897074
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @return The column index as an integer
	 * @author Nikolai Kolbenev 15897074
	 */
	public int getColumnIndex() {
		return columnIndex;
	}
	
	/**
	 * @return The row number as an integer
	 * @author Nikolai Kolbenev 15897074
	 */
	public int getRowNumber()
	{
		return PositionTranslation.mapIndexToRowNumber(rowIndex);
	}
	
	/**
	 * @return The column symbol as a character
	 * @author Nikolai Kolbenev 15897074
	 */
	public char getColumnSymbol()
	{
		return PositionTranslation.mapIndexToColumnSymbol(columnIndex);
	}
}
