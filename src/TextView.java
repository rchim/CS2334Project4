import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.*;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * This view allows the user to see news story data for a particular news maker
 * as text.
 * </P>
 * <P>
 * This class was written by Malachi Phillips (ID 112933834). It was debugged by
 * Ryan Chimienti (ID 113392576).
 * </P>
 * 
 * @author Malachi Phillips
 */
public class TextView implements ActionListener
{

	/** <code>JFrame</code> to hold the text information **/
	private JFrame jfText = new JFrame();
	
	/** <code>NewsMakerModel</code> to be shown in the view **/
	private NewsMakerModel newsMakerModel;
	
	/** List of <code>NewsMedia</code> associated with the stories **/
	private List<NewsMedia> newsMedia = new ArrayList<NewsMedia>();
	
	/** List of <code>SortCriterion</code> associated with the stories **/
	private List<SortCriterion> sortCriteria  = new ArrayList<SortCriterion>();
	
	/** <code>String</code> to hold the list of stories **/
	private String listOfStories = "";
	
	/** <code>String</code> to hold the summary line **/
	private String summaryLine = "";
	
	/** <code>JTextArea</code> to hold the textual information about the news stories **/
	private JTextArea jtaNewsStoryList;
	
	/** <code>JScrollPane</code> to place the <code>JTextArea</code> inside **/
	private JScrollPane jspNewsStoryList;
	
	/** <code>JTextArea</code> to hold the summary lines **/
	private JTextArea jtaSummaryLine;
	
