import java.util.Comparator;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * The LengthComparator compares the lengths of two news stories. Since
 * different types of news stories may use different units to measure their
 * lengths, we compare by converting the lengths to a common unit (words).
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
public class LengthComparator implements Comparator<NewsStory> {
	/**
	 * We create a single comparator object for the class and make a public
	 * final field that references that object. This comparator can then be used
	 * any time it is needed, simply by using its name.
	 */
	public static final LengthComparator LENGTH_COMPARATOR = new LengthComparator();

	/**
	 * The required <code>compare</code> method for implementing
	 * <code>Comparator</code>.
	 * 
	 * @param newsStory1
	 *            The first news story to compare based on length.
	 * @param newsStory2
	 *            The second news story to compare based on length.
	 */
	@Override
	public int compare(NewsStory newsStory1, NewsStory newsStory2) {
		return newsStory1.getLengthInWords() - newsStory2.getLengthInWords();
	}

}
