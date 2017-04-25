import java.util.List;

/**
 * Project 4, CS 2334, Section 010, May 8, 2017
 * <P>
 * Supplies constants that refer to different mediums for news stories.
 * </P>
 * <P>
 * This class was written by Ryan Chimienti (ID 113392576).
 * </P>
 *
 * @author Ryan Chimienti
 */
public enum NewsMedia 
{	
	NEWSPAPER, ONLINE, TV;

	// TODO Incorporate VALUES_LIST.
	
	/**
	 * The overridden <code>toString</code> method gives the news medium in
	 * "Title Case."
	 * 
	 * @return The calling news medium constant in title case.
	 */
	@Override
	public String toString() 
	{
		switch (this) 
		{
			case NEWSPAPER:
				return "Newspaper";
			case ONLINE:
				return "Online";
			case TV:
				return "TV";
			default:
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * TODO
	 * 
	 * @return TODO
	 */
	public List<NewsMedia> valuesAsList()
	{
		// TODO
		
		return null;
	}
}