import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.DefaultListModel;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * A NewsDataBaseModel acts as an overarching model for the application. It
 * encompasses a list of news makers and a list of news stories, as well as code
 * maps for news story sources, topics, and subjects. When its data is altered
 * by a method call, it informs all the registered action listeners by calling
 * their {@link ActionListener#actionPerformed} methods with a relevant event.
 * </P>
 * <P>
 * This class was written by Ryan Chimienti (ID 113392576).
 * </P>
 * 
 * @author Ryan Chimienti
 */
public class NewsDataBaseModel implements Serializable
{
	/**
	 * This is the first serializable version of NewsDataBaseModel, so we
	 * select a serialVersionUID of 1L.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * List to keep track of which objects are registered to listen for events
	 * from the NewsDataBaseModel.
	 */
	private ArrayList<ActionListener> actionListenerList 
			= new ArrayList<ActionListener>();
	
	/** A map from source codes to decoded source Strings. */
	private Map<String, String> newsSourceMap;
	
	/** A map from topic codes to decoded topic Strings. */
	private Map<String, String> newsTopicMap;
	
	/** A map from topic codes to decoded topic Strings. */
	private Map<String, String> newsSubjectMap;
	
	/** 
	 * The special news maker who is only assigned to stories without any lead
	 * news makers.
	 */
	NewsMakerModel none = new NewsMakerModel();
	
	/** A list of news makers. */
	private NewsMakerListModel newsMakerListModel;
	
	/** A list of news stories. */
	private NewsStoryListModel newsStoryListModel;
	
	/**
	 * Initializes the model with an empty news maker list and an empty news
	 * story list.
	 */
	public NewsDataBaseModel()
	{
		this.newsMakerListModel = new NewsMakerListModel();
		this.newsStoryListModel = new NewsStoryListModel();
	}
	
	/**
	 * Initializes the model with the given news maker list and the given news
	 * story list.
	 * 
	 * @param newsMakerListModel The news maker list with which to initialize
	 * this news database model.
	 * @param newsStoryListModel The news story list with which to initialize 
	 * this news database model.
	 */
	public NewsDataBaseModel(NewsMakerListModel newsMakerListModel,
			NewsStoryListModel newsStoryListModel)
	{
		this.newsMakerListModel = newsMakerListModel;
		this.newsStoryListModel = newsStoryListModel;
	}
	
	/**
	 * The accessor for the map from source codes to decoded source Strings.
	 * 
	 * @return The map from source codes to decoded source Strings.
	 */
	public Map<String, String> getNewsSourceMap()
	{
		return newsSourceMap;
	}
	
	/**
	 * Returns an array containing the decoded source Strings.
	 * 
	 * @return An array containing the decoded source Strings.
	 */
	public String[] getNewsSources()
	{
		return newsSourceMap.values().toArray(new String[0]);				
	}
	
	/**
	 * The mutator for the map from source codes to decoded source Strings.
	 * 
	 * @param newsSourceMap The map from source codes to decoded source Strings.
	 */
	public void setNewsSourceMap(
			Map<String, String> newsSourceMap)
	{
		this.newsSourceMap = newsSourceMap;
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"set source map"));
	}
	
	/**
	 * The accessor for the map from topic codes to decoded topic Strings.
	 * 
	 * @return The map from topic codes to decoded topic Strings.
	 */
	public Map<String, String> getNewsTopicMap()
	{
		return newsTopicMap;
	}
	
	/**
	 * Returns an array containing the decoded topic Strings.
	 * 
	 * @return An array containing the decoded topic Strings.
	 */
	public String[] getNewsTopics()
	{
		return newsTopicMap.values().toArray(new String[0]);				
	}
	
	/**
	 * The mutator for the map from topic codes to decoded topic Strings.
	 * 
	 * @param The map from topic codes to decoded topic Strings.
	 */
	public void setNewsTopicMap(
			Map<String, String> newsTopicMap)
	{
		this.newsTopicMap = newsTopicMap;
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"set topic map"));
	}
	
	/**
	 * The accessor for the map from subject codes to decoded subject Strings.
	 * 
	 * @return The map from subject codes to decoded subject Strings.
	 */
	public Map<String, String> getNewsSubjectMap()
	{
		return newsSubjectMap;
	}
	
	/**
	 * Returns an array containing the decoded subject Strings.
	 * 
	 * @return An array containing the decoded subject Strings.
	 */
	public String[] getNewsSubjects()
	{
		return newsSubjectMap.values().toArray(new String[0]);				
	}
	
	/**
	 * The mutator for the map from subject codes to decoded subject Strings.
	 * 
	 * @param The map from subject codes to decoded subject Strings.
	 */
	public void setNewsSubjectMap(
			Map<String, String> newsSubjectMap)
	{
		this.newsSubjectMap = newsSubjectMap;
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"set subject map"));
	}
	
	/**
	 * Returns true if there are no news makers in the database. Otherwise
	 * returns false.
	 * 
	 * @return True if there are no news makers in the database; false
	 * otherwise. 
	 */
	public boolean newsMakerListIsEmpty()
	{
		return newsMakerListModel.isEmpty();
	}
	
	/**
	 * Returns true if the supplied news maker is the database's news maker
	 * list. Returns false otherwise.
	 * 
	 * @param newsMakerModel The news maker to check for in the list.
	 * @return True if the supplied news maker is the database's news maker
	 * list; false otherwise.
	 */
	public boolean containsNewsMakerModel(NewsMakerModel newsMakerModel)
	{
		return newsMakerListModel.contains(newsMakerModel);
	}
	
	/**
	 * The accessor method for the database's news maker list.
	 * 
	 * @return The database's news maker list.
	 */
	public NewsMakerListModel getNewsMakerListModel()
	{
		return newsMakerListModel;
	}
	
	/**
	 * Returns an array containing the names of all the news makers in the
	 * database.
	 * 
	 * @return An array containing the names of all the news makers in the
	 * database.
	 */
	public String[] getNewsMakerNames()
	{
		return newsMakerListModel.getNewsMakerNames();
	}
	
	/**
	 * Returns a DefaultListModel containing all the news makers in the
	 * database.
	 * 
	 * @return A DefaultListModel containing all the news makers in the
	 * database.
	 */
	public DefaultListModel<NewsMakerModel> getNewsMakers()
	{
		return newsMakerListModel.getNewsMakers();
	}
	
	/**
	 * The mutator method for the database's news maker list.
	 * 
	 * @param newsMakerListModel The new news maker list.
	 */
	public void setNewsMakerListModel(NewsMakerListModel newsMakerListModel) 
	{
		this.newsMakerListModel = newsMakerListModel;
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"set news maker list"));
	}
	
	/**
	 * Adds a news maker to the database.
	 * 
	 * @param newsMakerModel The news maker to add.
	 */
	public void addNewsMakerModel(NewsMakerModel newsMakerModel)
	{
		this.newsMakerListModel.add(newsMakerModel);
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"add news maker"));
	}
	
	/**
	 * Replaces a news maker in the database's news maker list. The replacement
	 * news maker resides at the former index of the replaced news maker. If
	 * there is no news maker with the same name as the supplied news maker
	 * (i.e. no news maker to replace), the supplied news maker is simply
	 * appended to the end of the list.
	 * 
	 * @param newsMakerModel The replacement news maker.
	 */
	public void replaceNewsMakerModel(NewsMakerModel newsMakerModel)
	{
		this.newsMakerListModel.replace(newsMakerModel);
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"replace news maker"));
	}
	
	/**
	 * Removes every news maker in the given list from the database.
	 * 
	 * @param newsMakers A list of the news makers to be removed.
	 */
	public void removeNewsMakers(DefaultListModel<NewsMakerModel> newsMakers)
	{
		newsMakerListModel.removeListOfNewsMakers(newsMakers);
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"remove news makers"));
	}
	
	/**
	 * Removes all news makers from the database.
	 */
	public void removeAllNewsMakers()
	{
		newsMakerListModel.removeAllNewsMakers();
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"remove all news makers"));
	}
	
	/**
	 * Sorts this database's news maker list by name.
	 */
	public void sortNewsMakerListModel()
	{
		newsMakerListModel.sort();
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"sort news maker list"));
	}
	
	/**
	 * Returns true if there are no news stories in the database. Otherwise
	 * returns false.
	 * 
	 * @return True if there are no news stories in the database; false
	 * otherwise. 
	 */
	public boolean newsStoryListIsEmpty()
	{
		return newsStoryListModel.isEmpty();
	}
	
	/**
	 * Returns true if the supplied news story is the database's news story
	 * list. Returns false otherwise.
	 * 
	 * @param newsStory The news story to check for in the list.
	 * @return True if the supplied news story is the database's news story
	 * list; false otherwise.
	 */
	public boolean containsNewsStory(NewsStory newsStory)
	{
		return newsStoryListModel.contains(newsStory);
	}
	
	/**
	 * The accessor method for the database's news story list.
	 * 
	 * @return The database's news story list.
	 */
	public NewsStoryListModel getNewsStoryListModel()
	{
		return newsStoryListModel;
	}
	
	/**
	 * Returns a DefaultListModel containing all the NewsStory objects in the
	 * database.
	 * 
	 * @return A DefaultListModel containing all the NewsStory objects in the
	 * database.
	 */
	public DefaultListModel<NewsStory> getNewsStories()
	{
		return newsStoryListModel.getNewsStories();
	}
	
	/**
	 * The mutator method for the database's news story list.
	 * 
	 * @param newsStoryListModel The new news story list.
	 */
	public void setNewsStoryListModel(NewsStoryListModel newsStoryListModel)
	{
		this.newsStoryListModel = newsStoryListModel;
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"set news story list"));
	}
	
	/**
	 * Sets the stories in this database's news story list to match the stories
	 * in the given array.
	 * 
	 * @param newsStoryArray The array containing the news stories for this
	 * database's list.
	 */
	public void setNewsStoryListModelFromArray(NewsStory[] newsStoryArray)
	{
		newsStoryListModel.setNewsStoriesFromArray(newsStoryArray);
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"set news story list"));
	}
	
	/**
	 * Adds a news story to the database.
	 * 
	 * @param newsStory The news story to add.
	 */
	public void addNewsStory(NewsStory newsStory)
	{
		this.newsStoryListModel.add(newsStory);
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"add news story"));
	}
	
	/**
	 * Removes every news story in the given list from the database.
	 * 
	 * @param newsStories A list of the news stories to be removed.
	 */
	public void removeNewsStories(DefaultListModel<NewsStory> newsStories)
	{
		newsStoryListModel.removeListOfNewsStories(newsStories);
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"remove news stories"));
	}
	
	/**
	 * Removes all news stories from the database.
	 */
	public void removeAllNewsStories()
	{
		newsStoryListModel.setNewsStories(new NewsStoryListModel());
		
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"remove all news stories"));
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
