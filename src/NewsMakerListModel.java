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
 * Ryan Chimienti (ID 113392576).
 * </P>
 * 
 * @author Dean Hougen
 * @author Ryan Chimienti
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
	 * @param newsMakerModel
	 *            The news maker to get from the list.
	 * @return The news maker found, if any. Otherwise, null.
	 */
	public NewsMakerModel get(NewsMakerModel newsMakerModel) 
	{
		int index = newsMakerDefaultListModel.indexOf(newsMakerModel);
		if(index >= 0) 
		{
			// TODO Have it return a copy instead (Eventually)
			return this.newsMakerDefaultListModel.get(index);
		} 
		else
		{
			return null;
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
		// TODO KEEP THE LIST PERMANENTLY SORTED AND MAKE THIS A BINARY SEARCH.
		
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
	 * The mutator for adding news makers to the list.
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
		if (this.contains(newsMakerModel)) 
		{
			throw new IllegalArgumentException("NewsMaker " 
					+ newsMakerModel.getName() + " already in list."); 
		}
		else 
		{
			newsMakerDefaultListModel.addElement(newsMakerModel);
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
	 *   
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
