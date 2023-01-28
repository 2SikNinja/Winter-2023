import java.util.Random;

/**
 * MatrixDesign is used to create three
 * algorithms: The classic method, the divide and conquer method, and Strassen's
 * method, which tests complexity of each algorithm.
 * 
 * @author Peter Nguyen
 * 
 */
public class matrixDesign {

	/**
	 * Main will run an infinite loop create matrixes n * n increasing with 2^k and 
	 * multiply those matrices based on the three algorithms created
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		final int TIMES = 30;
		int n;
		long startTime, endTime;
		long totalTimeC = 0;
		long totalTimeDC = 0;
		long totalTimeS = 0;
		int[][] A, B;

		for (int i = 1; i > 0; i++) {
			n = (int) Math.pow(2, i);

			A = generateMatrix(n);
			B = generateMatrix(n);

			for (int j = 0; j < TIMES; j++) {
				startTime = System.nanoTime();
				classicMM(A, B, A.length);
				endTime = System.nanoTime();
				totalTimeC += endTime - startTime;

				startTime = System.nanoTime();
				divideAndConquerMM(A, B, A.length);
				endTime = System.nanoTime();
				totalTimeDC += endTime - startTime;

				startTime = System.nanoTime();
				strassenMM(A, B, A.length);
				endTime = System.nanoTime();
				totalTimeS += endTime - startTime;
			}

			totalTimeC = totalTimeC / TIMES;
			totalTimeDC = totalTimeDC / TIMES;
			totalTimeS = totalTimeS / TIMES;

			System.out
					.println("For n="
							+ n
							+ ": \n\tClassic Matrix Multiplication time: "
							+ totalTimeC
							+ " nanoseconds.\n\tDivide and Conquer Matrix Multiplication time: "
							+ totalTimeDC
							+ " nanoseconds.\n\tStrassen's Matrix Multiplication time: "
							+ totalTimeS + " nanoseconds.\n");
		}
	}

	/**
	 * This method will generates a matrix filled with random numbers
	 * from 0-100 which will then be multiplied
	 * 
	 * @param n
	 *            The size of the matrix
	 * @return array of size n x n
	 */
	public static int[][] generateMatrix(int n) {
		Random r = new Random();
		int[][] matrix = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = r.nextInt(100);
			}
		}
		return matrix;
	}

	/**
	 * This is used to display the matrix created but isn't used in the program
	 * 
	 * @param matrix
	 *            the matrix displayed
	 * @param n
	 *            the matrix's size
	 */
	public static void displayMatrix(int[][] matrix, int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.printf("%10d", matrix[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * Uses classic matrix multiplication
	 * 
	 * @param A
	 *            One matrix to be multiplied
	 * @param B
	 *            Another matrix to be multiplied
	 * @param n
	 *            the size of the matrix
	 * @return a new array C which is the result of the matrix multiplication
	 */
	public static int[][] classicMM(int[][] A, int[][] B, int n) {
		int[][] C = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = 0;
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					C[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		return C;
	}

     /**
	 * Subtracts two matrices
	 * 
	 * @param A
	 *            One matrix to be subtracted
	 * @param B
	 *            Another matrix to be subtracted
	 * @param n
	 *            the size of the matrix
	 * @return a new array C which is the result of the matrix subtraction
	 */
	private static int[][] subtractMatrix(int[][] A, int[][] B, int n) {

		int[][] C = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] - B[i][j];
			}
		}
		return C;
	}

	/**
	 * Creates a new matrix based off of part of another matrix
	 * 
	 * @param initialMatrix
	 *            the initial matrix
	 * @param newMatrix
	 *            the new matrix created from the initial matrix
	 * @param a
	 *            the initial row position of initialMatrix used when creating
	 *            newMatrix
	 * @param b
	 *            the initial column position of initialMatrix used when
	 *            creating newMatrix
	 */
	private static void removeMatrix(int[][] initialMatrix,
			int[][] newMatrix, int a, int b) {

		int y = b;
		for (int i = 0; i < newMatrix.length; i++) {
			for (int j = 0; j < newMatrix.length; j++) {
				newMatrix[i][j] = initialMatrix[a][y++];
			}
			y = b;
			a++;
		}
	}

     
	/**
	 * Will perform divide and conquer matrix multiplication by recursively
	 * calling itself on smaller matrices made up of 1/4 of the original matrix
	 * 
	 * @param A
	 *            One matrix to be multiplied
	 * @param B
	 *            Another matrix to be multiplied
	 * @param n
	 *            the size of the matrix
	 * @return a new array C which is the result of the matrix multiplication
	 */
	public static int[][] divideAndConquerMM(int[][] A, int[][] B, int n) {
		int[][] C = new int[n][n];

		if (n == 1) {
			C[0][0] = A[0][0] * B[0][0];
			return C;
		} else {
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

			removeMatrix(A, A11, 0, 0);
			removeMatrix(A, A12, 0, n / 2);
			removeMatrix(A, A21, n / 2, 0);
			removeMatrix(A, A22, n / 2, n / 2);
			removeMatrix(B, B11, 0, 0);
			removeMatrix(B, B12, 0, n / 2);
			removeMatrix(B, B21, n / 2, 0);
			removeMatrix(B, B22, n / 2, n / 2);

			int[][] C11 = addMatrix(divideAndConquerMM(A11, B11, n / 2),
					divideAndConquerMM(A12, B21, n / 2), n / 2);
			int[][] C12 = addMatrix(divideAndConquerMM(A11, B12, n / 2),
					divideAndConquerMM(A12, B22, n / 2), n / 2);
			int[][] C21 = addMatrix(divideAndConquerMM(A21, B11, n / 2),
					divideAndConquerMM(A22, B21, n / 2), n / 2);
			int[][] C22 = addMatrix(divideAndConquerMM(A21, B12, n / 2),
					divideAndConquerMM(A22, B22, n / 2), n / 2);

			createMatrix(C11, C, 0, 0);
			createMatrix(C12, C, 0, n / 2);
			createMatrix(C21, C, n / 2, 0);
			createMatrix(C22, C, n / 2, n / 2);
		}

		return C;
	}

	/**
	 * Will use the strassenMatrix method to multiply the two matrices
	 * 
	 * @param A
	 *            One matrix to be multiplied
	 * @param B
	 *            Another matrix to be multiplied
	 * @param n
	 *            the size of the matrix
	 * @return a new array C which is the result of the matrix multiplication
	 */
	public static int[][] strassenMM(int[][] A, int[][] B, int n) {
		int[][] C = new int[n][n];
		strassenMatrix(A, B, C, n);
		return C;
	}

	/**
	 * This creates 7 new matrices from Strassen's algorithm which is then used to find matrix C, the result of A*B
	 * 
	 * @param A
	 *            One matrix to be multiplied
	 * @param B
	 *            Another matrix to be multiplied
	 * @param C
	 *            the result of the matrix multiplication
	 * @param n
	 *            the size of the matrix
	 */
	public static void strassenMatrix(int[][] A, int[][] B, int[][] C, int n) {

		if (n == 2) {
			C[0][0] = (A[0][0] * B[0][0]) + (A[0][1] * B[1][0]);
			C[0][1] = (A[0][0] * B[0][1]) + (A[0][1] * B[1][1]);
			C[1][0] = (A[1][0] * B[0][0]) + (A[1][1] * B[1][0]);
			C[1][1] = (A[1][0] * B[0][1]) + (A[1][1] * B[1][1]);
		} else {
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

			int[][] P = new int[n / 2][n / 2];
			int[][] Q = new int[n / 2][n / 2];
			int[][] R = new int[n / 2][n / 2];
			int[][] S = new int[n / 2][n / 2];
			int[][] T = new int[n / 2][n / 2];
			int[][] U = new int[n / 2][n / 2];
			int[][] V = new int[n / 2][n / 2];

			removeMatrix(A, A11, 0, 0);
			removeMatrix(A, A12, 0, n / 2);
			removeMatrix(A, A21, n / 2, 0);
			removeMatrix(A, A22, n / 2, n / 2);
			removeMatrix(B, B11, 0, 0);
			removeMatrix(B, B12, 0, n / 2);
			removeMatrix(B, B21, n / 2, 0);
			removeMatrix(B, B22, n / 2, n / 2);

			strassenMatrix(addMatrix(A11, A22, n / 2),
					addMatrix(B11, B22, n / 2), P, n / 2);
			strassenMatrix(addMatrix(A21, A22, n / 2), B11, Q, n / 2);
			strassenMatrix(A11, subtractMatrix(B12, B22, n / 2), R, n / 2);
			strassenMatrix(A22, subtractMatrix(B21, B11, n / 2), S, n / 2);
			strassenMatrix(addMatrix(A11, A12, n / 2), B22, T, n / 2);
			strassenMatrix(subtractMatrix(A21, A11, n / 2),
					addMatrix(B11, B12, n / 2), U, n / 2);
			strassenMatrix(subtractMatrix(A12, A22, n / 2),
					addMatrix(B21, B22, n / 2), V, n / 2);

			int[][] C11 = addMatrix(
					subtractMatrix(addMatrix(P, S, P.length), T, T.length), V,
					V.length);
			int[][] C12 = addMatrix(R, T, R.length);
			int[][] C21 = addMatrix(Q, S, Q.length);
			int[][] C22 = addMatrix(
					subtractMatrix(addMatrix(P, R, P.length), Q, Q.length), U,
					U.length);

			createMatrix(C11, C, 0, 0);
			createMatrix(C12, C, 0, n / 2);
			createMatrix(C21, C, n / 2, 0);
			createMatrix(C22, C, n / 2, n / 2);
		}
	}

	/**
	 * Creates a new matrix based off of part of another matrix
	 * 
	 * @param initialMatrix
	 *            the initial matrix
	 * @param newMatrix
	 *            the new matrix created from the initial matrix
	 * @param a
	 *            the initial row position of initialMatrix used when creating
	 *            newMatrix
	 * @param b
	 *            the initial column position of initialMatrix used when
	 *            creating newMatrix
	 */
	private static void createMatrix(int[][] initialMatrix,
			int[][] newMatrix, int a, int b) {

		int y = b;

		for (int i = 0; i < initialMatrix.length; i++) {
			for (int j = 0; j < initialMatrix.length; j++) {
				newMatrix[a][y++] = initialMatrix[i][j];
			}
			y = b;
			a++;
		}
	}

	/**
	 * Adds two matrices together
	 * 
	 * @param A
	 *            One matrix to be added
	 * @param B
	 *            Another matrix to be added
	 * @param n
	 *            the size of the matrix
	 * @return a new array C which is the result of the matrix addition
	 */
	private static int[][] addMatrix(int[][] A, int[][] B, int n) {

		int[][] C = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] + B[i][j];
			}
		}
		return C;
	}

}