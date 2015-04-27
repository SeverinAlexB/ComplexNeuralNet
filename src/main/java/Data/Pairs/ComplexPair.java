package Data.Pairs;

import Data.Helper;
import Data.Pairs.RealPair;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 24.04.2015.
 */
public class ComplexPair {
    private FieldVector<Complex> input;
    private FieldVector<Complex> output;
    public ComplexPair(FieldVector<Complex> input, FieldVector<Complex> output){
        setInput(input);
        setOutput(output);
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

    public RealPair transform() {
        double[] input = Helper.transform(this.input);
        double[] output = Helper.transform(this.output);
        return new RealPair(input,output);
    }


}
