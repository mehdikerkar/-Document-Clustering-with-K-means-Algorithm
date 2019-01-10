package distances;

public class DistanceCorrelation extends DistanceFunction {

	static String NAME = "correlation";

	public double calculateDistance(double[] vector1, double[] vector2) {
		double mean1 = calculateMean(vector1);	
		double mean2 = calculateMean(vector2);	
		double standardDeviation1 = calculateStdDeviation(vector1, mean1);	
		double standardDeviation2 = calculateStdDeviation(vector2, mean2);	
		
		double correlation = 0;
		for(int i=0; i< vector1.length; i++){
			correlation -= (vector1[i] - mean1) * (vector2[i] - mean2);
		}
		// protection to avoid dividing by 0
		if(standardDeviation1 == 0) {
			standardDeviation1 = 0.0001;
		}
		// protection to avoid dividing by 0
		if(standardDeviation2 == 0) {
			standardDeviation2 = 0.0001;
		}
		double bottom = (standardDeviation1 * standardDeviation2 * (vector1.length - 1));
		correlation = correlation / (bottom );
		return (1.0 + correlation) / 2.0;
		// 0.5 0 1 1 
	}

	private static double calculateMean(double[] vector) {
		double sum = 0;
		for (double val : vector) {
			sum += val;
		}
		return sum / vector.length;
	}

	private static double calculateStdDeviation(double[] vector, double mean) {
		double deviation = 0;
		for (double val : vector) {
			deviation += Math.pow(mean - val, 2);
		}
		return Math.sqrt(deviation / (vector.length - 1));
	}
	
	public static void main(String[] args) {
		double[] array1 = new double[] {2, 3, 1, 1, 1};
		double[] array2 = new double[] {2, 1, 1, 1, 1};
		System.out.println(new DistanceCorrelation().calculateDistance(array1,array2));
		// The result should be 0.5
		
		double[] array5 = new double[] {3, 6, 0, 3, 6};
		double[] array6 = new double[] {1, 2, 0, 1, 2};
		System.out.println(new DistanceCorrelation().calculateDistance(array5,array6));
		// The result should be 0
		
		double[] array7 = new double[] {3, 6, 0, 3, 6};
		double[] array8 = new double[] {-1, -2, 0, -1, -2};
		System.out.println(new DistanceCorrelation().calculateDistance(array7,array8));
		// The result should be 1.
		
		double[] array3 = new double[] {3, -6, 0, 3, -6};
		double[] array4 = new double[] {-1, 2, 0, -1, 2};
		System.out.println(new DistanceCorrelation().calculateDistance(array3,array4));
		// result should be 1
	}
	
	@Override
	public String getName() {
		return NAME;
	}
	

}
