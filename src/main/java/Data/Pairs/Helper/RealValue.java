package Data.Pairs.Helper;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;

/**
 * Created by Severin on 14.05.2015.
 */
public class RealValue{
    private double value = 0.0;
    public RealValue(){}
    public RealValue(double value){
        this.value = value;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }


    public ComplexValue toComplex(){
        return new ComplexValue(toSector(1));
    }
    public  SectorValue toSector(int sectorsCount){
        double img = this.value * 2 * Math.PI / sectorsCount;
        return new SectorValue(new Complex(0,img).exp(),sectorsCount);
    }

    public static FieldVector<Complex> toSector(Double[] vector, int sectorsCount){
        Complex[] ret = new Complex[vector.length];
        for(int i = 0; i < ret.length; i++) {
            RealValue realValue = new RealValue(vector[i]);
            ret[i] = realValue.toSector(sectorsCount);
        }
        return MatrixUtils.createFieldVector(ret);
    }
    public static FieldVector<Complex> toComplex(Double[] vector){
        Complex[] ret = new Complex[vector.length];
        for(int i = 0; i < ret.length; i++) {
            RealValue realValue = new RealValue(vector[i]);
            ret[i] = realValue.toComplex();
        }
        return MatrixUtils.createFieldVector(ret);
    }
}
