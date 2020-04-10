package reservations.utilities;

import java.util.Scanner;
import java.util.function.Consumer;

import reservations.models.Flight;
import reservations.models.airlines.*;
import reservations.models.seatdata.Seat;
import reservations.models.seatdata.SeatType;

/**
 *This class provides methods for interacting with
 *the user. It contains command processing
 *functionality and enables to query data
 *
 *@author Nikolai Kolbenev 15897074
 */
public class UserSession 
{
	private Airline airline;
	private Flight flight;
	private boolean quit = false;
	
	/**
	 * Creates an instance of UserSession
	 * 
	 * @param airline The airline to use
	 * for this session
	 * @param flight the Flight data that the user
	 * will interact with
	 * @author Nikolai Kolbenev 15897074
	 */
	public UserSession(Airline airline, Flight flight) 
	{
		super();
		this.airline = airline;
		this.flight = flight;
	}
	
	/**
	 * This method provides the user with a choice of selecting
	 * a flight from the list and selecting the airline system.
	 * When all data is determined, a new session is created. 
	 * 
	 * @return The session established with current user
	 * @author Nikolai Kolbenev 15897074
	 */
	public static UserSession queryAboutFlight()
	{
		Flight flight = null;
		Airline airline = null;
		
		FlightDataGenerator flightGenerator = new FlightDataGenerator();
		
		boolean done = false;
		while (!done)
		{
			Flight[] randomFlights = flightGenerator.getRandomFlights(6);
			for (int i = 0; i < randomFlights.length; i++)
			{
				System.out.println((i + 1) + ". " + randomFlights[i].toString());
			}
			System.out.println(randomFlights.length + 1 + ". Generate 6 random flights again\n");
			
			int userChoice = getChoiceFromUser(7, "Select the number of flight: ", null);
			if (userChoice != randomFlights.length + 1)
			{
				done = true;
				flight = randomFlights[userChoice - 1];
			}
			
			System.out.println();
		}
		
		printAirlines();
		int userChoice = getChoiceFromUser(2, "Select an airline: ", null);
		if (userChoice == 1)
		{
			airline = new RynoAir();
		}
		else if (userChoice == 2)
		{
			airline = new SimpleJet();
		}
		
		//MapDebugging.reserveSeats(flight.getSeatMap(), seat -> seat.isInFirstClass() || seat.getSeatType() == SeatType.AISLE); 		//Reserve seats for debugging purposes
		System.out.println();
		
		if (airline != null && flight != null)
		{
			return new UserSession(airline, flight);
		}
		else
		{
			return new UserSession(flightGenerator.getRandomAirline(), flightGenerator.getRandomFlights(1)[0]);
		}
	}
	
	/**
	 * Print menu of available user actions
	 * @author Nikolai Kolbenev 15897074
	 */
	public void printActionsMenu()
	{
		String actions = 
				"1. Reserve First Class\n" + 
				"2. Reserve Economy Class\n" + 
				"3. Enter Seat Manually\n" +
				"4. Show Seating Map\n" + 
				"5. Quit";
		
		System.out.println(actions);
	}
	
	/**
	 * Print menu of available seat types
	 * @author Nikolai Kolbenev 15897074
	 */
	public void printSeatTypeMenu()
	{
		String seatTypes = 
				"1. WINDOW\n" +
				"2. AISLE\n" + 
				"3. MIDDLE";
		
		System.out.println(seatTypes);
	}
	
	public static void printAirlines()
	{
		String airlines = 
				"1. RynoAir\n" +
				"2. SimpleJet";
		
		System.out.println(airlines);
	}
	
	/**
	 * This method queries the user about
	 * which actions to take, then passes those action for processing,
	 * all until the user decides to exit.
	 * @author Nikolai Kolbenev 15897074
	 */
	public void startSession()
	{
		System.out.println(airline + " (" + flight.getSeatMap().getMapModel() + " company)\n" + airline.getFlightAnnouncement(flight));
		while(!quit)
		{
			printActionsMenu();
			UserSession.getChoiceFromUser(5, "Enter a number: ", (userAction) -> processUserAction(userAction));
		}
	}
	
