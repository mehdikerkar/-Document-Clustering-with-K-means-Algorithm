package distances;

public class Similarity extends DistanceFunction{
	static String NAME = "Similarity";
	
	@Override
	public double calculateDistance(double[] other, double[] that) {
		int index1 = 0;
		int index2 = 0;
		double result = 0.0;
		while ((index1 < that.length) && (index2 < other.length)) {
			result += (that[index1] * other[index2]);
			index1++;
			index2++;
		}
		return result;
	}

	@Override
	public String getName() {
		return null;
	}
}
