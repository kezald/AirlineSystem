package reservations.utilities;

/**
 * A collection of methods that supports translation of
 * seat array indices to seat markers on map. This class 
 * helps reuse code and make significant changes in one place
 * 
 * @author Nikolai Kolbenev 15897074
 */
public class PositionTranslation 
{
	/**
	 * Maps the row number to array index.
	 * Row numbers 1 to inf are mapped to indices 0 to inf
	 * 
	 * @param row The row number
	 * @return The array index
	 * @author Nikolai Kolbenev 15897074
	 */
	public static Integer mapRowNumberToIndex(int row)
	{
		if (row < 1)
		{
			return null;
		}
		else
		{
			return row - 1;
		}
	}
	
	/**
	 * Maps column symbol to array index.
	 * Letters A to Z are mapped to indices 0 - 25
	 * 
	 * @param columnSymbol The letter which represents
	 * a seat marker on map
	 * @return The array index where the marker would 
	 * be located logically
	 * @author Nikolai Kolbenev 15897074
	 */
	public static Integer mapColumnSymbolToIndex(char columnSymbol)
	{
		if (Character.getType(columnSymbol) != Character.UPPERCASE_LETTER)
		{
			return null;
		}
		else
		{
			return columnSymbol - 65;
		}
	}
	
	
	/**
	 * Maps array index to row number
	 * Array indices 0 to inf are mapped to row numbers 1 to inf
	 * 
	 * @param index The array index
	 * @return The row number 
	 * @author Nikolai Kolbenev 15897074
	 */
	public static Integer mapIndexToRowNumber(int index)
	{
		if (index < 0)
		{
			return null;
		}
		else
		{
			return index + 1;
		}
	}
	
	/**
	 * Maps array index to column symbol
	 * Indices in the range  0 - 25 are mapped to uppercase letters A - Z
	 * 
	 * @param index The array index
	 * @return The row number
	 * @author Nikolai Kolbenev 15897074
	 */
	public static Character mapIndexToColumnSymbol(int index)
	{
		char columnSymbol = (char)(index + 65);
		
		if (Character.getType(columnSymbol) == Character.UPPERCASE_LETTER)
		{
			return columnSymbol;
		}
		else
		{
			return null;
		}
	}
}
