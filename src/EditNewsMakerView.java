import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.*;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * This view allows the user to change the model data about a news maker.
 * </P>
 * <P>
 * This class was written by Malachi Phillips (ID 112933834).
 * </P>
 * 
 * @author Malachi Phillips
 */
public class EditNewsMakerView extends JPanel implements ActionListener
{
	/**
	 * serialVersionUID for <code>JPanel</code>
	 */
	private static final long serialVersionUID = -1852894847177820330L;
	
	/** <code>NewsMakerModel</code> (note: UML says this is package-protected,
	 * but I disagree. Private it is. 
	 */
	private NewsMakerModel newsMakerModel;
	
	/** <code>NewsDataBaseModel</code> **/
	private NewsDataBaseModel newsDataBaseModel;
	
	/** <code>DefaultListMode</code> for the story list string **/
	private DefaultListModel<String> newsStoryStringList;
	
	/** <code>JList</code> for <code>NewsStoryList</code> **/
	private JList<String> jlNewsStoryList;
	
	/** <code>JScrollPane</code> for the <code>NewsStoryList</code> **/
	private JScrollPane jspNewsStoryList;
	
	/** <code>JPanel</code> to hold the <code>NewsStoryList</code> **/
	private JPanel jpNewsStoryList;
	
	/** <code>JTextField</code> for entering in the name **/
	JTextField jtfName;
	
	/** <code>JLabel</code> to denote the location of where to enter the name */
	private JLabel jlbName = new JLabel("Name:");
	
	/** <code>JButton</code> for removing from a story **/
	JButton jbtRemoveFromStory = new JButton("Remove From Story");
	
	/** <code>JPanel</code> to hold the name information **/
	private JPanel jplName;
	
	/**
	 * Public constructor.
	 * 
	 * Draw/configure the necessary GUI components here
	 * 
	 * @param newsMakerModel
	 *   <code>NewsMakerModel</code> to be associated with this GUI
	 * @param newsDataBaseModel
	 *   <code>NewsDataBaseModel</code> to be associated with this GUI
	 * 
	 */
	public EditNewsMakerView(NewsMakerModel newsMakerModel, NewsDataBaseModel newsDataBaseModel){
		
		this.newsMakerModel = newsMakerModel;
		this.newsDataBaseModel = newsDataBaseModel;
		newsStoryStringList = new DefaultListModel<String>();
		
		// associate with the JList
		jlNewsStoryList = new JList<String>(newsStoryStringList);
		
		// fill the story string list
	    populateNewsStoryJList();
		
		// don't have th e needed image -- 
		
		// start by pre-filling in the fields with the current information
		jtfName = new JTextField(newsMakerModel.getName());
		
		// add in JLabel and field into JPanel
		jplName.setLayout(new GridLayout(1,2));
		jplName.add(jlbName);
		jplName.add(jtfName);
		
		// add JList into JScrollPane
		jspNewsStoryList = new JScrollPane();
		jspNewsStoryList.add(jlNewsStoryList);
		
		// add into pane
		jpNewsStoryList.add(jspNewsStoryList);
		
		// add in elements to current, don't know how it will look
		// so assume 1x3 Grid Layout
		this.setLayout(new GridLayout(1,3));
		this.add(jpNewsStoryList);
		this.add(jplName);
		this.add(jbtRemoveFromStory);
		
	}
	
	/**
	 * Method for determining the indices of
	 * the stories currently being selected by the user
	 * 
	 * @return indices
	 *   <code>int []</code> of the selected indices
	 */
	public int[] getSelectedNewsStoryIndices(){
		return (jlNewsStoryList.getSelectedIndices());
	}
	
	/**
	 * Private helper method for sorting out the details of
	 * the <code>NewsStory JList</code>.
	 */
	private void populateNewsStoryJList(){
		// populate the JList from the model
		// do this as follows: for each story in the model
		// create the summary string for the story (UserInterface)
		
		// get news stories
		DefaultListModel<NewsStory> stories = newsMakerModel.getNewsStoryListModel().getNewsStories();

		for (int i = 0 ; i < stories.size(); ++i){
			NewsStory story = stories.get(i);
			// pass story object to UserInterface
			String current = UserInterface.convertToOuputFormat(story, NewsMedia.valuesAsList());
			//add the string into the JList
			newsStoryStringList.addElement(current);
		}
	
	}

	/**
	 * Overriden method for actionPerformed, as part of <code>ActionListener</code>
	 * implementation
	 * 
	 * @param event
	 *   <code>ActionEvent</code> for this action
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
