package Data.Pairs.Helper;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;

/**
 * Created by Severin on 14.05.2015.
 */
public class SectorValue extends Complex {
    private int sectorsCount = 1;

    public SectorValue(Complex c, int sectorsCount){
        super(c.getReal(),c.getImaginary());
        this.sectorsCount = sectorsCount;
    }
    public SectorValue(double real, int sectorsCount){
        super(real);
        this.sectorsCount = sectorsCount;
    }
    public SectorValue(double real, double imaginary, int sectorsCount){
        super(real, imaginary);
        this.sectorsCount = sectorsCount;
    }

    public int getSectorsCount() {
        return this.sectorsCount;
    }

    public RealValue toReal(){
        double val = this.getArgument() / (2* Math.PI/sectorsCount);

        if(val < 0) return new RealValue(1 + val);
        else return new RealValue(val);
    }
    public static Double[] toReal(FieldVector<Complex> vector, int sectorsCount){
        Double[] ret = new Double[vector.getDimension()];
        for(int i = 0; i < ret.length; i++) {
            SectorValue sectorValue = new SectorValue(vector.getEntry(i),sectorsCount);
            ret[i] = sectorValue.toReal().getValue();
        }
        return ret;
    }
    public ComplexValue toComplex(){
       return new ComplexValue(this.pow(sectorsCount));
    }
    public static FieldVector<Complex> toComplex(FieldVector<Complex> vector, int sectorsCount){
        Complex[] ret = new Complex[vector.getDimension()];
        for(int i = 0; i < ret.length; i++) {
            SectorValue sectorValue = new SectorValue(vector.getEntry(i),sectorsCount);
            ret[i] = sectorValue.toComplex();
        }
        return MatrixUtils.createFieldVector(ret);
    }
}
