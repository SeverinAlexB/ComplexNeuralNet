import Data.ComplexDataset;
import Data.Pairs.Helper.ComplexValue;
import Data.Pairs.Helper.RealValue;
import Data.Pairs.RealPair;
import Data.RealDataset;
import Network.Calculation.ICalculationStrategy;
import Network.Calculation.SingleThreadCalculation;
import Network.FeedForwardNet;
import Network.Layer.Neuron.ActivationFunction;
import Network.Learning.CPULearning;
import Network.Learning.Propagation.SingleThreadPropagation;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Severin on 25.04.2015.
 */
public class CPULearningTest {

    private ComplexDataset getAndDataset() throws SeviException{
        RealDataset set = new RealDataset();
        Double[] in1 = {0.1,0.1,0.9};
        Double[] out1 = {0.1};
        Double[] in2 = {0.9,0.1,0.9};
        Double[] out2 = {0.1};
        Double[] in3 = {0.1, 0.9,0.9};
        Double[] out3 = {0.1};
        Double[] in4 = {0.9,0.9,0.9};
        Double[] out4 = {0.9};
        set.add(new RealPair(in1,out1));
        set.add(new RealPair(in2,out2));
        set.add(new RealPair(in3,out3));
        set.add(new RealPair(in4,out4));
        return set.toComplex();
    }
    private ComplexDataset getXorDataset() throws SeviException{
        RealDataset set = new RealDataset();
        Double[] in1 = {0.1,0.1,0.9};
        Double[] out1 = {0.1};
        Double[] in2 = {0.9,0.1,0.9};
        Double[] out2 = {0.9};
        Double[] in3 = {0.1, 0.9,0.9};
        Double[] out3 = {0.9};
        Double[] in4 = {0.9,0.9,0.9};
        Double[] out4 = {0.1};
        set.add(new RealPair(in1,out1));
        set.add(new RealPair(in2,out2));
        set.add(new RealPair(in3,out3));
        set.add(new RealPair(in4,out4));
        return set.toComplex();
    }
    @Test
    public void iterationTest() throws Exception {
        ComplexDataset andSet = getAndDataset();
        FeedForwardNet net = new FeedForwardNet();
        net.addLayer(3);
        //net.addLayer(2, ActivationFunction.Sigmoid);
        net.addLayer(1);

        SingleThreadCalculation calculation = new SingleThreadCalculation(net);
        SingleThreadPropagation propagation = new SingleThreadPropagation(net);
        propagation.setEta(0.6);

        CPULearning learning = new CPULearning(net,calculation,propagation);
        learning.setDataset(andSet);

        ArrayList<String> weight = new ArrayList<String>(10000);
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
        testen(testinput3,calculation);
    }
    private void testen(Double[] testinput, ICalculationStrategy calculation) throws SeviException{

        FieldVector<Complex> output = calculation.calculate(RealValue.toComplex(testinput));
        Double[] out = ComplexValue.toReal(output);

        for(int i = 0; i < out.length; i++) {
            System.out.print(out[i]);
        }
        System.out.println();
    }
    public void wirte(ArrayList<String> list) throws Exception{
        PrintWriter writer = new PrintWriter("C://temp//weightlist.txt", "UTF-8");
        writer.println("The first line");
        for(String s:list){
            writer.println(s);
        }
        writer.close();
    }
}
