package Data.Pairs.Helper;
import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 14.05.2015.
 */
public class RealValueTest {
    double delta = 0.0000001;
    @Test
    public void toComplexTest() {

        RealValue n1 = new RealValue(0.5);
        Complex c1 = n1.toComplex();
        assertEquals(n1+"",-1,c1.getReal(),delta);
        assertEquals(n1+"",0,c1.getImaginary(),delta);

        RealValue n2 = new RealValue(0.9);
        Complex c2 = n2.toComplex();
        assertEquals(n2+"",0.8090169943,c2.getReal(),delta);
        assertEquals(n2+"",-0.5877852522,c2.getImaginary(),delta);

        RealValue n3 = new RealValue(1.5);
        Complex c3 = n3.toComplex();
        assertEquals(n3+"",-1,c3.getReal(),delta);
        assertEquals(n3+"",0,c3.getImaginary(),delta);
    }
    @Test
    public void toSectorTest() {
        RealValue n1 = new RealValue(0.5);
        Complex c1 = n1.toSector(2);
        assertEquals(n1+"",0,c1.getReal(),delta);
        assertEquals(n1+"",1,c1.getImaginary(),delta);

        RealValue n2 = new RealValue(0.9);
        Complex c2 = n2.toSector(3);
        assertEquals(n2+"",-0.3090169943,c2.getReal(),delta);
        assertEquals(n2+"",0.9510565162,c2.getImaginary(),delta);


        RealValue n3 = new RealValue(1.5);
        Complex c3 = n3.toSector(3);
        assertEquals(n3+"",-1,c3.getReal(),delta);
        assertEquals(n3+"",0,c3.getImaginary(),delta);

    }
}
