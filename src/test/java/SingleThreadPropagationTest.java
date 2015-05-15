import Data.Pairs.Helper.RealValue;
import Network.FeedForwardNet;
import Network.Layer.Layer;
import Network.Learning.Calculation.SingleThreadCalculation;
import Network.Learning.Propagation.SingleThreadPropagation;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by Severin on 24.04.2015.
 */
public class SingleThreadPropagationTest extends SingleThreadPropagation {
    public SingleThreadPropagationTest() {
        super(null);
    }
    public FeedForwardNet getInizializedNet() throws SeviException {
        FeedForwardNet net = new FeedForwardNet(5);
        Layer l1 = net.addLayer(2,false);
        Layer l2 = net.addLayer(1,true);
        net.addLayer(1,false);

        l1.getNeurons().get(0).getOutputs().get(0).setWeight(new Complex(0.6905162797000783, 0.7233168513654049));
        l1.getNeurons().get(1).getOutputs().get(0).setWeight(new Complex(0.9990769852893715,0.042955528923538926));
        l1.getNeurons().get(2).getOutputs().get(0).setWeight(new Complex(0.897366517866153,0.44128599865934504));

        l2.getNeurons().get(0).getOutputs().get(0).setWeight(new Complex(0.9735594235749482,0.22843390459477475));

        return net;
    }

    private FieldVector<Complex> getInitializedVector() {
        Complex[] vector = new Complex[3];
        vector[0] = new RealValue(0.1).toSector(5);
        vector[1] = new RealValue(0.1).toSector(5);
        vector[2] = new RealValue(0.9).toSector(5);
        return MatrixUtils.createFieldVector(vector);
    }
    private FieldVector<Complex> getError() {
        Complex[] vector = new Complex[1];
        vector[0] = new Complex(0.1781361133166198,-0.2451741069857421);
        return MatrixUtils.createFieldVector(vector);
    }
    @Test
    public void propagationTest() throws SeviException{
        FeedForwardNet net = getInizializedNet();
        FieldVector<Complex> vec = getInitializedVector();
        FieldVector<Complex> error = getError();
        SingleThreadCalculation calculation = new SingleThreadCalculation(net);
        calculation.calculate(vec);

        SingleThreadPropagation propagation = new SingleThreadPropagation(net);
        //propagation.propagate(error, calculation.getLayerResults());

        FieldMatrix<Complex> weights1 = net.getLayers().get(0).getMatrixtoNextLayer();
        FieldMatrix<Complex> weights2 = net.getLayers().get(1).getMatrixtoNextLayer();

        assertEquals(0.9223058695166669, weights1.getEntry(0,0).getReal());
        assertEquals(0.044816515866659745, weights1.getEntry(0,0).getImaginary());
    }

    public void layerpropagateTest() throws Exception {
       /* FeedForwardNet net = getNetWith1Weights();
        this.setNetWork(net);
        SingleThreadPropagation prop = new SingleThreadPropagationTest();
        prop.setNetWork(net);

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
                assertEquals("delta", 0.0571124, lp.getDelta().getEntry(0).getReal(), 0.00001);
        assertEquals("error", error.getEntry(0).getReal(), lp.getError().getEntry(0).getReal(), 0.00001);
        assertNull("lastdelta", lp.getLastdelta());
        assertEquals("layer", net.getLayers().get(2), lp.getBottomLayer());
        assertEquals("Result", calc.getLayerResults().get(1), lp.getResult());
        assertEquals("weightdiff", 0.0438923, lp.getWeightDiff().getEntry(0, 0).getReal(), 0.00001);
        assertEquals("weightdiff", 0.0448809, lp.getWeightDiff().getEntry(0, 1).getReal(), 0.00001);
        assertEquals("weights",0.543892, net.getLayers().get(1).getNeurons().get(0).getOutputs().get(0).getWeight().getReal(), 0.00001);
        assertEquals("weights",0.744881, net.getLayers().get(1).getNeurons().get(1).getOutputs().get(0).getWeight().getReal(), 0.00001);


        LayerPropagation lp2 = lpList.get(1);
        lp2.ajustWeights(1);
        assertEquals("lastdelta", lp.getDelta(), lp2.getLastdelta());
        assertEquals("layer", net.getLayers().get(1), lp2.getBottomLayer());
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
        assertEquals("weights",0.213457, net.getLayers().get(0).getNeurons().get(1).getOutputs().get(1).getWeight().getReal(), 0.00001);*/
    }
}
