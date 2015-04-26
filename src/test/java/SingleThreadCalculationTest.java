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
    private FieldMatrix<Complex> getInitializedMatrix() {
        Complex[][] matrix = new Complex[3][2];
        matrix[0][0] = new Complex(1);
        matrix[0][1] = new Complex(2);
        matrix[1][0] = new Complex(3);
        matrix[1][1] = new Complex(4);
        matrix[2][0] = new Complex(5);
        matrix[2][1] = new Complex(6);
        return MatrixUtils.createFieldMatrix(matrix);
    }
    private FieldVector<Complex> getInitializedVector() {
        Complex[] vector = new Complex[2];
        vector[0] = new Complex(1);
        vector[1] = new Complex(2);
        return MatrixUtils.createFieldVector(vector);
    }

    @Test
    public void calculationTest() throws SeviException {
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

        Complex[] input = {new Complex(1), new Complex(2)};
        FieldVector<Complex> in = MatrixUtils.createFieldVector(input);
        SingleThreadCalculation calc = new SingleThreadCalculation(net);
        FieldVector<Complex> out = calc.calculate(in);

        assertEquals(0.7685247, calc.getLayerResults().get(0).getOutput().getEntry(0).getReal(),0.00001);
        assertEquals(0.7858349, calc.getLayerResults().get(0).getOutput().getEntry(1).getReal(),0.00001);
        assertEquals(0.717956, out.getEntry(0).getReal(),0.00001);
    }
}
