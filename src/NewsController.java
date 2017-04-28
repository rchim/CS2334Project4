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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	 * TODO
	 */
	private void saveNewsData()
	{
		// TODO
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
}
