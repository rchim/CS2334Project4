import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import java.util.List;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * The controller listens for user interactions with the views. In response, it
 * opens new views and/or updates the model.
 * </P>
 * <P>
 * TODO: Describe in this paragraph who did what, or state it in the method
 * documentation and describe that convention here.
 * </P>
 *
 * @author Ryan Chimienti
 * @author Malachi Phillips
 */
public class NewsController 
{
	/**
	 * The overarching model for the Nooz application.
	 */
	private NewsDataBaseModel newsDataBaseModel;
	
	/**
	 * The selection view for the Nooz application.
	 */
	private SelectionView selectionView;
	
	/**
	 * The edit news maker view for the Nooz application.
	 */
	private EditNewsMakerView editNewsMakerView;
	
	/**
	 * The view dialog for the Nooz application.
	 */
	private JDialog viewDialog;
	
	/**
	 * The add edit news story view for the Nooz application.
	 */
	private AddEditNewsStoryView addEditNewsStoryView;
	
	/**
	 * <code>NewsStory</code> object associated with edited story.
	 */
	private NewsStory editedNewsStory;
	
	/**
	 * Media type selection view for the Nooz application.
	 */
	private MediaTypeSelectionView mediaTypeSelectionView;
	
	/**
	 * List of <code>NewsMedia</code> that are selected.
	 */
	private List<NewsMedia> selectedMediaTypes;
	
	/**
	 * Public constructor for the news controller.
	 */
	public NewsController()
	{
	
	}
	
	/**
	 * Setter method for the <code>NewsDataBaseModel</code>
	 * 
	 * @param newsDataBaseModel
	 *   <code>MewsDataBaseModel</code> to be assocaited with the controller
	 */
	public void setNewsDataBaseModel(NewsDataBaseModel newsDataBaseModel)
	{
		this.newsDataBaseModel = newsDataBaseModel;
	}
	
	/**
	 * Registers listeners for the given selection view and stores it in an
	 * instance field so it can be recalled later.
	 * 
	 * @param selectionView The selection view to which this controller will be
	 * listening for user interaction events.
	 */
	public void setSelectionView(SelectionView selectionView)
	{
		this.selectionView = selectionView;
		
		selectionView.registerFileMenuListener(new FileMenuListener());
		selectionView.registerNewsMakerMenuListener(new NewsMakerMenuListener());
		selectionView.registerNewsStoryMenuListener(new NewsStoryMenuListener());
		selectionView.registerDisplayMenuListener(new DisplayMenuListener());
	}
	
	/**
	 * Asks the user to choose a binary data file (which should contain a
	 * serialized NewsDataBaseModel object) and sets the model to reflect that
	 * data.
	 */
	private void loadNewsData()
	{
		JFileChooser fileChooser = new JFileChooser();
		
		int fileChooserReturnVal
				= fileChooser.showDialog(selectionView, "Load");

		if(fileChooserReturnVal == JFileChooser.APPROVE_OPTION)
		{
			System.out.println(
					"You asked to load " + fileChooser.getSelectedFile() + "!");
			
			// TODO
		}
	}	
	
	/**
	 * Save the news data into a file
	 */
	private void saveNewsData()
	{
		// TODO
	}	
	
	/**
	 * Import news data from a binary file
	 */
	private void importNoozStories()
	{
		// TODO
	}	
	
	/**
	 * Export news data into a binary file
	 */
	private void exportNoozStories()
	{
		// TODO
	}
	
	/**
	 * Add a <code>NewsMaker</code> into the list.
	 */
	private void addNewsMaker()
	{
		// TODO
	}
	
	/**
	 * Edit several <code>NewsMaker</code>s
	 */
	private void editNewsMakers()
	{
		// TODO
	}
	
	/**
	 * Delete <code>NewsMaker</code>s
	 */
	private void deleteNewsMakers()
	{
		// TODO
	}
	
	/**
	 * Delete <b><i>all</i></b> <code>NewsMaker</code>s
	 */
	private void deleteNewsMakerList()
	{
		// TODO
	}
	
	/**
	 * Add in a new <code>NewsStory</code>
	 */
	private void addNewsStory()
	{
		// TODO
	}
	
	/**
	 * Edit a <code>NewsStory</code>
	 */
	private void editNewsStory()
	{
		// TODO
	}
	
