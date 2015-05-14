package Data.Pairs;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 14.05.2015.
 */
public abstract class AbstractPair<T> {
    protected T input;
    protected T target;

    public T getInput() {
        return input;
    }

    public void setInput(T input) {
        this.input = input;
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }
}
