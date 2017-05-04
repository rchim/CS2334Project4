import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * The selection view is the first view the user will see when the program is
 * loaded. It displays two lists, one for news makers and one for news stories.
 * The lists are kept current based on the model data, and they can be operated
 * on via any of the four menus in this view.
 * </P>
 * <P>
 * This class was written by Ryan Chimienti (ID 113392576). 
 * </P>
 * 
 * @author Ryan Chimienti
 */
public class SelectionView extends JFrame implements ActionListener
{
	/**
	 * A code to ensure that a serialized SelectionView can be instantiated
	 * as a SelectionView according to the current class design. Even though we
	 * don't intend to serialize this view, this is a good measure to take
	 * because all JFrames are serializable.
	 */
	private static final long serialVersionUID = 3828705860609989238L;
	
	/**
	 * The model to which this view is linked. By default, we use an empty
	 * model.
	 */
	private NewsDataBaseModel newsDataBaseModel;
	
	/**
	 * The menu bar at the top of the window.
	 */
	private JMenuBar jmb = new JMenuBar();
	
	/**
	 * The File menu.
	 */
	private JMenu jmFile = new JMenu("File");
	
	/**
	 * The Load item in the File menu.
	 */
	private JMenuItem jmiLoad = new JMenuItem("Load");
	
	/**
	 * The Save item in the File menu.
	 */
	private JMenuItem jmiSave = new JMenuItem("Save");
	
	/**
	 * The Import item in the File menu.
	 */
	private JMenuItem jmiImport = new JMenuItem("Import");
	
	/**
	 * The Export item in the File menu.
	 */
	private JMenuItem jmiExport = new JMenuItem("Export");
	
	/**
	 * The Newsmakers menu.
	 */
	private JMenu jmNewsMaker = new JMenu("Newsmakers");
	
	/**
	 * The Add News Maker item in the Newsmakers menu.
	 */
	private JMenuItem jmiAddNewsMaker = new JMenuItem("Add Newsmaker");
	
	/**
	 * The Edit News Maker item in the Newsmakers menu.
	 */
	private JMenuItem jmiEditNewsMaker = new JMenuItem("Edit Newsmaker");
	
	/**
	 * The Delete News Maker item in the Newsmakers menu.
	 */
	private JMenuItem jmiDeleteNewsMaker = new JMenuItem("Delete Newsmaker");
	
	/**
	 * The Delete News Maker List item in the Newsmakers menu.
	 */
	private JMenuItem jmiDeleteNewsMakerList 
			= new JMenuItem("Delete Newsmaker List");
	
	/**
	 * The News Stories menu.
	 */
	private JMenu jmNewsStory = new JMenu("News Stories");
	
	/**
	 * The Add News Story item in the News Stories menu.
	 */
	private JMenuItem jmiAddNewsStory = new JMenuItem("Add News Story");
	
	/**
	 * The Edit News Story item in the News Stories menu.
	 */
	private JMenuItem jmiEditNewsStory = new JMenuItem("Edit News Story");
	
	/**
	 * The Sort News Stories item in the News Stories menu.
	 */
	private JMenuItem jmiSortNewsStories = new JMenuItem("Sort News Stories");
	
	/**
	 * The Delete News Story item in the News Stories menu.
	 */
	private JMenuItem jmiDeleteNewsStory = new JMenuItem("Delete News Story");
	
	/**
	 * The Delete All News Stories item in the News Stories menu.
	 */
	private JMenuItem jmiDeleteAllNewsStories
			= new JMenuItem("Delete All News Stories");
	
	/**
	 * The Display menu.
	 */
	private JMenu jmDisplay = new JMenu("Display");
	
	/**
	 * The Pie Chart item in the Display menu.
	 */
	private JMenuItem jmiPieChart = new JMenuItem("Pie Chart");
	
	/**
	 * The Text item in the Display menu.
	 */
	private JMenuItem jmiText = new JMenuItem("Text");
	
	/**
	 * The list of news makers.
	 */
	private JList<NewsMakerModel> jlNewsMakerList = new JList<NewsMakerModel>();
	
	/**
	 * The scroll pane to contain the list of news makers.
	 */
	private JScrollPane jspNewsMakerList = new JScrollPane();
	
	/**
	 * The panel to contain the scroll pane for the list of news makers.
	 */
	private JPanel jpNewsMakerList = new JPanel(new BorderLayout());
	
	/**
	 * The list of news stories.
	 */
	private JList<NewsStory> jlNewsStoryList = new JList<NewsStory>();
	
	/**
	 * The scroll pane to contain the list of news stories.
	 */
	private JScrollPane jspNewsStoryList = new JScrollPane();
	
	/**
	 * The panel to contain the scroll pane for the list of news stories.
	 */
	private JPanel jpNewsStoryList = new JPanel(new BorderLayout());
	
	/**
	 * A split pane whose left side will house the news maker scroll pane and
	 * and whose right side will house the news story scroll pane.
	 */
	private JSplitPane splitPane = new JSplitPane();
	
