package Network.Learning.Propagation;

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
        propagation.propagate(error, calculation.getLast());
        FieldMatrix<Complex> weights1 = net.getLayers().get(0).getMatrixtoNextLayer();
        FieldMatrix<Complex> weights2 = net.getLayers().get(1).getMatrixtoNextLayer();

        assertEquals(0.9223058695166669, weights2.getEntry(0,0).getReal());
        assertEquals(0.044816515866659745, weights2.getEntry(0,0).getImaginary());


        assertEquals(0.7719943146953038, weights1.getEntry(0,0).getReal());
        assertEquals(0.43141935665686076, weights1.getEntry(0,0).getImaginary());

        assertEquals(1.080555020284597, weights1.getEntry(0,1).getReal());
        assertEquals(-0.24894196578500521, weights1.getEntry(0,1).getImaginary());

        assertEquals(0.6945674260523307, weights1.getEntry(0,2).getReal());
        assertEquals(0.2160853193457679, weights1.getEntry(0,2).getImaginary());
    }
}
