package Network.Learning;

import Network.SectorHelper;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 14.05.2015.
 */
public class SectorHelperTest extends SectorHelper {
    double delta = 0.0000001;
    @Test
    public void moduloSectorTest() {
        Complex c = new Complex(0,-1);
        c = SectorHelper.moduloSector(c, 2);
        assertEquals(0,c.getReal(),delta);
        assertEquals(1,c.getImaginary(),delta);

        c = new Complex(-0.10452846326765423,-0.9945218953682733);
        c = SectorHelper.moduloSector(c,3);
        assertEquals(0.9135454576426013,c.getReal(),delta);
        assertEquals(0.4067366430757993,c.getImaginary(),delta);
    }
    @Test
    public void roundSectorTest() {
        Complex[] c = {new Complex(-0.5,-0.5)};
        FieldVector<Complex> vec = MatrixUtils.createFieldVector(c);
        vec = SectorHelper.roundDownToSector(vec,4);

        assertEquals(Math.PI*-1,vec.getEntry(0).getArgument(),delta);
        System.out.println(vec.getEntry(0).getArgument());
    }
}
