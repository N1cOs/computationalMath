package lab1;

public class GaussMethod {
    private double[][] coefficients;
    private int numEquation = 0;

    public GaussMethod(double[][] coefficients){
        this.coefficients = coefficients;
    }

    /**
     *
     * @return {@link #forwardElimination(double[][])}
     */
    public double[][] getTriangularMatrix() {
        return forwardElimination(copyCoefficients());
    }

    public double[] getResult(double[][] triangularMatrix){
        return isUniqueSolution(triangularMatrix) ? backSubstitution(triangularMatrix) : null;
    }

    public double[] getResidual(double[] result){
        return calculateResidual(result);
    }


    /**
     *
     * @param coefficients of the system of linear equations
     * @return null if coefficients are invalid, otherwise return triangular matrix
     */
    private double[][] forwardElimination(double[][] coefficients){
        int nonZero = 0;
        for(int i = numEquation; i < coefficients.length; i++){
            if(coefficients[i][numEquation] != 0){
                nonZero = i;
                break;
            }
        }
        for(int i = coefficients.length; i >= numEquation; i--){
            if(coefficients[nonZero][numEquation] == 0){
                return null;
            }
            coefficients[nonZero][i] /= coefficients[nonZero][numEquation];
        }

        if(numEquation != nonZero)
            swapLines(coefficients, nonZero, numEquation);


        for(int i = (numEquation + 1); i < coefficients.length; i++){
            for(int j = coefficients.length; j >=0; j--){
                coefficients[i][j] -= coefficients[i][numEquation] * coefficients[numEquation][j];
            }
        }
        return ++numEquation < coefficients.length - 1 ? forwardElimination(coefficients) : coefficients;
    }

    private double[] backSubstitution(double[][] coefficients){
        double[] result = new double[coefficients.length];
        for(int i = coefficients.length - 1; i >= 0; i--){
            if(i == coefficients.length - 1){
                result[i] = coefficients[i][coefficients.length] / coefficients[i][i];
            }
            else {
                double sum = 0;
                for (int j = coefficients.length - 1; j >= i; j--) {
                    sum += result[j] * coefficients[i][j];
                }
                result[i] = (coefficients[i][coefficients.length] - sum);
            }
        }
        return result;
    }

    private boolean isUniqueSolution(double[][] coefficients){
        double determinant = 1;
        for(int i = 0; i < coefficients.length; i++)
            determinant *= coefficients[i][i];
        return determinant != 0;
    }

    private double[][] copyCoefficients(){
        double[][] coefficients = new double[this.coefficients.length][this.coefficients.length + 1];
        for(int i = 0; i < coefficients.length; i++)
            coefficients[i] = this.coefficients[i].clone();
        return coefficients;
    }

    private double[] calculateResidual(double[] result){
        double[] residual = new double[result.length];
        for(int i = 0; i < residual.length; i++){
            double sum = 0;
            for(int j = 0; j < residual.length; j++){
                sum += coefficients[i][j] * result[j];
            }
            residual[i] = coefficients[i][coefficients.length] - sum;
        }
        return residual;
    }

    private void swapLines(double[][] coefficients,int nonZero, int numEquation){
        for(int i = coefficients.length; i >= numEquation; i--){
            double temp = coefficients[numEquation][i];
            coefficients[numEquation][i] = coefficients[nonZero][i];
            coefficients[nonZero][i] = temp;
        }
    }

}
