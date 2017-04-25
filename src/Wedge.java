/**
 * This class models a wedge for a pie chart. Each wedge consists of a numeric
 * value that described what percentage of the pie should be taken up by this
 * wedge and the text to associate with that wedge.
 * 
 * @author Dean Hougen
 * @version 1.0
 */
public class Wedge {
	/** The percent of the pie that this wedge should occupy. */
	private double percent;

	/** The text describing this wedge. */
	private String text;

	/**
	 * The constructor for this class that takes parameters for both fields.
	 * 
	 * @param percent
	 *            The percent for the constructed wedge.
	 * @param text
	 *            The text for the constructed wedge.
	 */
	public Wedge(double percent, String text) {
		// TODO: Ensure a value between 0 and 100.
		this.setPercent(percent);
		this.setText(text);
	}

	/**
	 * Accessor for percent.
	 * 
	 * @return The value from the percent field.
	 */
	public double getPercent() {
		return this.percent;
	}

	/**
	 * Mutator for percent.
	 * 
	 * @param percent
	 *            The value to set in the percent field.
	 */
	public void setPercent(double percent) {
		// TODO: Ensure a value between 0 and 100.
		this.percent = percent;
	}

	/**
	 * Accessor for text.
	 * 
	 * @return The value of the text field.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Mutator for text.
	 * 
	 * @param text
	 *            The value to set in the text field.
	 */
	public void setText(String text) {
		this.text = text;
	}
}
