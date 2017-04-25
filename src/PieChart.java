import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.lang.Math;

/**
 * This class provides simple pie chart objects, each in its own heavy-weight
 * container. A pie chart is composed of <code>Wedge</code> objects, each of
 * which specifies its own label and width (in percent), plus an overall title
 * for the pie chart.
 * <P>
 * Note that this class should probably ensure that the total percentages of all
 * its wedges adds up to 100% (within some margin of error).
 * </P>
 * 
 * @author Dean Hougen
 * @version 1.0
 */
public class PieChart 
{
	/**
	 * The list of wedges, each of which specifies its own label and its own
	 * width (in percent).
	 */
	private List<Wedge> wedges;

	/**
	 * The title for the overall pie chart.
	 * <P>
	 * Note that we wouldn't really need a field to hold this info, since it is
	 * passed in as a parameter to the constructor and only used within the
	 * constructor. However, as a title is conceptually an element of a pie
	 * chart, it makes conceptual sense to have this field. Moreover, as we
	 * might later want to modify this class to allow for modification of the
	 * title of an existing pie chart (e.g., adding
	 * <code>setTitle(String title)</code> as a mutator) or we might later want
	 * to add the title to the text within the chart itself, the design choice
	 * was to make this a field so that these additions do not require later
	 * modifications of existing data structures.
	 * </P>
	 */
	private String title;

	/**
	 * The constructor for a pie chart. It takes all the data needed for the pie
	 * chart (its title and a list of wedges) and uses a <code>JFrame</code> as
	 * the heavy-weight container for the graphics objects.
	 * <P>
	 * Note that because this is our first exercise in this course on
	 * constructing graphics, we don't need to have it do anything fancy, like
	 * automatically resize its contents if the window is resized. However, we
	 * will organize the class by having the actual chart itself be drawn in an
	 * object that is an instance of a subclass of <code>JPanel</code>. This
	 * will allow for easier additions to this class that may come later.
	 * </P>
	 * 
	 * @param title
	 *            The title of the chart itself.
	 * @param wedges
	 *            The list of wedges that will comprise the pie.
	 */
	public PieChart(String title, List<Wedge> wedges) {
		this.title = title;
		this.wedges = wedges;

		// Create a general heavy-weight container.
		JFrame frame = new JFrame(this.title);

		// Set its size.
		frame.setSize(1000, 500);

		// Create a specialized light-weight container.
		JPanel panel = new PieChartPanel();

		// Add the specialized container to the general container.
		frame.add(panel);

		// Make the GUI visible on the screen.
		frame.setVisible(true);
	}

	/**
	 * This inner class provides the panel on which the arcs of the pie wedges
	 * and their labels will be drawn.
	 * 
	 * @author Dean Hougen
	 * @version 1.0
	 */
	private class PieChartPanel extends JPanel {
		// As a subclass of JPanel, PieChartPanel should be serializable.
		private static final long serialVersionUID = 1L;

		// Implicit no arg constructor

