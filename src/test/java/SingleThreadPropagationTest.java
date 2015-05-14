import Network.Calculation.SingleThreadCalculation;
import Network.FeedForwardNet;
import Network.Layer.Layer;
import Network.Layer.Neuron.ActivationFunction;
import Network.Learning.Propagation.LayerPropagation;
import Network.Learning.Propagation.SingleThreadPropagation;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by Severin on 24.04.2015.
 */
public class SingleThreadPropagationTest extends SingleThreadPropagation {
    public SingleThreadPropagationTest() {
        super(null);
    }

    public FeedForwardNet getNetWith1Weights() throws SeviException{
        FeedForwardNet net = new FeedForwardNet();
        Layer l1 = net.addLayer(2);
        Layer l2 = net.addLayer(2);
        Layer l3 = net.addLayer(1);

        l1.getNeurons().get(0).getOutputs().get(0).setWeight(new Complex(1));
        l1.getNeurons().get(0).getOutputs().get(1).setWeight(new Complex(0.9));
        l1.getNeurons().get(1).getOutputs().get(0).setWeight(new Complex(0.1));
        l1.getNeurons().get(1).getOutputs().get(1).setWeight(new Complex(0.2));

        l2.getNeurons().get(0).getOutputs().get(0).setWeight(new Complex(0.5));
        l2.getNeurons().get(1).getOutputs().get(0).setWeight(new Complex(0.7));
        return net;
    }
    @Test
    public void layerpropagateTest() throws Exception {
        FeedForwardNet net = getNetWith1Weights();
        this.setNetWork(net);
        SingleThreadPropagation prop = new SingleThreadPropagationTest();
        prop.setNetWork(net);
        prop.setEta(10);
        SingleThreadCalculation calc = new SingleThreadCalculation(net);
        Complex[] in = {new Complex(1),new Complex(2)};
        Complex[] tar = {new Complex(1)};
        FieldVector<Complex> input = MatrixUtils.createFieldVector(in);
        FieldVector<Complex> target = MatrixUtils.createFieldVector(tar);

        FieldVector<Complex> output = calc.calculate(input);
        assertEquals(0.717956, output.getEntry(0).getReal(), 0.00001);
        FieldVector<Complex> error = target.subtract(output);

        ArrayList<LayerPropagation> lpList = this.calcLayerPropagation(error, calc.getLayerResults());this.setNetWork(net);
        LayerPropagation lp = lpList.get(0);
        lp.ajustWeights(1);
        assertEquals("delta", 0.0571124, lp.getDelta().getEntry(0).getReal(), 0.00001);
        assertEquals("error", error.getEntry(0).getReal(), lp.getError().getEntry(0).getReal(), 0.00001);
        assertNull("lastdelta", lp.getLastdelta());
        assertEquals("layer", net.getLayers().get(2), lp.getLayer());
        assertEquals("Result", calc.getLayerResults().get(1), lp.getResult());
        assertEquals("weightdiff", 0.0438923, lp.getWeightDiff().getEntry(0, 0).getReal(), 0.00001);
        assertEquals("weightdiff", 0.0448809, lp.getWeightDiff().getEntry(0, 1).getReal(), 0.00001);
        assertEquals("weights",0.543892, net.getLayers().get(1).getNeurons().get(0).getOutputs().get(0).getWeight().getReal(), 0.00001);
        assertEquals("weights",0.744881, net.getLayers().get(1).getNeurons().get(1).getOutputs().get(0).getWeight().getReal(), 0.00001);


        LayerPropagation lp2 = lpList.get(1);
        lp2.ajustWeights(1);
        assertEquals("lastdelta", lp.getDelta(), lp2.getLastdelta());
        assertEquals("layer", net.getLayers().get(1), lp2.getLayer());
        assertEquals("result", calc.getLayerResults().get(0), lp2.getResult());
        assertEquals("error", 0.0285562, lp2.getError().getEntry(0).getReal(), 0.00001);
        assertEquals("error", 0.0399787, lp2.getError().getEntry(1).getReal(), 0.00001);
        assertEquals("delta", 0.0050799, lp2.getDelta().getEntry(0).getReal(), 0.00001);
        assertEquals("delta", 0.00672835, lp2.getDelta().getEntry(1).getReal(), 0.00001);
        assertEquals("weightdiff",0.00507999,lp2.getWeightDiff().getEntry(0, 0).getReal(),0.00001);
        assertEquals("weightdiff",0.01016,lp2.getWeightDiff().getEntry(0,1).getReal(),0.00001);
        assertEquals("weightdiff",0.00672835,lp2.getWeightDiff().getEntry(1, 0).getReal(),0.00001);
        assertEquals("weightdiff",0.013457,lp2.getWeightDiff().getEntry(1, 1).getReal(),0.00001);
        assertEquals("weights",1.00508, net.getLayers().get(0).getNeurons().get(0).getOutputs().get(0).getWeight().getReal(), 0.00001);
        assertEquals("weights",0.906728, net.getLayers().get(0).getNeurons().get(0).getOutputs().get(1).getWeight().getReal(), 0.00001);
        assertEquals("weights",0.11016, net.getLayers().get(0).getNeurons().get(1).getOutputs().get(0).getWeight().getReal(), 0.00001);
        assertEquals("weights",0.213457, net.getLayers().get(0).getNeurons().get(1).getOutputs().get(1).getWeight().getReal(), 0.00001);
    }
}
