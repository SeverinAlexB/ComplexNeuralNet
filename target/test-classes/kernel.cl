__kernel void mMultiplication(__global const float* a, __global const float* b, __global float* c, int c_rows, int c_cols, int b_rows)
{
    int row = get_global_id(0);
    int col = get_global_id(1);

	if(row < c_rows && col < c_cols) {
		float sum = 0;
		for(int k = 0; k < b_rows; k++){
			sum += a[row*b_rows+k] * b[k*c_cols + col];
		}	
		c[row*c_cols + col] = sum;
	}

}
