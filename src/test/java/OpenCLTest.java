import com.nativelibs4java.opencl.*;
import com.nativelibs4java.util.IOUtils;
import org.bridj.Pointer;

import static com.nativelibs4java.util.IOUtils.readText;
import static java.lang.Math.*;
import org.junit.Test;
import org.omg.PortableInterceptor.ACTIVE;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;

import static org.bridj.Pointer.allocateFloat;
import static org.bridj.Pointer.allocateFloats;


/**
 * Created by Severin on 21.04.2015.
 */
public class OpenCLTest  {
    final int A_ROWS = 2000;
    final int A_COLS = 300;
    final int B_ROWS = A_COLS;
    final int B_COLS = 1000;
    final int C_ROWS = A_ROWS;
    final int C_COLS = B_COLS;
    public Float[][] getA() {
        Float[][] a = new Float[A_ROWS][A_COLS];
        for(int row = 0; row < A_ROWS; row++) {
            for(int col = 0; col < A_COLS; col++) {
                a[row][col] = new Float(row + col);
            }
        }
        return a;
    }
    public Float[][] getB() {
        Float[][] b = new Float[B_ROWS][B_COLS];
        for(int row = 0; row < B_ROWS; row++) {
            for(int col = 0; col < B_COLS; col++) {
                b[row][col] = new Float(row + col);
            }
        }
        return b;
    }
    public Float[][] getC(boolean filled) {
        if(!filled) return new Float[C_ROWS][C_COLS];

        Float[][] a = getA();
        Float[][] b = getB();
        Float[][] c = new Float[C_ROWS][C_COLS];

        for(int row = 0; row < C_ROWS; row++) {
            for(int col = 0; col < C_COLS; col++) {
                float sum = 0;
                for(int i = 0; i < A_COLS;i++){
                    sum += a[row][i] * b[i][col];
                }
                c[row][col] = sum;
             }
        }
        return c;
    }

    public void Test()throws Exception {
        long start = 0,ende = 0;
        Float[][] ma = getA();
        Float[][] mb = getB();
        Float[][] mc = getC(false);

        ArrayList<CLDevice> devices = new ArrayList<CLDevice>();
        // try to list all platform and devices
        System.out.println("-Available plattforms:");
        for(CLPlatform platform : JavaCL.listPlatforms()) {
            System.out.println("\t" +platform.getName());
        }
        System.out.println("-Choose " + JavaCL.listPlatforms()[0].getName());
        for (CLDevice device : JavaCL.listPlatforms()[0].listAllDevices(true)) {
            devices.add(device);
        }

        CLDevice[] devicess = new CLDevice[1];
        devicess[0] = devices.get(0);
        for(int i = 0; i < devices.size(); i++) {
           // devicess[i] = devices.get(i);
        }

        CLContext context = JavaCL.createContext(null,devicess);
        System.out.println("-We are using these accelerators:");
        for (CLDevice c : context.getDevices()) {
            System.out.println("\t" + c.getName());
        }

        CLQueue queue = context.createDefaultQueue();
        ByteOrder byteOrder = context.getByteOrder();
        int localsize_x = 32;
        int localsize_y = 16;
        int blockscount_x = (C_ROWS + localsize_x -1)/localsize_x;
        int blockscount_y = (C_COLS + localsize_y -1)/localsize_y;
        int globalsize_x = blockscount_x*localsize_x;
        int globalsize_y = blockscount_y*localsize_y;
        Pointer<Pointer<Float>>
                aPtr = allocateFloats(A_ROWS,A_COLS).order(byteOrder),
                bPtr = allocateFloats(B_ROWS,B_COLS).order(byteOrder),
                cPtr = allocateFloats(C_ROWS,C_COLS).order(byteOrder);

        //Move A
        for (int row = 0; row < A_ROWS; row++) {
            aPtr.get(row).setArray(ma[row]);
        }
        //Move B
        for (int row = 0; row < B_ROWS; row++) {
            bPtr.get(row).setArray(mb[row]);
        }

        // Create OpenCL input buffers (using the native memory pointers aPtr and bPtr) :
        CLBuffer<Pointer<Float>>
                a = context.createBuffer(CLMem.Usage.Input, aPtr),
                b = context.createBuffer(CLMem.Usage.Input, bPtr),
                c = context.createBuffer(CLMem.Usage.InputOutput, cPtr);


        // Read the program sources and compile them :
        String src = readText(OpenCLTest.class.getResource("kernel.cl"));
        CLProgram program = context.createProgram(src);

        // Get and call the kernel :
        CLKernel addFloatsKernel = program.createKernel("mMultiplication");
        addFloatsKernel.setArgs(a, b,c, C_ROWS,C_COLS,B_ROWS);



        CLEvent addEvt = null;

        try {
            addEvt = addFloatsKernel.enqueueNDRange(queue, new int[]{globalsize_x,globalsize_y},new int[]{localsize_x,localsize_y});
            start = System.currentTimeMillis();
            addEvt.waitFor();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(System.getenv("CL_LOG_ERRORS"));
        }

        ende = System.currentTimeMillis();
        System.out.println("GPU Time: " + (ende-start) + "ms");

        c.read(queue,cPtr,true,addEvt);
        //Pointer<Pointer<Float>> outPtr = c.read(queue, addEvt); // blocks until add_floats finished

        //Move C
        for (int row = 0; row < C_ROWS; row++) {
           float[] f = cPtr.get(row).getFloats(C_COLS);
           for(int col = 0; col < f.length; col++){
               mc[row][col] = f[col];
           }
        }

        start = System.currentTimeMillis();
        Float[][] shouldC = getC(true);
        ende = System.currentTimeMillis();
        System.out.println("CPU Time: " + (ende - start) + "ms");


        boolean isWrong = false;
        for(int row = 0; row < C_ROWS; row++) {
            for(int col = 0; col < C_COLS; col++) {
                float diff = shouldC[row][col] -mc[row][col];
                if(diff < 0) diff = diff * -1;

                if(diff > 0.1) isWrong = true;
            }
        }
        if(isWrong) System.out.println("Test wrong");
        else System.out.println("Successfull");

        aPtr.release();
        bPtr.release();
        cPtr.release();

    }

}
