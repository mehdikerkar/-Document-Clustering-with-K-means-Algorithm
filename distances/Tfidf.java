package distances;

import java.util.List;

import methodes.AfDocument;
import methodes.MotCle;

public class Tfidf {
		
	public static void main(){
		
	}

	public static double tf(List<MotCle> listMotCle, String term, int nbrMotCle) { // autre paramétre
	 double n = 0;
	 for (MotCle s: listMotCle)
	  if (s.equals(term))
	   n++;
	 return n /nbrMotCle;
	}
	
	public static double calcultf(double nbri, double nbrt) {
		 return nbri / nbrt;
		}
	static double idf(List<AfDocument> docs, String term) { //, int nbrDoc)
	 double n = 0;
	for (AfDocument x: docs)
	  for (MotCle s: x.getMotCle())
	   if (s.getMot().equals(term)) {
	    n++;
	    break;
	   }
	 return Math.log10( docs.size() / n);
	}

}
