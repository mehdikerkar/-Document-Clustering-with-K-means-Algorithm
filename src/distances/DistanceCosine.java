package distances;

public class DistanceCosine extends DistanceFunction {

	static String NAME = "cosine";
	
	public double calculateDistance(double[] vector1, double[] vector2) {
		double dotproduct = 0;	
		double norm1 = 0;
		double norm2 = 0;
		for(int i=0; i< vector1.length; i++){
			dotproduct += vector1[i] * vector2[i];
			norm1 += Math.pow(vector1[i],2);
			norm2 += Math.pow(vector2[i],2);
		}
		if(norm1 == 0 || norm2 == 0) {
			return 0;
		}
		return dotproduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
	}
	
	public static void main(String[] args) {
		double[] array1 = new double[] {3, 2, 0,5, 0, 0, 0, 2, 0, 0};
		double[] array2 = new double[] {1, 0, 0, 0, 0, 0, 0, 1, 0, 2};
		System.out.println(new DistanceCosine().calculateDistance(array1,array2));
		// The result should be 1 - 0.3150
		
		double[] array4 = new double[] {0, 0};
		double[] array3 = new double[] {0, 0};
		System.out.println(new DistanceCosine().calculateDistance(array3,array4));
	}
	
	@Override
	public String getName() {
		return NAME;
	}
	

}
