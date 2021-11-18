package com.group3.racingbot.inventory;

/**
 * Ensures the iterator can print out the entire inventory.
 * @author Nick Sabia
 *
 * @param <T>
 */
public interface InventoryIterator<T> extends Iterator<T> {
	/**
	 * Print the entire inventory regardless of filter.
	 */
	public void printInventory();
	
	/**
	 * Retrieve the current index which the iterator is on.
	 * @return current index of the iterator
	 */
	public int getCurrentIndex();
}
