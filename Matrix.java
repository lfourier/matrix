public class Matrix {
	private int NUM_COLUMNS;
	private int NUM_ROWS;
	private int[][] MATRIX = new int[101][101];
	Matrix(int NUM_ROWS, int NUM_COLUMNS)
	{
		assert(NUM_ROWS <= 100 && NUM_COLUMNS <= 100);
		this.NUM_COLUMNS = NUM_COLUMNS;
		this.NUM_ROWS = NUM_ROWS;
	}
	void fillEntryOfMatrix(int row, int column, int value)
	{
		assert(row <= this.NUM_ROWS);
		assert(column <= this.NUM_COLUMNS);
		this.MATRIX[row][column] = value;
	}
	int getEntryOfMatrix(int row, int column)
	{
		assert(row <= this.NUM_ROWS);
		assert(column <= this.NUM_COLUMNS);
		return this.MATRIX[row][column];
	}
	Boolean isSquareMatrix()
	{
		return NUM_COLUMNS == NUM_ROWS
			 ? true : false;
	}
	Boolean isInvertibleMatrix()
	{
		return isSquareMatrix() 
			&& determinantOfMatrix() != 0 
			 ? true : false;
	}
	int traceOfMatrix()
	{
		int trace = 0;
		assert(isSquareMatrix());
		for(int i = 1; i <= NUM_COLUMNS; i++)
			trace += getEntryOfMatrix(i,i);
		return trace;
	}
	int determinantOfMatrix() //uses minors and cofactors to find determinant
	{
		int sumOfDeterminantsOfMinors = 0;
		assert isSquareMatrix();
		if(NUM_COLUMNS == 1 && NUM_ROWS == 1)
			return getEntryOfMatrix(1,1);
		else
			for(int i = 1; i <= NUM_ROWS; i++)
			{
				int cofactor = i % 2 == 1 
							?  1 : -1;
				Matrix minor = new Matrix(NUM_ROWS - 1, NUM_COLUMNS - 1);
				for(int j = 1; j <= NUM_ROWS - 1; j++)
					for(int k = 1; k <= NUM_COLUMNS - 1; k++)
						minor.fillEntryOfMatrix(j, k, getEntryOfMatrix(j + (j >= i ? 1:0), k + 1));
				sumOfDeterminantsOfMinors += minor.determinantOfMatrix() 
					* getEntryOfMatrix(i, 1) 
					* cofactor;
			}
		return sumOfDeterminantsOfMinors;
	}
	void printMatrix()
	{
		for(int i = 1; i <= NUM_ROWS; i++)
		{
			for(int j = 1; j <= NUM_COLUMNS; j++)
			{
				System.out.print(getEntryOfMatrix(i, j) + "    ");
			}
			System.out.println();
		}
	}
	static Matrix multiplyTwoMatricies(Matrix a, Matrix b)
	{
		assert(a.NUM_COLUMNS == b.NUM_ROWS);
		Matrix product = new Matrix(a.NUM_ROWS, b.NUM_COLUMNS);
		for(int i = 1; i <= a.NUM_ROWS; i++)
			for(int j = 1; j <= b.NUM_COLUMNS; j++)
			{
				int sumOfRow = 0;
				for(int k = 1; k <= a.NUM_COLUMNS; k++)
					sumOfRow += a.getEntryOfMatrix(i, k) * b.getEntryOfMatrix(k, j);
				product.fillEntryOfMatrix(i, j, sumOfRow);
			}
		return product;
	}
}
