package homework4Eclipse;
import java.util.LinkedList;

public class HashTable {
	private int size;
	private Entry[] buckets;

	public class tuple {
		private int first, second;

		/**
		 * Creates a new tuple
		 * 
		 * @param f the first value
		 * @param s the second value
		 */
		public tuple(int f, int s) {
			this.first = f;
			this.second = s;
		}

		/**
		 * @return the first value in the tuple
		 */
		public int getFirst() {
			return this.first;
		}

		/**
		 * @return the second value in the tuple
		 */
		public int getSecond() {
			return this.second;
		}

		/**
		 * @param other the tuple to compare
		 * 
		 * @return true if both tuples contain the same integer, false otherwise
		 */
		boolean hasDuplicate(tuple other) {
			return this.first == other.first || this.first == other.second
					|| this.second == other.first || this.second == other.second;
		}

		@Override
		public int hashCode() {
			return this.first + this.second;
		}

		@Override
		public boolean equals(Object other) {
			if (this.getClass() != other.getClass()) {
				return false;
			}
			tuple otherTuple = (tuple)other;
			return this.first == otherTuple.first && this.second == otherTuple.second;
		}
	}

	private class Entry {
		int key;
		LinkedList<tuple> vals;	//LinkedLists are mutable, so can add to them easily, but is faster to manipulate than an ArrayList

		/**
		 * Creates a new, empty Entry that defaults the key to 0
		 */
		Entry() {
			key = 0;
			vals = new LinkedList<>();
		}

		/**
		 * Adds a new tuple to the current Entry. It does nothing if Entry already contains the incoming value. 
		 * Increments the size of the current Graph if the Entry was previously empty
		 * 
		 * @param one the first integer
		 * @param two the second integer
		 */
		void addVal(int one, int two) {
			if (vals.size() == 0) {
				key = Math.abs(one + two);
			}

			tuple toAdd = new tuple(one, two);
			if (!vals.contains(toAdd)) {
				size++;
				vals.add(new tuple(one, two));
			}
		}

		/**
		 * Adds a new tuple to the current Entry. It does nothing if Entry already contains the incoming value. 
		 * Increments the size of the current Graph if the Entry was previously empty
		 * 
		 * @param one the first integer
		 * @param two the second integer
		 */
		void addUniqueVal(int one, int two) {
			if (vals.size() == 0) {
				key = Math.abs(one + two);
			}

			size++;
			vals.add(new tuple(one, two));
		}
	}
	
	/**
	 * Creates a new HashTable with size 0 and num entries
	 * Initializes each bucket to a new, empty Entry with a key of 0
	 * 
	 * @param num the size to make the new HashTable
	 */
	HashTable(int num){
		if (num <= 0) {
			throw new IllegalArgumentException("The HashTable must have a size greater than 0");
		}
		size = 0;
		buckets = new Entry[num];

		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new Entry();
		}
	}
	
	/**
	 * Creates a tuple t into our hash table at the key: sum of our two nums
	 * 
	 * @param  one  the first value in the tuple
	 * @param  two  the second value in the tuple
	 */
	public void put(int one, int two) {
		if (one + two >= buckets.length) {
			throw new IllegalArgumentException("Your numbers are too big for this HashTable");
		}
		buckets[one + two].addVal(one, two);
	}

	/**
	 * Creates a tuple t into our hash table at the key: sum of our two nums
	 * Assumes that all incoming numbers are positive unique integers
	 * 
	 * @param  one  the first value in the tuple
	 * @param  two  the second value in the tuple
	 */
	public void putUnique(int one, int two) {
		if (one + two >= buckets.length) {
			throw new IllegalArgumentException("Your numbers are too big for this HashTable");
		}
		buckets[one + two].addUniqueVal(one, two);
	}
	
	/**
	 * Gets back a tuple where the tuple sums to a certain size
	 * 
	 * @param  sum  The sum value to find
	 * 
	 * @return A corresponding tuple with sum, null if no such tuple exists
	 */
	public tuple get(int sum) {
		if (sum >= buckets.length || sum < 0) {
			return null;
		}

		Entry bucket = buckets[Math.abs(sum)];

		if (bucket.vals.size() > 0) {
			return bucket.vals.get(0);
		}

		return null;
	}

	/**
	 * @return the number of keys in the current HashTable
	 */
	public int size() {
		return size;
	}

	/**
	 * @return a LinkedList that contains all of the keys in the current HashTable
	 */
	public LinkedList<Integer> getKeys() {
		LinkedList<Integer> keys = new LinkedList<>(); //Planning on removing elements from this LinkedList

		for (Entry entry : buckets) {
			keys.add(entry.key);
		}
		return keys;
	}

	/** 
	 * @param sum the sum to look for
	 * 
	 * @return true if any two keys in the current HashTable add up to sum, false otherwise
	 */
	public boolean hasSum(int sum) {
		LinkedList<Integer> keys = this.getKeys();

		while (keys.size() > 0) {
			int num = keys.remove(0);
			
			for (int i = 0; i < keys.size(); i++) {
				if (num + keys.get(i) == sum && !this.get(num).hasDuplicate(this.get(keys.get(i)))) {
					return true;
				}
			}
		}
		return false;
	}
	
}
