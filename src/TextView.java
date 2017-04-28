import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * This view allows the user to see news story data for a particular news maker
 * as text.
 * </P>
 * <P>
 * This class was written by Malachi Phillips (ID 112933834).
 * </P>
 * 
 * @author Malachi Phillips
 */
public class TextView implements ActionListener
{

	/** <code>JFrame</code> to hold the text information **/
	private JFrame jfText;
	
	/** <code>NewsMakerModel</code> to be shown in the view **/
	private NewsMakerModel newsMakerModel;
	
	/** List of <code>NewsMedia</code> associated with the stories **/
	private List<NewsMedia> newsMedia = new ArrayList<NewsMedia>();
	
	/** List of <code>SortCriterion</code> assocaited with the stories **/
	private List<SortCriterion> sortCriteria  = new ArrayList<SortCriterion>();
	
	/** <code>String</code> to hold the list of stories **/
	private String listOfStories;
	
	/** <code>String</code> to hold the summary line **/
	private String summaryLine;
	
	/** <code>JTextArea</code> to hold the textual information about the news stories **/
	private JTextArea jtaNewsStoryList;
	
	/** <code>JScrollPane</code> to place the <code>JTextArea</code> inside **/
	private JScrollPane jspNewsStoryList = new JScrollPane(jtaNewsStoryList);
	
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
	public TextView(){
		//TODO: Construct the things here
	}
	
	/**
	 * Private helper method for constructing both the
	 * news stories and the summary
	 */
	private void constructNewsStoriesAndSummary(){
		//TODO: Construct the News stories and summary
	}
	
	/**
	 * Private helper method for constructing the title
	 * for the text view
	 */
	private void constructTitle(){
		//TODO: construct the title
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
		//TODO:: Perform the action!
	}
}
