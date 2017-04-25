import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Project 3, CS 2334, Section 010, March 8, 2017
 * <P>
 * This class provides helper methods to interact with the user.
 * </P>
 * <P>
 * Note that all methods in this class are static because we don't need to make
 * several <code>UserInterface</code> objects and have them maintain their own
 * data. Instead, we simply need a collection of useful methods to create
 * windows that pop up one at a time, are used, and then are discarded.
 * </P>
 * 
 * @author Dean Hougen
 * @version 3.0
 *
 */
class UserInterface {
	// TODO: Make inputReader a public static final field

	/**
	 * This method asks the user (at the console) whatever message is passed in
	 * as a parameter. A valid response choice is any one of the options
	 * specified in the array of Strings parameter. The method loops until a
	 * valid response in given.
	 * 
	 * @param message
	 *            The message to show to the user.
	 * @param options
	 *            An array of the valid options (all Strings).
	 * @return The user's choice as a String.
	 * @throws IOException
	 *             If something goes wrong reading a line from the console.
	 */
	public static String querySingleChoice(String message, String[] options) throws IOException {
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(message);
		String userChoice = "";
		while (true) {
			userChoice = inputReader.readLine();
			for (int i = 0; i < options.length; i++) {
				if (userChoice.equals(options[i])) {
					return userChoice;
				}
			}
			System.out.println("The choice you entered is invalid. Please enter a new choice.");
		}
	}

	/**
	 * This method asks the user (at the console) whatever message is passed in
	 * as a parameter. A valid response choice is any combination of the options
	 * specified in the array of Strings parameter. It is presumed that all
	 * options are Strings of length 1. The method loops until a valid response
	 * in given.
	 * 
	 * @param message
	 *            The message to show to the user.
	 * @param options
	 *            An array of the valid options (all Strings).
	 * @return The user's choices as a String.
	 * @throws IOException
	 *             If something goes wrong reading a line from the console.
	 */
	public static String queryMultipleChoices(String message, String[] options) throws IOException {
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(message);
		String userChoices = "";

		while (true) {
			userChoices = inputReader.readLine();
			boolean match = false;

			for (int i = 0; i < userChoices.length(); i++) {
				match = false;
				for (int j = 0; j < options.length; j++) {
					if (userChoices.substring(i, i + 1).equals(options[j])) {
						match = true; // finding any match is enough
						break; // go on to the next user choice
					}
				}
				if (!match) {
					break; // if no match found for this choice, go on to the
							// next readLine
				}
			}
			if (match) { // if we get to the final match, then it all matched
				return userChoices;
			} else {
				System.out.println("The choice you entered is invalid. Please enter a new choice.");
			}
		}
	}

	/**
	 * This method asks the user for the name of a news maker using console
	 * input and returns it. It will ask for an exact or partial match,
	 * depending on the parameter <code>matchType</code>, which has "e" and "p"
	 * as its two valid options.
	 * 
	 * @param matchType
	 *            Whether the name match will be exact (e) or partial (p).
	 * @return The name provided by the user (or "None" if none provided).
	 * @throws IOException
	 *             If something goes wrong reading a line from the console.
	 */
	public static String queryNewsMakerName(String matchType) throws IOException {
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		if (matchType.equals("e")) {
			System.out.print("Newsmaker (exact)? ");
		}
		// The only options are exact and partial
		else {
			System.out.print("Newsmaker (partial)? ");
		}
		String newsMakerName = inputReader.readLine();

		// If String returned is empty (""), news maker is "None" (a special
		// value).
		if (newsMakerName.equals("")) {
			newsMakerName = "None";
		}
		return newsMakerName;
	}

