import Network.Calculation.SingleThreadCalculation;
import Network.FeedForwardNet;
import Network.Layer.Layer;
import Network.Layer.Neuron.ActivationFunction;
import Network.Learning.Propagation.SingleThreadPropagation;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 24.04.2015.
 */
public class SingleThreadPropagationTest extends SingleThreadPropagation {
    public SingleThreadPropagationTest() {
        super(null);
    }

    public FeedForwardNet getNetWith1Weights() throws SeviException{
        FeedForwardNet net = new FeedForwardNet();
        Layer l1 = net.addLayer(2, ActivationFunction.Sigmoid);
        Layer l2 = net.addLayer(2, ActivationFunction.Sigmoid);
        Layer l3 = net.addLayer(1, ActivationFunction.Sigmoid);

        l1.getNeurons().get(0).getOutputs().get(0).setWeight(new Complex(1));
        l1.getNeurons().get(0).getOutputs().get(1).setWeight(new Complex(1));
        l1.getNeurons().get(1).getOutputs().get(0).setWeight(new Complex(1));
        l1.getNeurons().get(1).getOutputs().get(1).setWeight(new Complex(1));

        l2.getNeurons().get(0).getOutputs().get(0).setWeight(new Complex(1));
        l2.getNeurons().get(1).getOutputs().get(0).setWeight(new Complex(1));
        return net;
    }
    @Test
    public void layerpropagateTest() throws Exception {
        FeedForwardNet net = getNetWith1Weights();
        SingleThreadPropagation prop = new SingleThreadPropagationTest();
        prop.setNetWork(net);
        prop.setEta(10);
        SingleThreadCalculation calc = new SingleThreadCalculation(net);
        Complex[] in = {new Complex(1),new Complex(1)};
        Complex[] tar = {new Complex(1)};
        FieldVector<Complex> input = MatrixUtils.createFieldVector(in);
        FieldVector<Complex> target = MatrixUtils.createFieldVector(tar);

        FieldVector<Complex> output = calc.calculate(input);
        FieldVector<Complex> error = target.subtract(output);

        prop.propagate(error, calc.getLayerResults());

        assertEquals(1.161527544, net.getLayers().get(1).getNeurons().get(0).getOutputs().get(0).getWeight().getReal(), 0.00001);
        assertEquals(1.01925455, net.getLayers().get(0).getNeurons().get(0).getOutputs().get(0).getWeight().getReal(), 0.00001);
    }
}
