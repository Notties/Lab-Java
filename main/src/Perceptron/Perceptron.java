package Perceptron;

public class Perceptron {

  private double[] weights;
  private double bias;
  private double learningRate;

  public Perceptron(int inputSize, double learningRate) {
    this.weights = new double[inputSize];
    this.bias = 0;
    this.learningRate = learningRate;

    // Initialize weights with random values between -1 and 1
    for (int i = 0; i < inputSize; i++) {
      this.weights[i] = Math.random() * 2 - 1;
    }
  }

  public static void main(String[] args) {
    // AND gate training data
    double[][] andInputs = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
    int[] andLabels = { 0, 0, 0, 1 };

    // OR gate training data
    double[][] orInputs = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
    int[] orLabels = { 0, 1, 1, 1 };

    // XOR gate training data
    double[][] xorInputs = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
    int[] xorLabels = { 0, 1, 1, 0 };

    int inputSize = 2;
    double learningRate = 0.1;
    int rounds = 100;

    // Train AND gate
    Perceptron andPerceptron = new Perceptron(inputSize, learningRate);
    andPerceptron.train(andInputs, andLabels, rounds);

    // Train OR gate
    Perceptron orPerceptron = new Perceptron(inputSize, learningRate);
    orPerceptron.train(orInputs, orLabels, rounds);

    // Train XOR gate
    Perceptron xorPerceptron = new Perceptron(inputSize, learningRate);
    xorPerceptron.train(xorInputs, xorLabels, rounds);

    // Test AND gate
    System.out.println("AND Gate");
    for (int i = 0; i < andInputs.length; i++) {
      double[] currentInputs = andInputs[i];
      int predicted = andPerceptron.activate(currentInputs);
      System.out.println(
        Double.valueOf(currentInputs[0]).intValue() + " AND " + Double.valueOf(currentInputs[1]).intValue() + " = " + predicted
      );
    }

    // Test OR gate
    System.out.println("\nOR Gate");
    for (int i = 0; i < orInputs.length; i++) {
      double[] currentInputs = orInputs[i];
      int predicted = orPerceptron.activate(currentInputs);
      System.out.println(
        Double.valueOf(currentInputs[0]).intValue() + " OR " + Double.valueOf(currentInputs[1]).intValue() + " = " + predicted
      );
    }

    // Test XOR gate
    System.out.println("\nXOR Gate");
    for (int i = 0; i < xorInputs.length; i++) {
      double[] currentInputs = xorInputs[i];
      int predicted = xorPerceptron.activate(currentInputs);
      System.out.println(
        Double.valueOf(currentInputs[0]).intValue() + " XOR " + Double.valueOf(currentInputs[1]).intValue() + " = " + predicted
      );
    }
  }

  public int activate(double[] inputs) {
    double sum = 0;
    for (int i = 0; i < inputs.length; i++) {
      sum += inputs[i] * weights[i];
    }
    sum += bias;
    return (sum >= 0) ? 1 : 0;
  }

  public void train(double[][] inputs, int[] labels, int rounds) {
    for (int round = 0; round < rounds; round++) {
      for (int i = 0; i < inputs.length; i++) {
        double[] currentInputs = inputs[i];
        int predicted = activate(currentInputs);
        int label = labels[i];
        int error = label - predicted;

        // Update weights and bias
        for (int j = 0; j < weights.length; j++) {
          weights[j] += learningRate * error * currentInputs[j];
        }
        bias += learningRate * error;
      }
    }
  }
}
