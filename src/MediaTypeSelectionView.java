import java.awt.GridLayout;

import javax.swing.*   ;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * View where the user selects which type of media that will
 * be displayed in the form of either a text view or a pie chart view
 * 
 * The user is free to mark as many types of media to display as he/she likes.
 * </P>
 * <P>
 * This class was written by Malachi Phillips (ID 112933834).
 * </P>
 * 
 * @author Malachi Phillips
 */
public class MediaTypeSelectionView extends JPanel
{
	/**
	 * serialVersionUID for <code>JPanel</code>
	 */
	private static final long serialVersionUID = 2059807358828772171L;

	/** <code>JComboBox</code> for newspaper **/
	JCheckBox jcbNewspaper = new JCheckBox(NewsMedia.NEWSPAPER.toString());
	
	/** <code>JComboBox</code> for TV **/
	JCheckBox jcbTVNews = new JCheckBox(NewsMedia.TV.toString());
	
	/** <code>JComboBox</code> for online **/
	JCheckBox jcbOnline = new JCheckBox(NewsMedia.ONLINE.toString());
	
	/** <code>JLabel</code> for the media type **/
	private JLabel jlblMediaType = new JLabel("Display news stories from which media type(s)?");
	
	/** <code>JButton</code> blank button **/
	private JButton jbBlank = new JButton();
	
	/** <code>JButton</code> cancel button **/
	JButton jbCancel = new JButton("Cancel");
	
	/** <code>JButton</code> okay button **/
	JButton jbOkay = new JButton("OK");
	
	/** <code>JPanel</code> to hold the buttons **/
	JPanel jpCompletionButtons = new JPanel(new GridLayout(0,1));
	
	/** <code>JPanel</code> to hold the media types **/
	JPanel jpMediaType = new JPanel(new GridLayout(0,1));
	
	/**
	 * Public constructor. Draws the GUI components.
	 */
	public MediaTypeSelectionView(){
		jbBlank.setVisible(false);
		jbOkay.setActionCommand("OK");
		jbOkay.setSelected(true);
		jbCancel.setActionCommand("Cancel");
		
		jpCompletionButtons.add(jbBlank);
		jpCompletionButtons.add(jbCancel);
		jpCompletionButtons.add(jbOkay);
		
		jpMediaType.add(jlblMediaType);
		jpMediaType.add(jcbNewspaper);
		jpMediaType.add(jcbTVNews);
		jpMediaType.add(jcbOnline);
		jpMediaType.add(jpCompletionButtons);
		jpMediaType.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		
		this.add(jpMediaType);
	}
}
