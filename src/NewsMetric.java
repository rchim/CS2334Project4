/**
 * Project 4, CS 2334, Section 010, May 8, 2017
 * <P>
 * Supplies constants that refer to different ways to measure a set of news
 * stories. For example, it can be measured by number of stories (COUNT)
 * or total number of words between the stories (LENGTH).
 * </P>
 * <P>
 * This class was written by Ryan Chimienti (ID 113392576).
 * </P>
 *
 * @author Ryan Chimienti
 */
public enum NewsMetric
{
	LENGTH, COUNT;
	
	/**
	 * The overridden <code>toString</code> method gives the news metric in
	 * "Title Case."
	 * 
	 * @return The calling news metric constant in title case.
	 */
	@Override
	public String toString() 
	{
		switch (this) 
		{
			case LENGTH:
				return "Length";
			case COUNT:
				return "Count";
			default:
				throw new IllegalArgumentException();
		}
	}
}