import Data.Pairs.Helper.SectorValue;
import Data.Pairs.RealPair;
import Data.Pairs.SectorPair;
import Data.RealDataset;
import Data.SectorDataset;
import Network.FeedForwardNet;
import Network.Learning.CPULearning;
import Network.Learning.Calculation.SingleThreadCalculation;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 15.05.2015.
 */
public class AndTest {
    double delta = 0.001;
    public SectorDataset getXorSet() {
        RealDataset rset = new RealDataset();
        Double[][] in ={{0.1,0.1},{0.1,0.9},{0.9,0.1},{0.9,0.9}};
        Double[][] out = {{0.1},{0.9},{0.9},{0.1}};
        try{
            for(int i = 0; i <4; i++) {
                RealPair r = new RealPair(in[i],out[i]);
                rset.add(r);
            }
        } catch(SeviException ex) {
            System.out.println(ex.toString());
        }
        return rset.toSector(5);
    }
    public FeedForwardNet getNet() {
        FeedForwardNet net = new FeedForwardNet(5);
        try {
            net.addLayer(2, false);
            net.addLayer(1, true);
            net.addLayer(1, true);
        } catch(SeviException ex){
            System.out.println(ex);
        }
        return net;
    }

    @Test
    public void learnAndTest() throws SeviException{
        FeedForwardNet net = getNet();
        SectorDataset set = getXorSet();
        CPULearning learning = new CPULearning(net);
        learning.setDataset(set);

        for(int i = 0; i< 10; i++) {
            learning.iteration(100);
        }
        SingleThreadCalculation calc = new SingleThreadCalculation(net);

        FieldVector<Complex> in0 = set.getPairs().get(0).getInput();
        FieldVector<Complex> in1 = set.getPairs().get(1).getInput();
        FieldVector<Complex> in2 = set.getPairs().get(2).getInput();
        FieldVector<Complex> in3 = set.getPairs().get(3).getInput();

        FieldVector<Complex> out0 = calc.calculate(in0);
        FieldVector<Complex> out1 = calc.calculate(in1);
        FieldVector<Complex> out2 = calc.calculate(in2);
        FieldVector<Complex> out3 = calc.calculate(in3);

        double res0  = new SectorValue(out0.getEntry(0),5).toReal().getValue();
        double res1  = new SectorValue(out1.getEntry(0),5).toReal().getValue();
        double res2  = new SectorValue(out2.getEntry(0),5).toReal().getValue();
        double res3  = new SectorValue(out3.getEntry(0),5).toReal().getValue();

        assertEquals(0.1, res0,delta);
        assertEquals(0.9, res1,delta);
        assertEquals(0.9, res2,delta);
        assertEquals(0.1, res3,delta);




    }
}
