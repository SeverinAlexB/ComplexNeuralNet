package Network.Calculation;

import Network.Layer.Neuron.ActivationFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 25.04.2015.
 */
public class LayerResult {
    public LayerResult(){}


    private FieldVector<Complex> input;
    private FieldVector<Complex> netin;




    public FieldVector<Complex> getNetin() {
        return netin;
    }
    public void setNetin(FieldVector<Complex> netin) {
        this.netin = netin;
    }

    public FieldVector<Complex> getInput() {
        return input;
    }
    public void setInput(FieldVector<Complex> input) {
        this.input = input;
    }
}
