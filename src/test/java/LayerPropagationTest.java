import Network.Calculation.LayerResult;
import Network.Layer.Neuron.ActivationFunction;
import Network.Learning.Propagation.LayerPropagation;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 25.04.2015.
 */
public class LayerPropagationTest extends LayerPropagation {




    @Test
    public void calcDeltaTest() {
        Complex[] err = {new Complex(1), new Complex(2)};
        Complex[] netin = {new Complex(3), new Complex(4)};
        Complex[] sh = {new Complex(0.0451766), new Complex(0.0353254)};

        FieldVector<Complex> error = MatrixUtils.createFieldVector(err);
        FieldVector<Complex> neti = MatrixUtils.createFieldVector(netin);
        FieldVector<Complex> should = MatrixUtils.createFieldVector(sh);

        LayerResult layerResult = new LayerResult();
        layerResult.setNetin(neti);
        layerResult.setActivationFunction(ActivationFunction.Sigmoid);
        FieldVector<Complex> result = this.calcDelta(error, layerResult);


        assertEquals(sh[0].getReal(), result.getEntry(0).getReal(),0.0001);
        assertEquals(sh[1].getReal(), result.getEntry(1).getReal(),0.0001);
    }
    @Test
    public void calcErrorTest() {
        Complex[][] weights = {{new Complex(1), new Complex(2)},{new Complex(1), new Complex(2)}};
        Complex[] delta = {new Complex(1), new Complex(2)};
        Complex[] should = {new Complex(3), new Complex(6)};
        FieldMatrix<Complex> w = MatrixUtils.createFieldMatrix(weights);
        FieldVector<Complex> d = MatrixUtils.createFieldVector(delta);
        FieldVector<Complex> s = MatrixUtils.createFieldVector(should);

        assertEquals(s,this.calcError(w,d));
    }

    @Test
    public void calcWeightDeltaTest()  {
        double eta = 1;
        Complex[] d = {new Complex(0.0571124)};
        Complex[] in = {new Complex(0.768525),new Complex(0.785835)};
        FieldVector<Complex> delta = MatrixUtils.createFieldVector(d);
        FieldVector<Complex> input = MatrixUtils.createFieldVector(in);
        FieldMatrix<Complex> weightDiff =  this.calcWeightDelta(delta,input,eta);

        assertEquals(1,weightDiff.getRowDimension());
        assertEquals(2,weightDiff.getColumnDimension());

        assertEquals(0.0438923,weightDiff.getEntry(0,0).getReal(),0.00001);
        assertEquals(0.0448809,weightDiff.getEntry(0,1).getReal(),0.00001);
    }

}
