/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * Supplies constants that refer to different properties of a news story.
 * These are SOURCE, TOPIC, and SUBJECT. 
 * </P>
 * <P>
 * This class was written by Ryan Chimienti (ID 113392576).
 * </P>
 *
 * @author Ryan Chimienti
 */
public enum NewsContent
{
	SOURCE, TOPIC, SUBJECT;
	
	/**
	 * The overridden <code>toString</code> method gives the news content
	 * constant in "Title Case."
	 * 
	 * @return The calling news content constant in title case.
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
			default:
				throw new IllegalArgumentException();
		}
	}
}
