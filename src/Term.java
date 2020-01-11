
/*************************************************************************
 * @author Kevin Wayne
 *
 * Description: A term and its weight.
 * 
 * @author Owen Astrachan, revised for Java 8-11, toString added
 * 
 *************************************************************************/

import java.util.Comparator;

public class Term implements Comparable<Term> {

	private String myWord;
	private double myWeight;


	public Term(String word, double weight) {
		if(weight < 0) throw new IllegalArgumentException("negative weight "+weight);
		if(word == null) throw new NullPointerException("word cannot be null");
		this.myWord = word;
		this.myWeight = weight;

	}
	
	/**
	 * Default compare is by word, no weight involved
	 * @return this.getWord().compareTo(that.getWord())
	 */
	@Override
	public int compareTo(Term that) {
		//return myWord.compareTo(that.myWord);
		return this.getWord().compareTo(that.getWord());
	} //I don't see what the difference is between this line and the original. Maybe there's not one. What is this doing?

	/**
	 * Getter for Term's word
	 * @return this Term's word
	 */
	public String getWord() {
		return myWord;
	}

	/**
	 * Getter for Term's weight
	 * @return this Term's weight
	 */
	public double getWeight() {
		return myWeight;
	}

	/**
	 * @return (word,weight) for this Term
	 */
	@Override
	public String toString() {
		return String.format("(%2.1f,%s)", myWeight, myWord);
	}
	
	/**
	 * Use default compareTo, so only word for equality, not weight
	 * @return true if this.getWord().equal(o.getWord())
	 */
	@Override
	public boolean equals(Object o) {
		Term other = (Term) o;
		return this.compareTo(other) == 0;
	}

	/**
	 * A Comparator for comparing Terms using a set number of the letters they
	 * start with. 
	 * This Comparator required for assignment.
	 *
	 */
	public static class PrefixOrder implements Comparator<Term> {
		private final int myPrefixSize;

		public PrefixOrder(int r) {
			this.myPrefixSize = r;
		}

		/**
		 * Compares v and w lexicographically using only their first r letters.
		 * If the first r letters are the same, then v and w should be
		 * considered equal. This method should take O(r) to run, and be
		 * independent of the length of v and w's length. You can access the
		 * Strings to compare using v.word and w.word.
		 *
		 * @param v/w
		 *            - Two Terms whose words are being compared
		 */
		public int compare(Term v, Term w) {
			int toReturn = 0;
			int newPrefixSize = 0;
			if(myPrefixSize > v.myWord.length() || myPrefixSize > w.myWord.length()){
				newPrefixSize = Math.min(v.myWord.length(), w.myWord.length());
			}
			else newPrefixSize = myPrefixSize;
			for(int k = 0; k < newPrefixSize; k++){
				toReturn = v.myWord.charAt(k) - w.myWord.charAt(k);
				if(toReturn != 0){
					return toReturn;
				}
				if(k + 1 == newPrefixSize && newPrefixSize != myPrefixSize && v.myWord.length() < w.myWord.length()){
					return -1;
				}
				if(k + 1 == newPrefixSize && newPrefixSize != myPrefixSize && v.myWord.length() > w.myWord.length()){
					return 1;
				}
			}
			if(!w.myWord.equals("") && v.myWord.equals("") && myPrefixSize != 0) return -1;
			if(w.myWord.equals("") && !v.myWord.equals("") && myPrefixSize != 0) return 1;
			return toReturn;
			/*
			if(myPrefixSize < v.myWord.length() && myPrefixSize < w.myWord.length()){
				return v.myWord.substring(0, myPrefixSize + 1).compareTo(w.myWord.substring(0, myPrefixSize + 1));
			}
			else{
				int newPrefixSize = Math.min(v.myWord.length(), w.myWord.length());
				return v.myWord.substring(0, newPrefixSize).compareTo(w.myWord.substring(0, newPrefixSize));
			}
			//this returns the compared value of the first r substrings within each word. I don't think I need to do anything more, do I?

			 */
		}
	
	}
}
