import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Matrix {
    private int rowsCount;
    private int columnsCount;
    private int min;
    private int max;

    public int count = 0;

    private int[][] matrix;

    public Matrix(int rows, int columns, int min, int max) {
        this.rowsCount = rows;
        this.columnsCount = columns;
        this.min = min;
        this.max = max;

        this.matrix = new int[rows][columns];
        generateMatrix();
    }

    private void generateMatrix() {
        var rnd = new Random();

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                matrix[i][j] = rnd.ints(min, max + 1).limit(1).findFirst().getAsInt();
            }
        }
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColumnsCount() {
        return columnsCount;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public BigInteger getRowMultiply(int[] row) {
        return Arrays.stream(row).mapToObj(BigInteger::valueOf).reduce(BigInteger::multiply).get();
    }

    public BigInteger getSumRowsMultiply() {
        return Arrays.stream(matrix).map(this::getRowMultiply).reduce(BigInteger::add).get();
    }

    public long getSumRowsMultiplyAsync() {
//        return Arrays.stream(matrix).mapToLong(this::getRowMultiply).sum();
        return 0L;
    }

    @Override
    public String toString() {
        return String.join("\n\n", Arrays.stream(matrix)
                .map(row -> String.join("\t\t", Arrays.stream(row)
                        .mapToObj(String::valueOf).toArray(String[]::new))
                ).toArray(String[]::new));
    }
}
