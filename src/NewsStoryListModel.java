import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * A <code>NewsStoryList</code> is a list of <code>NewsStory</code> objects.
 * </P>
 * <P>
 * This class was originally written by Dr. Hougen (as NewsStoryList). It was
 * modified by Ryan Chimienti (ID 113392576).
 * </P>
 * 
 * @author Dean Hougen
 * @author Ryan Chimienti
 */
class NewsStoryListModel implements Serializable 
{
	/**
	 * This is the first serializable version of NewsStoryList, so we select a
	 * serialVersionUID of 1L.
	 */
	private static final long serialVersionUID = 1L;

	/** The list of newspaper stories. */
	private DefaultListModel<NewsStory> newsStories 
			= new DefaultListModel<NewsStory>();

	/**
	 * Creates a new NewsStoryListModel with no news stories.
	 */
	public NewsStoryListModel()
	{
		
	}
	
	/**
	 * Creates a new NewsStoryListModel that is a shallow copy of the supplied
	 * one.
	 * 
	 * @param newsStoryListModel 
	 */
	public NewsStoryListModel(NewsStoryListModel newsStoryListModel)
	{
		this.newsStories = newsStoryListModel.getNewsStories();
	}
	
	/**
	 * Returns true if the list contains no news stories. Returns false
	 * otherwise.
	 * 
	 * @return True if the list contains no news stories; false otherwise. 
	 */
	public boolean isEmpty()
	{
		if(newsStories.isEmpty())
			return true;
		
		return false;
	}
	
	/**
	 * The accessor for determining the number of stories in the list.
	 * <P>
	 * Note that this accessor name violates the convention that accessor names
	 * should start with "get" (or "is" for booleans). However, "size" is an
	 * accepted convention for names serving this particular purpose, so we are
	 * following the second convention rather than the first.
	 * </P>
	 * 
	 * @return The number of stories in the list.
	 */
	public int size() 
	{
		return newsStories.size();
	}
	
	/**
	 * Returns true if and only if the list contains a news story ns such that
	 * ns.equals(<code>newsStory</code>) is true.
	 * 
	 * @param newsStory The news story to check against for equality. 
	 * @return True if the list contains a story equal to 
	 * <code>newsStory</code>; false otherwise. 
	 */
	public boolean contains(NewsStory newsStory)
	{
		if(newsStories.contains(newsStory))
			return true;
		
		return false;
	}
	
	/**
	 * An accessor for getting a story from the list based on its position
	 * (index) in the list.
	 * 
	 * @param index
	 *            The location from which to get the story.
	 * @return The newspaper story at the index, if the index is valid.
	 * @throws IllegalArgumentException
	 *             if the index is not valid.
	 */
	public NewsStory get(int index) 
	{
		if (index >= 0 && index < this.newsStories.size()) 
			return this.newsStories.get(index);
		else 
			throw new IllegalArgumentException("Index out of bounds: " + index);		
	}
	
	/**
	 * Returns the list of news stories as a DefaultListModel. This is a
	 * direct reference to the list stored in the NewsStoryListModel's
	 * instance field. Altering it WILL affect the NewsStoryListModel.
	 * 
	 * @return The list of news stories as a DefaultListModel.
	 */
	public DefaultListModel<NewsStory> getNewsStories()
	{
		return newsStories;
	}
	
	/**
	 * The mutator for adding a news story to the list.
	 * <P>
	 * By using our own class with its own <code>add</code> method, rather than
	 * directly using the <code>add</code> method of <code>ArrayList</code>, we
	 * could ensure that we don't add duplicate <code>NewsStory</code> objects
	 * to our list. However, to keep the project relatively simple, this
	 * requirement was not made in the project description and this check
	 * doesn't need to be made yet.
	 * </P>
	 * 
	 * @param newsStory
	 *            The news story to add.
	 */
	public void add(NewsStory newsStory) {
		// TODO Refine this to prevent duplicates (Eventually)
		this.newsStories.addElement(newsStory);
	}

	/**
	 * Finds the first occurrence in the list of a news story equal to the
	 * supplied news story. Removes that news story from the list.
	 * 
	 * TODO: When Dr. H fixes the UML, verify return type.
	 * 
	 * @param newsStory The news story to be removed from the list. 
	 */
	public void remove(NewsStory newsStory)
	{
		newsStories.removeElement(newsStory);
	}

	/**
	 * TODO
	 * 
	 * @param newsStories TODO 
	 */
	public void removeListOfNewsStories(DefaultListModel<NewsStory> newsStories)
	{
		// TODO
	}
	
	/**
	 * TODO
	 * 
	 * @param newsStoryListModel TODO 
	 */
	public void setNewsStories(NewsStoryListModel newsStoryListModel)
	{
		// TODO
	}
	
	/**
	 * Sets the news stories in this list to match the news stories in the given
	 * array.
	 * 
	 * @param newsStoryArray The array of news stories that this list will
	 * be made to match.
	 */
	public void setNewsStoriesFromArray(NewsStory[] newsStoryArray)
	{
		newsStories.clear();
		
		for(NewsStory newsStory : newsStoryArray)
		{
			newsStories.addElement(newsStory);
		}
	}
}
