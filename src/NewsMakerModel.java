import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * A <code>NewsMakerModel</code> respresents a news maker, who is the subject
 * of a <code>NewsStory</code>. A news maker may be a person or an organization.
 * A news maker consists of a name and a collection of news stories that feature
 * that news maker. There is a special <code>NewsMakerModel</code> with the name
 * "None" that is used for news stories that don't have at least two named news
 * makers. When this model's data is altered by a method call, it informs all
 * the registered action listeners by calling their
 * {@link ActionListener#actionPerformed} methods with a relevant event.
 * </P>
 * <P>
 * This class was originally written by Dr. Hougen (as NewsMaker). It was
 * modified by Ryan Chimienti (ID 113392576).
 * </P>
 * 
 * @author Dean Hougen
 * @author Ryan Chimienti
 */
class NewsMakerModel implements Comparable<NewsMakerModel>, Serializable 
{
	/**
	 * This is the first serializable version of NewsMakerModel, so we select a
	 * serialVersionUID of 1L.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List to keep track of which objects are registered to listen for events
	 * from the NewsMakerModel.
	 */
	private ArrayList<ActionListener> actionListenerList 
			= new ArrayList<ActionListener>();
	
	/** The name of the news maker. */
	private String name;

	/** The list of news stories in which the news maker is featured */
	private NewsStoryListModel newsStories = new NewsStoryListModel();

	/**
	 * The no-argument constructor for the class. Because we will often want to
	 * construct the special news maker "None," the no-arg constructor gives us
	 * this news maker.
	 */
	public NewsMakerModel() 
	{
		name = "None";
	}

	/**
	 * The general constructor for the class which takes the name of the news
	 * maker (generally the only thing we know about a news maker when the
	 * constructor is called) as an argument.
	 * 
	 * @param name
	 *            The name of the news maker.
	 */
	public NewsMakerModel(String name) 
	{
		this.name = name;
	}

	/**
	 * The accessor for the name field.
	 * <P>
	 * Note that <code>String</code> objects are immutable, so it is fine to
	 * return the field itself.
	 * </P>
	 * 
	 * @return The name of the news maker.
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * The accessor for the list of news stories.
	 * <P>
	 * Note that <code>NewsStoryListModel</code> objects are mutable, so this
	 * really should return a copy of the list instead. However, we haven't
	 * studied that yet, so returning the list itself is acceptable for now.
	 * </P>
	 * 
	 * @return The list of stories featuring the news maker.
	 */
	public NewsStoryListModel getNewsStoryListModel()
	{
		// TODO Have it return a copy instead (Eventually)
		return newsStories;
	}

	/**
	 * The mutator that adds a news story to a news maker's list of
	 * stories.
	 * <P>
	 * Note that since this list should contain only stories in which the news
	 * maker is featured, we should have this method verify that the
	 * <code>NewsMakerModel</code> object is referenced in the
	 * <code>NewsStory</code> object before the story is added to the list.
	 * However, to keep the project relatively simple, this requirement was not
	 * made in the project description and this check doesn't need to be made
	 * yet.
	 * </P>
	 * 
	 * @param newsStory
	 *            The news story to add.
	 */
	public void addNewsStory(NewsStory newsStory) 
	{
		// TODO Verify that story is about this NewsMaker (Eventually)
		newsStories.add(newsStory);
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"add news story"));
	}
	
	/**
	 * Sets this news maker's name to the supplied name.
	 * 
	 * @param name The new name for this news maker.
	 */
	public void setName(String name)
	{
		this.name = name;
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"set name"));
	}
	
	/**
	 * Sets this news maker's news story list to the supplied one.
	 * 
	 * @param newsStoryListModel The new news story list for this news maker.
	 */
	public void setNewsStoryListModel(NewsStoryListModel newsStoryListModel)
	{
		newsStories = newsStoryListModel;
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"set news story list"));
	}	
	
	/**
	 * Finds the first news story in this news maker's list that is equal to the
	 * supplied one. Removes that news story from the list.
	 * 
	 * @param newsStory The news story to be removed from this news maker's
	 * list. 
	 */
	public void removeNewsStory(NewsStory newsStory)
	{
		newsStories.remove(newsStory);
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"remove news story"));
	}
	
	/**
	 * An overridden <code>equals</code> method.
	 * <P>
	 * A <code>NewsMakerModel</code> should be equal to another object if that
	 * object is also a <code>NewsMakerModel</code> object and they have the
	 * same name.
	 * </P>
	 * 
	 * @param o
	 *            The Object to which to compare this.
	 */
	@Override
	public boolean equals(Object o) 
	{
		if (o instanceof NewsMakerModel) 
		{
			NewsMakerModel newsMaker = (NewsMakerModel) o;
			return this.name.equals(newsMaker.getName());
		}
		return false;
	}

	/**
	 * The required <code>compareTo</code> method for implementing
	 * <code>Comparable</code>. Looks at name only.
	 * 
	 * @param newsMakerModel
	 *            The other news maker to which to compare this.
	 */
	@Override
	public int compareTo(NewsMakerModel newsMakerModel) 
	{
		return this.name.compareTo(newsMakerModel.name);
	}
	
	/**
	 * The overridden toString method returns the news maker's name as a
	 * String.
	 * 
	 * @return The news maker's name as a String;
	 */
	@Override
	public String toString() 
	{
		return name;
	}
	
	/**
	 * Method to register an action event listener.
	 * 
	 * @param l The action listener to add.
	 */
	public synchronized void addActionListener(ActionListener l)
	{			
		actionListenerList.add(l);
	}
	
	/**
	 * Method to remove an action event listener.
	 * 
	 * @param l The action listener to remove.
	 */
	public synchronized void removeActionListener(ActionListener l)
	{			
		actionListenerList.remove(l);
	}
	
	/**
	 * Informs all registered action listeners of a change to the model data.
	 * 
	 * @param e The event of which to inform the action listeners.
	 */
	private void processEvent(ActionEvent e) 
	{
		for(ActionListener l : actionListenerList)
		{
			l.actionPerformed(e);
		}		
	}
}
