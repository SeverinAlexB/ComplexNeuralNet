package Data;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;

/**
 * Created by Severin on 25.04.2015.
 */
public class Helper {
    public static double max_value = 2;
    public static Complex transform(double real){
        return new Complex(real);
    }
    public static double transform(Complex c){
        return c.getReal();
    }
    public static Complex transformE(double real){
        double img = (real/max_value)*(Math.PI/2);
        Complex c = new Complex(real,img);
        return c.exp();
    }
    public static double transformE(Complex c){
        //c = c.log();
        //return (c.getImaginary()/(Math.PI/2)) *max_value;
        return c.getReal();
    }
    public static double[] transform(FieldVector<Complex> vector){
        double[] ret = new double[vector.getDimension()];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = transform(vector.getEntry(i));
        }
        return ret;
    }
    public static FieldVector transform(double[] vector){
        Complex[] ret = new Complex[vector.length];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = transform(vector[i]);
        }
        return MatrixUtils.createFieldVector(ret);
    }
    public static double[] transformE(FieldVector<Complex> vector){
        double[] ret = new double[vector.getDimension()];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = transformE(vector.getEntry(i));
        }
        return ret;
    }
    public static FieldVector transformE(double[] vector){
        Complex[] ret = new Complex[vector.length];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = transformE(vector[i]);
        }
        return MatrixUtils.createFieldVector(ret);
    }
}
