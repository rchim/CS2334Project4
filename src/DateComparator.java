import java.util.Comparator;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * The DateComparator compares the dates of two news stories. If the dates are
 * equals, it considers time of publication.
 * </P>
 * <P>
 * For time of publication, newspaper stories considered to be published first
 * thing in the morning, followed by morning broadcasts for TV and morning
 * snapshots for online news sources, followed by afternoon TV broadcasts and
 * afternoon snapshots for online news sources, followed by evening TV
 * broadcasts, followed by late night TV broadcasts.
 * </P>
 * <P>
 * Note that the constructor has nothing to do, so it is omitted.
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
public class DateComparator implements Comparator<NewsStory> {
	/**
	 * We create a single comparator object for the class and make a public
	 * final field that references that object. This comparator can then be used
	 * any time it is needed, simply by using its name.
	 */
	public static final DateComparator DATE_COMPARATOR = new DateComparator();

	/**
	 * The required <code>compare</code> method for implementing
	 * <code>Comparator</code>.
	 * 
	 * @param newsStory1
	 *            The first news story to compare based on date/part of day.
	 * @param newsStory2
	 *            The second news story to compare based on date/part of day.
	 */
	@Override
	public int compare(NewsStory newsStory1, NewsStory newsStory2) {
		// First, compare the dates themselves.
		int comparison = newsStory1.getDate().compareTo(newsStory2.getDate());

		// If the dates are the same, newspaper stories come before other types.
		if (comparison == 0) {
			if (newsStory1 instanceof NewspaperStory) {
				if (newsStory2 instanceof NewspaperStory) {
					return 0;
				} else {
					return -1;
				}
			} else if (newsStory2 instanceof NewspaperStory) {
				return 1;
			}

			// If neither is a newspaper story, look at part of day.
			PartOfDay partOfDay1;
			if (newsStory1 instanceof TVNewsStory) {
				partOfDay1 = ((TVNewsStory) newsStory1).getPartOfDay();
			} else {
				partOfDay1 = ((OnlineNewsStory) newsStory1).getPartOfDay();
			}

			PartOfDay partOfDay2;
			if (newsStory2 instanceof TVNewsStory) {
				partOfDay2 = ((TVNewsStory) newsStory2).getPartOfDay();
			} else {
				partOfDay2 = ((OnlineNewsStory) newsStory2).getPartOfDay();
			}

			comparison = partOfDay1.compareTo(partOfDay2);
		}
		return comparison;
	}
}