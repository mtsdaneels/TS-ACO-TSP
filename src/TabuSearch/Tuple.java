package TabuSearch;

/**
 * Class representing a tuple from class X and class Y.
 * @param <X> The class of the first element.
 * @param <Y> The class of the second element.
 */
public class Tuple<X, Y> {

    /**
     * The first element.
     */
    public final X x;

    /**
     * Returns the first element.
     */
    public X getX(){
        return x;
    }

    /**
     * The second element.
     */
    public final Y y;

    /**
     * Return the second element.
     */
    public Y getY(){
        return y;
    }

    /**
     * Initializes a new tuple with elements x and y.
     * @param x The first element of the new tuple.
     * @param y The second element of the new tuple.
     */
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Check if 2 tuples are equal.
     * @param obj The second tuple.
     * @return True if and only if the second tuple has the same x value and the same y value as this one.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tuple)) return false;
        Tuple<X,Y> tuple2 = (Tuple<X,Y>) obj;
        return this.x.equals(tuple2.getX()) &&
                this.y.equals(tuple2.getY());
    }
}