import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.*;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * This view allows the user to add new news stories to the model or alter
 * existing ones.
 * </P>
 * <P>
 * This class was written by Malachi Phillips (ID 112933834).
 * </P>
 * 
 * @author Malachi Phillips
 */
public class AddEditNewsStoryView extends JPanel
{

	/**
	 * serialVersionUID for <code>JPanel</code>
	 * Default generated -- these values must be unique
	 */
	private static final long serialVersionUID = -9178933691767562282L;
	
	/** <code>NewsDataBaseModel</code> needed for displaying information **/
	private NewsDataBaseModel newsDataBaseModel;
	
	/** <code>NewsStory</code> to be edited **/
	private NewsStory newsStory;
	
	/** <code>JLabel</code> for showing NewsStory type **/
	private JLabel jlbNewsStoryType = new JLabel("Type:");
	
	/** <code>JComboBox</code> to hold the various <code>NewsMedia</code> types **/
	JComboBox<NewsMedia> jcbNewsStoryType = new JComboBox<NewsMedia>(NewsMedia.values());
	
	/** <code>JPanel</code> for holding the <code>NewsStory</code> type **/
	private JPanel jpNewsStoryType;
	
	/** <code>JLabel</code> for holding the <code>NewsStory</code> source **/
	private JLabel jlbNewsStorySource = new JLabel("Source:");
	
	/** <code>JComboBox</code> for holding the <code>NewsStory</code> source information **/
	JComboBox<String> jcbNewsStorySource;
	
	/** <code>JPanel</code> for holding the <code>NewsStory</code> source information **/
	private JPanel jpNewsStorySource;
	
	/** <code> JLabel</code> for holding the label to the <code>NewsStory</code> topic **/
	private JLabel jlbNewsStoryTopic = new JLabel("Topic:");
	
	/** <code>JComboBox</code> for holding the various options for <code>NewsStory</code> topic **/
	JComboBox<String> jcbNewsStoryTopic;
	
	/** <code>JPanel</code> for holding the <code>NewsStory</code> topic **/
	private JPanel jpNewsStoryTopic;
	
	/** <code>JLabel</code> for marking the location of the <code>NewsStory</code> subject field **/
	private JLabel jlbNewsStorySubject = new JLabel("Subject:");
	
	/** <code>JComboBox</code> for holding the <code>NewsStory</code> subject **/
	JComboBox<String> jcbNewsStorySubject;
	
	/** <code>JPanel</code> for holding the <code>NewsStory</code> subject information **/
	private JPanel jpNewsStorySubject;
	
	/** <code>JLabel</code> for marking the field to have the <code>NewsStory</code> primary <code>NewsMaker</code> **/
	private JLabel jlbNewsStoryNewsMaker1 = new JLabel("News Maker 1:");
	
	/** <code>JComboBox</code> to choose the primary <code>NewsMaker</code> for the <code>NewsStory</code> **/
	JComboBox<String> jcbNewsStoryNewsMaker1;
	
	/** <code>JPanel</code> to display the choice for the primary <code>NewsMaker</code> for the <code>NewsStory</code> **/
	private JPanel jpNewsStoryNewsMaker1;
	
	/** <code>JLabel</code> for marking the field to have the <code>NewsStory</code> secondary <code>NewsMaker</code> **/
	private JLabel jlbNewsStoryNewsMaker2 = new JLabel("News Maker 2:");
	
	/** <code>JComboBox</code> to choose the secondaary <code>NewsMaker</code> for the <code>NewsStory</code> **/
	JComboBox<String> jcbNewsStoryNewsMaker2;
	
	/** <code>JPanel</code> to display the choice for the secondary <code>NewsMaker</code> for the <code>NewsStory</code> **/
	private JPanel jpNewsStoryNewsMaker2;
	
	/** <code>JLabel</code> to mark the location of the <code>NewsStory</code< length **/
	private JLabel jlbNewsStoryLength = new JLabel("Length:");
	
	/** <code>NumberFormat</code> for the length field **/
	private NumberFormat integerFieldFormatter;
	
	/** <code>JFormattedTextField</code> to for entering the <code>NewsStory</code> length **/
	JFormattedTextField jtftfNewsStoryLength;
	
	/** <code>JPanel</code> for showing the information for the <code>NewsStory</code> length **/
	private JPanel jplNewsStoryLength;
	
