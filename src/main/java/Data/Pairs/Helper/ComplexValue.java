package Data.Pairs.Helper;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;

/**
 * Created by Severin on 14.05.2015.
 */
public class ComplexValue extends Complex{
    public ComplexValue(Complex c){
        super(c.getReal(),c.getImaginary());
    }
    public ComplexValue(double real){
        super(real);
    }
    public ComplexValue(double real, double imaginary) {
        super(real, imaginary);
    }
    public  RealValue toReal(){
        return  new SectorValue(this,1).toReal();
    }
    public static Double[] toReal(FieldVector<Complex> vector){
        Double[] ret = new Double[vector.getDimension()];
        for(int i = 0; i < ret.length; i++) {
            ComplexValue complexValue = new ComplexValue(vector.getEntry(i));
            ret[i] = complexValue.toReal().getValue();
        }
        return ret;
    }
    public SectorValue toSector(int sectorsCount){
        double arg = this.getArgument();
        if(arg < 0) arg = 2 * Math.PI + arg;
        arg /= sectorsCount;
        double abs = this.abs();
        double real = Math.cos(arg)*abs;
        double img = Math.sin(arg)*abs;
        return new SectorValue(real,img,sectorsCount);
    }
    public static FieldVector<Complex> toSector(FieldVector<Complex> vector, int sectorsCount){
        Complex[] ret = new Complex[vector.getDimension()];
        for(int i = 0; i < ret.length; i++) {
            ComplexValue complexValue = new ComplexValue(vector.getEntry(i));
            ret[i] = complexValue.toSector(sectorsCount);
        }
        return MatrixUtils.createFieldVector(ret);

    }

}
