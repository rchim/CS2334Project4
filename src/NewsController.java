import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * The controller listens for user interactions with the views. In response, it
 * opens new views and/or updates the model.
 * </P>
 * <P>
 * This class was worked on by both group members. Each method specifies its
 * author in its own documentation.
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
	private MediaTypeSelectionView mediaTypeSelectionView = new MediaTypeSelectionView();
	
	/**
	 * List of <code>NewsMedia</code> that are selected.
	 */
	private List<NewsMedia> selectedMediaTypes = new LinkedList<NewsMedia>();
	
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
	 *   <code>MewsDataBaseModel</code> to be associated with the controller
	 *   
	 * @author Ryan Chimienti
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
	 * 
	 * @author Ryan Chimienti
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
	 * Asks the user to choose a binary data file (which should contain
	 * serialized data about various parts of a news database model) and sets
	 * the model to reflect that data.
	 * 
	 * @author Ryan Chimienti
	 */
	@SuppressWarnings("unchecked")
	private void loadNewsData()
	{
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
		
		int fileChooserReturnVal
				= fileChooser.showDialog(selectionView, "Load");

		if(fileChooserReturnVal == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				FileInputStream fis 
						= new FileInputStream(fileChooser.getSelectedFile());
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				this.newsDataBaseModel.none	= 
						(NewsMakerModel) (ois.readObject());
				this.newsDataBaseModel.setNewsMakerListModel(	
						(NewsMakerListModel) (ois.readObject()));
				this.newsDataBaseModel.setNewsStoryListModel(	
						(NewsStoryListModel) (ois.readObject()));
				this.newsDataBaseModel.setNewsSourceMap(	
						(Map<String, String>) (ois.readObject()));
				this.newsDataBaseModel.setNewsTopicMap(	
						(Map<String, String>) (ois.readObject()));
				this.newsDataBaseModel.setNewsSubjectMap(	
						(Map<String, String>) (ois.readObject()));
				
				ois.close();					
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(selectionView, 
						"Error encountered when loading file.", 
						"Oops!", JOptionPane.ERROR_MESSAGE);				
				return;
			}
			
			JOptionPane.showMessageDialog(selectionView, 
					"Data was successfully loaded.", 
					"Success", JOptionPane.PLAIN_MESSAGE);	
		}
	}	
	
	/**
	 * Method that saves binary data to a file from the news database model.
	 * 
	 * @author Ryan Chimienti
	 */
	private void saveNewsData()
	{
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
		
		int fileChooserReturnVal 
				= fileChooser.showDialog(selectionView, "Save");
		
		// If the user selected a file for saving, we write to it.
		if(fileChooserReturnVal == JFileChooser.APPROVE_OPTION)
		{
			String filePath = fileChooser.getSelectedFile().getPath();
			
			if((new File(filePath)).exists())
			{
				JOptionPane.showMessageDialog(selectionView, 
						"That file already exists. Please save to a new file.", 
						"Oops!",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			try
			{
				FileOutputStream fileOutputStream 
						= new FileOutputStream(filePath);
				ObjectOutputStream objectOutputStream 
						= new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(newsDataBaseModel.none);
						
				try
				{
					objectOutputStream.writeObject(
							newsDataBaseModel.getNewsMakerListModel());		
				}
				// If a part of the news maker list model is unserializable, it
				// must be that there is an unserializable action listener on
				// one of the news maker models. In Nooz 4.0, this means a pie
				// chart.
				catch(NotSerializableException e)
				{
					JOptionPane.showMessageDialog(selectionView, 
							"Unable to save. Close all extraneous windows"
							+ " and try again.", 
							"Oops!", JOptionPane.WARNING_MESSAGE);
					objectOutputStream.close();					
					
					// Since writing to the file failed, we delete the 
					// half-finished file that got created.
					(new File(filePath)).delete();
					
					return;
				}
				
				objectOutputStream.writeObject(
						newsDataBaseModel.getNewsStoryListModel());
				objectOutputStream.writeObject(
						newsDataBaseModel.getNewsSourceMap());
				objectOutputStream.writeObject(
						newsDataBaseModel.getNewsTopicMap());
				objectOutputStream.writeObject(
						newsDataBaseModel.getNewsSubjectMap());
				objectOutputStream.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(selectionView, 
						"IO error encountered when saving data.", 
						"Oops!",
						JOptionPane.ERROR_MESSAGE);
				
				// Since writing to the file failed, we delete the 
				// half-finished file that got created.
				(new File(filePath)).delete();
				
				return;
			}
			
			JOptionPane.showMessageDialog(selectionView, 
					"Saved data to " + filePath + ".", 
					"Success",
					JOptionPane.PLAIN_MESSAGE);
		}
	}	
	
	/**
	 * Asks for a series of four files with a JFileChooser. After each file is
	 * chosen, asks the user what type of data the file contains. Options for
	 * data types are news sources, news topics, news subjects, and news
	 * stories. Each file must refer to a different data type. 
	 * <P>
	 * After all the files are chosen, adds the imported data to the model.
	 * </P>
	 * 
	 * @author Ryan Chimienti
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
					JOptionPane.showMessageDialog(selectionView, 
							"IO error encountered when importing file.", 
							"Oops!", JOptionPane.ERROR_MESSAGE);
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
		
		// We've got all the data we need to construct the new model.
		try
		{
			this.newsDataBaseModel = new NewsDataBaseModel();
			this.newsDataBaseModel = NoozFileProcessor.readNoozFile(
					newsStoryFilePath, sourceMap, topicMap, subjectMap);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(selectionView, 
					"IO error encountered when processing news story file.", 
					"Oops!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		selectionView.setNewsDataBaseModel(this.newsDataBaseModel);
	}	
	
	/**
	 * Writes text data to a file based on the news news stories in the model.
	 * 
	 * @author Ryan Chimienti
	 */
	private void exportNewsStories()
	{
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
		
		int fileChooserReturnVal 
				= fileChooser.showDialog(selectionView, "Export");
		
		// If the user selected a file for saving, we'll write to it.
		if(fileChooserReturnVal == JFileChooser.APPROVE_OPTION)
		{
			ListModel<NewsStory> newsStories =
					newsDataBaseModel.getNewsStories();
			
			ArrayList<NewsMedia> storyTypes = new ArrayList<NewsMedia>();
			
			// Iterate through the stories, collecting all the distinct story
			// types. 
			for(int i = 0; i < newsStories.getSize(); i++)
			{
				if(newsStories.getElementAt(i) instanceof NewspaperStory)
				{
					if(!storyTypes.contains(NewsMedia.NEWSPAPER))
						storyTypes.add(NewsMedia.NEWSPAPER);
				}
				else if(newsStories.getElementAt(i) instanceof TVNewsStory)
				{
					if(!storyTypes.contains(NewsMedia.TV))
						storyTypes.add(NewsMedia.TV);
				}
				else
				{
					if(!storyTypes.contains(NewsMedia.ONLINE))
						storyTypes.add(NewsMedia.ONLINE);
				}	
				
				// If we've found all the media types, there is no point in
				// searching on for more.
				if(storyTypes.size() == 3)
					break;					
			}
			
			// Assemble the list of stories to write.
			String listOfStories = UserInterface.convertToOutputFormat(
					newsStories.getElementAt(0), storyTypes);
			for(int i = 1; i < newsStories.getSize(); i++)
			{
				listOfStories += "\n";						
				listOfStories += UserInterface.convertToOutputFormat(
						newsStories.getElementAt(i), storyTypes);				
			}			
			
			// Now we can write the list of stories to the specified file.			
			String filePath = fileChooser.getSelectedFile().getPath();			
			try
			{
				NoozFileProcessor.writeNewsStoriesFile(filePath, listOfStories);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(selectionView, 
						"IO error encountered when saving data.", 
						"Oops!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JOptionPane.showMessageDialog(selectionView, 
					"Exported data to " + filePath + ".", 
					"Success",
					JOptionPane.PLAIN_MESSAGE);			
		}
	}
	
	/**
	 * Add a <code>NewsMaker</code> into the list.
	 * @author Malachi Phillips
	 */
	private void addNewsMaker()
	{
		// prompt the user for a name to enter
		String newsMakerName = (String)JOptionPane.showInputDialog(
				selectionView,
				"Enter in the NewsMaker name:",
				"NewsMaker Name Entry",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				null
				);
		
		// make a check for if the name is a null
		if (null == newsMakerName){
			// Send warning to the user that news maker(s) must be selected
			JOptionPane.showMessageDialog(selectionView,
					"Nothing entered for adding a news maker.",
					"Invalid Selection",
					JOptionPane.WARNING_MESSAGE);
			return;
		}				
		
		// Construct the new NewsMakerModel object, add
		newsDataBaseModel.addNewsMakerModel(new NewsMakerModel(newsMakerName));		
	}
	
	/**
	 * Edit several <code>NewsMaker</code>s
	 * @author Malachi Phillips
	 */
	private void editNewsMakers()
	{
		// get the selected newsmakers to edit
		int[] selected = selectionView.getSelectedNewsMakers();
		
		// Make sure some news makers have been selected.
		if(0 == selected.length)
		{
			// Send warning to the user that news maker(s) must be selected
			JOptionPane.showMessageDialog(selectionView,
					"No news makers selected.",
					"Invalid Selection",
					JOptionPane.WARNING_MESSAGE);
			return;			
		}
		
		// Make sure 'None' is not among the selected news makers.
		for (int index : selected)
		{
			if(newsDataBaseModel.getNewsMakerListModel().get(index).equals(
					new NewsMakerModel("None")))
			{
				JOptionPane.showMessageDialog(selectionView,
						"The special newsmaker 'None' cannot be edited.",
						"Invalid Selection",
						JOptionPane.ERROR_MESSAGE);
				return;			
			}
		}
		
		for (int index : selected){
			// grab the current newsmakerModel
			NewsMakerModel current = newsDataBaseModel.getNewsMakers().get(index);
			// create the view
			this.editNewsMakerView = new EditNewsMakerView(current, this.newsDataBaseModel);
			EditNewsMakerNameListener editNewsMakerNameListener = new EditNewsMakerNameListener();
			RemoveNewsMakerFromNewsStoriesListener removeNewsMakerFromStoriesListener
				= new RemoveNewsMakerFromNewsStoriesListener();
			
			// add both listeners to the view to their respective buttons
			this.editNewsMakerView.jbtRemoveFromStory.addActionListener(removeNewsMakerFromStoriesListener);
			this.editNewsMakerView.jtfName.addActionListener(editNewsMakerNameListener);
			
			// make modal
			this.viewDialog = new JDialog(selectionView, "", true);
			this.viewDialog.add(editNewsMakerView);
			this.viewDialog.setResizable(true);
			this.viewDialog.setSize(1190,1000);
			this.viewDialog.setLocationRelativeTo(selectionView);
			
			current.addActionListener(editNewsMakerView);
			this.viewDialog.addWindowListener(new java.awt.event.WindowAdapter() {
				/**
				 * Overriden method for whenever the window is closed
				 * 
				 * @param windowEvent
				 *   java.awt.event.WindowEvent for this event
				 */
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent){
					// This is a weird construction
					current.removeActionListener(NewsController.this.editNewsMakerView);
				}
			});
			
			this.viewDialog.setVisible(true);
		}		
	}
	
	/**
	 * Delete <code>NewsMaker</code>s
	 * @author Malachi Phillips
	 */
	private void deleteNewsMakers()
	{
		// create sub-list from selected, and delete only those
		
		DefaultListModel<NewsMakerModel> removedNewsMakers = new DefaultListModel<NewsMakerModel>();
		// fill from selected indices what to remove
		int[] selected = selectionView.getSelectedNewsMakers();
		for(int i = 0 ; i < selected.length; ++i){
			// loop through the selected, pull out each newsmaker
			NewsMakerModel news = newsDataBaseModel.getNewsMakerListModel().get(selected[i]);
			removedNewsMakers.addElement(news);
		}
		newsDataBaseModel.removeNewsMakers(removedNewsMakers);
	}
	
	/**
	 * @author Malachi Phillips
	 * Delete <b><i>all</i></b> <code>NewsMaker</code>s
	 */
	private void deleteNewsMakerList()
	{
		newsDataBaseModel.removeAllNewsMakers();
	}
	
	/**
	 * @author Malachi Phillips
	 * Add in a new <code>NewsStory</code>
	 */
	private void addNewsStory()
	{
		// Get information using the AddEditNewsStoryView
		this.addEditNewsStoryView = new AddEditNewsStoryView(this.newsDataBaseModel, null);
		AddEditNewsStoryListener addEditNewsStoryListener =
				new AddEditNewsStoryListener();
		// add listener
		this.addEditNewsStoryView.jbtAddEditNewsStory.addActionListener(addEditNewsStoryListener);
		this.viewDialog = new JDialog(selectionView, "", true);
		this.viewDialog.add(addEditNewsStoryView);
		this.viewDialog.setResizable(false);
		this.viewDialog.pack();
		this.viewDialog.setLocationRelativeTo(selectionView);
		this.viewDialog.setVisible(true);
		
	}
	
	/**
	 * @author Malachi Phillips
	 * Edit a <code>NewsStory</code>
	 */
	private void editNewsStory()
	{
		// get the selected news stories -- ensure that it is non-zero!
		int[] selected = selectionView.getSelectedNewsStories();
		
		// make the check here
		if (0 == selected.length){
			JOptionPane.showMessageDialog(selectionView,
					"No news stories selected.",
					"Invalid Selection",
					JOptionPane.WARNING_MESSAGE);
			return;
		} 
		
		for (int index : selected){
			// Get information using the AddEditNewsStoryView
			editedNewsStory = newsDataBaseModel.getNewsStoryListModel().get(index);
			this.addEditNewsStoryView = new AddEditNewsStoryView(this.newsDataBaseModel, editedNewsStory);
			AddEditNewsStoryListener addEditNewsStoryListener = new AddEditNewsStoryListener();
			// add listener
			this.addEditNewsStoryView.jbtAddEditNewsStory.addActionListener(addEditNewsStoryListener);
			this.viewDialog = new JDialog(selectionView, "", true);
			this.viewDialog.add(addEditNewsStoryView);
			this.viewDialog.setResizable(false);
			this.viewDialog.pack();
			this.viewDialog.setLocationRelativeTo(selectionView);
			this.viewDialog.setVisible(true);		
		}
	}
	
	/**
	 * @author Malachi Phillips
	 * Sort the <code>NewsStory</code> based on criterion
	 */
	private void sortNewsStories()
	{
		//Prompt the user for the type of sort criteria
		Object[] possibilities = {SortCriterion.SOURCE,
				SortCriterion.TOPIC,
				SortCriterion.SUBJECT,
				SortCriterion.LENGTH,
				SortCriterion.DATE_TIME};
		
		SortCriterion criterion = (SortCriterion)JOptionPane.showInputDialog(
				selectionView,
				"Enter in the sort criterion:",
				"Sort Criterion Entry",
				JOptionPane.PLAIN_MESSAGE,
				null,
				possibilities,
				null
				);
		
		// If the JOptionPane returned null, the user cancelled the input.
		// In that case, we stop executing.
		if(criterion == null)
			return;
		
		// get all the news stories
		DefaultListModel<NewsStory> news = new DefaultListModel<NewsStory>();
		news = newsDataBaseModel.getNewsStories();
		ArrayList<NewsStory> newsStories = new ArrayList<NewsStory>();
		for (int i = 0 ; i < news.size(); ++i){
			newsStories.add(news.get(i));
		}
		// empty news
		news.clear();
		
		// remove all the stories
		deleteAllNewsStories();
		
		
		switch(criterion){
		case SOURCE:
			Collections.sort(newsStories, SourceComparator.SOURCE_COMPARATOR);
			break;
		case TOPIC:
			Collections.sort(newsStories);
			break;
		case SUBJECT:
			Collections.sort(newsStories, SubjectComparator.SUBJECT_COMPARATOR);
			break;
		case LENGTH:
			Collections.sort(newsStories, LengthComparator.LENGTH_COMPARATOR);
			break;
		case DATE_TIME:
			Collections.sort(newsStories, DateComparator.DATE_COMPARATOR);
			break;
		}
		
		// re-cast into DefaultListModel
		NewsStory[] newsArray = new NewsStory[newsStories.size()];
		for(int i = 0 ; i < newsArray.length; ++i){
			newsArray[i] = newsStories.get(i);
		}
			
		
		newsDataBaseModel.setNewsStoryListModelFromArray(newsArray);
		
		JOptionPane.showMessageDialog(selectionView, 
				"News Stories were successfully sorted by " + criterion + ".",
				"Success", JOptionPane.PLAIN_MESSAGE);	
	}
	
	/**
	 * @author Malachi Phillips
	 * Delete several <code>NewsStory</code>s
	 */
	private void deleteNewsStories()
	{
		// delete the selected news stories
		// start by determining which ones were selected in the view
		int[] selected = selectionView.getSelectedNewsStories();
		DefaultListModel<NewsStory> news = new DefaultListModel<NewsStory>();
		NewsStory currentStory;
		// for each of the selected, get the story and add to the list
		for (int i = 0 ; i < selected.length; ++i){
			currentStory = newsDataBaseModel.getNewsStories().get(selected[i]);
			news.addElement(currentStory);
			
			// Since this story is going away, the news makers featured in it
			// should no longer know about it.
			currentStory.getNewsMaker1().removeNewsStory(currentStory);
			currentStory.getNewsMaker2().removeNewsStory(currentStory);
		}
		
		// call the removing method
		newsDataBaseModel.removeNewsStories(news);		
	}
	
	/**
	 * @author Malachi Phillips
	 * Delete <b><i>ALL</i></b> <code>NewsStory</code>s
	 */
	private void deleteAllNewsStories()
	{
		newsDataBaseModel.removeAllNewsStories();
		
		// We make the news makers forget about the deleted news stories. This
		// is as simple as clearing all their news story lists.
		NewsMakerListModel newsMakers 
				= newsDataBaseModel.getNewsMakerListModel();
		for(int i = 0; i < newsMakers.size(); i++)
		{
			newsMakers.get(i).setNewsStoryListModel(new NewsStoryListModel());
		}
	}
	
	/**
	 * @author Malachi Phillips
	 * Display the pie chart objects to the user.
	 */
	private void displayPieCharts()
	{
		// for each newsmaker selected
		// display a pie chart
		
		// selected newsmakers
		int[] selected = selectionView.getSelectedNewsMakers();
		
		// If there are no selected news makers, alert the user and return.
		if (0 == selected.length){
			JOptionPane.showMessageDialog(selectionView,
					"No newsmaker selected.",
					"Invalid Selection",
					JOptionPane.WARNING_MESSAGE);
		} else{
			// If there are selected news makers, go through the process for each.
			NewsMakerListModel newsMakerListModel = this.newsDataBaseModel.getNewsMakerListModel();
			for (int index : selected){
				NewsMakerModel newsMakerModel = newsMakerListModel.get(index);
				String newsMakerName = newsMakerModel.getName();
				
				// Get media types using the MediaTypeSelectionView
				this.selectedMediaTypes = new ArrayList<NewsMedia>();
				this.mediaTypeSelectionView = new MediaTypeSelectionView();
				MediaTypeSelectionListener mediaTypeSelectionListener =
						new MediaTypeSelectionListener();
				this.mediaTypeSelectionView.jbOkay.addActionListener(mediaTypeSelectionListener);
				this.mediaTypeSelectionView.jbCancel.addActionListener(mediaTypeSelectionListener);
				this.viewDialog = new JDialog(selectionView, newsMakerName, true);
				this.viewDialog.add(mediaTypeSelectionView);
				this.viewDialog.setResizable(false);
				this.viewDialog.pack();
				this.viewDialog.setLocationRelativeTo(selectionView);
				this.viewDialog.setVisible(true);
				
				// If the user has not selected any media types, then he must have
				// cancelled or closed out of the dialog (the OK button only ends
				// the dialog once you select some types, so it was not what ended
				// the dialog). In that case, we stop displaying text views.
				if(selectedMediaTypes.size() == 0)
				{
					JOptionPane.showMessageDialog(selectionView, 
							"Cancelled all pending pie chart displays.",
							"Cancelled Pie Charts",
							JOptionPane.PLAIN_MESSAGE);
					return;
				}
				
				// Get content type using JOptionPane.
				NewsContent selectedNewsContent = null;
				
				selectedNewsContent = (NewsContent) JOptionPane.showInputDialog(
						selectionView,
						"Graph news stories based on which content?",
						newsMakerName,
						JOptionPane.PLAIN_MESSAGE,
						null,
						NewsContent.values(),
						NewsContent.TOPIC);
				if (null == selectedNewsContent){
					JOptionPane.showMessageDialog(selectionView, 
							"Cancelled all pending pie chart displays.",
							"Cancelled Pie Charts",
							JOptionPane.PLAIN_MESSAGE);
					return;
				}
				
				// Get the metric type using JOptionPane
				NewsMetric selectedNewsMetric = null;
				selectedNewsMetric = (NewsMetric) JOptionPane.showInputDialog(
						selectionView,
						"Graph news stories based on which metric?",
						newsMakerName,
						JOptionPane.PLAIN_MESSAGE,
						null,
						NewsMetric.values(), NewsMetric.LENGTH);
				if (null == selectedNewsMetric){
					JOptionPane.showMessageDialog(selectionView, 
							"Cancelled all pending pie chart displays.",
							"Cancelled Pie Charts",
							JOptionPane.PLAIN_MESSAGE);
					return;
				}
				
				// Create the pie chart.
				PieChartView pieChartView = new PieChartView(newsMakerModel,
						selectedMediaTypes,
						selectedNewsContent,
						selectedNewsMetric);
				
				
				// Make sure the pie chart listens for model changes so that it 
				// can update itself
				newsMakerModel.addActionListener(pieChartView);
			}
		}
	}
	
	/**
	 * @author Malachi Phillips
	 * Display all the text views to the user.
	 */
	private void displayTextViews()
	{
		// for each newsmaker selected
		// display a text view
		// selected newsmakers
		int[] selected = selectionView.getSelectedNewsMakers();
		
		if(selected.length < 1)
		{
			JOptionPane.showMessageDialog(selectionView, 
					"Please select a news maker first.", 
					"No news maker selected.",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		DefaultListModel<NewsMakerModel> news = newsDataBaseModel.getNewsMakers();
		ArrayList<NewsMakerModel> selectedNewsMakers = new ArrayList<NewsMakerModel>();
		for (int i = 0 ; i < selected.length; ++i){
			selectedNewsMakers.add(news.get(selected[i]));
		}
		
		// imbed inside for-loop over the selected newsmakers
		for (NewsMakerModel nm : selectedNewsMakers){
			
			// loop over possible sorting choices
			Set<SortCriterion> possibleSorting = new TreeSet<SortCriterion>();
			List<SortCriterion> chosenSorting = new ArrayList<SortCriterion>();
			possibleSorting.add(SortCriterion.DATE_TIME);
			possibleSorting.add(SortCriterion.LENGTH);
			possibleSorting.add(SortCriterion.SOURCE);
			possibleSorting.add(SortCriterion.TOPIC);
			possibleSorting.add(SortCriterion.SUBJECT);
			int choiceNumber = 1;
			while(possibleSorting.size() > 1){
				String choiceString = null;
				switch(choiceNumber){
				case 1:
					choiceString = "primary";
					break;
				case 2:
					choiceString = "secondary";
					break;
				case 3:
					choiceString = "tertiary";
					break;
				case 4:
					choiceString = "quaternary";
					break;
				default:
					// Should never reach here
				}
				

				Object[] options = new Object[possibleSorting.size()];

				//  make the options list
				int count = 0;
				for (SortCriterion s : possibleSorting){
					options[count] = s;
					count++; //increment by one
				}
				SortCriterion sortCriterion = (SortCriterion)JOptionPane.showInputDialog(
						selectionView,
						"Choose the " + choiceString + " sort criterion for news stories about "
						+ nm.getName() + ".",
						nm.getName() + " - Text Display",
						JOptionPane.PLAIN_MESSAGE,
						null,
						options,
						null
						);
				// If the user cancels or closes out of the sort criterion selection window
				if(sortCriterion == null)
				{
					JOptionPane.showMessageDialog(selectionView, 
							"Cancelled all pending text displays.",
							"Cancelled Text Displays.",
							JOptionPane.PLAIN_MESSAGE);
					return;
				}
				
				// tack onto list
				chosenSorting.add(sortCriterion);
				
				// remove from set, so no longer possible
				possibleSorting.remove(sortCriterion);
				choiceNumber++;
			} // at end, should have the list of sorting options
			
			// Get media types using the MediaTypeSelectionView
			this.selectedMediaTypes = new LinkedList<NewsMedia>();
			this.mediaTypeSelectionView = new MediaTypeSelectionView();
			MediaTypeSelectionListener mediaTypeSelectionListener =
					new MediaTypeSelectionListener();
			this.mediaTypeSelectionView.jbOkay.addActionListener(mediaTypeSelectionListener);
			this.mediaTypeSelectionView.jbCancel.addActionListener(mediaTypeSelectionListener);
			this.viewDialog = new JDialog(selectionView, nm.getName() + " Text Display", true);
			this.viewDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.viewDialog.add(mediaTypeSelectionView);
			this.viewDialog.setResizable(false);
			this.viewDialog.pack();
			this.viewDialog.setLocationRelativeTo(selectionView);
			this.viewDialog.setVisible(true);
			
			// If the user has not selected any media types, then he must have
			// cancelled or closed out of the dialog (the OK button only ends
			// the dialog once you select some types, so it was not what ended
			// the dialog). In that case, we stop displaying text views.
			if(selectedMediaTypes.size() == 0)
			{
				JOptionPane.showMessageDialog(selectionView, 
						"Cancelled all pending text displays.",
						"Cancelled Text Displays",
						JOptionPane.PLAIN_MESSAGE);
				return;
			}
			
			// create text view(s)
			TextView text = new TextView(nm, selectedMediaTypes, chosenSorting);		
			nm.addActionListener(text);
		}			
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
				exportNewsStories();
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
				// should check that some are actually selected here
				// TODO:
				editNewsMakers();
			}
			else if("Delete Newsmaker".equals(clickedItemText))
			{
				// prompt the user if they *REALLY* want to do this
				int choice = JOptionPane.showConfirmDialog(
						selectionView,
						"Do you wish to delete the selected newsmaker(s)?",
						"Confirm Deletion",
						
						JOptionPane.YES_NO_OPTION);
				if (choice != JOptionPane.YES_OPTION) return;
				deleteNewsMakers();
			}
			else if("Delete Newsmaker List".equals(clickedItemText))
			{
				// prompt the user if they *REALLY* want to do this
				int choice = JOptionPane.showConfirmDialog(
						selectionView,
						"Do you wish to delete all the newsmakers?",
						"Confirm Deletion",						
						JOptionPane.YES_NO_OPTION);
				if (choice != JOptionPane.YES_OPTION) return;
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
				sortNewsStories();
			}
			else if("Delete News Story".equals(clickedItemText))
			{
				// prompt the user if they *REALLY* want to do this
				int choice = JOptionPane.showConfirmDialog(
						selectionView,
						"Do you wish to delete the selected news stories?",
						"Confirm Deletion",						
						JOptionPane.YES_NO_OPTION);
				if (choice != JOptionPane.YES_OPTION) return;
				deleteNewsStories();
			}
			else if("Delete All News Stories".equals(clickedItemText))
			{
				// prompt the user if they *REALLY* want to do this
				int choice = JOptionPane.showConfirmDialog(
						selectionView,
						"Do you wish to delete all the news stories?",
						"Confirm Deletion",						
						JOptionPane.YES_NO_OPTION);
				if (choice != JOptionPane.YES_OPTION) return;
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
			// check if the field has been updated
			String newName = editNewsMakerView.jtfName.getText();
			
			// set the new name
			editNewsMakerView.newsMakerModel.setName(newName);
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
		 * Overridden method used for <code>ActionListener</code>
		 * 
		 * Performs the action specified
		 * 
		 * @param actionEvent
		 *   <code>ActionEvent</code> needing to occur
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) 
		{
			// Acquire a reference to the news maker model being edited.
			NewsMakerModel newsMakerModel = newsDataBaseModel
					.getNewsMakerListModel().get(
					editNewsMakerView.newsMakerModel);
			
			// Bring the selected news stories for the news maker being edited
			// into an array list.
			ArrayList<NewsStory> selectedStories = new ArrayList<NewsStory>();
			int[] selected = editNewsMakerView.getSelectedNewsStoryIndices();
			for (int index : selected) 
			{ 
				selectedStories.add(newsMakerModel.getNewsStoryListModel()
						.get(index));
			}
				
			for(NewsStory story : selectedStories)
			{
				// Take the story out of the news maker's list.
				newsMakerModel.removeNewsStory(story);
				
				// Replace the news maker with 'None' in the story.
				if (story.getNewsMaker1().equals(newsMakerModel)) 
					story.setNewsMaker1(newsDataBaseModel.none);
				else if (story.getNewsMaker2().equals(newsMakerModel)) 
					story.setNewsMaker2(newsDataBaseModel.none);						
			}				

			// Update the selection view to reflect any changes.
			selectionView.repaint();
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
			if("Add News Story".equals(actionEvent.getActionCommand())){
				// add news story
				
				// code for add news story, from the addEditNewsStoryView
				
				// get all of the fields!
				String newsMakerName1 = (String) addEditNewsStoryView.jcbNewsStoryNewsMaker1.getSelectedItem();
				NewsMakerModel newsMaker1 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName1);
				
				String newsMakerName2 = (String) addEditNewsStoryView.jcbNewsStoryNewsMaker2.getSelectedItem();
				NewsMakerModel newsMaker2 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName2);
				
				// check if these are the same (except for none)
				if(newsMakerName1.equals(newsMakerName2) && !"None".equals(newsMakerName1)){
					JOptionPane.showMessageDialog(selectionView,
							"Cannot select two of the same newsmakers for a news story.",
							"Invalid Selection",
							JOptionPane.WARNING_MESSAGE);
					return; // halt the process here
				}
				
				PartOfDay pod = (PartOfDay) addEditNewsStoryView.jcbNewsStoryPartOfDay.getSelectedItem();
				
				String source = (String) addEditNewsStoryView.jcbNewsStorySource.getSelectedItem();
				
				// just why?
				int length = (int) (long) (addEditNewsStoryView.jtftfNewsStoryLength.getValue());
				
				String topic = (String) addEditNewsStoryView.jcbNewsStoryTopic.getSelectedItem();
				
				String subject = (String) addEditNewsStoryView.jcbNewsStorySubject.getSelectedItem();
				
				// Date information
				int day = (int) addEditNewsStoryView.jcbNewsStoryDay.getSelectedItem();
				
				Month month = (Month) addEditNewsStoryView.jcbNewsStoryMonth.getSelectedItem();
				
				int year = (int) addEditNewsStoryView.jcbNewsStoryYear.getSelectedItem();
				
				// Construct LocalDate object
				LocalDate date = LocalDate.of(year, month.toInt(), day);
				
				// determine the type of the story
				
				NewsMedia type = (NewsMedia) addEditNewsStoryView.jcbNewsStoryType.getSelectedItem();
				
				NewsStory news = null;
				
				// check if the news makers exist, if not, add them
				if(!newsDataBaseModel.getNewsMakerListModel().contains(newsMaker1)){
					// create new NewsMakerModel
					newsDataBaseModel.addNewsMakerModel(new NewsMakerModel(newsMakerName1));
					newsMaker1 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName1);
				}
				if(!newsDataBaseModel.getNewsMakerListModel().contains(newsMaker2) && !"None".equals(newsMakerName2)){
					// create new NewsMakerModel
					newsDataBaseModel.addNewsMakerModel(new NewsMakerModel(newsMakerName2));
					newsMaker2 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName2);
				}
				
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
				
				// add to relevant news makers
				newsMaker1.addNewsStory(news);
				
				// check for if it is none
				if(!"None".equals(newsMakerName2)){
					newsMaker2.addNewsStory(news);
				}
				
				// add news story
				newsDataBaseModel.addNewsStory(news);	
				
				viewDialog.dispose(); // once added, throw out
				
				
			} else if ("Edit News Story".equals(actionEvent.getActionCommand())){
				// tuck the current story into a DefaultListModel
				DefaultListModel<NewsStory> edited = new DefaultListModel<NewsStory>();
				edited.addElement(editedNewsStory);
				
				// remove the story from the list
				newsDataBaseModel.removeNewsStories(edited);
				
				// remove also from the respective newsMakers
				NewsMakerModel newsMakerFirst = newsDataBaseModel.getNewsMakerListModel().get(editedNewsStory.getNewsMaker1());
				NewsMakerModel newsMakerSecond = newsDataBaseModel.getNewsMakerListModel().get(editedNewsStory.getNewsMaker2());
				
				newsMakerFirst.removeNewsStory(editedNewsStory);
				newsMakerSecond.removeNewsStory(editedNewsStory);
				
				// code for add news story, from the addEditNewsStoryView
				
				// get all of the fields!
				String newsMakerName1 = (String) addEditNewsStoryView.jcbNewsStoryNewsMaker1.getSelectedItem();
				NewsMakerModel newsMaker1 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName1);
				
				String newsMakerName2 = (String) addEditNewsStoryView.jcbNewsStoryNewsMaker2.getSelectedItem();
				NewsMakerModel newsMaker2 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName2);
				
				// check if these are the same (except for none)
				if(newsMakerName1.equals(newsMakerName2) && !"None".equals(newsMakerName1)){
					JOptionPane.showMessageDialog(selectionView,
							"Cannot select two of the same newsmaker for a news story.",
							"Invalid Selection",
							JOptionPane.WARNING_MESSAGE);
					return; // halt the process here
				}
				
				PartOfDay pod = (PartOfDay) addEditNewsStoryView.jcbNewsStoryPartOfDay.getSelectedItem();
				
				String source = (String) addEditNewsStoryView.jcbNewsStorySource.getSelectedItem();
				
				int length = (int) (long) (addEditNewsStoryView.jtftfNewsStoryLength.getValue());
				
				String topic = (String) addEditNewsStoryView.jcbNewsStoryTopic.getSelectedItem();
				
				String subject = (String) addEditNewsStoryView.jcbNewsStorySubject.getSelectedItem();
				
				// Date information
				int day = (int) addEditNewsStoryView.jcbNewsStoryDay.getSelectedItem();
				
				Month month = (Month) addEditNewsStoryView.jcbNewsStoryMonth.getSelectedItem();
				
				int year = (int) addEditNewsStoryView.jcbNewsStoryYear.getSelectedItem();
				
				// Construct LocalDate object
				LocalDate date = LocalDate.of(year, month.toInt(), day);
				
				// determine the type of the story
				
				NewsMedia type = (NewsMedia) addEditNewsStoryView.jcbNewsStoryType.getSelectedItem();
				
				NewsStory news = null;
				
				// check if the news makers exist, if not, add them
				if(!newsDataBaseModel.getNewsMakerListModel().contains(newsMaker1)){
					// create new NewsMakerModel
					newsDataBaseModel.addNewsMakerModel(new NewsMakerModel(newsMakerName1));
					newsMaker1 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName1);
				}
				if(!newsDataBaseModel.getNewsMakerListModel().contains(newsMaker2) && !"None".equals(newsMakerName2)){
					// create new NewsMakerModel
					newsDataBaseModel.addNewsMakerModel(new NewsMakerModel(newsMakerName2));
					newsMaker2 = newsDataBaseModel.getNewsMakerListModel().getExactMatch(newsMakerName2);
				}
				
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
				
				newsMaker1.addNewsStory(news);
				
				// check for if it is none
				if(!"None".equals(newsMakerName2)){
					newsMaker2.addNewsStory(news);
				}
				
				// add news story
				newsDataBaseModel.addNewsStory(news);
			
				// dispose of view dialog
				viewDialog.dispose();
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
			selectedMediaTypes = new LinkedList<NewsMedia>();
			
			if("OK".equals(actionEvent.getActionCommand())){
				// clear selected media types -- start fresh
				
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
				
				// If the user hasn't selected any media types, ask him to do
				// so.
				if(selectedMediaTypes.size() == 0)
				{
					JOptionPane.showMessageDialog(viewDialog, 
							"Please select a type of news story.",
							"No Media Types Selected.",
							JOptionPane.WARNING_MESSAGE);					
				}
				// Otherwise, we have the information we need, so we can close
				// the dialog.
				else
				{				
					viewDialog.dispose();
				}
			}
			// If the user cancels out of the media type selection view,
			// close the window.
			else if("Cancel".equals(actionEvent.getActionCommand()))
			{
				viewDialog.dispose();
			}
		}		
	}	
}
