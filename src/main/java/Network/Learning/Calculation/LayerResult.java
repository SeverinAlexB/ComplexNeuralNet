package Network.Learning.Calculation;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 25.04.2015.
 */
public class LayerResult {


    private FieldVector<Complex> netin;

    private LayerResult before;
    private LayerResult next;

    public LayerResult(FieldVector<Complex> netin){
        this.netin = netin;
    }

    public FieldVector<Complex> getNetin() {
        return netin;
    }
    public void setNetin(FieldVector<Complex> netin) {
        this.netin = netin;
    }
    public LayerResult createNext(FieldVector<Complex> netin) {
        LayerResult next = new LayerResult(netin);
        this.next = next;
        next.before = this;
        return next;
    }
    public LayerResult getBefore() {
        return before;
    }
    public void setBefore(LayerResult before) {
        this.before = before;
    }
    public LayerResult getNext() {
        return next;
    }
    public void setNext(LayerResult next) {
        this.next = next;
    }
}
