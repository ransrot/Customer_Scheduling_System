package Models;

/** The type in the database
 * @author Alex Bader
 * @version 1.0
 */

public class Type {
    /** Name of the type */
    private String name;

    /** how many times it appears in the database */
    private int count;

    /**
     *
     * @param name name of the type
     * @param count number of times in the database
     */
    public Type(String name, int count) {
        this.name = name;
        this.count = count;
    }

    /**
     *
     * @return the name of type
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the count of type
     */
    public int getCount() {
        return count;
    }
}
