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
    //@Test
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

        CLDevice[] devicess = new CLDevice[devices.size()];
        for(int i = 0; i < devices.size(); i++) {
            devicess[i] = devices.get(i);
        }

        CLContext context = JavaCL.createContext(null,devicess);
        System.out.println("-We are using these accelerators:");
        for (CLDevice c : context.getDevices()) {
            System.out.println("\t" + c.getName());
        }
        CLQueue queue = context.createDefaultQueue();
        ByteOrder byteOrder = context.getByteOrder();

        int n = 1024;
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
        addFloatsKernel.setArgs(a, b);

        long start = System.currentTimeMillis();
        CLEvent addEvt = addFloatsKernel.enqueueNDRange(queue, new int[]{n});
        for(int i = 0; i < 1000; i++){
            if(i%50==0) {
                addEvt.waitFor();
                System.out.println("Command " + i);
            }
            addEvt = addFloatsKernel.enqueueNDRange(queue, new int[]{n},addEvt);
        }
        addEvt.waitFor();

        long end = System.currentTimeMillis();
        Pointer<Float> outPtr = a.read(queue, addEvt); // blocks until add_floats finished

        // Print the first 10 output values :
        for (int i = 0; i < 10 && i < n; i++)
            System.out.println("out[" + i + "] = " + outPtr.get(i));

        System.out.println("Time: " + (end-start) + "ms");

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
        if(iswrong) System.out.println("Test wrong");

    }

}