	/**
	 * This method asks the user (at the console) for a sort criterion. It is
	 * called with an array of <code>String</code> objects specifying the
	 * criteria already specified. If the first array object is an empty
	 * <code>String</code> (""), it will ask about the primary sort criterion.
	 * If the first array object is a non-empty <code>String</code> but the
	 * second array object is an empty <code>String</code>, it will ask for a
	 * secondary sort criterion that isn't the sort criterion specified in the
	 * first array object. If the first two array objects are non-empty
	 * <code>String</code> but the third array object is an empty
	 * <code>String</code>, it will ask for a tertiary sort criterion that isn't
	 * one of the sort criteria specified by either of the first two array
	 * objects. If the sort criteria array provided as a parameter is invalid,
	 * it will throw an <code>IllegalArgumentException</code>. The method loops
	 * until a valid answer in given.
	 * 
	 * @param sortCriteria
	 *            The array of sort criteria already specified by the user (if
	 *            any).
	 * @return The sort criterion specified by the user.
	 * @throws IOException
	 *             If something goes wrong reading a line from the console.
	 */
	public static String querySortCriterion(String[] sortCriteria) throws IOException {
		if (sortCriteria[0] == "") {
			return querySingleChoice("Primary sort criterion is source, topic, length, or date/time (s, t, l, or d)? ",
					new String[] { "s", "t", "l", "d" });

		} else if (sortCriteria[1] == "") {
			if (sortCriteria[0].equals("s")) {
				return querySingleChoice("Secondary sort criterion is topic, length, or date/time (t, l, or d)? ",
						new String[] { "t", "l", "d" });
			} else if (sortCriteria[0].equals("t")) {
				return querySingleChoice("Secondary sort criterion is source, length, or date/time (s, l, or d)? ",
						new String[] { "s", "l", "d" });
			} else if (sortCriteria[0].equals("l")) {
				return querySingleChoice("Secondary sort criterion is source, topic, or date/time (s, t, or d)? ",
						new String[] { "s", "t", "d" });
			} else if (sortCriteria[0].equals("d")) {
				return querySingleChoice("Secondary sort criterion is source, topic, or length (s, t, or l)? ",
						new String[] { "s", "t", "l" });
			} else {
				throw new IllegalArgumentException(
						"Method querySortCriterion called with illegal sort criteria: " + sortCriteria);
			}

		} else if (sortCriteria[2] == "") {
			if ((sortCriteria[0].equals("l") && sortCriteria[1].equals("d"))
					|| (sortCriteria[1].equals("l") && sortCriteria[0].equals("d"))) {
				return querySingleChoice("Tertiary sort criterion is source or topic (s or t)? ",
						new String[] { "s", "t" });
			} else if ((sortCriteria[0].equals("t") && sortCriteria[1].equals("d"))
					|| (sortCriteria[1].equals("t") && sortCriteria[0].equals("d"))) {
				return querySingleChoice("Tertiary sort criterion is source or length (s or l)? ",
						new String[] { "s", "l" });
			} else if ((sortCriteria[0].equals("l") && sortCriteria[1].equals("t"))
					|| (sortCriteria[1].equals("l") && sortCriteria[0].equals("t"))) {
				return querySingleChoice("Tertiary sort criterion is source or date/time (s or d)? ",
						new String[] { "s", "d" });
			} else if ((sortCriteria[0].equals("s") && sortCriteria[1].equals("d"))
					|| (sortCriteria[1].equals("s") && sortCriteria[0].equals("d"))) {
				return querySingleChoice("Tertiary sort criterion is topic or length (t or l)? ",
						new String[] { "t", "l" });
			} else if ((sortCriteria[0].equals("s") && sortCriteria[1].equals("l"))
					|| (sortCriteria[1].equals("s") && sortCriteria[0].equals("l"))) {
				return querySingleChoice("Tertiary sort criterion is topic or date (t or d)? ",
						new String[] { "t", "d" });
			} else if ((sortCriteria[0].equals("s") && sortCriteria[1].equals("t"))
					|| (sortCriteria[1].equals("s") && sortCriteria[0].equals("t"))) {
				return querySingleChoice("Tertiary sort criterion is length or date (l or d)? ",
						new String[] { "l", "d" });
			} else {
				throw new IllegalArgumentException(
						"Method querySortCriterion called with illegal sort criteria: " + sortCriteria);
			}

		} else {
			throw new IllegalArgumentException(
					"Method querySortCriterion called with illegal sort criteria: " + sortCriteria);
		}

	}

