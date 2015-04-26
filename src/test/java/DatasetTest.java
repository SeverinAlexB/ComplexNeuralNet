import Data.ComplexDataset;
import Data.Pairs.ComplexPair;
import Data.Pairs.RealPair;
import Data.RealDataset;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 24.04.2015.
 */
public class DatasetTest {
    @Test
    public void transformPairTest() throws SeviException{
        double[] in = {0.1}, out = {0.5};

        RealPair r = new RealPair(in,out);
        RealDataset rd = new RealDataset();
        rd.add(r);
        RealDataset rd2 = rd.transform().transform();

        assertEquals(rd.getPairs().get(0).getInput()[0],rd2.getPairs().get(0).getInput()[0], 0.0001);
        assertEquals(rd.getPairs().get(0).getOutput()[0],rd.getPairs().get(0).getOutput()[0],0.0001);
    }
    @Test
    public void getInputMatrixTest() throws SeviException{
        Complex[] in = {new Complex(1)}, out = {new Complex(2)};

        ComplexPair c = new ComplexPair(MatrixUtils.createFieldVector(in),MatrixUtils.createFieldVector(out));
        ComplexDataset cd =  new ComplexDataset();
        cd.add(c);

        Complex[][] matrix = cd.getInputMatrix();
        assertEquals(1,matrix.length);
        assertEquals(1,matrix[0].length);
        assertEquals(1,matrix[0][0].getReal(),0.0001);
    }
    @Test
    public void getOutputMatrixTest() throws SeviException{
        Complex[] in = {new Complex(1)}, out = {new Complex(2)};
        ComplexPair c = new ComplexPair(MatrixUtils.createFieldVector(in),MatrixUtils.createFieldVector(out));
        ComplexDataset cd =  new ComplexDataset();
        cd.add(c);

        Complex[][] matrix = cd.getOutputMatrix();
        assertEquals(1,matrix.length);
        assertEquals(1,matrix[0].length);
        assertEquals(2,matrix[0][0].getReal(),0.0001);
    }
}
