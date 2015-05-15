package Network.Learning.Calculation;

import Data.Pairs.Helper.RealValue;
import Network.Learning.Calculation.SingleThreadCalculation;
import Network.FeedForwardNet;
import Network.Layer.Layer;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 24.04.2015.
 */
public class SingleThreadCalculationTest extends SingleThreadCalculation {
    public SingleThreadCalculationTest() {
        super(new FeedForwardNet(1));
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

    @Test
    public void calculationTest() throws SeviException {
        FeedForwardNet net = getInizializedNet();
        FieldVector<Complex> in = getInitializedVector();

        SingleThreadCalculation calc = new SingleThreadCalculation(net);
        FieldVector<Complex> out = calc.calculate(in);

        assertEquals(0.42574693457499907, out.getEntry(0).getReal(),0.00001);
        assertEquals(0.9048422778031492, out.getEntry(0).getImaginary(),0.00001);

    }

    @Test
    public void layerResultTest() throws SeviException {
        FeedForwardNet net = getInizializedNet();
        FieldVector<Complex> in = getInitializedVector();

        SingleThreadCalculation calc = new SingleThreadCalculation(net);
        FieldVector<Complex> out = calc.calculate(in);

        Complex wsum1 = calc.getLast().getBefore().getNetin().getEntry(0);
        Complex wsum2 = calc.getLast().getNetin().getEntry(0);

        assertEquals(0.3907558732537849,wsum1.getReal());
        assertEquals(0.4929610555586965,wsum1.getImaginary());

        assertEquals(0.1339075219945228,wsum2.getReal());
        assertEquals(0.2845943854826628,wsum2.getImaginary());
    }
}
