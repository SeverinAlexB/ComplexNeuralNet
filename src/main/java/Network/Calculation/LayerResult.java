package Network.Calculation;

import Network.Layer.Neuron.ActivationFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 25.04.2015.
 */
public class LayerResult {
    public LayerResult(){}



    private ActivationFunction activationFunction;
    private FieldVector<Complex> input;
    private FieldVector<Complex> netin;
    private FieldVector<Complex> output;


    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

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

    public FieldVector<Complex> getOutput() {
        return output;
    }
    public void setOutput(FieldVector<Complex> output) {
        this.output = output;
    }
}