	/**
	 * Sort the <code>NewsStory</code> based on criterion
	 */
	private void sortNewsStories()
	{
		// TODO
	}
	
	/**
	 * Delete several <code>NewsStory</code>s
	 */
	private void deleteNewsStories()
	{
		// TODO
	}
	
	/**
	 * Delete <b><i>ALL</i></b> <code>NewsStory</code>s
	 */
	private void deleteAllNewsStories()
	{
		// TODO
	}
	
	/**
	 * Display the pie chart objects to the user.
	 */
	private void displayPieCharts()
	{
		// TODO
	}
	
	/**
	 * Display all the text views to the user.
	 */
	private void displayTextViews()
	{
		// TODO
	}
	
	/**
	 * <P>
	 * Listens for items in the selection view File menu to be clicked. Responds
	 * as necessary.
	 * </P>
	 * <P>
	 * This class was written by Ryan Chimienti (ID 113392576)
	 * </P>
	 * 
	 * @author Ryan Chimienti
	 */
	private class FileMenuListener implements ActionListener
	{
		/**
		 * Fulfills user requests associated with clicks to any of the items in
		 * the File menu.
		 * 
		 * @param e An event from the File menu.
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			// The name of the menu item that was clicked.
			String clickedItemText = ((JMenuItem)(actionEvent.getSource()))
					.getText();				
			
			if("Load".equals(clickedItemText))
			{				
				loadNewsData();
			}
			else if("Save".equals(clickedItemText))
			{
				saveNewsData();
			}
			else if("Import".equals(clickedItemText))
			{
				importNoozStories();
			}
			else if("Export".equals(clickedItemText))
			{
				exportNoozStories();
			}
		}
	}
	
	/**
	 * <P>
	 * Listens for items in the selection view Newsmakers menu to be clicked.
	 * Responds as necessary.
	 * </P>
	 * <P>
	 * This class was written by Ryan Chimienti (ID 113392576)
	 * </P>
	 * 
	 * @author Ryan Chimienti
	 */
	private class NewsMakerMenuListener implements ActionListener
	{
		/**
		 * Fulfills user requests associated with clicks to any of the items in
		 * the Newsmakers menu.
		 * 
		 * @param e An event from the Newsmakers menu.
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			// The name of the menu item that was clicked.
			String clickedItemText = ((JMenuItem)(actionEvent.getSource()))
					.getText();
					
			if("Add Newsmaker".equals(clickedItemText))
			{
				addNewsMaker();
			}
			else if("Edit Newsmaker".equals(clickedItemText))
			{
				editNewsMakers();
			}
			else if("Delete Newsmaker".equals(clickedItemText))
			{
				deleteNewsMakers();
			}
			else if("Delete Newsmaker List".equals(clickedItemText))
			{
				deleteNewsMakerList();
			}
		}
	}
	
	/**
	 * <P>
	 * Listens for items in the selection view News Stories menu to be clicked.
	 * Responds as necessary.
	 * </P>
	 * <P>
	 * This class was written by Ryan Chimienti (ID 113392576)
	 * </P>
	 * 
	 * @author Ryan Chimienti
	 */
	private class NewsStoryMenuListener implements ActionListener
	{
		/**
		 * Fulfills user requests associated with clicks to any of the items in
		 * the News Stories menu.
		 * 
		 * @param e An event from the News Stories menu.
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			// The name of the menu item that was clicked.
			String clickedItemText = ((JMenuItem)(actionEvent.getSource()))
					.getText();
					
			if("Add News Story".equals(clickedItemText))
			{
				addNewsStory();
			}
			else if("Edit News Story".equals(clickedItemText))
			{
				editNewsStory();
			}
			else if("Sort News Stories".equals(clickedItemText))
			{
				// TODO NOTE: THIS IS AT ODDS WITH THE PROJECT DESC, WHICH
				// SUGGESTS THE SORT NEWS STORIES ELEMENT IS NOT A CLICKABLE
				// MENU ITEM IN ITSELF, BUT A MENU THAT SPAWNS A SUBMENU.
				// HOWEVER, THE UML LISTS IT AS A MENU ITEM. WILL NEED CLARITY.
			}
			else if("Delete News Story".equals(clickedItemText))
			{
				deleteNewsStories();
			}
			else if("Delete All News Stories".equals(clickedItemText))
			{
				deleteAllNewsStories();
			}
		}
	}
	
	/**
	 * <P>
	 * Listens for items in the selection view Display menu to be clicked.
	 * Responds as necessary.
	 * </P>
	 * <P>
	 * This class was written by Ryan Chimienti (ID 113392576)
	 * </P>
	 * 
	 * @author Ryan Chimienti
	 */
	private class DisplayMenuListener implements ActionListener
	{
		/**
		 * Fulfills user requests associated with clicks to any of the items in
		 * the News Stories menu.
		 * 
		 * @param e An event from the News Stories menu.
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			// The name of the menu item that was clicked.
			String clickedItemText = ((JMenuItem)(actionEvent.getSource()))
					.getText();
					
			if("Pie Chart".equals(clickedItemText))
			{
				displayPieCharts();
			}
			else if("Text".equals(clickedItemText))
			{
				displayTextViews();
			}
		}
	}
	
	/**
	 * <P> Listens for the items on the edit news maker name view
	 * and responds as needed.
	 * </P>
	 * @author Malachi Phillips
	 *
	 */
	public class EditNewsMakerNameListener implements ActionListener
	{

