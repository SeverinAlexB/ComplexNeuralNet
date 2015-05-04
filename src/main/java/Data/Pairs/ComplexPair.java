package Data.Pairs;

import Data.Helper;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 24.04.2015.
 */
public class ComplexPair {
    private FieldVector<Complex> input;
    private FieldVector<Complex> target;
    public ComplexPair(FieldVector<Complex> input, FieldVector<Complex> target){
        setInput(input);
        setTarget(target);
    }
    public FieldVector<Complex> getInput() {
        return input;
    }

    public void setInput(FieldVector<Complex> input) {
        this.input = input;
    }

    public FieldVector<Complex> getTarget() {
        return target;
    }

    public void setTarget(FieldVector<Complex> target) {
        this.target = target;
    }

    public RealPair transform() {
        double[] input = Helper.transformE(this.input);
        double[] output = Helper.transformE(this.target);
        return new RealPair(input,output);
    }


}
