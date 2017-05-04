import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;


/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * This class provides helper methods to interact with the user.
 * </P>
 * <P>
 * Note that all methods in this class are static because we don't need to make
 * several <code>UserInterface</code> objects and have them maintain their own
 * data. Instead, we simply need a collection of useful methods to create
 * windows that pop up one at a time, are used, and then are discarded.
 * </P>
 * <P>
 * This class was originally written by Dr. Hougen. It was modified slightly by
 * both group members.
 * </P>
 * 
 * @author Dean Hougen
 * @author Malachi Phillips
 * @author Ryan Chimienti
 * @version 3.0
 *
 */
class UserInterface {

	/**
	 * This method converts an individual story to the desired display format.
	 * <P>
	 * The returned line will have a slightly different format depending on
	 * media type specified and story type.
	 * </P>
	 * <dl>
	 * <dt>Newspapers/newspapers:</dt>
	 * <dd><i>date</i>; <i>source</i>; <i>number</i> words; <i>topic</i>;
	 * <i>subject</i></dd>
	 * <dt>TV news/TV news:</dt>
	 * <dd><i>date</i>; <i>source</i>; <i>number</i> seconds; <i>topic</i>;
	 * <i>subject</i>; <i>broadcast time</i></dd>
	 * <dt>Online news/online news:</dt>
	 * <dd><i>date</i>; <i>source</i>; <i>number</i> words; <i>topic</i>;
	 * <i>subject</i>; <i>capture time</i></dd>
	 * <dt>Mixed (TV plus either or both of newspapers and online news)/TV or
	 * online:</dt>
	 * <dd><i>date</i>; <i>source</i>; <i>number</i> word equivalents;
	 * <i>topic</i>; <i>subject</i>; <i>part of day</i></dd>
	 * <dt>Mixed (TV plus either or both of newspapers and online
	 * news)/newspapers:</dt>
	 * <dd><i>date</i>; <i>source</i>; <i>number</i> word equivalents;
	 * <i>topic</i>; <i>subject</i></dd>
	 * </dl>
	 * 
	 * @param newsStory The story to convert to the display format.
	 * @param newsMedia List of types of news media present among the news
	 * stories.
	 * @return The story in the display format.
	 */
	public static String convertToOutputFormat(NewsStory newsStory,
			List<NewsMedia> newsMedia) 
	{
		String storyString;
		
		// The first item in the story string is the media type.
		if(newsStory instanceof NewspaperStory)
			storyString = "Newspaper Story; ";
		else if(newsStory instanceof TVNewsStory)
			storyString = "TV News Story; ";
		else
			storyString = "Online News Story; ";	
		
		LocalDate date = newsStory.getDate();

		// If the type doesn't include TV, use words
		if (!newsMedia.contains(NewsMedia.TV)) {
			if (newsStory instanceof NewspaperStory) {
				storyString += date.getMonth().getDisplayName(TextStyle.FULL, Locale.US) + " " + date.getDayOfMonth()
						+ ", " + date.getYear() + "; " + newsStory.getSource() + "; " + newsStory.getLength()
						+ " words; " + newsStory.getTopic() + "; " + newsStory.getSubject();
			} else if (newsStory instanceof OnlineNewsStory) {
				storyString += date.getMonth().getDisplayName(TextStyle.FULL, Locale.US) + " " + date.getDayOfMonth()
						+ ", " + date.getYear() + "; " + newsStory.getSource() + "; " + newsStory.getLength()
						+ " words; " + newsStory.getTopic() + "; " + newsStory.getSubject() + "; "
						+ ((OnlineNewsStory) newsStory).getPartOfDay().toString();
			}
		}
		// If the type is only TV news, use seconds (from length)
		else if (newsMedia.size() == 1) {
			storyString += date.getMonth().getDisplayName(TextStyle.FULL, Locale.US) + " " + date.getDayOfMonth() + ", "
					+ date.getYear() + "; " + newsStory.getSource() + "; " + newsStory.getLength() + " seconds; "
					+ newsStory.getTopic() + "; " + newsStory.getSubject() + "; "
					+ ((TVNewsStory) newsStory).getPartOfDay().toString();
		}
		// If the type is mixed, use words as common unit
		else {
			if (newsStory instanceof NewspaperStory) {
				storyString += date.getMonth().getDisplayName(TextStyle.FULL, Locale.US) + " " + date.getDayOfMonth()
						+ ", " + date.getYear() + "; " + newsStory.getSource() + "; " + newsStory.getLengthInWords()
						+ " word equivalents; " + newsStory.getTopic() + "; " + newsStory.getSubject();
			} else if (newsStory instanceof TVNewsStory) {
				storyString += date.getMonth().getDisplayName(TextStyle.FULL, Locale.US) + " " + date.getDayOfMonth()
						+ ", " + date.getYear() + "; " + newsStory.getSource() + "; " + newsStory.getLengthInWords()
						+ " word equivalents; " + newsStory.getTopic() + "; " + newsStory.getSubject() + "; "
						+ ((TVNewsStory) newsStory).getPartOfDay().toString();
			} else if (newsStory instanceof OnlineNewsStory) {
				storyString += date.getMonth().getDisplayName(TextStyle.FULL, Locale.US) + " " + date.getDayOfMonth()
						+ ", " + date.getYear() + "; " + newsStory.getSource() + "; " + newsStory.getLength()
						+ " word equivalents; " + newsStory.getTopic() + "; " + newsStory.getSubject() + "; "
						+ ((OnlineNewsStory) newsStory).getPartOfDay().toString();
			}
		}
		
		storyString += "; " + newsStory.getNewsMaker1().getName() + "; ";
		storyString += newsStory.getNewsMaker2().getName();		
		
		return storyString;
	}

}