		/**
		 * Overriden method used for <code>ActionListener</code>
		 * 
		 * Performs the action specified
		 * 
		 * @param actionEvent
		 *   <code>ActionEvent</code> needing to occur
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * <P>Listens for any events that happen in the remove
	 * news maker from news stories</P>
	 * 
	 * @author Malachi Phillips
	 */
	public class RemoveNewsMakerFromNewsStoriesListener implements ActionListener
	{

		/**
		 * Overriden method used for <code>ActionListener</code>
		 * 
		 * Performs the action specified
		 * 
		 * @param actionEvent
		 *   <code>ActionEvent</code> needing to occur
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// In relevant stories, set the News Maker to "none" instead
			
			// not sure what to do here -- what view is this found in?
		}
		
	}
	
	/**
	 * <P>Listens for the add edit news story view</P>
	 * 
	 * @author Malachi Phillips
	 */
	public class AddEditNewsStoryListener implements ActionListener
	{

		/**
		 * Overriden method used for <code>ActionListener</code>
		 * 
		 * Performs the action specified
		 * 
		 * @param actionEvent
		 *   <code>ActionEvent</code> needing to occur
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// Determine what button has been pressed
			String clickedItemText = ((JButton)(actionEvent.getSource())).getText();
			if("Add News Story".equals(clickedItemText)){
				// add news story
				addNewsStory();
			} else if ("Edit News Story".equals(clickedItemText)){
				// edit the news story
				editNewsStory();
			} else {
				// close the window (JFrame)
				addEditNewsStoryView.setVisible(false);
			}
		}
		
	}
	
	/**
	 * <P>Listens for media type selection view</P>
	 * 
	 * @author Malachi Phillips
	 */
	public class MediaTypeSelectionListener implements ActionListener
	{

		/**
		 * Overriden method used for <code>ActionListener</code>
		 * 
		 * Performs the action specified
		 * 
		 * @param actionEvent
		 *   <code>ActionListener</code> needing to occur
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			String clickedItemText = ((JButton)(actionEvent.getSource())).getText();
			
			if("Cancel".equals(clickedItemText)){
				// close the window
				mediaTypeSelectionView.setVisible(false);
			}
			if("OK".equals(clickedItemText)){
				// clear selected media types -- start fresh
				selectedMediaTypes.clear();
				// determine which of the checkboxes are checked
				if(mediaTypeSelectionView.jcbNewspaper.isSelected()){
					// add into the list
					selectedMediaTypes.add(NewsMedia.NEWSPAPER);
				} // unsure what needs to be done here
				
				if(mediaTypeSelectionView.jcbTVNews.isSelected()){
					// add into the list
					selectedMediaTypes.add(NewsMedia.TV);
				}
				
				if(mediaTypeSelectionView.jcbOnline.isSelected()){
					// add into the list
					selectedMediaTypes.add(NewsMedia.ONLINE);
				}
				
				// make invisible
				mediaTypeSelectionView.setVisible(false);
			}
			
		}
		
	}
	
	
}
