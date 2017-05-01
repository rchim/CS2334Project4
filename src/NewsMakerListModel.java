import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;

/**
 * Project 4, CS 2334, Section 010, May 4, 2017
 * <P>
 * A <code>NewsMakerListModel</code> represents a list of news makers.
 * Each news maker in the list must have a unique name.
 * </P>
 * <P>
 * This class was written by Dr. Hougen (as NewsMakerList). It was modified by
 * Ryan Chimienti (ID 113392576). A deep copy was implemented by Malachi 
 * Phillips where indicated.
 * </P>
 * 
 * @author Dean Hougen
 * @author Ryan Chimienti
 * @author Malachi Phillips
 */
class NewsMakerListModel implements Serializable 
{
	/**
	 * This is the first serializable version of NewsMakerListModel, so we
	 * select a serialVersionUID of 1L.
	 */
	private static final long serialVersionUID = 1L;

	/** The list of news makers. */
	private DefaultListModel<NewsMakerModel> newsMakerDefaultListModel;

	/**
	 * The no-argument constructor initializes the list to be an empty
	 * <code>DefaultListModel</code> of <code>NewsMakerModel</code> objects.
	 */
	public NewsMakerListModel() 
	{
		newsMakerDefaultListModel = new DefaultListModel<NewsMakerModel>();
	}

	/**
	 * Initializes this <code>NewsMakerListModel</code> to be a shallow copy
	 * of the supplied newsMakerListModel.
	 * 
	 * @param newsMakerListModel The newsMakerListModel to copy.
	 */
	public NewsMakerListModel(NewsMakerListModel newsMakerListModel) 
	{
		newsMakerDefaultListModel = newsMakerListModel.getNewsMakers();
	}
	
	/**
	 * Returns true if the list contains no news makers. Returns false
	 * otherwise.
	 * 
	 * @return True if the list contains no news makers; false otherwise.
	 */
	public boolean isEmpty()
	{
		return newsMakerDefaultListModel.isEmpty();
	}
	
	/**
	 * Returns the number of news makers in the list as an int.
	 * 
	 * @return The number of news makers in the list.
	 */
	public int size()
	{
		return newsMakerDefaultListModel.size();
	}
	
	/**
	 * An accessor method to test whether the list already contains a news
	 * maker.
	 * <P>
	 * Simply makes use of the <code>contains</code> method of
	 * <code>ArrayList</code>.
	 * </P>
	 * 
	 * @param newsMakerModel
	 *            The news maker to check for in the list.
	 * @return The boolean value true if the news maker is in the list; false
	 *         otherwise.
	 */
	public boolean contains(NewsMakerModel newsMakerModel) 
	{
		return this.newsMakerDefaultListModel.contains(newsMakerModel);
	}

