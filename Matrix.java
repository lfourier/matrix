public class Matrix {
	private int NUM_COLUMNS;
	private int NUM_ROWS;
	private double[][] MATRIX = new double[1000][1000];
	Matrix(int NUM_ROWS, int NUM_COLUMNS)
	{
		assert(NUM_ROWS <= 100 && NUM_COLUMNS <= 100);
		this.NUM_COLUMNS = NUM_COLUMNS;
		this.NUM_ROWS = NUM_ROWS;
	}
	void fillEntry(int row, int column, double value)
	{
		assert(row <= this.NUM_ROWS);
		assert(column <= this.NUM_COLUMNS);
		this.MATRIX[row][column] = value;
	}
	double getEntry(int row, int column)
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
			&& determinant() != 0 
			 ? true : false;
	}
	static int roundToInt(double num)
	{
		double fracpart = num - Math.floor(num);
		return (int) (Math.floor(num) + (fracpart < 0.5 ? 0 : 1));
	}
	static double roundToDecimal(double num)
	{
		return (double) (roundToInt(num * 100))/100;
	}
	double trace()
	{
		double trace = 0;
		assert(isSquareMatrix());
		for(int i = 1; i <= NUM_COLUMNS; i++)
			trace += getEntry(i,i);
		return trace;
	}
	double determinant() //uses minors and cofactors to find determinant
	{
		double sumOfDeterminantsOfMinors = 0;
		assert isSquareMatrix();
		if(NUM_COLUMNS == 1 && NUM_ROWS == 1)
			return getEntry(1,1);
		else
			for(int i = 1; i <= NUM_ROWS; i++)
			{
				int cofactor = i % 2 == 1 
							?  1 : -1;
				Matrix minor = new Matrix(NUM_ROWS - 1, NUM_COLUMNS - 1);
				for(int j = 1; j <= NUM_ROWS - 1; j++)
					for(int k = 1; k <= NUM_COLUMNS - 1; k++)
						minor.fillEntry(j, k, getEntry(j + (j >= i ? 1:0), k + 1));
				sumOfDeterminantsOfMinors += minor.determinant() 
					* getEntry(i, 1) 
					* cofactor;
			}
		return sumOfDeterminantsOfMinors;
	}
	void print()
	{
		for(int i = 1; i <= NUM_ROWS; i++)
		{
			for(int j = 1; j <= NUM_COLUMNS; j++)
			{
				System.out.print(roundToDecimal(getEntry(i, j)) + "    ");
			}
			System.out.println();
		}
		System.out.println();
	}
	void scalarMultiply(double scalar)
	{
		for(int i = 1; i <= NUM_ROWS; i++)
			for(int j = 1; j <= NUM_COLUMNS; j++)
				fillEntry(i, j, getEntry(i, j) * scalar);
	}
	Matrix transposeMatrix()
	{
		Matrix transposeMatrix = new Matrix(NUM_COLUMNS, NUM_ROWS);
		for(int i = 1; i <= NUM_ROWS; i++)
			for(int j =  1; j <= NUM_COLUMNS; j++)
				transposeMatrix.fillEntry(j, i, getEntry(i, j));
		return transposeMatrix;
	}
	Matrix cofactorMatrix()
	{
		assert(isSquareMatrix());
		Matrix cofactorMatrix = new Matrix(NUM_ROWS, NUM_COLUMNS);
		Matrix minor = new Matrix(NUM_ROWS - 1, NUM_COLUMNS - 1);
		for(int i = 1; i <= NUM_ROWS; i++)
		{
			for(int j = 1; j <= NUM_COLUMNS; j++)
			{
				int cofactor = (i + j) % 2 == 0 
							 ? 1 : -1;
				for(int minor_i = 1; minor_i <= NUM_ROWS - 1; minor_i++)
				{
					for(int minor_j = 1; minor_j <= NUM_COLUMNS - 1; minor_j++)
					{
						minor.fillEntry(minor_i, minor_j, getEntry
								(minor_i + ((i <= minor_i) ? 1 : 0), 
								 minor_j + ((j <= minor_j) ? 1 : 0)));
					}
				}
				cofactorMatrix.fillEntry(i, j, cofactor * minor.determinant());
			}
		}
		return cofactorMatrix;
	}
	Matrix adjointMatrix()
	{
		return cofactorMatrix().transposeMatrix();
	}
	Matrix inverseMatrix()
	{
		Matrix adjoint = adjointMatrix();
		adjoint.scalarMultiply(1/determinant());
		return adjoint;
	}
	static Matrix multiplyTwoMatricies(Matrix a, Matrix b)
	{
		assert(a.NUM_COLUMNS == b.NUM_ROWS);
		Matrix product = new Matrix(a.NUM_ROWS, b.NUM_COLUMNS);
		for(int i = 1; i <= a.NUM_ROWS; i++)
			for(int j = 1; j <= b.NUM_COLUMNS; j++)
			{
				double sumOfRow = 0;
				for(int k = 1; k <= a.NUM_COLUMNS; k++)
					sumOfRow += a.getEntry(i, k) * b.getEntry(k, j);
				product.fillEntry(i, j, sumOfRow);
			}
		return product;
	}
}
