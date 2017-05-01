/**
 * Group Members:
 * Ryan Chimienti (ID 113392576)
 * Malachi Phillips (ID 112933834)
 * 
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * NoozDriver is the driver class for Nooz, a simple news story data
 * system. Its main method links up the controller, selection view, and
 * overarching model. Then, it gets out of the way and lets them do the work.
 * </P>
 * <P>
 * This class was written by Ryan Chimienti (ID 113392576).
 * </P>
 *
 * @author Ryan Chimienti
 */
public class NoozDriver
{
	/**
	 * The overarching model for the Nooz application.
	 */
	private static NewsDataBaseModel newsDataBaseModel 
			= new NewsDataBaseModel();
	
	/**
	 * The selection view for the Nooz application.
	 */
	private static SelectionView selectionView = new SelectionView();
	
	/**
	 * The controller for the Nooz application.
	 */
	private static NewsController newsController = new NewsController();
	
	/**
	 * The entry point for the Nooz application, which connects the controller,
	 * selection view, and overarching model. After they are connected, they do
	 * all the work.
	 * 
	 * @param args The program arguments, which are ignored.
	 */
	public static void main(String[] args)
	{
		// The selection view needs to know the overarching model so that when
		// the model is updated, the selection view can ask what has changed.
		selectionView.setNewsDataBaseModel(newsDataBaseModel);
		
		// The news controller needs to know the overarching model so it can
		// alter it in response to an event from a view. 
		newsController.setNewsDataBaseModel(newsDataBaseModel);
		
		// The news controller needs to know the selection view so it can
		// listen for user interactions with the view.
		newsController.setSelectionView(selectionView);
	}
}
