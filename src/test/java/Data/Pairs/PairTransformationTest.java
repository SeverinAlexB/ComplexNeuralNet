package Data.Pairs;

import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 14.05.2015.
 */
public class PairTransformationTest {
    double delta = 0.0000001;
    @Test
    public void realToComplexTest() throws SeviException{
        Double[] in = {0.5};
        Double[] out = {0.9};
        RealPair r = new RealPair(in,out);
        RealPair r2 = r.toComplex().toReal();
        assertEquals(in[0],r2.getInput()[0],delta);
        assertEquals(out[0],r2.getTarget()[0],delta);
    }
    @Test
    public void realToSectorTest() throws SeviException{
        Double[] in = {0.5};
        Double[] out = {0.9};
        RealPair r = new RealPair(in,out);
        RealPair r2 = r.toSector(5).toReal();
        assertEquals(in[0],r2.getInput()[0],delta);
        assertEquals(out[0],r2.getTarget()[0],delta);
    }
    @Test
    public void complexToRealTest() throws SeviException{
        Complex[] in = {new Complex(0.5)};
        Complex[] out = {new Complex(0.9)};
        ComplexPair c = new ComplexPair(MatrixUtils.createFieldVector(in),MatrixUtils.createFieldVector(out));
        ComplexPair c2 = c.toReal().toComplex();
        assertEquals(in[0].getArgument(),c2.getInput().getEntry(0).getArgument(),delta);
        assertEquals(out[0].getArgument(),c2.getTarget().getEntry(0).getArgument(),delta);
    }
    @Test
    public void complexToSectorTest() throws SeviException{
        Complex[] in = {new Complex(0.5)};
        Complex[] out = {new Complex(0.9)};
        ComplexPair c = new ComplexPair(MatrixUtils.createFieldVector(in),MatrixUtils.createFieldVector(out));
        ComplexPair c2 = c.toSector(5).toComplex();
        assertEquals(in[0].getArgument(),c2.getInput().getEntry(0).getArgument(),delta);
        assertEquals(out[0].getArgument(),c2.getTarget().getEntry(0).getArgument(),delta);
    }

}
