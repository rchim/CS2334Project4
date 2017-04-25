/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * Supplies constants that refer to different sort criteria for news stories.
 * </P>
 * <P>
 * This class was written by Ryan Chimienti (ID 113392576).
 * </P>
 *
 * @author Ryan Chimienti
 */
public enum SortCriterion
{
	SOURCE, TOPIC, SUBJECT, LENGTH, DATE_TIME;
	
	/**
	 * The overridden <code>toString</code> method gives the sort criterion in
	 * "Title Case."
	 * 
	 * @return The calling sort criterion constant in title case.
	 */
	@Override
	public String toString() 
	{
		switch (this) 
		{
			case SOURCE:
				return "Source";
			case TOPIC:
				return "Topic";
			case SUBJECT:
				return "Subject";
			case LENGTH:
				return "Length";
			case DATE_TIME:
				return "Date/Time";
			default:
				throw new IllegalArgumentException();
		}
	}
}
