import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;

import javax.swing.*;

import java.util.List;
import java.util.Map;

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
		
		this.selectionView.registerFileMenuListener(
				new FileMenuListener());
		this.selectionView.registerNewsMakerMenuListener(
				new NewsMakerMenuListener());
		this.selectionView.registerNewsStoryMenuListener(
				new NewsStoryMenuListener());
		this.selectionView.registerDisplayMenuListener(
				new DisplayMenuListener());
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
			System.out.println("Loading " + fileChooser.getSelectedFile());
			
			try
			{
				FileInputStream fis 
						= new FileInputStream(fileChooser.getSelectedFile());
				ObjectInputStream ois = new ObjectInputStream(fis);
				NewsDataBaseModel loadedDataBaseModel 
						= (NewsDataBaseModel) (ois.readObject());
				selectionView.setNewsDataBaseModel(loadedDataBaseModel);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}			
		}
	}	
	
	/**
	 * Save the news data into a file
	 */
	private void saveNewsData()
	{
		
//JFileChooser fileChooser = new JFileChooser();
//		
//		int fileChooserReturnVal
//				= fileChooser.showDialog(selectionView, "Load");
//
//		if(fileChooserReturnVal == JFileChooser.APPROVE_OPTION)
//		{
//			System.out.println("Loading " + fileChooser.getSelectedFile());
//			
//			try
//			{
//				FileInputStream fis 
//						= new FileInputStream(fileChooser.getSelectedFile());
//				ObjectInputStream ois = new ObjectInputStream(fis);
//				NewsDataBaseModel loadedDataBaseModel 
//						= (NewsDataBaseModel) (ois.readObject());
//				selectionView.setNewsDataBaseModel(loadedDataBaseModel);
//			}
//			catch (IOException e) 
//			{
//				e.printStackTrace();
//			} 
//			catch (ClassNotFoundException e) 
//			{
//				e.printStackTrace();
//			}			
//		}
//	}	// TODO
	}	
	
	/**
	 * Asks for a series of four files with a JFileChooser. After each file is
	 * chosen, asks the user what type of data the file contains. Options for
	 * data types are news sources, news topics, news subjects, and news
	 * stories. Each file must refer to a different data type. 
	 * <P>
	 * After all the files are chosen, adds the imported data to the model.
	 * </P>
	 */
	private void importNoozStories()
	{
		// A file chooser to obtain the four files to import. 		
		JFileChooser fileChooser = new JFileChooser();
		
		// We attempt to let the user pick files from their working directory
		// for convenience.
		try
		{
			File workingDirectory = new File(System.getProperty("user.dir"));
			fileChooser.setCurrentDirectory(workingDirectory);
		}
		catch(Exception e)
		{
			// If this process fails (e.g. because the code is being run by
			// Mimir), it won't seriously affect the user experience. We ignore
			// the exception.
		}
		
		// To construct a complete NewsDataBaseModel from the import files, we
		// will need one file of each of these types.
		String[] fileTypes = new String[] {"News Sources", "News Topics",
				"News Subjects", "News Stories"};
		
		// Prepare a JPanel to ask what type of file the user selected from the
		// file chooser.
		JPanel fileTypeChooserPanel = new JPanel(new GridLayout(3, 1, 0, 20));		
		JLabel lblChooseFileType = new JLabel("Which file type was that?",
				JLabel.CENTER);
		JComboBox<String> jcbFileTypes = new JComboBox<String>(fileTypes);
		JButton jbtSubmit = new JButton("Submit");	
		fileTypeChooserPanel.add(lblChooseFileType);
		fileTypeChooserPanel.add(jcbFileTypes);
		fileTypeChooserPanel.add(jbtSubmit);		
		fileTypeChooserPanel.setBorder(
				BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// Add the file type panel to a JDialog for display.
		this.viewDialog = new JDialog(selectionView, "Choose file type.", true);
		this.viewDialog.add(fileTypeChooserPanel);
		this.viewDialog.setResizable(false);
		this.viewDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.viewDialog.pack();
		this.viewDialog.setLocationRelativeTo(selectionView);
				
		// When the user selects a file type in the dialog and presses the
		// submit button, the dialog should disappear.
		jbtSubmit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				NewsController.this.viewDialog.setVisible(false);				
			}			
		});		
				
		// Variables to be determined by the user's file chooser and dialog
		// responses.
		Map<String, String> sourceMap = null;
		Map<String, String> topicMap = null;
		Map<String, String> subjectMap = null;
		String newsStoryFilePath = null;
		
		// On each iteration of the loop, we will ask for and acquire another
		// file. By the end of the loop, if the user has not canceled the
		// import, we will have obtained all four.
		int fileChooserReturnVal;
		String currentFileType;
		File currentFile;
		for(int i = 0; i < fileTypes.length; i++)
		{
			fileChooserReturnVal 
					= fileChooser.showDialog(selectionView, "Import");
			
			// If the user selected a file for import, we proceed to determine
			// the file type.
			if(fileChooserReturnVal == JFileChooser.APPROVE_OPTION)
			{
				// We show the dialog asking which file type was selected. This
				// line blocks until the dialog is closed or the submit button
				// is pressed.
				viewDialog.setVisible(true);
				
				// If the view dialog is not displayable, then it has been
				// disposed. This only happens when the user closes the dialog.
				// Since we know the user closed the dialog instead of
				// submitting a file type, we terminate the import. 
				if(!viewDialog.isDisplayable())
					return;
				
				currentFile = fileChooser.getSelectedFile();
				currentFileType = (String) jcbFileTypes.getSelectedItem();
				
				try
				{
					// Store the data we'll need from the current file in order
					// to construct a news data base model.
					if("News Sources".equals(currentFileType))
					{
						sourceMap = CodeFileProcessor.readCodeFile(
								currentFile.getPath()); 						
					}
					else if("News Topics".equals(currentFileType))
					{
						topicMap = CodeFileProcessor.readCodeFile(
								currentFile.getPath()); 		
					}
					else if("News Subjects".equals(currentFileType))
					{
						subjectMap = CodeFileProcessor.readCodeFile(
								currentFile.getPath()); 
					}
					else if("News Stories".equals(currentFileType))
					{
						newsStoryFilePath = currentFile.getPath();
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(selectionView, "Oops!", 
							"IO error encountered when importing file.",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// We don't need another file of this type, so we remove it from
				// the combo box.
				jcbFileTypes.removeItem(currentFileType);
			}
			// If the user cancelled or closed the file chooser, we terminate the
			// import.
			else
			{
				return;
			}
		}
		
		// We've got all the data we need to construct the model.
		NewsDataBaseModel importedModel = null;		
		try
		{
			importedModel = NoozFileProcessor.readNoozFile(
					newsStoryFilePath, sourceMap, topicMap, subjectMap);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(selectionView, "Oops!", 
					"IO error encountered when processing news story file.",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		selectionView.setNewsDataBaseModel(importedModel);
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
		// prompt the user for a name to enter
		JFrame frame = new JFrame();
		String newsMakerName = (String)JOptionPane.showInputDialog(
				frame,
				"Enter in the NewsMaker name:",
				"NewsMaker Name Entry",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				null
				);
		// Construct the new NewsMakerModel object, add
		newsDataBaseModel.getNewsMakerListModel().add(new NewsMakerModel(newsMakerName));
		
	}
	
	/**
	 * Edit several <code>NewsMaker</code>s
	 */
	private void editNewsMakers()
	{
		// for the newsMaker to be edited, read the field
		// and update the name
		
		// get the new name
		String newName = editNewsMakerView.jtfName.getText();
		
		// set the new name
		editNewsMakerView.newsMakerModel.setName(newName);
		
	}
	
	/**
	 * Delete <code>NewsMaker</code>s
	 */
	private void deleteNewsMakers()
	{
		// create sub-list from selected, and delete only those
		
		DefaultListModel<NewsMakerModel> removedNewsMakers = new DefaultListModel<NewsMakerModel>();
		// fill from selected indices what to remove
		int[] selected = editNewsMakerView.getSelectedNewsStoryIndices();
		for(int i = 0 ; i < selected.length; ++i){
			// loop through the selected, pull out each newsmaker
			NewsMakerModel news = newsDataBaseModel.getNewsMakerListModel().get(selected[i]);
			removedNewsMakers.addElement(news);
		}
		newsDataBaseModel.getNewsMakerListModel().removeListOfNewsMakers(removedNewsMakers);
	}
	
	/**
	 * Delete <b><i>all</i></b> <code>NewsMaker</code>s
	 */
	private void deleteNewsMakerList()
	{
		newsDataBaseModel.getNewsMakerListModel().removeAllNewsMakers();
	}
	
	/**
	 * Add in a new <code>NewsStory</code>
	 */
	private void addNewsStory()
	{
		// code for add news story, from the addEditNewsStoryView
		
		// get all of the fields!
		String newsMakerName1 = (String) addEditNewsStoryView.jcbNewsStoryNewsMaker1.getSelectedItem();
		NewsMakerModel newsMaker1 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName1);
		
		String newsMakerName2 = (String) addEditNewsStoryView.jcbNewsStoryNewsMaker2.getSelectedItem();
		NewsMakerModel newsMaker2 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName2);
		
		PartOfDay pod = (PartOfDay) addEditNewsStoryView.jcbNewsStoryPartOfDay.getSelectedItem();
		
		String source = (String) addEditNewsStoryView.jcbNewsStorySource.getSelectedItem();
		
		int length = Integer.parseInt(addEditNewsStoryView.jtftfNewsStoryLength.getText());
		
		String topic = (String) addEditNewsStoryView.jcbNewsStoryTopic.getSelectedItem();
		
		String subject = (String) addEditNewsStoryView.jcbNewsStorySubject.getSelectedItem();
		
		// Date information
		int day = (int) addEditNewsStoryView.jcbNewsStoryDay.getSelectedItem();
		
		Month month = (Month) addEditNewsStoryView.jcbNewsStoryMonth.getSelectedItem();
		
		int year = (int) addEditNewsStoryView.jcbNewsStoryYear.getSelectedItem();
		
		// Construct LocalDate object
		LocalDate date = new LocalDate(year, month.toInt(), day);
		
		// determine the type of the story
		
		NewsMedia type = (NewsMedia) addEditNewsStoryView.jcbNewsStoryType.getSelectedItem();
		
		NewsStory news;
		
		switch(type){
		case TV:
			news = new TVNewsStory(date,
					source, length,
					topic, subject,
					pod, newsMaker1, newsMaker2);
			break;
		case ONLINE:
			news = new OnlineNewsStory(date,
					source, length,
					topic, subject,
					pod, newsMaker1, newsMaker2);
			break;
		case NEWSPAPER:
			news = new NewspaperStory(date,
					source, length,
					topic, subject,
					newsMaker1, newsMaker2);
			break;
		}
		
		// add news story
		newsDataBaseModel.addNewsStory(news);
		
	}
	
	/**
	 * Edit a <code>NewsStory</code>
	 */
	private void editNewsStory()
	{
		// tuck the current story into a DefaultListModel
		DefaultListModel<NewsStory> edited = new DefaultListModel<NewsStory>();
		edited.addElement(editedNewsStory);
		
		// remove the story from the list
		newsDataBaseModel.removeNewsStories(edited);
		
		// code for add news story, from the addEditNewsStoryView
		
		// get all of the fields!
		String newsMakerName1 = (String) addEditNewsStoryView.jcbNewsStoryNewsMaker1.getSelectedItem();
		NewsMakerModel newsMaker1 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName1);
		
		String newsMakerName2 = (String) addEditNewsStoryView.jcbNewsStoryNewsMaker2.getSelectedItem();
		NewsMakerModel newsMaker2 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName2);
		
		PartOfDay pod = (PartOfDay) addEditNewsStoryView.jcbNewsStoryPartOfDay.getSelectedItem();
		
		String source = (String) addEditNewsStoryView.jcbNewsStorySource.getSelectedItem();
		
		int length = Integer.parseInt(addEditNewsStoryView.jtftfNewsStoryLength.getText());
		
		String topic = (String) addEditNewsStoryView.jcbNewsStoryTopic.getSelectedItem();
		
		String subject = (String) addEditNewsStoryView.jcbNewsStorySubject.getSelectedItem();
		
		// Date information
		int day = (int) addEditNewsStoryView.jcbNewsStoryDay.getSelectedItem();
		
		Month month = (Month) addEditNewsStoryView.jcbNewsStoryMonth.getSelectedItem();
		
		int year = (int) addEditNewsStoryView.jcbNewsStoryYear.getSelectedItem();
		
		// Construct LocalDate object
		LocalDate date = new LocalDate(year, month.toInt(), day);
		
		// determine the type of the story
		
		NewsMedia type = (NewsMedia) addEditNewsStoryView.jcbNewsStoryType.getSelectedItem();
		
		NewsStory news;
		
		switch(type){
		case TV:
			news = new TVNewsStory(date,
					source, length,
					topic, subject,
					pod, newsMaker1, newsMaker2);
			break;
		case ONLINE:
			news = new OnlineNewsStory(date,
					source, length,
					topic, subject,
					pod, newsMaker1, newsMaker2);
			break;
		case NEWSPAPER:
			news = new NewspaperStory(date,
					source, length,
					topic, subject,
					newsMaker1, newsMaker2);
			break;
		}
		
		// add news story
		newsDataBaseModel.addNewsStory(news);
		
		
	}
	
	/**
	 * Sort the <code>NewsStory</code> based on criterion
	 */
	private void sortNewsStories()
	{
		// grab from the TextView the sortCriteria
	}
	
	/**
	 * Delete several <code>NewsStory</code>s
	 */
	private void deleteNewsStories()
	{
		// delete the selected news stories
		// start by determining which ones were selected in the view
		
		
	}
	
	/**
	 * Delete <b><i>ALL</i></b> <code>NewsStory</code>s
	 */
	private void deleteAllNewsStories()
	{
		newsDataBaseModel.removeAllNewsStories();
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
		 * @param actionEvent An event from the File menu.
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
		 * @param actionEvent An event from the Newsmakers menu.
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
		 * @param actionEvent An event from the News Stories menu.
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
		 * @param actionEvent An event from the News Stories menu.
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
			// How do we ever get here? There is no button for this!
			String clickedItemText = ((JButton)(actionEvent.getSource())).getText();
			if("Edit NewsMaker".equals(clickedItemText)){
				// change the newsmaker
				editNewsMakers(); //??? unsure
			}
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
			
			// check for the proper button name
			String clickedItemText = ((JButton)(actionEvent.getSource())).getText();
			// determine correct type
			if ("Remove From Story".equals(clickedItemText)){
				// call deleteNewsMaker
				deleteNewsMakers();
			}
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
