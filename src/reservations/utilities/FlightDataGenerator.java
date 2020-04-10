package reservations.utilities;

import java.util.Arrays;
import java.util.Random;

import reservations.models.Flight;
import reservations.models.airlines.*;
import reservations.models.seatdata.Seat;
import reservations.models.seatmaps.*;
import java.time.LocalTime;

/**
 * The class to help generate random
 * flight data such as random time, flight number, a random city
 * from a predefined array, random seat reservations and others.
 * 
 * @author Nikolai Kolbenev 15897074
 *
 */
public class FlightDataGenerator 
{
	private final String[] cities = { "Los Angeles", "London", "Madrid", "Bangkok", "Adelaide", "Moscow", 
			"Auckland", "Tokyo", "New York", "Sao Paulo", "Seoul/Incheon", "Paris"};
	private Random rand = new Random();
	
	/**
	 * Generates an array with random flights while calling
	 * more specialized methods that return more specific random data.
	 * This method aggregates all other methods in one.
	 * 
	 * @param number The number of random flights
	 * to be generated
	 * @return The list of random flights
	 * @author Nikolai Kolbenev 15897074
	 */
	public Flight[] getRandomFlights(int number)
	{
		Flight[] flights = new Flight[number];
		for (int i = 0; i < number; i++)
		{
			String startCity = getRandomCity(null);
			String destCity = getRandomCity(startCity);
			LocalTime departureTime = getRandomTime();
			String flightNumber = getRandomFlightNumber();
			SeatMap seatMap = getRandomSeatMapModel();
			
			flights[i] = new Flight(startCity, destCity, departureTime, flightNumber, seatMap);
			reserveRandomSeats(flights[i].getSeatMap());
		}
		
		return flights;
	}
	
	/**
	 * Randomly chooses between Boeing and
	 * AirBus companies, affecting the number of available seats
	 * 
	 * @return A randomly chosen SeatMap
	 * @author Nikolai Kolbenev 15897074
	 */
	public SeatMap getRandomSeatMapModel()
	{
		int chance = rand.nextInt(2);
		if (chance < 1)
		{
			return new BoeingSeatMap();
		}
		else
		{
			return new AirBusSeatMap();
		}
	}
	
	
	/**
	 * Gets a random city from the array of predefined cities.
	 * Since this is mostly used to get start and destination cities,
	 * a repeated value is not desired and the value of one of the cities
	 * should be ignored/excluded.
	 * 
	 * @param cityToExclude The city, in string, which is ignored 
	 * and never returned as a result
	 * @return A string from city list, containing a random city name
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getRandomCity(String cityToExclude)
	{
		String[] filteredCities;
		if (cityToExclude == null || cityToExclude.isEmpty())
		{
			filteredCities = this.cities;
		}
		else
		{
			filteredCities = 
					Arrays
					.stream(cities)
					.filter(city -> !city.equals(cityToExclude))
					.toArray(String[]::new);
		}
		
		int randomIndex = (int)(Math.random() * filteredCities.length);
		return filteredCities[randomIndex];
	}
	
	/**
	 * Generates a random 2-letter 3-digit
	 * string representation of a flight number
	 * 
	 * @return A string with flight number
	 * @author Nikolai Kolbenev 15897074
	 */
	public String getRandomFlightNumber()
	{
		String number = "";
		number += (char)(rand.nextInt(25) + 66);
		number += (char)(rand.nextInt(25) + 66);
		number += (char)(rand.nextInt(10) + 48);
		number += (char)(rand.nextInt(10) + 48);
		number += (char)(rand.nextInt(10) + 48);
		
		return number;
	}
	
	/**
	 * @return LocalTime object with randomly selected
	 * minutes and hours
	 * @author Nikolai Kolbenev 15897074
	 */
	public LocalTime getRandomTime()
	{
		int hours = rand.nextInt(24);
		int minutes = rand.nextInt(12) * 5;
		
		LocalTime time = LocalTime.of(hours, minutes);
		
		return time;
	}
	
	/**
	 * Randomly chooses between RynoAir and AirSimpleJet 
	 * airline systems, affecting seat reservation policies.
	 * 
	 * @return a randomly chosen airline system
	 * @author Nikolai Kolbenev 15897074
	 */
	public Airline getRandomAirline()
	{
		int chance = rand.nextInt(2);
		if (chance < 1)
		{
			return new RynoAir();
		}
		else
		{
			return new SimpleJet();
		}
	}

	/**
	 * The method iterates through seats on seat map
	 * and randomly chooses a seat to reserve.
	 * 
	 * @param seatMap The SeatMap object where
	 * seats are located
	 * @author Nikolai Kolbenev 15897074
	 */
	public void reserveRandomSeats(SeatMap seatMap)
	{
		for (int row = 0; row < seatMap.getNumberOfRows(); row++)
		{
			for (int col = 0; col < seatMap.getNumberOfColumns(); col++)
			{
				int rowNumber = PositionTranslation.mapIndexToRowNumber(row);
				char columnSymbol = PositionTranslation.mapIndexToColumnSymbol(col);

				Seat seat = seatMap.getSeat(rowNumber, columnSymbol);
				
				int chance = rand.nextInt(2);
				if (chance < 1)
				{
					seat.setReserved(true);
				}
			}
		}
	}
}
