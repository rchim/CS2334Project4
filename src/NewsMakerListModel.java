import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Project 3, CS 2334, Section 010, March 8, 2017
 * <P>
 * A <code>NewsMakerList</code> is a list of <code>NewsMaker</code> objects.
 * Each <code>NewsMaker</code> in the list must have a unique name.
 * </P>
 * <P>
 * Note that this class now keeps track of whether its list has been sorted, to
 * ensure that a binary search will work, if used.
 * </P>
 * 
 * @author Dean Hougen
 * @version 3.0
 *
 */
class NewsMakerListModel implements Serializable {
	/**
	 * This is the first serializable version of NewsMakerList, so we select a
	 * serialVersionUID of 1L.
	 */
	private static final long serialVersionUID = 1L;

	/** The list of news makers. */
	private List<NewsMaker> newsMakers;

	/** A flag to indicate whether the list is sorted. */
	private boolean sorted;

	/**
	 * The no-argument constructor initializes the list to be an empty
	 * <code>ArrayList</code> of <code>NewsMaker</code> objects.
	 */
	NewsMakerListModel() {
		this.newsMakers = new ArrayList<NewsMaker>();
		this.sorted = false;
	}

	/**
	 * The mutator for adding news makers to the list.
	 * <P>
	 * By using our own class with its own <code>add</code> method, rather than
	 * directly using the <code>add</code> method of <code>ArrayList</code>, we
	 * can ensure that we don't add multiple <code>NewsMaker</code> objects with
	 * the same name to our list (thereby keeping the names unique).
	 * </P>
	 * 
	 * @param newsMaker
	 *            The news maker to add.
	 * @throws IllegalArgumentException
	 *             If the news maker to add is already in the list.
	 */
	public void add(NewsMaker newsMaker) {
		if (!this.newsMakers.contains(newsMaker)) {
			this.newsMakers.add(newsMaker);
			this.sorted = false;
		} else {
			throw new IllegalArgumentException("NewsMaker " + newsMaker.getName() + " already in list.");
		}
	}

	/**
	 * An accessor method to test whether the list already contains a news
	 * maker.
	 * <P>
	 * Simply makes use of the <code>contains</code> method of
	 * <code>ArrayList</code>.
	 * </P>
	 * 
	 * @param newsMaker
	 *            The news maker to check for in the list.
	 * @return The boolean value true if the news maker is in the list, false
	 *         otherwise.
	 */
	public boolean contains(NewsMaker newsMaker) {
		return this.newsMakers.contains(newsMaker);
	}

	/**
	 * An accessor method to get a news maker from the list.
	 * <P>
	 * Note that <code>NewsMaker</code> objects are mutable, so this really
	 * should return a copy of the news maker instead. However, we haven't
	 * studied that yet, so returning the news maker itself is acceptable for
	 * now.
	 * </P>
	 * 
	 * @param newsMaker
	 *            The news maker to get from the list.
	 * @return The news maker found, if any. Otherwise, null.
	 */
	public NewsMaker get(NewsMaker newsMaker) {
		int index = newsMakers.indexOf(newsMaker);
		if (index >= 0) {
			// TODO Have it return a copy instead (Eventually)
			return this.newsMakers.get(index);
		} else {
			return null;
		}
	}

	/**
	 * This method should be able to use a binary search to find the news maker
	 * but relies on the list being sorted first. It therefore checks the
	 * <code>sorted</code> flag and prints an error to the standard error if it
	 * was called with an unsorted list. It will conduct a linear search if a
	 * binary search is not possible.
	 * 
	 * @param newsMakerName
	 *            The exact name for which to search.
	 * @return The news maker found or null if none found.
	 */
	public NewsMaker getExactMatch(String newsMakerName) {
		if (sorted) {
			int index = Collections.binarySearch(newsMakers, new NewsMaker(newsMakerName));
			if (index >= 0) {
				// TODO Have it return a copy instead (Eventually)
				return this.newsMakers.get(index);
			} else {
				return null;
			}
		}
		System.err.println("Attempted to conduct binary search on unsorted list.");
		for (NewsMaker newsMaker : newsMakers) {
			if (newsMaker.getName().equals(newsMakerName)) {
				return newsMaker;
			}
		}
		return null;
	}

	/**
	 * This method searches for partial matches in the list, and returns the
	 * first news maker that contains the search string specified.
	 * 
	 * @param newsMakerName
	 *            The string on which to search.
	 * @return The news maker found or null if none found.
	 */
	public NewsMaker getPartialMatch(String newsMakerName) {
		for (NewsMaker newsMaker : newsMakers) {
			if (newsMaker.getName().contains(newsMakerName)) {
				return newsMaker;
			}
		}
		return null;
	}

	/**
	 * This method sorts the list using a stable sort. It also sets the
	 * <code>sorted</code> flag to <code>true</code>.
	 */
	public void sort() {
		Collections.sort(newsMakers);
		this.sorted = true;
	}
}
