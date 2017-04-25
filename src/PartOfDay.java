/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * This enumerated type specifies the part of the day when something happened,
 * with the possibilities being morning, afternoon, evening, or late night.
 * </P>
 * <P>
 * This class was written by Dr. Hougen and not meaningfully modified by either
 * of the group members.
 * </P>
 * 
 * @author Dean Hougen
 * @version 1.0
 * 
 */
public enum PartOfDay 
{
	MORNING, AFTERNOON, EVENING, LATE_NIGHT;

	/**
	 * The overridden <code>toString</code> method gives the part of day in
	 * "Title Case."
	 */
	@Override
	public String toString() 
	{
		switch (this) 
		{
			case MORNING:
				return "Morning";
			case AFTERNOON:
				return "Afternoon";
			case EVENING:
				return "Evening";
			case LATE_NIGHT:
				return "Late Night";
			default:
				throw new IllegalArgumentException();
		}
	}
}