	/** <code>JLabel</code> for marking the location of the <code>NewsStory</code> year field **/
	private JLabel jlbNewsStoryYear = new JLabel("Year:");
	
	/** <code>Integer[]</code> for holding the various years **/
	private Integer[] years;
	
	/** <code>JComboBox</code> for the <code>NewsStory</code> month **/
	JComboBox<Integer> jcbNewsStoryYear;
	
	/** <code>JPanel</code> for the <code>NewsStory</code> month **/
	private JPanel jplNewsStoryYear;
	
	/** <code>JLabel</code> for marking the location of the <code>NewsStory</code> month field **/
	private JLabel jlbNewsStoryMonth = new JLabel("Month:");
	
	/** <code>JComboBox</code> for the <code>NewsStory</code> month **/
	JComboBox<Month> jcbNewsStoryMonth = new JComboBox<Month>(Month.values());
	
	/** <code>JPanel</code> for the <code>NewsStory</code> month **/
	private JPanel jplNewsStoryMonth;
	
	/** <code>JLabel</code> for marking the location of the <code>NewsStory</code> day field **/
	private JLabel jlbNewsStoryDay = new JLabel("Day:");
	
	/** <code>Integer[]</code> for holding the various days **/
	private Integer[] days;
	
	/** <code>JComboBox</code> for the <code>NewsStory</code> day **/
	JComboBox<Integer> jcbNewsStoryDay;
	
	/** <code>JPanel</code> for the <code>NewsStory</code> day **/
	private JPanel jplNewsStoryDay;
	
	/** <code>JLabel</code> for the location of the <code>NewsStory PartOfDay</code> field **/
	private JLabel jlbNewsStoryPartOfDay = new JLabel("Part of Day:");
	
	/** <code>JComboBox</code> for the <code>NewsStory PartOfDay</code> field **/
	JComboBox<PartOfDay> jcbNewsStoryPartOfDay = new JComboBox<PartOfDay>(PartOfDay.values());
	
	/** <code>JPanel</code> for the <code>NewsStory PartOFDay</code> field **/
	private JPanel jplNewsStoryPartOfDay;
	
	/** <code>JPanel</code> for when the <code>NewsStory</code> occurred **/
	private JPanel jplNewsStoryWhen;
	
	/** <code>JButton</code> for <code>AddEditNewsStory</code> **/
	JButton jbtAddEditNewsStory;
	
	/** <code>JPanel</code> for <code>AddEditNewsStory</code> **/
	private JPanel jplAddEditNewsStory;
	
	
	
