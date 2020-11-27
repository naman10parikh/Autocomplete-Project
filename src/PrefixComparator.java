import java.util.Comparator;

 /*
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
       return new PrefixComparator(prefix);
    }


    @Override
    public int compare(Term v, Term w) {
        String s1 = v.getWord();
        String s2 = w.getWord();
        if (s1.length() > myPrefixSize) {
            s1 = s1.substring(0, myPrefixSize);
        }
        if (s2.length() > myPrefixSize) {
            s2 = s2.substring(0,myPrefixSize);
        }
        return s1.compareTo(s2);
    }
}