	/**
	 * The constructor for this view adds all the components to the frame and
	 * then shows the frame.
	 */
	public SelectionView()
	{
		this.setTitle("Nooz");
		
		// Assign the view an empty model by default. This guarantees that the 
		// proper menu items are disabled to reflect the fact that the view
		// doesn't yet have data.
		this.setNewsDataBaseModel(new NewsDataBaseModel());	
		
		// Add the menu items to their corresponding menus.
		jmFile.add(jmiLoad);
		jmFile.add(jmiSave);
		jmFile.add(jmiImport);
		jmFile.add(jmiExport);
		jmNewsMaker.add(jmiAddNewsMaker);
		jmNewsMaker.add(jmiEditNewsMaker);
		jmNewsMaker.add(jmiDeleteNewsMaker);
		jmNewsMaker.add(jmiDeleteNewsMakerList);
		jmNewsStory.add(jmiAddNewsStory);
		jmNewsStory.add(jmiEditNewsStory);
		jmNewsStory.add(jmiSortNewsStories);
		jmNewsStory.add(jmiDeleteNewsStory);
		jmNewsStory.add(jmiDeleteAllNewsStories);
		jmDisplay.add(jmiPieChart);
		jmDisplay.add(jmiText);
		
		// Add the menus the menu bar.
		jmb.add(jmFile);
		jmb.add(jmNewsMaker);
		jmb.add(jmNewsStory);
		jmb.add(jmDisplay);
		
		// Add the menu bar to the frame.
		this.setJMenuBar(jmb);	
		
		// Assign the JLists to their scroll panes.
		jspNewsMakerList.setViewportView(jlNewsMakerList);
		jspNewsStoryList.setViewportView(jlNewsStoryList);
				
		// Add the scroll panes to their JPanels.
		jpNewsMakerList.add(jspNewsMakerList, BorderLayout.CENTER);
		jpNewsStoryList.add(jspNewsStoryList, BorderLayout.CENTER);
		
		// Add JLabels above the scroll panes to distinguish between the
		// 'Newsmakers' and 'News Stories' scroll panes.
		jpNewsMakerList.add(new JLabel("Newsmakers"), BorderLayout.NORTH);
		jpNewsStoryList.add(new JLabel("News Stories"), BorderLayout.NORTH);
		
		// Add the JPanels to the split pane.		
		splitPane.setLeftComponent(jpNewsMakerList);
		splitPane.setRightComponent(jpNewsStoryList);	
		
		// The split pane divider is initially positioned so as to give at least
		// 150px of space to the Newsmakers scroll pane.
		splitPane.setDividerLocation(150 + splitPane.getInsets().left);
		
		// Set up the content pane and add the split pane to it.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(splitPane, BorderLayout.CENTER);		
		setSize(700, 500);
		setVisible(true);
	}
	
	/**
	 * Attaches an action listener to each item in the File menu.
	 * 
	 * @param fileMenuListener The listener that will listen for events from the
	 * File menu items.
	 */
	public void registerFileMenuListener(ActionListener fileMenuListener)
	{
		for(int i = 0; i < jmFile.getItemCount(); i++)
			jmFile.getItem(i).addActionListener(fileMenuListener);
	}
	
	/**
	 * Attaches an action listener to each item in the Newsmakers menu.
	 * 
	 * @param newsMakerMenuListener The listener that will listen for events
	 * from the Newsmakers menu items.
	 */
	public void registerNewsMakerMenuListener(
			ActionListener newsMakerMenuListener)
	{
		for(int i = 0; i < jmNewsMaker.getItemCount(); i++)
			jmNewsMaker.getItem(i).addActionListener(newsMakerMenuListener);
	}
	
	/**
	 * Attaches an action listener to each item in the News Stories menu.
	 * 
	 * @param newsStoryMenuListener The listener that will listen for events
	 * from the News Stories menu items.
	 */
	public void registerNewsStoryMenuListener(
			ActionListener newsStoryMenuListener)
	{
		for(int i = 0; i < jmNewsStory.getItemCount(); i++)
			jmNewsStory.getItem(i).addActionListener(newsStoryMenuListener);
	}
	
	/**
	 * Attaches an action listener to each item in the Display menu.
	 * 
	 * @param displayMenuListener The listener that will listen for events
	 * from the Display menu items.
	 */
	public void registerDisplayMenuListener(ActionListener displayMenuListener)
	{
		for(int i = 0; i < jmDisplay.getItemCount(); i++)
			jmDisplay.getItem(i).addActionListener(displayMenuListener);
	}
	
