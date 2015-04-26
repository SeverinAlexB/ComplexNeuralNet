package Network.Calculation;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 25.04.2015.
 */
public class LayerResult {
    public LayerResult(FieldVector<Complex> input, FieldVector<Complex> output) {
        this.input = input;
        this.output = output;
    }
    public LayerResult(){}

    private FieldVector<Complex> input;
    private FieldVector<Complex> output;

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