	/**
	 * This method prompts the user (at the console) with whatever message is
	 * passed in through the parameter <code>message</code>. This message should
	 * ask for a filename.
	 * <P>
	 * If <code>mustExist</code> is true, this method checks whether a file with
	 * the given name exists and is readable. If so, it returns it. If not, it
	 * alerts the user to the problem and asks again.
	 * </P>
	 * <P>
	 * If <code>mustExist</code> is false, any file name is allowed. This is
	 * intended for writing to new files or existing files
	 * </P>
	 * <P>
	 * If the user hits enter without entering any filename, it will cause the
	 * program to exit. This is intended for the case where the user doesn't
	 * have a needed file after all and just wants to exit.
	 * </P>
	 * 
	 * @param message
	 *            The message with which to prompt the user.
	 * @param mustExist
	 *            A boolean specifying whether the file must exist for the name
	 *            chosen to be valid.
	 * @return The filename as a String.
	 * @throws IOException
	 *             If something goes wrong reading a line from the console.
	 */
	public static String queryFileName(String message, boolean mustExist) throws IOException {
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(message);

		while (true) {
			String fileName = inputReader.readLine();

			if ("".equals(fileName)) {
				System.out.println("Empty filename. Exiting.");
				System.exit(0);
			}

			File f = new File(fileName);
			if (mustExist == false || (f.isFile() && f.canRead())) {
				return fileName;
			} else {
				System.out.println(
						"A file with the name " + fileName + " does not exist or is not readable. Please try again.");
			}
		}
	}

	/**
	 * This method prompts the user (at the console) with whatever message is
	 * passed in through the parameter <code>message</code>, followed by the
	 * String " (y/n)?". This message is intended to ask questions to which the
	 * answer should be yes (y) or no (n) and it returns a boolean value of
	 * <code>true</code> or <code>false</code>, respectively. The method loops
	 * until a valid answer in given.
	 * 
	 * @param message
	 *            The message with which to prompt the user.
	 * @return A boolean value of true for yes or false for no.
	 * @throws IOException
	 *             If something goes wrong reading a line from the console.
	 */
	public static boolean queryBoolean(String message) throws IOException {
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(message + " (y/n)? ");
		String booleanChoice = "";
		while (true) {
			booleanChoice = inputReader.readLine();
			if (booleanChoice.equals("y")) {
				return true;
			} else if (booleanChoice.equals("n")) {
				return false;
			} else {
				System.out.println("The choice you entered is invalid. Please enter a new choice.");
			}
		}
	}

