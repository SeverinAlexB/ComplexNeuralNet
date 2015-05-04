import Network.Calculation.LayerResult;
import Network.Calculation.SingleThreadCalculation;
import Network.FeedForwardNet;
import Network.Layer.Neuron.ActivationFunction;
import Network.Layer.Neuron.Neuron;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexField;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import java.rmi.MarshalException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 24.04.2015.
 */
public class SingleThreadCalculationTest extends SingleThreadCalculation {
    public SingleThreadCalculationTest() {
        super(new FeedForwardNet());
    }
    public FeedForwardNet getInizializedNet() throws SeviException {
        FeedForwardNet net = new FeedForwardNet();
        net.addLayer(2, ActivationFunction.Sigmoid);
        net.addLayer(2, ActivationFunction.Sigmoid);
        net.addLayer(1, ActivationFunction.Sigmoid);

        Neuron outputn = net.getLayers().get(2).getNeurons().get(0);
        outputn.getInputs().get(0).setWeight(new Complex(0.5));
        outputn.getInputs().get(1).setWeight(new Complex(0.7));

        Neuron input1 = net.getLayers().get(0).getNeurons().get(0);
        input1.getOutputs().get(0).setWeight(new Complex(1));
        input1.getOutputs().get(1).setWeight(new Complex(0.9));
        Neuron input2 = net.getLayers().get(0).getNeurons().get(1);
        input2.getOutputs().get(0).setWeight(new Complex(0.1));
        input2.getOutputs().get(1).setWeight(new Complex(0.2));
        return net;
    }
    public FeedForwardNet getInizializedNet2() throws SeviException {
        FeedForwardNet net = new FeedForwardNet();
        net.addLayer(3, ActivationFunction.Sigmoid);
        net.addLayer(1, ActivationFunction.Sigmoid);

        Neuron outputn = net.getLayers().get(1).getNeurons().get(0);
        outputn.getInputs().get(0).setWeight(new Complex(0.5,0.5));
        outputn.getInputs().get(1).setWeight(new Complex(0.7,-0.7));
        outputn.getInputs().get(2).setWeight(new Complex(-0.1,0.9));
        return net;
    }
    private FieldVector<Complex> getInitializedVector() {
        Complex[] vector = new Complex[2];
        vector[0] = new Complex(1);
        vector[1] = new Complex(2);
        return MatrixUtils.createFieldVector(vector);
    }

    @Test
    public void calculationTest() throws SeviException {
        FeedForwardNet net = getInizializedNet();

        Complex[] input = {new Complex(1), new Complex(2)};
        FieldVector<Complex> in = MatrixUtils.createFieldVector(input);
        SingleThreadCalculation calc = new SingleThreadCalculation(net);
        FieldVector<Complex> out = calc.calculate(in);

        assertEquals(0.717956, out.getEntry(0).getReal(),0.00001);
    }
    @Test
    public void calculationComplexTest() throws SeviException {
        FeedForwardNet net = getInizializedNet2();

        Complex[] input = {new Complex(0.9,0.9), new Complex(0.1,0.5),new Complex(0.7,-0.2)};
        FieldVector<Complex> in = MatrixUtils.createFieldVector(input);
        SingleThreadCalculation calc = new SingleThreadCalculation(net);
        FieldVector<Complex> out = calc.calculate(in);

        assertEquals(0.812783, out.getEntry(0).getReal(),0.00001);
        assertEquals(0.544586, out.getEntry(0).getImaginary(),0.00001);
    }
    @Test
    public void layerResultTest() throws SeviException {
        FeedForwardNet net = getInizializedNet();

        Complex[] input = {new Complex(1), new Complex(2)};
        FieldVector<Complex> in = MatrixUtils.createFieldVector(input);
        SingleThreadCalculation calc = new SingleThreadCalculation(net);
        calc.calculate(in);

        LayerResult lr1 = calc.getLayerResults().get(0);
        assertEquals("output", 0.7685247, lr1.getOutput().getEntry(0).getReal(),0.00001);
        assertEquals("output", 0.7858349, lr1.getOutput().getEntry(1).getReal(), 0.00001);
        assertEquals("input", 1, lr1.getInput().getEntry(0).getReal(),0.00001);
        assertEquals("input", 2, lr1.getInput().getEntry(1).getReal(), 0.00001);
        assertEquals("netin", 1.2, lr1.getNetin().getEntry(0).getReal(), 0.00001);
        assertEquals("netin", 1.3, lr1.getNetin().getEntry(1).getReal(), 0.00001);

        LayerResult lr2 = calc.getLayerResults().get(1);
        assertEquals("output", 0.717956, lr2.getOutput().getEntry(0).getReal(),0.00001);
        assertEquals("input", 0.7685247, lr2.getInput().getEntry(0).getReal(),0.00001);
        assertEquals("input", 0.7858349, lr2.getInput().getEntry(1).getReal(),0.00001);
        assertEquals("netin", 0.934347, lr2.getNetin().getEntry(0).getReal(), 0.00001);
    }
}
