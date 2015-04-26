import Network.Layer.Layer;
import Network.Layer.Neuron.ActivationFunction;
import Network.Layer.Neuron.Neuron;
import Network.Layer.Neuron.Synapse;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Severin on 24.04.2015.
 */
public class LayerTest {
    @Test
    public void connectToTest() throws SeviException {
        Layer l1 = new Layer(2, ActivationFunction.Sigmoid);
        Layer l2 = new Layer(2, ActivationFunction.Sigmoid);

        assertEquals(2, l1.getNeurons().size());
        assertEquals(2, l2.getNeurons().size());

        l1.connectTo(l2);


        Neuron n0 = l1.getNeurons().get(0);
        Neuron n1 = l1.getNeurons().get(1);

        Neuron n2 = l2.getNeurons().get(0);
        Neuron n3 = l2.getNeurons().get(1);

        //Hat 2*2 Synapse gehen aus Neuronen raus
        assertEquals(n0.getOutputs().size(),2);
        assertEquals(n1.getOutputs().size(),2);
        //Hat 2*2 Synapse gehen in die Neuronen rein
        assertEquals(n2.getInputs().size(),2);
        assertEquals(n3.getInputs().size(),2);

        //Synapse zeigen an den richtigen Ort
        assertEquals(n0.getOutputs().get(0).getOutput(),n2);
        assertEquals(n0.getOutputs().get(1).getOutput(),n3);
        assertEquals(n1.getOutputs().get(0).getOutput(),n2);
        assertEquals(n1.getOutputs().get(1).getOutput(),n3);
    }
    @Test
    public void getMatrixToNextLayerTest() throws  Exception{
        Layer l1 = new Layer(2, ActivationFunction.Sigmoid);
        Layer l2 = new Layer(3, ActivationFunction.Sigmoid);
        l1.connectTo(l2);

        //Set weights manually
        l1.getNeurons().get(0).getOutputs().get(0).setWeight(new Complex(1));
        l1.getNeurons().get(0).getOutputs().get(1).setWeight(new Complex(2));
        l1.getNeurons().get(0).getOutputs().get(2).setWeight(new Complex(3));
        l1.getNeurons().get(1).getOutputs().get(0).setWeight(new Complex(4));
        l1.getNeurons().get(1).getOutputs().get(1).setWeight(new Complex(5));
        l1.getNeurons().get(1).getOutputs().get(2).setWeight(new Complex(6));



        FieldMatrix<Complex> matrix = l1.getMatrixtoNextLayer();
        //out(matrix);

        assertEquals(1, matrix.getEntry(0, 0).getReal(), 0.0001);
        assertEquals(4, matrix.getEntry(0, 1).getReal(), 0.0001);
        assertEquals(2,matrix.getEntry(1, 0).getReal(), 0.0001);
        assertEquals(5,matrix.getEntry(1, 1).getReal(), 0.0001);
        assertEquals(3,matrix.getEntry(2, 0).getReal(), 0.0001);
        assertEquals(6,matrix.getEntry(2, 1).getReal(), 0.0001);
    }
    @Test
    public void setMatrixToNextLayerTest() throws SeviException{
        Layer l1 = new Layer(2, ActivationFunction.Sigmoid);
        Layer l2 = new Layer(3, ActivationFunction.Sigmoid);
        l1.connectTo(l2);

        FieldMatrix<Complex> matrix1 = l1.getMatrixtoNextLayer();
        l1.setMatrixtoNextLayer(matrix1);
        FieldMatrix<Complex> matrix2 = l1.getMatrixtoNextLayer();

        for(int x = 0; x < matrix1.getRowDimension(); x++) {
            for(int y = 0; y < matrix1.getColumnDimension(); y++) {
                assertTrue(matrix1.getEntry(x, y).equals(matrix2.getEntry(x, y)));
            }
        }

    }


    public void getOutTest(){
        Complex[][] m = {{new Complex(1),new Complex(2)},{new Complex(3),new Complex(4)}};
        FieldMatrix<Complex> matrix = MatrixUtils.createFieldMatrix(m);

        Complex[] v = {new Complex(1),new Complex(2)};
        FieldVector<Complex> vector = MatrixUtils.createFieldVector(v);

        FieldVector<Complex> multi = matrix.operate(vector);

        System.out.println("Matrix:");
        out(matrix);

        System.out.println("Vector:");
        out(vector);

        System.out.println("Result:");
        out(multi);

    }

    public static void out(FieldMatrix<Complex> field){
        for(int row = 0; row < field.getRowDimension(); row++){
            for(int column = 0; column < field.getColumnDimension(); column++){
                Complex c =field.getEntry(row, column);
                System.out.print(c.getReal()+ "(" + c.getImaginary() + "i)" + ", ");
            }
            System.out.println();
        }
    }
    public static void out(FieldVector<Complex> field){
        for(int row = 0; row < field.getDimension(); row++){
            System.out.print(field.getEntry(row).getReal());
            System.out.println();
        }
    }
}
