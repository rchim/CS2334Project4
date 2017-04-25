import java.util.Comparator;

/**
 * Project 4, CS 2334, Section 010, May 8, 2017
 * <P>
 * The SubjectComparator compares the sources of two news stories.
 * </P>
 * <P>
 * Note that the constructor has nothing to do, so it is omitted.
 * </P>
 * <P>
 * This class was written by Dr. Hougen (as SourceComparator) and modified by
 * Ryan Chimienti (ID 113392576).
 * </P>
 * 
 * @author Dean Hougen
 * @author Ryan Chimienti 
 */
public class SubjectComparator implements Comparator<NewsStory> 
{
	/**
	 * We create a single comparator object for the class and make a public
	 * final field that references that object. This comparator can then be used
	 * any time it is needed, simply by using its name.
	 */
	public static final SubjectComparator SUBJECT_COMPARATOR 
			= new SubjectComparator();

	/**
	 * The required <code>compare</code> method for implementing
	 * <code>Comparator</code>.
	 * 
	 * @param newsStory1
	 *            The first news story to compare based on subject.
	 * @param newsStory2
	 *            The second news story to compare based on subject.
	 */
	@Override
	public int compare(NewsStory newsStory1, NewsStory newsStory2) 
	{
		return newsStory1.getSubject().compareTo(newsStory2.getSubject());
	}
}