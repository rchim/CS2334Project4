import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	 * TODO
	 */
	private EditNewsMakerView editNewsMakerView;
	
	/**
	 * TODO
	 */
	private JDialog viewDialog;
	
	/**
	 * TODO
	 */
	private AddEditNewsStoryView addEditNewsStoryView;
	
	/**
	 * TODO
	 */
	private NewsStory editedNewsStory;
	
	/**
	 * TODO
	 */
	private MediaTypeSelectionView mediaTypeSelectionView;
	
	/**
	 * TODO
	 */
	private List<NewsMedia> selectedMediaTypes;
	
	/**
	 * TODO
	 */
	public NewsController()
	{
	
	}
	
	/**
	 * TODO
	 * 
	 * @param newsDataBaseModel TODO
	 */
	public void setNewsDataBaseModel(NewsDataBaseModel newsDataBaseModel)
	{
		// TODO
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
	 * TODO
	 */
	private void saveNewsData()
	{
		// TODO
	}	
	
	/**
	 * TODO
	 */
	private void importNoozStories()
	{
		// TODO
	}	
	
	/**
	 * TODO
	 */
	private void exportNoozStories()
	{
		// TODO
	}
	
	/**
	 * TODO
	 */
	private void addNewsMaker()
	{
		// TODO
	}
	
	/**
	 * TODO
	 */
	private void editNewsMakers()
	{
		// TODO
	}
	
	/**
	 * TODO
	 */
	private void deleteNewsMakers()
	{
		// TODO
	}
	
	/**
	 * TODO
	 */
	private void deleteNewsMakerList()
	{
		// TODO
	}
	
	/**
	 * TODO
	 */
	private void addNewsStory()
	{
		// TODO
	}
	
	/**
	 * TODO
	 */
	private void editNewsStory()
	{
		// TODO
	}
	
	/**
	 * TODO
	 */
	private void sortNewsStories()
	{
		// TODO
	}
	
	/**
	 * TODO
	 */
	private void deleteNewsStories()
	{
		// TODO
	}
	
	/**
	 * TODO
	 */
	private void deleteAllNewsStories()
	{
		// TODO
	}
	
	/**
	 * TODO
	 */
	private void displayPieCharts()
	{
		// TODO
	}
	
	/**
	 * TODO
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
}