	/**
	 * This method takes a news maker and turns its list of news stories into a
	 * <code>String</code> formated for display to the user or saving to a text
	 * file. At the end it includes a line summarizing the number of stories
	 * found, the number of different news sources in which these stories were
	 * published, the total length of these stories, and the number of different
	 * topics found.
	 * <P>
	 * The summary line will have a slightly different format depending on media
	 * type specified.
	 * </P>
	 * <dl>
	 * <dt>Newspapers and/or online news:</dt>
	 * <dd>Number of Stories: <i>n1</i>; Number of Sources: <i>n2</i>; Number of
	 * Words: <i>n3</i>; Number of Topics: <i>n4</i>; Number of Subjects:
	 * <i>n5</i></dd>
	 * <dt>TV news:</dt>
	 * <dd>Number of Stories: <i>n1</i>; Number of Sources: <i>n2</i>; Seconds:
	 * <i>n3</i>; Number of Topics: <i>n4</i>; Number of Subjects:
	 * <i>n5</i></dd>
	 * <dt>Mixed (TV plus either or both of newspapers and online news):</dt>
	 * <dd>Number of Stories: <i>n1</i>; Number of Sources: <i>n2</i>; Number of
	 * Word Equivalents: <i>n3</i>; Number of Topics: <i>n4</i>; Number of
	 * Subjects: <i>n5</i></dd>
	 * </dl>
	 * 
	 * @param newsMaker
	 *            The news maker for which to create the story list as a string.
	 * @param mediaType
	 *            The type of media (newspaper, TV news, online, or mixed, given
	 *            as one or more of n, t, or o in any order) that should be
	 *            included in the list.
	 * @param sortCriteria
	 *            The primary, secondary, tertiary, and quaternary sort criteria
	 *            to use (source, topic, length, or date/time, given as s, t, l,
	 *            or d), in an array where element 0 corresponds to primary, 1
	 *            to secondary, 2 to tertiary, and 3 to quaternary.
	 * @return The list of stories as one (potentially very large) string
	 */
	public static String createListOfNewsStoriesForNewsmaker(NewsMaker newsMaker, String mediaType,
			String[] sortCriteria) {
		/* The list of stories as a String */
		String listOfStories = "";

		/*
		 * Sets to keep track of the distinct news source names and topics found
		 * (for the summary line).
		 */
		Set<String> distinctNewsSourceNames = new TreeSet<String>();
		Set<String> distinctTopics = new TreeSet<String>();
		Set<String> distinctSubjects = new TreeSet<String>();

		/* The running total of the length of the stories. */
		int totalLength = 0;

		/*
		 * A local reference to the story list so that we don't have use the
		 * accessor method repeatedly (wasting time).
		 */
		NewsStoryListModel newsStoryList = newsMaker.getNewsStories();

		// Make our own copy of the data so that we can sort it.
		List<NewsStory> newsStories = new ArrayList<NewsStory>(newsStoryList.size());

		// If all media types specified, add them all.
		if (mediaType.contains("n") && mediaType.contains("t") && mediaType.contains("o")) {
			for (int i = 0; i < newsStoryList.size(); i++) {
				newsStories.add(newsStoryList.get(i));
			}
		}
		// Otherwise, we need to check for particular media types.
		else {
			// If the user asked for newspaper stories, add those.
			if (mediaType.contains("n")) {
				for (int i = 0; i < newsStoryList.size(); i++) {
					NewsStory newsStory = newsStoryList.get(i);
					if (newsStory instanceof NewspaperStory) {
						newsStories.add(newsStory);
					}
				}
			}
			// If the user asked for TV news stories, add those.
			if (mediaType.contains("t")) {
				for (int i = 0; i < newsStoryList.size(); i++) {
					NewsStory newsStory = newsStoryList.get(i);
					if (newsStory instanceof TVNewsStory) {
						newsStories.add(newsStory);
					}
				}
			}
			// If the user asked for online news stories, add those.
			if (mediaType.contains("o")) {
				for (int i = 0; i < newsStoryList.size(); i++) {
					NewsStory newsStory = newsStoryList.get(i);
					if (newsStory instanceof OnlineNewsStory) {
						newsStories.add(newsStory);
					}
				}
			}
		}

		// Sort the list based on the user's sort criteria
		// Start with tertiary sort criterion and work to primary
		if (sortCriteria.length != 4) {
			throw new IllegalArgumentException(
					"createListOfNewsStoriesForNewsmaker called with illegal sort criteria list size: "
							+ sortCriteria.length);
		}
		for (int i = sortCriteria.length - 1; i >= 0; i--) {
			if (sortCriteria[i].equals("t")) {
				Collections.sort(newsStories);
			} else if (sortCriteria[i].equals("l")) {
				Collections.sort(newsStories, LengthComparator.LENGTH_COMPARATOR);
			} else if (sortCriteria[i].equals("d")) {
				Collections.sort(newsStories, DateComparator.DATE_COMPARATOR);
			} else if (sortCriteria[i].equals("s")) {
				Collections.sort(newsStories, SourceComparator.SOURCE_COMPARATOR);
			} else {
				throw new IllegalArgumentException(
						"createListOfNewsStoriesForNewsmaker called with illegal sort criterion: " + sortCriteria[i]);
			}
		}

		// Cycle through the stories one at a time
		for (NewsStory newsStory : newsStories) {

			// Add any new source name encountered to the set of names
			// Since sets exclude duplicates, we don't need to check
			distinctNewsSourceNames.add(newsStory.getSource());

			// Add any new topic encountered to the set of topics
			// Since sets exclude duplicates, we don't need to check
			distinctTopics.add(newsStory.getTopic());

			// Add any new topic encountered to the set of topics
			// Since sets exclude duplicates, we don't need to check
			distinctSubjects.add(newsStory.getSubject());

			// Add to the running total for length
			// If the type is TV news, use seconds (from length)
			if (mediaType.equals("t")) {
				totalLength += newsStory.getLength();
			}
			// If the type is newspaper, use words
			// If the type is online, use words
			// If the type is mixed, use words as common unit
			else {
				totalLength += newsStory.getLengthInWords();
			}

			// Convert the story to the display format and add it to the end of
			// the list
			listOfStories += convertToOutputFormat(newsStory, mediaType) + "\n";
		}

		// Construct the summary line
		// If the type doesn't include TV, use words
		if (!mediaType.contains("t")) {
			listOfStories += "Number of Stories: " + newsStories.size() + "; Number of Sources: "
					+ distinctNewsSourceNames.size() + "; Number of Words: " + totalLength + "; Number of Topics: "
					+ distinctTopics.size() + "; Number of Subjects: " + distinctSubjects.size();
		}
		// If the type is TV news, use seconds (from length)
		else if (mediaType.equals("t")) {
			listOfStories += "Number of Stories: " + newsStories.size() + "; Number of Sources: "
					+ distinctNewsSourceNames.size() + "; Seconds: " + totalLength + "; Number of Topics: "
					+ distinctTopics.size() + "; Number of Subjects: " + distinctSubjects.size();
		}
		// If the type is mixed, use words as common unit
		else {
			listOfStories += "Number of Stories: " + newsStories.size() + "; Number of Sources: "
					+ distinctNewsSourceNames.size() + "; Number of Word Equivalents: " + totalLength
					+ "; Number of Topics: " + distinctTopics.size() + "; Number of Subjects: "
					+ distinctSubjects.size();
		}
		return listOfStories;
	}

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
	 * @param newsStory
	 *            The story to convert to the display format.
	 * @param mediaType
	 *            The type of media (newspaper, TV news, online, or mixed, given
	 *            as one or more of n, t, or o in any order) that should be
	 *            included in the list.
	 * @return The story in the display format.
	 */
	private static String convertToOutputFormat(NewsStory newsStory, String mediaType) {

		// TODO: Append each line with subject and (for TV and online) part of
		// day.
		String storyString = "";
		LocalDate date = newsStory.getDate();

		// If the type doesn't include TV, use words
		if (!mediaType.contains("t")) {
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
		// If the type is TV news, use seconds (from length)
		else if (mediaType.equals("t")) {
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
		return storyString;
	}

	/**
	 * This method displays the list of news stories to the user at the console.
	 * 
	 * @param listOfStories
	 *            The list of stories to display, all as one (potentially very
	 *            large) String.
	 * @param newsMakerName
	 *            The name of the news maker (which is put into the title of the
	 *            JOptionPane).
	 */
	public static void presentNewspaperStories(String listOfStories, String newsMakerName) {
		System.out.println("News stories for " + newsMakerName);
		System.out.println(listOfStories);
	}

	/**
	 * This method reports at the console that there were no relevant stories
	 * found.
	 * <P>
	 * "There are no relevant stories about <i>news maker</i> in this database."
	 * </P>
	 * 
	 * @param queriedNewsMaker
	 *            The news maker the user searched for.
	 */
	public static void reportNewsmakerNotFound(NewsMaker queriedNewsMaker) {
		System.out.println("There are no relevant stories about " + queriedNewsMaker.getName() + " in this database.");
	}

	/**
	 * This method displays a pie chart for a given news maker. The chart will
	 * display different information based on the media type(s) (newspaper, TV
	 * news, and/or online), content (source, topic, or subject), and measure
	 * (length or count) chosen.
	 * 
	 * @param newsMaker
	 *            The news maker whose stories are to be charted.
	 * @param mediaType
	 *            The type(s) of media to chart.
	 * @param content
	 *            The content field to chart.
	 * @param measure
	 *            The measure to use in determining how to weight each story in
	 *            the chart.
	 */
	public static void displayPieChartForNewsMaker(NewsMaker newsMaker, String mediaType, String content,
			String measure) {
		NewsStoryListModel newsStoryList = newsMaker.getNewsStories();

		/* List to hold a copy of the relevant data. */
		List<NewsStory> selectedNewsStories = new ArrayList<NewsStory>();

		// Select the news stories of the media type(s) requested.
		for (int i = 0; i < newsStoryList.size(); i++) {
			NewsStory newsStory = newsStoryList.get(i);
			if ((mediaType.contains("n") && newsStory instanceof NewspaperStory)
					|| (mediaType.contains("t") && newsStory instanceof TVNewsStory)
					|| (mediaType.contains("o") && newsStory instanceof OnlineNewsStory)) {
				selectedNewsStories.add(newsStory);
			}
		}

		/*
		 * Map to keep track of the items found and the quantity for each (for
		 * the pie chart wedges). Note that the items could be sources, topics,
		 * or subjects and the quantity could be measured by length or count.
		 */
		Map<String, Integer> itemNameQuantityMap = new TreeMap<String, Integer>();

		/* Need total to determine percentage. */
		int totalQuantity = 0;

		// Run through all the selected stories to add up values.
		for (NewsStory newsStory : selectedNewsStories) {

			/* Get items of the correct content type. */
			String itemName = null;
			if ("s".equals(content)) {
				itemName = newsStory.getSource();
			} else if ("t".equals(content)) {
				itemName = newsStory.getTopic();
			} else if ("b".equals(content)) {
				itemName = newsStory.getSubject();
			} // TODO: Check for invalid content type

			/*
			 * Need variable to hold quantity of item. If this item has not been
			 * added previously, we'll want to add it to the map with a starting
			 * quantity. If it is already in the map, we'll want to add to its
			 * previous quantity.
			 */
			Integer itemQuantity = itemNameQuantityMap.get(itemName);

			// If the measure is count, we'll just add one.
			if ("c".equals(measure)) {
				if (itemQuantity == null) {
					itemNameQuantityMap.put(itemName, 1);
				} else {
					itemNameQuantityMap.put(itemName, itemQuantity + 1);
				}
				totalQuantity++;
			}
			// If the measure is length, we'll need to add the length.
			else if ("l".equals(measure)) {
				int addedQuantity = newsStory.getLengthInWords();
				if (itemQuantity == null) {
					itemNameQuantityMap.put(itemName, addedQuantity);
				} else {
					itemNameQuantityMap.put(itemName, itemQuantity + addedQuantity);
				}
				totalQuantity += addedQuantity;
			} // TODO: Check for invalid measure count.
		}

		/* List of pie wedges to put in the chart. */
		List<Wedge> wedges = new ArrayList<Wedge>();

		/*
		 * The scaling factor takes into account the total quantity and the fact
		 * that wedges require percentages.
		 */
		double scale = totalQuantity / 100.0;

		// Take the data from the map and put it into the list of wedges.
		for (Map.Entry<String, Integer> entry : itemNameQuantityMap.entrySet()) {
			wedges.add(new Wedge(entry.getValue() / scale, entry.getKey()));
		}

		// The title always starts with the news maker's name.
		String titleString = newsMaker.getName() + " - ";

		// If not all media types are selected, specify those that are.
		if (mediaType.length() < 3) {
			if (mediaType.contains("n")) {
				titleString += "Newspaper";
				if (mediaType.length() > 1) {
					titleString += "/";
				} else {
					titleString += " ";
				}
			}
			if (mediaType.contains("t")) {
				titleString += "TV News";
				if (mediaType.length() > 2) {
					titleString += "/";
				} else {
					titleString += " ";
				}
			}
			if (mediaType.contains("o")) {
				titleString += "Online ";
			}
		}

		// Specify the content selected.
		if ("s".equals(content)) {
			titleString += "Sources ";
		} else if ("t".equals(content)) {
			titleString += "Topics ";
		} else if ("b".equals(content)) {
			titleString += "Subjects ";
		}

		// Specify the measure selected.
		if (measure.equals("l")) {
			titleString += "by Length";
		} else if (measure.equals("c")) {
			titleString += "by Count";
		}

		// Create the actual pie chart.
		new PieChart(titleString, wedges);
	}
}
