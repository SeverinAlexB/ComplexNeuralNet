package Data.Pairs.Helper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 14.05.2015.
 */
public class ComplexValueTest {
    double delta = 0.0000001;
    @Test
    public void toReal() {
        RealValue n1 = new RealValue(0.4);
        ComplexValue c1 = new ComplexValue(n1.toComplex());
        assertEquals(n1.getValue()+"",n1.getValue(),c1.toReal().getValue(),delta);

        RealValue n2 = new RealValue(0.999);
        ComplexValue c2 = new ComplexValue(n2.toComplex());
        assertEquals(n2.getValue()+"",n2.getValue(),c2.toReal().getValue(),delta);

        RealValue n3 = new RealValue(1.5);
        ComplexValue c3 = new ComplexValue(n3.toComplex());
        assertEquals(n3.getValue()+"",0.5,c3.toReal().getValue(),delta);
    }
    @Test
    public void toSector() {
        RealValue n1 = new RealValue(0.4);
        ComplexValue c1 = n1.toComplex();
        SectorValue s1 = c1.toSector(3);
        assertEquals(n1+"",0.6691306063588,s1.getReal(),delta);
        assertEquals(n1+"",0.743144825477,s1.getImaginary(),delta);

        RealValue n2 = new RealValue(0.9);
        ComplexValue c2 = n2.toComplex();
        SectorValue s2 = c2.toSector(5);
        assertEquals(n2+"",0.4257792915,s2.getReal(),delta);
        assertEquals(n2+"",0.904827052466,s2.getImaginary(),delta);

        RealValue n3 = new RealValue(1.1);
        ComplexValue c3 = n3.toComplex();
        SectorValue s3 = c3.toSector(4);
        assertEquals(n3+"",0.98768834059,s3.getReal(),delta);
        assertEquals(n3+"",0.156434465040,s3.getImaginary(),delta);
    }
}
