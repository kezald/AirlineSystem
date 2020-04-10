package reservations;

import reservations.utilities.UserSession;

/**
 *The entry point of application. Here, user session is established
 *
 *@author Nikolai Kolbenev 15897074
 */
public class AirlineReservationApplication 
{
	/**
	 * The entry point of program.
	 * User session is being established and launched.
	 * 
	 * @param args
	 * @author Nikolai Kolbenev 15897074
	 */
	public static void main(String[] args) 
	{
		UserSession userSession = UserSession.queryAboutFlight();
		userSession.startSession();
	}
}
