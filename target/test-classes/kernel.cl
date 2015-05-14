__kernel void mMultiplication(__global const float* a, __global const float* b, __global float* c, int c_rows, int c_cols, int b_rows)
{
    int row = get_global_id(0);
    int col = get_global_id(1);
	int blocksizex = get_local_size(0);
	int blocksizey = get_local_size(1);
	int blockidx = get_local_id(0);
	int bockidy = get_local_id(1);

	__local float[blocksizex][blocksizey] tileA;
	__local float[blocksizex][blocksizey] tileB; 

	//load tiles into local memory
	tileA[row % blocksizex][col % blocksizey] = a[


	if(row < c_rows && col < c_cols) {
		float sum = 0;
		for(int k = 0; k < b_rows; k++){
			sum += a[row*b_rows+k] * b[k*c_cols + col];
		}	
		c[row*c_cols + col] = sum;
	}

}