		/**
		 * This overridden method will be called any time the panel is redrawn
		 * (when the window is first displayed, when it is resized, etc.).
		 * <P>
		 * Note that trigonometric methods of <code>java.lang.Math</code> such
		 * as <code>sin</code> and <code>cos</code> take their arguments in
		 * radians whereas shape drawing methods of
		 * <code>java.awt.Graphics</code> such as <code>drawArc</code> take
		 * their arguments in degrees.
		 * </P>
		 * 
		 * @param g
		 *            The graphics object that we'll be using in the panel.
		 */
		@Override
		public void paintComponent(Graphics g) {
			// Should almost always call parent's paintComponent method first,
			// to ensure that it draws itself first.
			super.paintComponent(g);

			// White is likely to work at least moderately well with most
			// colors.
			setBackground(Color.WHITE);

			/* We need font metrics to get our text spacing right. */
			FontMetrics metrics = g.getFontMetrics(this.getFont());

			/* In particular, we need the height of a line of text. */
			int textHeight = metrics.getHeight();

			/* We need the x and y midpoints to center the elements. */
			int xMid = getWidth() / 2;
			int yMid = getHeight() / 2;

			/* We want to size the pie to fit within the window. */
			int pieSize = Math.min(xMid, yMid);

			/*
			 * We need the origin of the pie wedges. Because fillArc uses the
			 * origin of a bounding box that encloses the oval enclosing the
			 * arc, the origin of the pie is not in the center of the window.
			 */
			int pieXOrig = xMid - pieSize / 2;
			int pieYOrig = yMid - pieSize / 2;

			/*
			 * The text for each wedge should be drawn around the pie with a bit
			 * of space between the edge of the pie and the text. The constant
			 * here determines how much space is left over. A value of 0.5 would
			 * result in no space. Greater gives more space. Less than 0.5 puts
			 * text at least partly within the pie.
			 */
			double labelScale = pieSize * 0.6;

			/* We need to start drawing somewhere. Initialize to zero. */
			double startRadians = 0.0;
			double startDegrees = startRadians;

			/*
			 * We need to determine the color of the pie wedges and associated
			 * text.
			 */
			int nextColor = 0;

			// Cycle through the wedges.
			for (Wedge wedge : wedges) {

				// Set color for wedge and associated text.
				g.setColor(new Color(nextColor));

				/*
				 * We need the width of this piece of text to get our spacing
				 * right for labels on the left side of the chart.
				 */
				int textWidth = metrics.stringWidth(wedge.getText());

				/*
				 * We need the size of each wedge in both radians (for the text)
				 * and degrees (for the wedges).
				 */
				double sizeRadians = wedge.getPercent() * 2 * Math.PI / 100.0;
				double sizeDegrees = wedge.getPercent() * 360.0 / 100.0;

				/* We want to center the text on its associated wedge. */
				double midRadians = startRadians + sizeRadians / 2.0;

				// Draw a wedge.
				g.fillArc(pieXOrig, pieYOrig, pieSize, pieSize, (int) startDegrees,
						(int) Math.round(sizeDegrees + 0.49));

				// Draw associated text. We should adjust how we position the
				// text by which quadrant we're in. For quadrants two and three,
				// we should worry about the width of the text, since drawString
				// draws text from left to right and we specify the starting x
				// location. For quadrants three and four, we should worry about
				// the height of the text, since the starting y location we
				// specify to drawString is interpreted as the baseline of the
				// text, meaning that drawString effectively draws above the
				// location we specify.
				if (midRadians < Math.PI / 2.0) { // Quadrant one
					g.drawString(wedge.getText(), xMid + (int) (Math.cos(midRadians) * labelScale),
							yMid - (int) (Math.sin(midRadians) * labelScale));
				} else if (midRadians >= Math.PI / 2.0 && midRadians < Math.PI) { // Quadrant
																					// two
					g.drawString(wedge.getText(), xMid + (int) (Math.cos(midRadians) * labelScale) - textWidth,
							yMid - (int) (Math.sin(midRadians) * labelScale));
				} else if (midRadians >= Math.PI && midRadians < Math.PI * 3.0 / 2.0) { // Quadrant
																						// three
					g.drawString(wedge.getText(), xMid + (int) (Math.cos(midRadians) * labelScale) - textWidth, yMid
							- (int) (Math.sin(midRadians) * labelScale) - (int) (Math.sin(midRadians) * textHeight));
				} else { // Otherwise, it must be quadrant four
					g.drawString(wedge.getText(), xMid + (int) (Math.cos(midRadians) * labelScale), yMid
							- (int) (Math.sin(midRadians) * labelScale) - (int) (Math.sin(midRadians) * textHeight));
				}

				// Move the start for the next wedge by the size of the wedge
				// just drawn.
				startRadians += sizeRadians;
				startDegrees += sizeDegrees;

				// This isn't a good way to choose color values.
				nextColor += 987654321 % Integer.MAX_VALUE;
			}
		}
	}
}