	/**
	 * Public constructor for <code>AddEditNewsStoryView</code>
	 * 
	 * @param newsDataBaseModel
	 *   <code>NewsDataBaseModel</code> to be used in constructing this view
	 * @param newsStory
	 *   <code>NewsStory</code> to be edited (used to pre-fill in the boxes)
	 */
	public AddEditNewsStoryView(NewsDataBaseModel newsDataBaseModel, NewsStory newsStory){
		this.newsDataBaseModel = newsDataBaseModel;
		this.newsStory = newsStory;
		
		integerFieldFormatter = NumberFormat.getNumberInstance();
		integerFieldFormatter.setParseIntegerOnly(true); // only allow integers
		jtftfNewsStoryLength = new JFormattedTextField(integerFieldFormatter);
		// perform a check on the integer, ie. namely a positive number
		
		// initialize JComboBox objects with possible values from NewsDataBaseModel
		
		// if these indices have yet to be included, some work needs to be done to ensure that 
		String[] newsSources;
		if( null == newsDataBaseModel.getNewsSourceMap()){
			newsSources = new String[1];
			newsSources[0] = "";
		} else {
			newsSources = newsDataBaseModel.getNewsSources();
		}
		jcbNewsStorySource = new JComboBox<String>(newsSources);
		
		// repeat this same check, but for topics, subjects, and news makers
		String[] newsTopics;
		if (null == newsDataBaseModel.getNewsTopicMap()){
			newsTopics = new String[1];
			newsTopics[0] = "";
		} else {
			newsTopics = newsDataBaseModel.getNewsTopics();
		}
		jcbNewsStoryTopic = new JComboBox<String>(newsTopics);
		
		// check for subjects
		String[] newsSubjects;
		if (null == newsDataBaseModel.getNewsSubjectMap()){
			newsSubjects = new String[1];
			newsSubjects[0] = "";
		} else {
			newsSubjects = newsDataBaseModel.getNewsSubjects();
		}
		jcbNewsStorySubject = new JComboBox<String>(newsSubjects);
		
		// check for newsmaker names
		String[] newsMakerNames = new String[1];
		if (null == newsDataBaseModel.getNewsMakerNames()){
			newsMakerNames = new String[1];
			newsMakerNames[0] = "";
		} else {
			newsMakerNames = newsDataBaseModel.getNewsMakerNames();
		}
		jcbNewsStoryNewsMaker1 = new JComboBox<String>(newsMakerNames);
		jcbNewsStoryNewsMaker2 = new JComboBox<String>(newsMakerNames);

		// make all the boxes editable
		jcbNewsStorySource.setEditable(true);
		jcbNewsStoryTopic.setEditable(true);
		jcbNewsStorySubject.setEditable(true);
		jcbNewsStoryNewsMaker1.setEditable(true);
		jcbNewsStoryNewsMaker2.setEditable(true);
	
		// I hate this -- why aren't we using a date selector?
		years = new Integer[100];
		
		for(int i = 0 ; i < years.length; ++i){
			years[i] = 1918 + i;
		}
		
		jcbNewsStoryYear = new JComboBox<Integer>(years);
		
		// I also hate this. This is such a hack
		days = new Integer[31];
		for(int i = 0 ; i < days.length; ++i){
			days[i] = i+1;
		} // 1-31
		
		jcbNewsStoryDay = new JComboBox<Integer>(days);
		
		// pre-select the correct JComboBox options
		
		// only run this if we know we have a non-null story (ie. edit)
		if(null != this.newsStory){
			// determine the type of the newsStory
			String instance = newsStory.getClass().getName();
			NewsMedia type = null;
			PartOfDay part = null;
			switch (instance) {
			case "NewspaperStory":
				type = NewsMedia.NEWSPAPER;
				break;
			case "TVNewsStory":
				type = NewsMedia.TV;
				// cast newsStory as TV
				TVNewsStory tv = (TVNewsStory) newsStory;
				part = tv.getPartOfDay();
				break;
			case "OnlineNewsStory":
				type = NewsMedia.ONLINE;
				// cast as online
				OnlineNewsStory online = (OnlineNewsStory) newsStory;
				part = online.getPartOfDay();
				break;
			}

			jcbNewsStoryType.setSelectedItem(type);
			jcbNewsStorySource.setSelectedItem(newsStory.getSource());
			jcbNewsStoryTopic.setSelectedItem(newsStory.getTopic());
			jcbNewsStorySubject.setSelectedItem(newsStory.getSubject());

			jcbNewsStoryNewsMaker1.setSelectedItem(
					this.newsStory.getNewsMaker1().toString()
					);
			jcbNewsStoryNewsMaker2.setSelectedItem(newsDataBaseModel.getNewsMakerListModel().get(newsStory.getNewsMaker2()).toString());

			jtftfNewsStoryLength.setValue(Integer.toString(newsStory.getLengthInWords()));

			jcbNewsStoryYear.setSelectedItem(newsStory.getDate().getYear());
			jcbNewsStoryMonth.setSelectedItem(Month.fromInt(newsStory.getDate().getMonthValue()));
			jcbNewsStoryDay.setSelectedItem(newsStory.getDate().getDayOfMonth());
			jcbNewsStoryPartOfDay.setSelectedItem(part);
			
			jbtAddEditNewsStory = new JButton("Edit News Story");
			jbtAddEditNewsStory.setActionCommand("Edit News Story");
		}
		
		if (null == this.newsStory){
			jbtAddEditNewsStory = new JButton("Add News Story");
			jbtAddEditNewsStory.setActionCommand("Add News Story");
		}
		
		jplAddEditNewsStory = new JPanel();
		jplAddEditNewsStory.setLayout(new BoxLayout(jplAddEditNewsStory,BoxLayout.Y_AXIS)); // 9x1 layout
		
		// throw the type label and box into the panel
		jpNewsStoryType = new JPanel();
		jpNewsStoryType.setLayout(new FlowLayout());
		jpNewsStoryType.add(jlbNewsStoryType);
		jpNewsStoryType.add(jcbNewsStoryType);
		
		// throw the source label and box into the panel
		jpNewsStorySource = new JPanel();
		jpNewsStorySource.setLayout(new FlowLayout());
		jpNewsStorySource.add(jlbNewsStorySource);
		jpNewsStorySource.add(jcbNewsStorySource);
		
		// throw the topic label and box into the panel
		jpNewsStoryTopic = new JPanel();
		jpNewsStoryTopic.setLayout(new FlowLayout());
		jpNewsStoryTopic.add(jlbNewsStoryTopic);
		jpNewsStoryTopic.add(jcbNewsStoryTopic);
		
		// throw the topic label and box into the panel
		jpNewsStorySubject = new JPanel();
		jpNewsStorySubject.setLayout(new FlowLayout());
		jpNewsStorySubject.add(jlbNewsStorySubject);
		jpNewsStorySubject.add(jcbNewsStorySubject);		
		
		// throw the newsmaker 1 label and box into the panel
		jpNewsStoryNewsMaker1 = new JPanel();
		jpNewsStoryNewsMaker1.setLayout(new FlowLayout());
		jpNewsStoryNewsMaker1.add(jlbNewsStoryNewsMaker1);
		jpNewsStoryNewsMaker1.add(jcbNewsStoryNewsMaker1);
		
		// throw the newsmaker 2 label and box into the panel
		jpNewsStoryNewsMaker2 = new JPanel();
		jpNewsStoryNewsMaker2.setLayout(new FlowLayout());
		jpNewsStoryNewsMaker2.add(jlbNewsStoryNewsMaker2);
		jpNewsStoryNewsMaker2.add(jcbNewsStoryNewsMaker2);
		
		// throw the length label and box into the panel
		jplNewsStoryLength = new JPanel();
		jplNewsStoryLength.setLayout(new FlowLayout());
		jplNewsStoryLength.add(jlbNewsStoryLength);
		jtftfNewsStoryLength.setColumns( 20 );
		jplNewsStoryLength.add(jtftfNewsStoryLength);
		// bundle year label and box into panel
		jplNewsStoryYear = new JPanel();
		jplNewsStoryYear.setLayout(new FlowLayout());
		jplNewsStoryYear.add(jlbNewsStoryYear);
		jplNewsStoryYear.add(jcbNewsStoryYear);
		
		// bundle month label and box into panel
		jplNewsStoryMonth = new JPanel();
		jplNewsStoryMonth.setLayout(new FlowLayout());
		jplNewsStoryMonth.add(jlbNewsStoryMonth);
		jplNewsStoryMonth.add(jcbNewsStoryMonth);
		
		// bundle day label and box into panel
		jplNewsStoryDay = new JPanel();
		jplNewsStoryDay.setLayout(new FlowLayout());
		jplNewsStoryDay.add(jlbNewsStoryDay);
		jplNewsStoryDay.add(jcbNewsStoryDay);
		
		// bundle part of day label and box into panel
		jplNewsStoryPartOfDay = new JPanel();
		jplNewsStoryPartOfDay.setLayout(new FlowLayout());
		jplNewsStoryPartOfDay.add(jlbNewsStoryPartOfDay);
		jplNewsStoryPartOfDay.add(jcbNewsStoryPartOfDay);
		
		// add in all the date information into one date JPanel
		jplNewsStoryWhen = new JPanel();
		jplNewsStoryWhen.setLayout(new FlowLayout());
		jplNewsStoryWhen.add(jplNewsStoryYear);
		jplNewsStoryWhen.add(jplNewsStoryMonth);
		jplNewsStoryWhen.add(jplNewsStoryDay);
		jplNewsStoryWhen.add(jplNewsStoryPartOfDay);
		
		// bundle everything into the current bundle
		jplAddEditNewsStory.add(jpNewsStoryType);
		jplAddEditNewsStory.add(jpNewsStorySource);
		jplAddEditNewsStory.add(jpNewsStoryTopic);
		jplAddEditNewsStory.add(jpNewsStorySubject);
		jplAddEditNewsStory.add(jpNewsStoryNewsMaker1);
		jplAddEditNewsStory.add(jpNewsStoryNewsMaker2);
		jplAddEditNewsStory.add(jplNewsStoryLength);
		jplAddEditNewsStory.add(jplNewsStoryWhen);
		jplAddEditNewsStory.add(jbtAddEditNewsStory);
		
		this.add(jplAddEditNewsStory);
	}

	
	
}
