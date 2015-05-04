import com.nativelibs4java.opencl.*;
import com.nativelibs4java.util.IOUtils;
import org.bridj.Pointer;

import static com.nativelibs4java.util.IOUtils.readText;
import static java.lang.Math.*;
import org.junit.Test;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;

import static org.bridj.Pointer.allocateFloat;
import static org.bridj.Pointer.allocateFloats;


/**
 * Created by Severin on 21.04.2015.
 */
public class OpenCLTest  {
    int n = 100000000;
    @Test
    public void Test()throws Exception {

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
        int localsize = 512;
        int blockscount = n/200000;
        int globalsize = ((blockscount)/localsize + 1) *localsize;
        Pointer<Float>
                aPtr = allocateFloats(n).order(byteOrder),
                bPtr = allocateFloats(n).order(byteOrder),
                oPtr = allocateFloats(n).order(byteOrder);

        for (int i = 0; i < n; i++) {
            aPtr.set(i, (float)i);
            bPtr.set(i, (float)i);
        }

        // Create OpenCL input buffers (using the native memory pointers aPtr and bPtr) :
        CLBuffer<Float>
                a = context.createBuffer(CLMem.Usage.InputOutput, aPtr),
                b = context.createBuffer(CLMem.Usage.Input, bPtr);

        // Create an OpenCL output buffer :


        // Read the program sources and compile them :
        String src = readText(OpenCLTest.class.getResource("kernel.cl"));
        CLProgram program = context.createProgram(src);

        // Get and call the kernel :
        CLKernel addFloatsKernel = program.createKernel("add_floats");
        addFloatsKernel.setArgs(a, b,n);


        long start = System.currentTimeMillis();
        CLEvent addEvt = null;

        try {
            addEvt = addFloatsKernel.enqueueNDRange(queue, new int[]{globalsize},new int[]{localsize});
            start = System.currentTimeMillis();
            addEvt.waitFor();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(System.getenv("CL_LOG_ERRORS"));
        }



        long end = System.currentTimeMillis();
        Pointer<Float> outPtr = a.read(queue, addEvt); // blocks until add_floats finished

        // Print the first 10 output values :
        for (int i = 0; i < 10 && i < n; i++)
            System.out.println("out[" + i + "] = " + outPtr.get(i));

        System.out.println("GPU Time: " + (end-start) + "ms");

        boolean iswrong = false;
        for(int i = 0; i < n; i++){
            float res = outPtr.get(i);
            float temp = res-i*2;
            if(temp <-0.1 || temp >0.1){
                System.out.println("Error: " + res + ", " + i);
                iswrong= true;
                break;
            }

        }
        aPtr.release();
        bPtr.release();
        oPtr.release();
        if(iswrong) System.out.println("Test wrong");
        else System.out.println("Successfull");
    }
    @Test
    public void CPuTest() {
        float[] a = new float[n];
        float[] b = new float[n];
        float[] c = new float[n];
        for(int i = 0; i < n; i++){
            a[i] = i;
            b[i] = i;
        }
        long start = System.currentTimeMillis();
        for(int i = 0; i < n; i++){
            c[i] = a[i] + b[i];
        }

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end-start) + "ms");
    }

}
