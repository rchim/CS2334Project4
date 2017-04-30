/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * Supplies constants that refer to months (e.g. JANUARY).
 * </P>
 * <P>
 * This class was written by Ryan Chimienti (ID 113392576)
 *                        and Malachi Phillips (ID 112933834)
 * </P>
 *
 * @author Ryan Chimienti
 * @author Malachi Phillips
 */
public enum Month 
{	
	JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER,
	OCTOBER, NOVEMBER, DECEMBER;

	/**
	 * The overridden <code>toString</code> method gives the month in
	 * "Title Case."
	 * @author Ryan Chimienti
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
	 * Returns an int from 1-12 from the month
	 * @author Malachi Phillips
	 * @return the int corresponding to the current month
	 */
	public int toInt(){
		switch(this){
		case JANUARY:
			return 1;
		case FEBRUARY:
			return 2;
		case MARCH:
			return 3;
		case APRIL:
			return 4;
		case MAY:
			return 5;
		case JUNE:
			return 6;
		case JULY:
			return 7;
		case AUGUST:
			return 8;
		case SEPTEMBER:
			return 9;
		case OCTOBER:
			return 10;
		case NOVEMBER:
			return 11;
		case DECEMBER:
			return 12;
		default:
			return 0; // shouldn't reach here
		}
	}
	
	/**
	 * Takes an int from 1-12 and returns the corresponding month
	 * constant.
	 * @author Ryan Chimienti
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