	/**
	 * An accessor method to get a news maker from the list.
	 * 
	 * @author Malachi Phillips
	 *   -- added in the Deep Copy
	 *   -- credit to: <a href="http://alvinalexander.com/java/java-deep-clone-example-source-code">Java Deep Copy</a>
	 * 
	 * @param newsMakerModel
	 *            The news maker to get from the list.
	 * @return The news maker found, if any. Otherwise, null.
	 */
	public NewsMakerModel get(NewsMakerModel newsMakerModel) 
	{
		int index = newsMakerDefaultListModel.indexOf(newsMakerModel);
		if(index >= 0) 
		{
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(this.newsMakerDefaultListModel.get(index));
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				ObjectInputStream ois = new ObjectInputStream(bais);
				return (NewsMakerModel) ois.readObject();
			} catch (Exception e){
				e.printStackTrace();
				return null; // this means that it could not be found
			}
		} else {
			return null; // can't do a negative
		}
		
	}
	
	/**
	 * This method searches the list and returns the news maker with the 
	 * specified name.
	 * 
	 * @param newsMakerName
	 *            The exact name for which to search.
	 * @return The news maker found or null if none found.
	 */
	public NewsMakerModel getExactMatch(String newsMakerName) 
	{		
		// TODO MAKE THIS A BINARY SEARCH.
		
		NewsMakerModel currentNewsMaker;		
		for(int i = 0; i < newsMakerDefaultListModel.size(); i++) 
		{
			currentNewsMaker = newsMakerDefaultListModel.get(i);
			
			if(currentNewsMaker.getName().equals(newsMakerName))
				return currentNewsMaker;			
		}
		return null;
	}
	
	/**
	 * This method returns the first news maker in the list whose name contains
	 * the search string specified.
	 * 
	 * @param newsMakerName
	 *            The name part for which to search.
	 * @return The news maker found or null if none found.
	 */
	public NewsMakerModel getPartialMatch(String newsMakerName) 
	{
		NewsMakerModel currentNewsMaker;		
		for(int i = 0; i < newsMakerDefaultListModel.size(); i++) 
		{
			currentNewsMaker = newsMakerDefaultListModel.get(i);
			
			if(currentNewsMaker.getName().contains(newsMakerName))
				return currentNewsMaker;			
		}		
		return null;
	}
	
	/**
	 * Returns the DefaultListModel object that stores the news makers for this
	 * NewsMakerListModel.
	 * 
	 * @return The DefaultListModel containing the news makers.
	 */
	public DefaultListModel<NewsMakerModel> getNewsMakers() 
	{
		return newsMakerDefaultListModel;		
	}
	
	/**
	 * Gets the news maker at the specified index in the list.
	 * 
	 * @param index
	 *            The index of the news maker to get from the list.
	 * @return The news maker found.
	 */
	public NewsMakerModel get(int index) 
	{
		return this.newsMakerDefaultListModel.get(index);		
	}
	
	/**
	 * Returns the names of the news makers in the list as an array of
	 * <code>String</code>s.
	 * 
	 * @return The names of the news makers in the list as an array of
	 * <code>String</code>s.
	 */
	public String[] getNewsMakerNames() 
	{
		int numberOfNewsMakers = newsMakerDefaultListModel.size();
		String[] newsMakerNames = new String[numberOfNewsMakers];
		
		for(int i = 0; i < numberOfNewsMakers; i++)
		{
			newsMakerNames[i] = this.get(i).getName();
		}
		
		return newsMakerNames;
	}
	
	/**
	 * The mutator for adding news makers to the list. Finds where to insert the
	 * news maker to keep the list sorted by name.
	 * <P>
	 * By using our own class with its own <code>add</code> method, rather than
	 * directly using the <code>add</code> method of
	 * <code>DefaultListModel</code>, we can ensure that we don't add multiple
	 * <code>NewsMaker</code> objects with the same name to our list (thereby
	 * keeping the names unique).
	 * </P>
	 * 
	 * @param newsMakerModel
	 *            The news maker to add.
	 * @throws IllegalArgumentException
	 *             If the news maker to add is already in the list.
	 */
	public void add(NewsMakerModel newsMakerModel) 
	{
		// If the news maker is already in the list, don't let it get added
		// again.
		if (this.contains(newsMakerModel)) 
		{
			throw new IllegalArgumentException("NewsMaker " 
					+ newsMakerModel.getName() + " already in list."); 
		}
		// Otherwise, add the supplied news maker in the right position to keep
		// the list sorted.
		else 
		{
			// If there are no news makers in the list, or if the supplied news
			// maker should follow the last one in the list, simply add the
			// supplied news maker to the end of the list.
			if(this.isEmpty() 
					|| newsMakerModel.compareTo(this.get(this.size()-1)) > 0)
			{
				newsMakerDefaultListModel.addElement(newsMakerModel);
			}
			// If the supplied news maker should precede the first one in the
			// list, simply add it to the beginning of the list.
			else if(newsMakerModel.compareTo(this.get(0)) < 0)
			{
				newsMakerDefaultListModel.insertElementAt(newsMakerModel, 0);
			}
			// Otherwise, use a binary search to find where it should be
			// inserted to keep the list sorted.
			else
			{
				int insertionIndex;
				boolean foundCorrectInsertionIndex = false;
				
				// We will close in on the insertion index by trapping it 
				// between an upper and a lower bound.
				int insertionIndexUpperBound = this.size() - 1;
				int insertionIndexLowerBound = 1;				
				
				do 
				{
					// We try an insertion index right in the middle of the
					// upper and lower bound.
					insertionIndex = 
							(insertionIndexUpperBound 
							+ insertionIndexLowerBound) / 2;
					
					// If the supplied news maker should come after the news
					// maker currently at the insertion index, then the
					// insertion index is too small, so we raise the lower
					// bound.
					if(newsMakerModel.compareTo(this.get(insertionIndex)) > 0)
					{
						insertionIndexLowerBound = insertionIndex + 1;
					}
					// If the news maker should precede the news maker that
					// comes before the insertion index, then the insertion
					// index is too great, so we reduce the upper bound.
					else if(newsMakerModel.compareTo(
							this.get(insertionIndex-1)) < 0)
					{
						insertionIndexUpperBound = insertionIndex - 1;
					}
					// Otherwise, the insertion index must be right where it
					// belongs.
					else
					{
						foundCorrectInsertionIndex = true;
					}					
				}
				while(!foundCorrectInsertionIndex);
				
				newsMakerDefaultListModel.insertElementAt(
						newsMakerModel, insertionIndex);
			}			
		}
	}	
	
	/**
	 * Replaces a news maker in the list. The replacement news maker resides at
	 * the former index of the replaced news maker. If there is no news maker
	 * with the same name as the supplied news maker (i.e. no news maker to
	 * replace), the supplied news maker is simply appended to the end of the
	 * list.
	 * 
	 * @param newsMakerModel The replacement news maker.
	 */
	public void replace(NewsMakerModel newsMakerModel)
	{
		int index = newsMakerDefaultListModel.indexOf(newsMakerModel);
		
		// If a news maker was found with the same name as the replacement news
		// maker, replace him. 
		if(index != -1) 
		{
			newsMakerDefaultListModel.removeElementAt(index);
			newsMakerDefaultListModel.add(index, newsMakerModel);
		}
		// Otherwise, append the replacement news maker to the end of the list. 
		else
		{
			newsMakerDefaultListModel.addElement(newsMakerModel);
		}
	}
	
	/**
	 * Removes a news maker from the list.
	 * 
	 * @param newsMakerModel The news maker to remove from the list.
	 */
	public void remove(NewsMakerModel newsMakerModel)
	{
		newsMakerDefaultListModel.removeElement(newsMakerModel);
	}
	
	/**
	 * Remove several <code>NewsMakerModel</code>s, all contained
	 * within the passed list.
	 * 
	 * @param newsMakers
	 *   <code>DefaultListModel</code> containing <code>NewsMakerModel</code>s
	 *   to be deleted from the base   
	 */
	public void removeListOfNewsMakers(
			DefaultListModel<NewsMakerModel> newsMakers)
	{
		for(int i = 0 ; i < newsMakers.size(); ++i){ // iterate through
			remove(newsMakers.get(i));
		}
	}
	
	/**
	 * Removes all the news makers from the list.
	 */
	public void removeAllNewsMakers()
	{
		newsMakerDefaultListModel.clear();
	}
	
	/**
	 * Sets the news makers in this list to be the same as the news makers in
	 * the passed list.
	 * 
	 * @param newsMakerListModel The list of news makers that this list will be
	 * made to match.
	 */
	public void setNewsMakersFromNewsMakerList(
			NewsMakerListModel newsMakerListModel)
	{
		this.newsMakerDefaultListModel = newsMakerListModel.getNewsMakers();
	}
	
	/**
	 * Sorts the list of news makers by name using a stable sort.
	 */
	public void sort() 
	{
		// Copy the news makers into an ArrayList for sorting by
		// Collections.sort().
		ArrayList<NewsMakerModel> tempListForSorting 
				= Collections.list(newsMakerDefaultListModel.elements());
		
		Collections.sort(tempListForSorting);
		
		// Empty the news maker list and refill it with the news makers from
		// the properly sorted ArrayList. This results in a sorted list as
		// desired.
		newsMakerDefaultListModel.clear();
		for(int i = 0; i < tempListForSorting.size(); i++)
		{
			newsMakerDefaultListModel.addElement(tempListForSorting.get(i));
		}
	}
}
