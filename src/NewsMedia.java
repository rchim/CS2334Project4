import java.util.ArrayList;
import java.util.List;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
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

	/** A static final reference to a List of all the possible values of
	 * NewsMedia.
	 */
	public static final List<NewsMedia> VALUES_LIST = valuesAsList(); 
	
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
	 * @author Malachi Phillips
	 * Accessor method that returns a List of type NewsMedia of all of the
	 * possible values of this enum.
	 * 
	 * @return A List of type NewsMedia of all of the possible values of this
	 * enum.
	 */
	public static List<NewsMedia> valuesAsList()
	{
		ArrayList<NewsMedia> valuesList = new ArrayList<NewsMedia>();
		valuesList.add(NEWSPAPER);
		valuesList.add(ONLINE);
		valuesList.add(TV);
		return valuesList;
	}
}