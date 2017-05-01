import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.*;

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
		this.newsMakerModel = newsMakerModel;
		this.media = media;
		this.content = content;
		this.measure = measure;
		
		// construct the title
		String title = constructTitle();
		
		// construct the wedges
		List<Wedge> wedges = constructWedges();
		
		// construct pie chart object
		
		pieChart = new PieChart(title, wedges);
	}
	
	/**
	 * Private helper method for constructing the proper title
	 * 
	 * @return title
	 *   The correct title to be displayed on the chart
	 */
	private String constructTitle(){
		// title has several components
		// NewsMaker name
		// media
		// content
		// and measure
		return ("NewsMaker: " + newsMakerModel.getName() + "; Media: " + media + "; Content:  " + content + "; Measure: " + measure);
	}
	
	/**
	 * Private helper method for constructing the wedges needed
	 * 
	 * @return wedges
	 *   List of wedges to be drawn
	 */
	private List<Wedge> constructWedges(){
		// go through the newsMakerList model associated with the story
		
		DefaultListModel<NewsStory> newsList = newsMakerModel.getNewsStoryListModel().getNewsStories();
		List<NewsStory> stories = new ArrayList<NewsStory>();
		List<Wedge> wedges = new ArrayList<Wedge>();
		
		// cast the media into a specific instance of NewsMedia
		
		
		for(int i = 0 ; i < newsList.size(); ++i){
			// get the current story
			NewsStory current = newsList.getElementAt(i);
			
			// determine if this is the right type of media
			switch(media){
			case "TV":
				// only get if instance of TVNewsStory
				if (current instanceof TVNewsStory) stories.add(current);
				break;
			case "Online":
				// only get if instance of OnlineNewsStory
				if (current instanceof OnlineNewsStory) stories.add(current);
				break;
			case "Newspaper":
				// only get if instance of NewspaperStory
				if (current instanceof NewspaperStory) stories.add(current);
				break;
			}
		} // at this point, the list of stories is all the stories we care about
		// iterate through this list
		
		Set<String> uniqueContents = new TreeSet<String>(); // holds the unique contents
		Map<String, Double> statisticMap = new TreeMap<String, Double>(); // holds the statistic, by lookup
		for (NewsStory n : stories){
			// determine the content to be taking
			// ie. Source, Topic, Subject
			if("Source".equals(content)){
				String contentName = n.getSource();
				uniqueContents.add(contentName);
				if ("Length".equals(measure)){
					// determine if the map value has already been initialized
					if(!statisticMap.containsKey(contentName)) statisticMap.put(contentName, (double)n.getLengthInWords());
					else {
						// get current value
						double currVal = statisticMap.get(contentName);
						currVal += (double) n.getLengthInWords();
						// tuck back into map
						statisticMap.put(contentName, currVal);
					}
				}
				
				if ("Count".equals(measure)){
					// determine if the map value has already been initialized
					if(!statisticMap.containsKey(contentName)) statisticMap.put(contentName, 1.0);
					else {
						// get current value
						double currVal = statisticMap.get(contentName);
						currVal += 1.0; // increment by one
						// tuck back into map
						statisticMap.put(contentName, currVal);
					}
				}
				
			} // now onto Topic case
			if("Topic".equals(content)){
				String contentName = n.getTopic();
				uniqueContents.add(contentName);
				if ("Length".equals(measure)){
					// determine if the map value has already been initialized
					if(!statisticMap.containsKey(contentName)) statisticMap.put(contentName, (double)n.getLengthInWords());
					else {
						// get current value
						double currVal = statisticMap.get(contentName);
						currVal += (double) n.getLengthInWords();
						// tuck back into map
						statisticMap.put(contentName, currVal);
					}
				}
				
				if ("Count".equals(measure)){
					// determine if the map value has already been initialized
					if(!statisticMap.containsKey(contentName)) statisticMap.put(contentName, 1.0);
					else {
						// get current value
						double currVal = statisticMap.get(contentName);
						currVal += 1.0; // increment by one
						// tuck back into map
						statisticMap.put(contentName, currVal);
					}
				}
				
			} // now onto Subject case
			if("Subject".equals(content)){
				String contentName = n.getSubject();
				uniqueContents.add(contentName);
				if ("Length".equals(measure)){
					// determine if the map value has already been initialized
					if(!statisticMap.containsKey(contentName)) statisticMap.put(contentName, (double)n.getLengthInWords());
					else {
						// get current value
						double currVal = statisticMap.get(contentName);
						currVal += (double) n.getLengthInWords();
						// tuck back into map
						statisticMap.put(contentName, currVal);
					}
				}
				
				if ("Count".equals(measure)){
					// determine if the map value has already been initialized
					if(!statisticMap.containsKey(contentName)) statisticMap.put(contentName, 1.0);
					else {
						// get current value
						double currVal = statisticMap.get(contentName);
						currVal += 1.0; // increment by one
						// tuck back into map
						statisticMap.put(contentName, currVal);
					}
				}
				
			}
		} // once this iteration is complete, go back through and deal with figuring out the actual statistics	
		
		double sum = 0;
		for (String key : uniqueContents){
			// get current value
			double currVal = statisticMap.get(key);
			
			// add to sum
			sum += currVal;
			
		} // at end, now have determined the sum and the individual values
		  // convert to % and assign a name to create a wedge
		for (String key : uniqueContents){
			// make current value a %
			double currVal = statisticMap.get(key);
			currVal /= sum; // should be a % now
			// create a wedge
			wedges.add(new Wedge(currVal, key));			
		}
		
		// return the wedges
		return wedges;
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
		// reconstruct if there is ever a change in the newsMakerModel
		pieChart.setTitle(constructTitle());
		
		pieChart.setWedges(constructWedges());
		
	}
	
	
}
