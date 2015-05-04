__kernel void add_floats(__global float* a, __global const float* b, const int n)
{
    int gid = get_global_id(0);
    int size = get_global_size(0);
    if(gid < size) {
        a[gid] = a[gid] + b[gid];
    }

}
