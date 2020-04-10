package reservations.models.seatdata;

/**
 * Enumeration with seat types i.e.
 * the location of a seat
 * 
 * @author Nikolai Kolbenev 15897074 
 *
 */
public enum SeatType 
{
	WINDOW, AISLE, MIDDLE;
	public static final SeatType[] SEAT_TYPES = SeatType.values();
	
	/**
	 * @return A character which is a short version of current enumeration object
	 * @author Nikolai Kolbenev 15897074
	 */
	public char getTypeAsLetter()
	{
		char type;
		
		switch(this)
		{
		case AISLE:
			type = 'a';
			break;
		case MIDDLE:
			type = 'm';
			break;
		case WINDOW:
			type = 'w';
			break;
		default:
			type = '?';
			break;
		}

		return type;
	}
}
