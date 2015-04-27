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
}
