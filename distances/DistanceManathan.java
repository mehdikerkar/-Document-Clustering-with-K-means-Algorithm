package distances;

public class DistanceManathan extends DistanceFunction {

	static String NAME = "manathan";

	public double calculateDistance(double[] vector1, double[] vector2) {
		double sum =0;	
		for(int i=0; i< vector1.length; i++){
			sum += Math.abs(vector1[i] - vector2[i]);
		}
		return sum;
	}
	
	@Override
	public String getName() {
		return NAME;
	}
	
	public static void main(String[] args) {
		double[] array1 = new double[] {0,2};
		double[] array2 = new double[] {2,0};
		System.out.println(new DistanceManathan().calculateDistance(array1,array2));
	}
	

}