	/**
	 * Public constructor. Constructs the view and GUI components.
	 * 
	 * @param newsMakerModel
	 *   <code>NewsMakerModel</code> associated with the view
	 * @param newsMedia
	 *   List of <code>NewsMedia</code> to be used for drawing the text
	 * @param sortCriteria
	 *   List of <code>StoryCriterion</code> used to sort the storiese
	 *   
	 */
	public TextView(NewsMakerModel newsMakerModel,
			        List<NewsMedia> newsMedia,
			        List<SortCriterion> sortCriteria){
		
		// set the newsMedia, sortCriteria
		this.newsMakerModel = newsMakerModel;
		this.newsMedia = newsMedia;
		this.sortCriteria = sortCriteria;
		
		// construct the title
	    String title = constructTitle();
	    
	    // construct everything else
	    constructNewsStoriesAndSummary();
	    
	    // create the graphics!
	    
	    jtaSummaryLine = new JTextArea(summaryLine);
	    jtaNewsStoryList = new JTextArea(listOfStories);
	    jspNewsStoryList = new JScrollPane(jtaNewsStoryList);
	    
	    jtaSummaryLine.setEditable(false);
	    jtaNewsStoryList.setEditable(false);
	    
	    jfText.setTitle(title);
	    jfText.setLayout(new BorderLayout());
	    jfText.add(jtaSummaryLine, BorderLayout.SOUTH);
	    
	    jfText.add(jspNewsStoryList, BorderLayout.CENTER);
	    jfText.setSize(1000, 700);
	    jfText.setVisible(true);
	    
	    jfText.addWindowListener(new java.awt.event.WindowAdapter() {
			/**
			 * Overriden method for whenever the window is closed
			 * 
			 * @param windowEvent
			 *   java.awt.event.WindowEvent for this event
			 */
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent){
				// This is a weird construction
				newsMakerModel.removeActionListener(TextView.this);
			}
		});
	    
	}
	
	/**
	 * Private helper method for constructing both the
	 * news stories and the summary
	 * 
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
	 */
	@SuppressWarnings("unchecked")
	private void constructNewsStoriesAndSummary(){
		// start by extracting out all of the news stories of the correct type
		// convert the list of news media types into their respective class names
		ArrayList<String> mediaTypes = new ArrayList<String>();
		for (int i = 0 ; i < newsMedia.size(); ++i){
			switch(newsMedia.get(i).toString()){
			case "TV":
				mediaTypes.add("TVNewsStory");
				break;
			case "Online":
				mediaTypes.add("OnlineNewsStory");
				break;
			case "Newspaper":
				mediaTypes.add("NewspaperStory");
				break;
			}	
		}
		
		// now, pull the newsStories from the newsMakerModel object
		DefaultListModel<NewsStory> news = newsMakerModel.getNewsStoryListModel().getNewsStories();
		
		// the actual news stories that we care about
		ArrayList<NewsStory> relevantStories = new ArrayList<NewsStory>();
		
		// Just because the user asked for TV news stories doesn't mean he will
		// get them. We will repurpose the news story list to keep track of
		// which media types are actually present among the stories.
		newsMedia.clear();
		
		for (int i = 0 ; i < news.size(); ++i){
			NewsStory current = news.getElementAt(i);
			
			// get the name of the type
			String type = current.getClass().getName();
			if (mediaTypes.contains(type)){ // type is included, so add the story!
				relevantStories.add(current);
			}
			
			// Keep track of the distinct types of news media that are present
			// among the stories for conversion to output format.
			if(type.equals("NewspaperStory") &&
					!newsMedia.contains(NewsMedia.NEWSPAPER))
			{
				newsMedia.add(NewsMedia.NEWSPAPER);
			}
			else if(type.equals("TVNewsStory") &&
					!newsMedia.contains(NewsMedia.TV))
			{
				newsMedia.add(NewsMedia.TV);
			}
			else if(type.equals("OnlineNewsStory") &&
					!newsMedia.contains(NewsMedia.ONLINE))
			{
				newsMedia.add(NewsMedia.ONLINE);
			}		
			
		} // once this is done, we care about each and every story left in the list
		
		HashMap<SortCriterion, Comparator<NewsStory>> comparators 
				= new HashMap<SortCriterion, Comparator<NewsStory>>();
		
		comparators.put(SortCriterion.SOURCE, SourceComparator.SOURCE_COMPARATOR);
		comparators.put(SortCriterion.SUBJECT, SubjectComparator.SUBJECT_COMPARATOR);
		comparators.put(SortCriterion.LENGTH, LengthComparator.LENGTH_COMPARATOR);
		comparators.put(SortCriterion.DATE_TIME, DateComparator.DATE_COMPARATOR);
		comparators.put(SortCriterion.TOPIC, null);
		
		// First find the value that wasn't passed with the sort criteria (the
		// fifth and least important sort criterion) and sort by it.
		for(int i = 0; i < SortCriterion.values().length; i++)
		{
			if(!sortCriteria.contains(SortCriterion.values()[i]))
			{
				Collections.sort(relevantStories, comparators.get(SortCriterion.values()[i]));
			}
		}
		
		// Sort by the remaining sort criteria, starting with the quaternary
		// and working our way to the primary.
		for(int i = sortCriteria.size() - 1; i >= 0 ; i--)
		{
			Collections.sort(relevantStories, comparators.remove(sortCriteria.get(i)));
		}		
			
		// Of course, we need some statistics for the summary line -- store in here!
		// Set for sources
		Set<String> sourceSet = new TreeSet<String>();
		
		// Set for subjects
		Set<String> subjectSet = new TreeSet<String>();
		
		// Set for topics
		Set<String> topicSet = new TreeSet<String>();
		
		// counters
		int secondLength = 0;
		int wordLength = 0;
		
		// No stories have been added yet in this method, so the list should be
		// empty.
		listOfStories = "";
				
		// iterate over the stories
		for (NewsStory n : relevantStories){
			// add in the subject, source and topic
			sourceSet.add(n.getSource());
			subjectSet.add(n.getSubject());
			topicSet.add(n.getTopic());
			wordLength += n.getLengthInWords();
			secondLength += n.getLength();
			
			// add onto the listOfStory this current story
			listOfStories += UserInterface.convertToOutputFormat(n, newsMedia) + "\n";
			
			//TODO: Verify if newline character should be here or in the method call
			
		} // statistics collected
		
		// now, make the summary line
		summaryLine = "Number of Stories: " + relevantStories.size();
		summaryLine += "; Number of Sources: " + sourceSet.size();
		if(mediaTypes.contains("TVNewsStory") && (mediaTypes.contains("NewspaperStory") || mediaTypes.contains("OnlineNewsStory"))){
			summaryLine += "; Number of Word Equivalents: " + wordLength;
		} else if(!mediaTypes.contains("TVNewsStory")){
			summaryLine += "; Number of Words: " + wordLength;
		} else {
			summaryLine += "; Seconds: " + secondLength;
		}
		
		summaryLine += "; Number of Topics: " + topicSet.size();
		summaryLine += "; Number of Subjects: " + subjectSet.size();
		
		// summary line is finished, listOfStory is finished
		// end here
				
	}
	
	/**
	 * Private helper method for constructing the title
	 * for the text view
	 */
	private String constructTitle(){
		// title consists of:
		String title = newsMakerModel.getName() + " - ";
		
		if(newsMedia.size() == 1)
		{
			title += newsMedia.get(0).toString();
			
			if(newsMedia.get(0) == NewsMedia.TV 
					|| newsMedia.get(0) == NewsMedia.ONLINE)
			{
				title += " News";
			}
		}
		else if(newsMedia.size() == 2)
		{
			title += newsMedia.get(0).toString() + "/";
			title += newsMedia.get(1).toString();			
		}
		
		title += " Stories sorted by ";
		
		for (int i = 0 ; i < sortCriteria.size(); i++)
		{
			title += sortCriteria.get(i).toString();
			title += ", ";
		}
		
		// First find the value that wasn't passed with the sort criteria (the
		// fifth and least important sort criterion).
		SortCriterion fifthSortCriterion = null;
		for(int i = 0; i < SortCriterion.values().length; i++)
		{
			if(!sortCriteria.contains(SortCriterion.values()[i]))
			{
				fifthSortCriterion = SortCriterion.values()[i];
				break;
			}
		}
		
		title += fifthSortCriterion;
		
		return title;
	}
	
	/**
	 * Overriden method for responding to an action
	 * 
	 * Needed for implementing <code>ActionListener</code>
	 * 
	 * 
	 * Note this was a mistake in the UML -- this method
	 * MUST be public as per overriding the same method signature
	 * inside <code>ActionListener</code>
	 * 
	 * @param actionEvent
	 *    <code>ActionEvent</code> to be performed here
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent){
		// Update the info.
		constructNewsStoriesAndSummary();
		
		jtaNewsStoryList.setText(listOfStories);
		jtaSummaryLine.setText(summaryLine);
		
		jfText.repaint();
	}
}
