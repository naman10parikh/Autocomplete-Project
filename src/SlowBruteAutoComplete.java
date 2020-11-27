import java.util.*;
 /*
 * This class extends BruteAutocomplete, however, it does not use binary search or a PriorityQueue
 * to find the top matches. The runtime is relatively slower because all possible matches are
 * required to be sorted instead of only the specified number of top matches.
 */

public class SlowBruteAutoComplete extends BruteAutocomplete {
    public SlowBruteAutoComplete(String[] terms, double[] weights) {
        super(terms, weights);
    }
    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("Illegal value of k:"+k);
        }
        List<Term> newlist = new ArrayList<>();
        for (Term t : super.myTerms) {
            if (t.getWord().startsWith(prefix)) {
                newlist.add(t);
            }
        }
        Collections.sort(newlist, Comparator.comparing(Term::getWeight).reversed());
        int min = Math.min(k, newlist.size());
        return newlist.subList(0, min);
    }
}
