import java.awt.GridLayout;

import javax.swing.*   ;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * TODO
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
	JCheckBox jcbNewspaper = new JCheckBox("Newspaper Story");
	
	/** <code>JComboBox</code> for TV **/
	JCheckBox jcbTVNews = new JCheckBox("TV News Story");
	
	/** <code>JComboBox</code> for online **/
	JCheckBox jcbOnline = new JCheckBox("Online News Story");
	
	/** <code>JLabel</code> for the media type **/
	private JLabel jlblMediaType = new JLabel("Graph news stories from which media type(s)?");
	
	/** <code>JButton</code> blank button **/
	private JButton jbBlank = new JButton("Blank");
	
	/** <code>JButton</code> cancel button **/
	JButton jbCancel = new JButton("Cancel");
	
	/** <code>JButton</code> okay button **/
	JButton jbOkay = new JButton("Okay");
	
	/** <code>JPanel</code> to hold the buttons **/
	JPanel jbCompletionButtons;
	
	/** <code>JPanel</code> to hold the media types **/
	JPanel jpMediaType;
	
	/**
	 * Public constructor. Draws the GUI components.
	 */
	public MediaTypeSelectionView(){
		jpMediaType.setLayout(new GridLayout(4,1));
		jpMediaType.add(jlblMediaType);
		jpMediaType.add(jcbNewspaper);
		jpMediaType.add(jcbTVNews);
		jpMediaType.add(jcbOnline);
		
		jbCompletionButtons.setLayout(new GridLayout(1,2));
		jbCompletionButtons.add(jbCancel);
		jbCompletionButtons.add(jbOkay);
		
		this.setLayout(new GridLayout(2,1));
		this.add(jpMediaType);
		this.add(jbCompletionButtons);
	}
}
