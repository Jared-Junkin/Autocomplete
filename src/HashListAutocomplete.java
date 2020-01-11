import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights){
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }
        if (terms.length != weights.length) {
            throw new IllegalArgumentException("terms and weights are not the same length");
        }
        initialize(terms,weights);
    }
    @Override
    public List<Term> topMatches(String prefix, int k) {
        if(prefix.length() > MAX_PREFIX) prefix = prefix.substring(0, MAX_PREFIX);
        if(myMap.containsKey(prefix)){
            List<Term> all = myMap.get(prefix); //this is finding the top k matches. It's short because initialize has already done everything right.
            List<Term> list = all.subList(0, Math.min(all.size(), k));
            return list;
        }
        else return new ArrayList<>();
    }

    //for initialize, take first max prefix substrings
    //if substring not in dict, add it as key with an empty arraylist as value
    //add this term and its weight to the list
    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<>();
        for(int k = 0; k < terms.length ; k++){
            for(int sub = 0; sub <= Math.min(terms[k].length(), MAX_PREFIX); sub++){
                String curr = terms[k].substring(0, sub);
                    if(!myMap.containsKey(curr)){
                        myMap.put(curr, new ArrayList<>());
                    }
                    Term temp = new Term(terms[k], weights[k]);
                    myMap.get(curr).add(temp);
            }
        }
        for(List list : myMap.values()){
            Collections.sort(list, Comparator.comparing(Term::getWeight).reversed()); //is this enough? I feel like I still don't really get comparators
        } //what would the complexity of this comparator sort be?
    }

    @Override
    public int sizeInBytes() {
        if(mySize == 0){
            for(String key : myMap.keySet()){//how do I say for Key, Value like in python//
                mySize += key.length()*BYTES_PER_CHAR;
                for(Term t : myMap.get(key)){
                    mySize += t.getWord().length()*BYTES_PER_CHAR;
                    mySize += BYTES_PER_DOUBLE;
                }
            }
        }
        return mySize;
    }
}
/*

	BYTES_PER_CHAR*length to the bytes need.
	Each double stored contributes BYTES_PER_DOUBLE.
	You'll account for every Term and for every key in the map -- these are all strings.



    @Override
    public int sizeInBytes() {
        if (mySize == 0) {

            for(Term t : myTerms) {
                mySize += BYTES_PER_DOUBLE +
                        BYTES_PER_CHAR*t.getWord().length();
            }
        }
        return mySize;
    }
 */