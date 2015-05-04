import Data.Pairs.ComplexPair;
import Data.Pairs.RealPair;
import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 24.04.2015.
 */
public class PairTransformatinTest {
    public void transformation(double d){
        Complex c = Data.Helper.transform(d);
        double d2 = Data.Helper.transform(c);
        assertEquals(d,d2,0.0001);
    }
    @Test
    public void transformTest() {
        transformation(0.1);
        transformation(0);
        transformation(0.9);
        transformation(0.5);
    }

    @Test
    public void transformPairTest() {
        double[] in = {0.1}, out = {0.5};

        RealPair r = new RealPair(in,out);

        ComplexPair c = r.transform();
        RealPair r2 = c.transform();

        assertEquals(r.getInput()[0],r2.getInput()[0], 0.0001);
        assertEquals(r.getTarget()[0],r2.getTarget()[0],0.0001);
    }
}
