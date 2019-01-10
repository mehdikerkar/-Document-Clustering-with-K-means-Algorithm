package distances;

public abstract class DistanceFunction {
	
	public abstract String getName();
	
	public abstract double calculateDistance(double[] vector1, double[] vector2);
	
	public double[] normalize(double[] that) throws Exception {
		double similarity = calculateDistance(that, that);
		similarity = Math.sqrt(similarity);
		similarity = 1.0 / similarity;
		int index;
		for (index = 0; index < that.length; index++){
			that[index] *= similarity;
		}
		return that;
	}

}
