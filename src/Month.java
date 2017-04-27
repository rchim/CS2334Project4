/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * Supplies constants that refer to months (e.g. JANUARY).
 * </P>
 * <P>
 * This class was written by Ryan Chimienti (ID 113392576).
 * </P>
 *
 * @author Ryan Chimienti
 */
public enum Month 
{	
	JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER,
	OCTOBER, NOVEMBER, DECEMBER;

	/**
	 * The overridden <code>toString</code> method gives the month in
	 * "Title Case."
	 * 
	 * @return The calling month constant in title case.
	 */
	@Override
	public String toString() 
	{
		switch (this) 
		{
			case JANUARY:
				return "January";
			case FEBRUARY:
				return "February";
			case MARCH:
				return "March";
			case APRIL:
				return "April";
			case MAY:
				return "may";
			case JUNE:
				return "June";
			case JULY:
				return "July";
			case AUGUST:
				return "August";
			case SEPTEMBER:
				return "September";
			case OCTOBER:
				return "October";
			case NOVEMBER:
				return "November";
			case DECEMBER:
				return "December";
			default:
				throw new IllegalArgumentException();
		}
	}	
	
	
	/**
	 * Takes an int from 1-12 and returns the corresponding month
	 * constant.
	 * 
	 * @param integer An int from 1 to 12.
	 * @return The month constant that corresponds to the given int.
	 */
	public static Month fromInt(int integer)
	{
		switch (integer) 
		{
			case 1:
				return JANUARY;
			case 2:
				return FEBRUARY;
			case 3:
				return MARCH;
			case 4:
				return APRIL;
			case 5:
				return MAY;
			case 6:
				return JUNE;
			case 7:
				return JULY;
			case 8:
				return AUGUST;
			case 9:
				return SEPTEMBER;
			case 10:
				return OCTOBER;
			case 11:
				return NOVEMBER;
			case 12:
				return DECEMBER;
			default:
				throw new IllegalArgumentException();
		}
	}
}
