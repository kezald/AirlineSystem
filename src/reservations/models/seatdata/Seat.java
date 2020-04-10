package reservations.models.seatdata;

/**
 * The Seat class that stores data about seat state,
 * position, type and provides methods to get this data
 * in formatted style 
 * 
 * @author Nikolai Kolbenev 15897074
 *
 */
public class Seat 
{
	private boolean isReserved;
	private boolean isInFirstClass;
	private SeatType seatType;
	private SeatPosition seatPosition;

	/**
	 * Creates a Seat object
	 * 
	 * @param isInFirstClass A boolean value 
	 * that denotes seat class
	 * @param seatType The SeatType value
	 * that denotes seat type
	 * @param seatPosition The SeatPosition value
	 * that denotes seat position
	 * @author Nikolai Kolbenev 15897074
	 */
	public Seat(boolean isInFirstClass, SeatType seatType, SeatPosition seatPosition)
	{
		this.isReserved = false;
		this.isInFirstClass = isInFirstClass;
		this.seatType = seatType;
		this.seatPosition = seatPosition;
	}
	
	/** 
	 * This is used to make a visual map of seats in plane
	 * 
	 * @return A string with shortened version of current seat state
	 * @author Nikolai Kolbenev 15897074
	 */
	@Override
	public String toString()
	{
		char seatTypeLetter = this.seatType.getTypeAsLetter();
		
		String seatDisplay = "[";
		seatDisplay += (isInFirstClass()) ? Character.toUpperCase(seatTypeLetter) : Character.toLowerCase(seatTypeLetter);
		seatDisplay += (isReserved) ? " X " : " _ ";
		seatDisplay += "]";
		
		return seatDisplay;
	}
	
	/**
	 * This is used to give the user visual 
	 * feedback about which seat is reserved  
	 * 
	 * @return A string with detailed description
	 * of current seat state
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getDescription()
	{
		String description = "";
		description += (isInFirstClass) ? "First class " : "Economy class ";
		description += seatType.toString();
		description += " seat at: " + seatPosition.getOnBoardPosition();
		//description += (isReserved) ? " is reserved." : " is not reserved.";
		
		return description;
	}
	
	/**
	 * @return true if current seat is reserved,
	 * otherwise false
	 * @author Nikolai Kolbenev 15897074
	 */
	public boolean isReserved() {
		return isReserved;
	}

	/**
	 * @param isReserved A boolean value that 
	 * determines whether this seat must be reserved or not
	 * @author Nikolai Kolbenev 15897074
	 */
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	/**
	 * @return A boolean value that indicates whether this seat is in first class
	 * @author Nikolai Kolbenev 15897074
	 */
	public boolean isInFirstClass() {
		return isInFirstClass;
	}

	/**
	 * @return The SeatType object of this seat
	 * @author Nikolai Kolbenev 15897074
	 */
	public SeatType getSeatType() {
		return seatType;
	}

	/**
	 * @return The SeatPosition object of this seat
	 * @author Nikolai Kolbenev 15897074
	 */
	public SeatPosition getSeatPosition() {
		return seatPosition;
	}
}
