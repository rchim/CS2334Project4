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
	
	/** List of allowable media types **/
	private List<NewsMedia> media;
	
	/** NewsContent **/
	private NewsContent content;
	
	/** NewsMetric **/
	private NewsMetric measure;
	
	/**
	 * Public constructor for a <code>PieChartView</code>
	 * 
	 * Create all the GUI components
	 * 
	 * @param newsMakerModel
	 *   <code>NewsMakerModel</code> to be associated with the <code>PieChart</code>
	 * @param selectedMediaTypes
	 *   String holding the media information
	 * @param selectedNewsContent
	 *   String holding the content information
	 * @param selectedNewsMetric
	 *   String holding the measure information
	 */
	public PieChartView(NewsMakerModel newsMakerModel, List<NewsMedia> selectedMediaTypes, NewsContent selectedNewsContent, NewsMetric selectedNewsMetric){
		this.newsMakerModel = newsMakerModel;
		this.media = selectedMediaTypes;
		this.content = selectedNewsContent;
		this.measure = selectedNewsMetric;
		
		// construct the title
		String title = constructTitle();
		
		// construct the wedges
		List<Wedge> wedges = constructWedges();
		
		// construct pie chart object
		
		pieChart = new PieChart(title, wedges);
		pieChart.addWindowListener(new java.awt.event.WindowAdapter() {
			/**
			 * Overriden method for whenever the window is closed
			 * 
			 * @param windowEvent
			 *   java.awt.event.WindowEvent for this event
			 */
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent){
				// This is a weird construction
				newsMakerModel.removeActionListener(PieChartView.this);
			}
		});
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
		String str = "";
		str += "NewsMaker: " + newsMakerModel.getName() + "; Media Types: ";
		for (int i = 0 ; i < media.size()-1; ++i){
			str += media.get(i).toString() + ", ";
		}
		str += media.get(media.size()-1);
		str += "; Content: " + content.toString();
		str += "; Measure: " + measure.toString();
				
		return (str);
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
		
		for(int i = 0 ; i < newsList.size(); ++i){
			// get the current story
			NewsStory current = newsList.getElementAt(i);
			
			// determine whether to keep the story based on type
			String storyType = current.getClass().getName();
			switch(storyType){
			case "TVNewsStory":
				if (media.contains(NewsMedia.TV)) stories.add(current);
			case "OnlineNewsStory":
				if (media.contains(NewsMedia.ONLINE)) stories.add(current);
			case "NewspaperStory":
				if (media.contains(NewsMedia.NEWSPAPER)) stories.add(current);
			}

		} // at this point, the list of stories is all the stories we care about
		// iterate through this list
		
		Set<String> uniqueContents = new TreeSet<String>(); // holds the unique contents
		Map<String, Double> statisticMap = new TreeMap<String, Double>(); // holds the statistic, by lookup
		for (NewsStory n : stories){
			// determine the content to be taking
			// ie. Source, Topic, Subject
			if(NewsContent.SOURCE.equals(content)){
				String contentName = n.getSource();
				uniqueContents.add(contentName);
				if (NewsMetric.LENGTH.equals(measure)){
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
				
				if (NewsMetric.COUNT.equals(measure)){
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
			if(NewsContent.TOPIC.equals(content)){
				String contentName = n.getTopic();
				uniqueContents.add(contentName);
				if (NewsMetric.LENGTH.equals(measure)){
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
				
				if (NewsMetric.COUNT.equals(measure)){
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
			if(NewsContent.SUBJECT.equals(content)){
				String contentName = n.getSubject();
				uniqueContents.add(contentName);
				if (NewsMetric.LENGTH.equals(measure)){
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
				
				if (NewsMetric.COUNT.equals(measure)){
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
			currVal *= 100;
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
		pieChart.repaint();
	}
	
	
}
