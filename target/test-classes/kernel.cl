kernel void add_floats(__global float* a, __global const float* b)
{
    int gid = get_global_id(0);


   a[gid] = a[gid] + b[gid];



}
