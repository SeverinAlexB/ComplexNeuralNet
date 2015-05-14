package Data.Pairs.Helper;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Severin on 14.05.2015.
 */
public class SectorValueTest {
    double delta = 0.0000001;
    @Test
    public void toRealTest() {
        SectorValue s1 = new SectorValue(0.9781476007338,0.20791169081,3);
        assertEquals(0.1,s1.toReal().getValue(),delta);

        SectorValue s2 = new SectorValue(0.156434446504023,0.9876883405951,4);
        assertEquals(0.9,s2.toReal().getValue(),delta);

        SectorValue s3 = new SectorValue(0.7071067811865476,0.7071067811865476,4);
        assertEquals(0.5,s3.toReal().getValue(),delta);
    }

    @Test
    public void toComplexTest() {
        SectorValue s1 = new SectorValue(0.9781476007338,0.20791169081,3);
        ComplexValue c1 = s1.toComplex();
        assertEquals(0.8090169943749475,c1.getReal(),delta);
        assertEquals(0.5877852522924731,c1.getImaginary(),delta);

        SectorValue s2 = new SectorValue(-0.30901699437494734,0.9510565162951536,3);
        ComplexValue c2 = s2.toComplex();
        assertEquals(0.8090169943749473,c2.getReal(),delta);
        assertEquals(-0.5877852522924734,c2.getImaginary(),delta);

        SectorValue s3 = new SectorValue(0.7071067811865476,0.7071067811865476,4);
        ComplexValue c3 = s3.toComplex();
        assertEquals(-1,c3.getReal(),delta);
        assertEquals(0,c3.getImaginary(),delta);
    }
}
