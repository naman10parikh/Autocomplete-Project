import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int prefixmax = 10;
    private Map<String, List<Term>> map;
    private int size;
    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("Null argument(s)");
        }
        initialize(terms, weights);
    }
    @Override
    public List<Term> topMatches(String prefix, int k) {

        if (k <= 0 || !map.containsKey(prefix)) {
            return new ArrayList<Term>();
        }
        List<Term> everyterm = map.get(prefix);
        List<Term> l1 = everyterm.subList(0, Math.min(k, everyterm.size()));
        return l1;
    }
    @Override
    public void initialize(String[] terms, double[] weights) {
        map = new HashMap<String, List<Term>>();
        for (int i = 0; i < terms.length; i++) {
            int count = 0;
            Term term = new Term(terms[i], weights[i]);
            while (count <= terms[i].length() && count <= prefixmax) {
                String key = terms[i].substring(0, count);
                if (! map.containsKey(key)) {
                    List<Term> value = new ArrayList<Term>();
                    value.add(term);
                    map.put(key, value);
                } else {
                    List<Term> value = map.get(key);
                    value.add(term);
                }
                count++;
            }
        }

        for (String i : map.keySet()) {
            Collections.sort(map.get(i), Comparator.comparing(Term::getWeight).reversed());
        }
    }
    @Override
    public int sizeInBytes() {
        if (size == 0) {
            for (String key : map.keySet()) {
                size += BYTES_PER_CHAR*key.length();
                for (Term T : map.get(key)) {
                    size += BYTES_PER_DOUBLE +
                            BYTES_PER_CHAR*T.getWord().length();
                }
            }
        }
        return size;
    }
}