	/**
	 * Sets the overarching model for this view.
	 * 
	 * @param newsDataBaseModel The overarching model whose data this view
	 * should reflect.
	 */
	public void setNewsDataBaseModel(NewsDataBaseModel newsDataBaseModel)
	{
		this.newsDataBaseModel = newsDataBaseModel;
		this.newsDataBaseModel.addActionListener(this);
				
		this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				"Set new model."));
	}
	
	/**
	 * Responds to a change in the model data. The response consists of
	 * determining which menu items should be enabled and disabled and setting
	 * them accordingly. Also, it is ensured that the JLists are displaying the
	 * most current list models.
	 * 
	 * @param e The event being responded to.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// In case the default list models within the database have been
		// replaced with new ones (or in case we are dealing with an entirely
		// new model), we make sure the JLists point to the most current list
		// models.
		this.jlNewsMakerList.setModel(this.newsDataBaseModel.getNewsMakers());		
		this.jlNewsStoryList.setModel(this.newsDataBaseModel.getNewsStories());
		
		// Enable all the menu items by default. We will later disable those
		// that are unusable.
		jmiSave.setEnabled(true);
		jmiExport.setEnabled(true);
		jmiEditNewsMaker.setEnabled(true);
		jmiDeleteNewsMaker.setEnabled(true);
		jmiDeleteNewsMakerList.setEnabled(true);
		jmiEditNewsStory.setEnabled(true);
		jmiSortNewsStories.setEnabled(true);
		jmiDeleteNewsStory.setEnabled(true);
		jmiDeleteAllNewsStories.setEnabled(true);
		jmiPieChart.setEnabled(true);
		jmiText.setEnabled(true);
		
		// Remove all the tool tips by default. We will add them to any menu
		// items we disable to explain why those items are unusable.
		jmiSave.setToolTipText(null);
		jmiExport.setToolTipText(null);
		jmiEditNewsMaker.setToolTipText(null);
		jmiDeleteNewsMaker.setToolTipText(null);
		jmiDeleteNewsMakerList.setToolTipText(null);
		jmiEditNewsStory.setToolTipText(null);
		jmiSortNewsStories.setToolTipText(null);
		jmiDeleteNewsStory.setToolTipText(null);
		jmiDeleteAllNewsStories.setToolTipText(null);
		jmiPieChart.setToolTipText(null);
		jmiText.setToolTipText(null);
		
		// Determine whether news makers or news stories or both are absent from
		// the database.
		boolean newsMakersAbsent = (jlNewsMakerList.getModel().getSize() == 1 
				&& jlNewsMakerList.getModel().getElementAt(0)
						.equals(newsDataBaseModel.none));
		boolean newsStoriesAbsent = jlNewsStoryList.getModel().getSize() == 0;
		
		// If news makers are absent from the database, disable all the menu
		// items that need news makers to be usable.
		if(newsMakersAbsent)
		{
			jmiEditNewsMaker.setEnabled(false);
			jmiDeleteNewsMaker.setEnabled(false);
			jmiDeleteNewsMakerList.setEnabled(false);
						
			jmiEditNewsMaker.setToolTipText("Cannot edit the special newsmaker 'None'.");
			jmiDeleteNewsMaker.setToolTipText("Cannot delete the special newsmaker 'None'.");
			jmiDeleteNewsMakerList.setToolTipText("Cannot delete the special newsmaker 'None'.");	
		}
		
		// If news stories are absent from the database, Disable all the menu
		// items that need news stories to be usable.
		if(newsStoriesAbsent)
		{
			jmiEditNewsStory.setEnabled(false);
			jmiSortNewsStories.setEnabled(false);
			jmiDeleteNewsStory.setEnabled(false);
			jmiDeleteAllNewsStories.setEnabled(false);
			jmiPieChart.setEnabled(false);
			jmiText.setEnabled(false);
			jmiExport.setEnabled(false);
			
			jmiEditNewsStory.setToolTipText("No news stories to edit.");
			jmiSortNewsStories.setToolTipText("No news stories to sort.");
			jmiDeleteNewsStory.setToolTipText("No news stories to delete.");
			jmiDeleteAllNewsStories.setToolTipText("No news stories to delete.");
			jmiPieChart.setToolTipText("No news stories to display.");
			jmiText.setToolTipText("No news stories to display.");
			jmiExport.setToolTipText("No news stories to export.");
		}
		
		// If both news makers and news stories are absent from the database,
		// there is no data to save, so disable that item.
		if(newsMakersAbsent && newsStoriesAbsent)
		{
			jmiSave.setEnabled(false);			
			
			jmiSave.setToolTipText("No data to save.");
		}		
	}
	
	/**
	 * Returns the indices in the news maker list of the news makers that are
	 * currently selected. Since the list remains ordered by name, these indices
	 * describe where the selected news makers lie alphabetically relative to
	 * all the news makers in the model. 
	 * 
	 * @return The indices in the news maker list of the news makers that are
	 * currently selected.
	 */
	public int[] getSelectedNewsMakers()
	{
		return jlNewsMakerList.getSelectedIndices();
	}
	
	/**
	 * Returns the indices in the news story list of the news stories that are
	 * currently selected. 
	 * 
	 * @return The indices in the news story list of the news stories that are
	 * currently selected.
	 */
	public int[] getSelectedNewsStories()
	{
		return jlNewsStoryList.getSelectedIndices();
	}
}
