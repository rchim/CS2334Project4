import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
	
	/** <code>NewsMakerModel</code> 
	 */
	NewsMakerModel newsMakerModel;
	
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
		
		// fill the story string list
	    populateNewsStoryJList();
		
		// associate with the JList
		jlNewsStoryList = new JList<String>(newsStoryStringList);
		
		// don't have the needed image -- 
		
		// start by pre-filling in the fields with the current information
		jtfName = new JTextField(newsMakerModel.getName());
		
		// Add something that will generate an action whenever this field is changed
		jtfName.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void changedUpdate (DocumentEvent e){
				
				if(isValidName()){
					// change the name of the newsmaker
					EditNewsMakerView.this.newsMakerModel.setName(jtfName.getText());
				} else {
					// warn user of illegal name usage!
					// TODO:
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// unsure if anything needs to be done here
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// unsure if anything needs to be done here
			}
			
			/**
			 * Check if this is valid
			 * 
			 * @return valid if newsMaker is not "none"
			 */
			private boolean isValidName(){
				if("None".equals(jtfName.getText())){
					return false;
				} else{
					return true;
				}
			}

		});
		
		// add in JLabel and field into JPanel
		jplName = new JPanel();
		jplName.setLayout(new GridLayout(1,2));
		jplName.add(jlbName);
		jplName.add(jtfName);
		
		// add JList into JScrollPane
		jspNewsStoryList = new JScrollPane(jlNewsStoryList);
		
		// add into pane
		jpNewsStoryList = new JPanel();
		jpNewsStoryList.add(jspNewsStoryList);
		
		// add a listener to the jbtRemoveFromStory button
		jbtRemoveFromStory.setActionCommand("Removing news stories from newsmaker");
		
		// default -- not enabled button
		jbtRemoveFromStory.setEnabled(false);
		
		// check if newsmaker is not none and stories exist

		enableRemovalButton();
		
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

		// set for unique types of stories
		Set<String> uniqueMediaTypes = new TreeSet<String>();
		
		// List of story types encountered
		List<NewsMedia> newsMediaTypes = new ArrayList<NewsMedia>();
		
		for (int i = 0 ; i < stories.size(); ++i){
			uniqueMediaTypes.add(stories.getElementAt(i).getClass().getName());
		} // loop over the stories to find the unique types that occur
		
		// Loop over everything inside the set
		// put everything from here into the correct media type
		for (String str : uniqueMediaTypes){
			switch(str){
			case "TVNewsStory":
				newsMediaTypes.add(NewsMedia.TV);
				break;
			case "OnlineNewsStory":
				newsMediaTypes.add(NewsMedia.ONLINE);
				break;
			case "NewspaperStory":
				newsMediaTypes.add(NewsMedia.NEWSPAPER);
				break;
			}
		}
		for (int i = 0 ; i < stories.size(); ++i){
			NewsStory story = stories.get(i);
			// pass story object to UserInterface
			String current = UserInterface.convertToOutputFormat(story, newsMediaTypes);
			current += "\n"; // newline character
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
			
		// what sort of changes in the model do we need to listen for?
		// - any changes inside the newsMakerListModel
		// - any changes inside the newsMakerModel
		
		if(event.getActionCommand().equals("Modified News Story List")){
			// recall populateNewsStoryJList
			populateNewsStoryJList();
		}
		if(event.getActionCommand().equals("Modified News Maker List")){
			// re-set the newsMakerModel
			this.newsMakerModel = newsDataBaseModel.getNewsMakerListModel().get(newsMakerModel);
			// re-call the populateNewsStoryJList
			populateNewsStoryJList();
		}
		
		enableRemovalButton();
		
	}
	
	/**
	 * enableRemovalButton
	 * 
	 * allows the button to be enabled/disable button
	 */
	private void enableRemovalButton(){
		if(!newsMakerModel.getNewsStoryListModel().isEmpty() && !"None".equals(newsMakerModel.getName())){
			jbtRemoveFromStory.setEnabled(true);
		} else {
			jbtRemoveFromStory.setEnabled(false);
		}
	
	}
}
