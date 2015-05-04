import Network.Layer.Neuron.ActivationFunction;
import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 24.04.2015.
 */
public class ActivationFunctionTest {
    @Test
    public void sigmoidTest() {
        ActivationFunction func = ActivationFunction.Sigmoid;
        Complex c = new Complex(1);
        Complex res = func.calc(c);
        Complex should = new Complex(0.731059);
        assertEquals(res.getReal(),should.getReal(),0.0001);
    }
}
