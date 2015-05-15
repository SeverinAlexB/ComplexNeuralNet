package Network.Learning;

import Data.ComplexDataset;
import Data.Pairs.Helper.ComplexValue;
import Data.Pairs.Helper.RealValue;
import Data.Pairs.RealPair;
import Data.Pairs.SectorPair;
import Data.RealDataset;
import Data.SectorDataset;
import Network.Layer.Layer;
import Network.Learning.Calculation.ICalculationStrategy;
import Network.Learning.Calculation.SingleThreadCalculation;
import Network.FeedForwardNet;
import Network.Learning.CPULearning;
import Network.Learning.Propagation.SingleThreadPropagation;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 25.04.2015.
 */
public class CPULearningTest {
    double delta = 0.0000001;
    public FeedForwardNet getInizializedNet() throws SeviException {
        FeedForwardNet net = new FeedForwardNet(5);
        Layer l1 = net.addLayer(2,false);
        Layer l2 = net.addLayer(1,true);
        net.addLayer(1,false);

        l1.getNeurons().get(0).getOutputs().get(0).setWeight(new Complex(0.6905162797000783, 0.7233168513654049));
        l1.getNeurons().get(1).getOutputs().get(0).setWeight(new Complex(0.9990769852893715, 0.042955528923538926));
        l1.getNeurons().get(2).getOutputs().get(0).setWeight(new Complex(0.897366517866153, 0.44128599865934504));

        l2.getNeurons().get(0).getOutputs().get(0).setWeight(new Complex(0.9735594235749482, 0.22843390459477475));

        return net;
    }

    private SectorDataset getInitializedSet() throws SeviException {
        FieldVector<Complex> input, target;
        Complex[] vector = new Complex[2];
        vector[0] = new RealValue(0.1).toSector(5);
        vector[1] = new RealValue(0.1).toSector(5);
        input = MatrixUtils.createFieldVector(vector);

        Complex[] vector2 = new Complex[1];
        vector2[0] = new RealValue(0.1).toSector(5);

        target = MatrixUtils.createFieldVector(vector2);
        SectorDataset set = new SectorDataset(5);
        SectorPair pair = new SectorPair(input,target,5);
        set.add(pair);
        return set;
    }
    @Test
    public void iterationTest() throws Exception {
        SectorDataset set = getInitializedSet();
        FeedForwardNet net = getInizializedNet();

        SingleThreadCalculation calculation = new SingleThreadCalculation(net);
        SingleThreadPropagation propagation = new SingleThreadPropagation(net);


        CPULearning learning = new CPULearning(net,calculation,propagation);
        learning.setDataset(set);

        learning.iteration(1);

        FieldMatrix<Complex> weights1 = net.getLayers().get(0).getMatrixtoNextLayer();
        FieldMatrix<Complex> weights2 = net.getLayers().get(1).getMatrixtoNextLayer();

        assertEquals(0.9223058695166669, weights2.getEntry(0,0).getReal(),delta);
        assertEquals(0.044816515866659745, weights2.getEntry(0,0).getImaginary(),delta);


        assertEquals(0.7719943146953038, weights1.getEntry(0,0).getReal(),delta);
        assertEquals(0.43141935665686076, weights1.getEntry(0,0).getImaginary(),delta);

        assertEquals(1.080555020284597, weights1.getEntry(0,1).getReal(),delta);
        assertEquals(-0.24894196578500521, weights1.getEntry(0,1).getImaginary(),delta);

        assertEquals(0.6945674260523307, weights1.getEntry(0, 2).getReal(),delta);
        assertEquals(0.2160853193457679, weights1.getEntry(0, 2).getImaginary(),delta);

        /*ArrayList<String> weight = new ArrayList<String>(10000);
        for(int i = 0; i < 100; i++) {
            learning.iteration(1000);
            weight.add(net.getLayers().get(0).getNeurons().get(0).getOutputs().get(0).getWeight().toString());
            //Network.Layer.LayerTest.out(net.getLayers().get(0).getMatrixtoNextLayer());
             System.out.println("Error: " + learning.getError());
        }
        this.wirte(weight);
        Double[] testinput = {0d,0d,1d};
        System.out.println("0,0:");
        testen(testinput, calculation);

        Double[] testinput1 = {0d,1d,1d};
        System.out.println("0,1:");
        testen(testinput1, calculation);

        Double[] testinput2 = {1d,0d,1d};
        System.out.println("1,0:");
        testen(testinput2,calculation);

        Double[] testinput3= {1d,1d,1d};
        System.out.println("1,1:");
        testen(testinput3,calculation);*/
    }


}
