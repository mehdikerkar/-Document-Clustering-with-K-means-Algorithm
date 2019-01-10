package distances;
	import it.uniroma1.lcl.adw.ADW;
	
	import it.uniroma1.lcl.adw.DisambiguationMethod;
			
	import it.uniroma1.lcl.adw.ItemType;
			
	import it.uniroma1.lcl.adw.comparison.SignatureComparison;
			
	import it.uniroma1.lcl.adw.comparison.WeightedOverlap;
public class adw {
					
	public static void main(String args[])
				{
					ADW pipeLine = new ADW();

	//the two lexical items
					
	String text1 = "a mill that is powered by the wind";
				
		String text2 = "windmill#n rotate#v wind#n";

		
				//types of the lexical items (set as auto-detect)
			
			ItemType text1Type = ItemType.SURFACE;
					
	ItemType text2Type = ItemType.SURFACE_TAGGED;

					
	//measure for comparing semantic signatures
					
	SignatureComparison measure = new WeightedOverlap(); 

				
	  	//calculate the similarity of text1 and text2
				
		double similarity = pipeLine.getPairSimilarity(
			
			    text1, text2,
					
	    DisambiguationMethod.ALIGNMENT_BASED, 
				
		    measure,
					   
	 text1Type, text2Type);
				    
		
				//print out the similarity
		
				System.out.println(similarity);

				}


}
