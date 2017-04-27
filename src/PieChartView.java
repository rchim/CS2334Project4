import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * This view displays a pie chart representing data about the news stories for
 * a particular news maker.
 * </P>
 * <P>
 * This class was written by Malachi Phillips (ID 112933834).
 * </P>
 * 
 * @author Malachi Phillips
 */
public class PieChartView implements ActionListener 
{
	/** <code>PieChart</code> object to draw */
	private PieChart pieChart;
	
	/** <code>NewsMakerModel</code> associated with the drawing **/
	private NewsMakerModel newsMakerModel;
	
	/** String holding the media information **/
	private String media;
	
	/** String holding the content information **/
	private String content;
	
	/** String holding the measure information **/
	private String measure;
	
	/**
	 * Public constructor for a <code>PieChartView</code>
	 * 
	 * Create all the GUI components
	 * 
	 * @param newsMakerModel
	 *   <code>NewsMakerModel</code> to be associated with the <code>PieChart</code>
	 * @param media
	 *   String holding the media information
	 * @param content
	 *   String holding the content information
	 * @param measure
	 *   String holding the measure information
	 */
	public PieChartView(NewsMakerModel newsMakerModel, String media, String content, String measure){
		// create the pie chart object
		
	}
	
	/**
	 * Private helper method for constructing the proper title
	 * 
	 * @return title
	 *   The correct title to be displayed on the chart
	 */
	private String constructTitle(){
		return null;
	}
	
	/**
	 * Private helper method for constructing the wedges needed
	 * 
	 * @return wedges
	 *   List of wedges to be drawn
	 */
	private List<Wedge> constructWedges(){
		return null;
	}
	
	/**
	 * Public <code>actionPerformed</code> as part of the 
	 * <code>ActionListener</code> implementation
	 * 
	 * @param actionEvent
	 *   The event to be processed
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent){
		
	}
	
	
}
