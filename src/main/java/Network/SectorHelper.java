package Network;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 14.05.2015.
 */
public class SectorHelper {
    public static FieldVector<Complex> roundDownToSector(FieldVector<Complex> vector, int sectorsCount) {
        return vector.ebeDivide(moduloSector(vector,sectorsCount));
    }
    public static FieldVector<Complex> moduloSector(FieldVector<Complex> vector, int sectorsCount){
        FieldVector<Complex> ret = vector.copy();
        for(int i = 0; i < ret.getDimension(); i++) {
            Complex res = moduloSector(ret.getEntry(i),sectorsCount);
            ret.setEntry(i,res);
        }
        return ret;
    }
    protected static Complex moduloSector(Complex c, int sectorsCount){
        double arg = c.getArgument();
        if(arg < 0) arg = 2 * Math.PI + arg;
        arg = arg % (2*Math.PI/sectorsCount);
        return new Complex(0,arg).exp();
    }

}
