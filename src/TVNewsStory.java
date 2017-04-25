import java.io.Serializable;
import java.time.LocalDate;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * A <code>TVNewsStory</code> is composed of the date the story was broadcast,
 * <b>the part of the day when the broadcast happened,</b> the name of the TV
 * news show that broadcast the story, the length the story in seconds, the
 * broad topic for the story, the specific subject matter of the story, and two
 * lead news makers in the story.
 * </P>
 * <P>
 * Since most of these elements are present in <code>NewsStory</code>,
 * <code>TVNewsStory</code> is created as a subclass of <code>NewsStory</code>.
 * However, <code>TVNewsStory</code> can implement <code>getLengthInWords</code>
 * because we know the inherent length measurement units for TV news stories
 * (seconds), whereas different news stories may use different length units.
 * <b>In addition, each <code>TVNewsStory</code> includes the part of the day
 * when the broadcast happened (morning, afternoon, evening, or late night),
 * which isn't true for all types of <code>NewsStory</code>. For this reason, an
 * additional field (<code>partOfDay</code>) and a corresponding accessor method
 * (<code>getPartOfDay()</code>) is included in <code>TVNewsStory</code>.</b>
 * </P>
 * <P>
 * This class was originally written by Dr. Hougen. It was modified by Ryan
 * Chimienti (ID 113392576).
 * </P>
 *  
 * @author Dean Hougen
 * @author Ryan Chimienti 
 */
public class TVNewsStory extends NewsStory implements Serializable
{
	/** 
	 * A code to ensure that a serialized TVNewsStory can be instantiated as a
	 * TVNewsStory according to the current class design.
	 */
	private static final long serialVersionUID = 1L;	
	
	/**
	 * The part of the day that the news story was broadcast (morning,
	 * afternoon, evening, or late night).
	 */
	private PartOfDay partOfDay;

	/**
	 * The constructor which takes parameters for all of the fields can simply
	 * pass most of them to the constructor for <code>NewsStory</code> and let
	 * it do most of the work. It only needs to handle <code>partOfDay</code>
	 * itself.
	 * 
	 * @param date
	 *            The date the story was published as a java.time.LocalDate.
	 * @param source
	 *            The name of the source in which the story was published.
	 * @param length
	 *            The length of the story.
	 * @param topic
	 *            The broad topic of the story.
	 * @param subject
	 *            The specific subject matter of the story.
	 * @param partOfDay
	 *            The part of the day during which the story was broadcast.
	 * @param newsMaker1
	 *            The first news maker featured in the story.
	 * @param newsMaker2
	 *            The second news maker featured in the story.
	 */
	public TVNewsStory(LocalDate date, String source, int length,
			String topic, String subject, PartOfDay partOfDay,
			NewsMakerModel newsMaker1, NewsMakerModel newsMaker2) 
	{
		super(date, source, length, topic, subject, newsMaker1, newsMaker2);
		this.partOfDay = partOfDay;
	}

	/**
	 * Overrides the <code>getLengthInWords</code> method from
	 * <code>NewsStory</code>.
	 * <P>
	 * Because the inherent length measurement units for TV news stories are
	 * seconds, we need to convert the value we get from <code>getLength</code>.
	 * The design says we should use the conversion factor of 150 words per
	 * minute. (Of course, a minute is 60 seconds, so this is a conversion
	 * factor of 150/60.)
	 * </P>
	 * 
	 * @see NewsStory#getLengthInWords()
	 */
	@Override
	public int getLengthInWords() 
	{
		return this.getLength() * 150 / 60;
	}

	/**
	 * Accessor method for the part of the day when the broadcast occurred.
	 * 
	 * @return The part of the day when the broadcast occurred.
	 */
	public PartOfDay getPartOfDay() 
	{
		return partOfDay;
	}

	/**
	 * Overridden equals method to account for possible differences in
	 * <code>partOfDay</code>.
	 * <P>
	 * Note that we let the method in the parent do as much work as possible to
	 * reduce code duplication.
	 * </P>
	 * 
	 * @param o
	 *            The object to which to compare this.
	 */
	@Override
	public boolean equals(Object o) 
	{
		if (o instanceof TVNewsStory) 
		{
			if (super.equals(o))
				return this.partOfDay.equals(((TVNewsStory) o).partOfDay);
			else 
				return false;			
		}
		// If it isn't a TVNewsStory, it's not equivalent.
		else 
		{
			return false;
		}
	}
}