	/**
	 * This method responds to user actions and executes them or 
	 * suggests additional options to select from.
	 * User actions are identified by an integer, which is
	 * the line they appear on when printed. 
	 * *Enums could replace those integers.
	 * 
	 * @param userAction An integer that corresponds to
	 * user action when that action is displayed in stdout
	 * @author Nikolai Kolbenev 15897074
	 */
	public void processUserAction(int userAction)
	{
		System.out.println();

		if (userAction == 1 || userAction == 2)
		{
			printSeatTypeMenu();
			UserSession.getChoiceFromUser(3, "Which seat type? ", 
					(userChoice) -> processSeatTypeChoice(userAction, SeatType.SEAT_TYPES[userChoice - 1]));
		}
		else if (userAction == 3)
		{
			allowEnterSeatManually();
		}
		else if (userAction == 4)
		{
			flight.getSeatMap().printMap();
		}
		else if (userAction == 5)
		{
			quit = true;
		}
	}

	
	/**
	 * This method is directly related to 
	 * seat reservation process. This is where
	 * the user's seat choice is passed for 
	 * reservation 
	 * 
	 * @param userClassChoice  An integer value which is
	 * either 1 for first class or 2 for economy class.
	 * @param seatType The type of seat that user prefers
	 * @author Nikolai Kolbenev 15897074
	 */
	private void processSeatTypeChoice(int userClassChoice, SeatType seatType)
	{
		System.out.println();

		switch(userClassChoice)
		{
		case 1:
			airline.reserveFirstClass(flight, seatType);
			break;
		case 2:
			airline.reserveEconomy(flight, seatType);
			break;
		}
		
		System.out.println();
	}
	
	
	/**
	 * Allows the user to enter seat place manually.
	 * One attempt is given before the user goes back to
	 * main menu, where the user can trigger this command again
	 * This is one of the user's actions that 
	 * needs additional error checking and data validation.
	 * @author Nikolai Kolbenev 15897074
	 */
	private void allowEnterSeatManually()
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter a row number and a column symbol, seperated by dash \"1-A\": ");
		String[] userInput = scan.nextLine().trim().toUpperCase().split("-");
		
		if (userInput.length != 2 || userInput[1].length() != 1)
		{
			System.out.println("Invalid input.");
		}
		else
		{
			try
			{
				Seat chosenSeat = flight.getSeatMap().getSeat(Integer.valueOf(userInput[0]), userInput[1].charAt(0));
				if (chosenSeat != null && !chosenSeat.isReserved())
				{
					chosenSeat.setReserved(true);
					flight.getSeatMap().printMap();
					System.out.println("Seat was successfully reserved: " + chosenSeat.getDescription());
				}
				else
				{
					System.out.println("Seat is either already reserved or does not exist.");
				}
			}
			catch (Exception ex)
			{
				System.out.println("Invalid input.");
			}
		}
		System.out.println();
	}

	
	/**
	 * Gets the airline object
	 * @return the Airline object
	 * @author Nikolai Kolbenev 15897074
	 */
	public Airline getAirline() {
		return airline;
	}
	

	/**
	 * @return the Flight object
	 * @author Nikolai Kolbenev 15897074
	 */
	public Flight getFlight() {
		return flight;
	}
	
	/**
	 * There are cases when user must choose an item,
	 * action or option from selection menu. The number of options
	 * may be different and this method allows me to get a valid user choice,
	 * as an integer, from any number of options available.
	 * 
	 * @param numberOfOptions The number of options available for the
	 * user to choose from
	 * @param queryPhrase A string value that will be used to 
	 * query the user until a valid option is chosen
	 * @return An integer corresponding to a valid option, chosen
	 * by the user
	 * @author Nikolai Kolbenev 15897074
	 */
	public static int getChoiceFromUser(int numberOfOptions, String queryPhrase, Consumer<Integer> actionProcessor)
	{
		if (numberOfOptions < 1)
		{
			return 0;
		}
		
		int selectedOption = 0;
		
		Scanner scan = new Scanner(System.in);
		boolean optionSelected = false;
		while (optionSelected == false)
		{
			System.out.print(queryPhrase);
			String userChoice = scan.nextLine().trim();
			
			try
			{
				selectedOption = Integer.parseInt(userChoice);
				if (selectedOption > 0 && selectedOption <= numberOfOptions)
				{
					optionSelected = true;
				}
			}
			catch (Exception ex){}
			
			if (optionSelected == false)
			{
				System.out.println("Invalid input. Please enter a number between 1 and " + numberOfOptions + " inclusive.");
			}
		}
		
		if (actionProcessor != null)
		{
			actionProcessor.accept(selectedOption);
		}
		
		return selectedOption;
	}
}
