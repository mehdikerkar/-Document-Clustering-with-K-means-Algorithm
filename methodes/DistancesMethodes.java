package methodes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.plaf.synth.SynthSeparatorUI;

import distances.*;

public class DistancesMethodes {

	private Set<String> Terms;
	private ArrayList<AfDocument> ListDoc;
	private static double MTf[][];
	private DistanceFunction distAll;
	

	public DistancesMethodes(Corpus c) throws InterruptedException {
		ListDoc = c.getListDoc();
		Terms = c.getTerms();
		MTf = c.getMTf();
	}
	
	public double[][] distanceChoix(String distName) throws Exception {
		if (distName.equals("Similarity")) {
			distAll = new Similarity();
			return distSimilarity(MTf);
		} else if (distName.equals("Cos")) {
			System.out.println("je suis dans Cosinus");
			distAll = new DistanceCosine();
			return distCosinus(MTf);
		} else if (distName.equals("Man")) {
			System.out.println("je suis dans Manhatan");
			distAll = new DistanceManathan();
			return distManhatan(MTf);
		} else if (distName.equals("Euc")) {
			System.out.println("je suis dans Euclide");
			distAll = new DistanceEuclidian();
			return distEuclidienne(MTf);
		} else if (distName.equals("Correlation")) {
			distAll = new DistanceCorrelation();
			return distCorrelation(MTf);
		} else if (distName.equals("tf-idf")) {
			return calc();
		} else
			return null;
	}
	
	public double[][] distSimilarity(double[][] tab) throws Exception {
		double Tab2[][] = new double [ListDoc.size()][ListDoc.size()];
		
		for (int k = 0; k < ListDoc.size(); k++) {
			tab[k] = distAll.normalize(tab[k]);
		}
		//Thread.sleep(30000);
		for (int i = 0; i < ListDoc.size(); i++) {
			for (int k = 0; k < ListDoc.size(); k++) {
				Tab2[i][k] = distAll.calculateDistance(tab[i], tab[k]);
			}
		}
		return Tab2;
	}
	
	public double[][] distManhatan(double[][] tab) {
		double Tab2[][] = new double [ListDoc.size()][ListDoc.size()];
		
		for (int i = 0; i < ListDoc.size(); i++) {
			for (int k = 0; k < ListDoc.size(); k++) {
				Tab2[i][k] = distAll.calculateDistance(tab[i], tab[k]);
			}
		}
		return Tab2;
	}
	
	public double[][] distCorrelation(double[][] tab) {
		double Tab2[][] = new double [ListDoc.size()][ListDoc.size()];
		
		for (int i = 0; i < ListDoc.size(); i++) {
			for (int k = 0; k < ListDoc.size(); k++) {
				Tab2[i][k] = distAll.calculateDistance(tab[i], tab[k]);
			}
		}
		return Tab2;
	}
	
	public double[][] distEuclidienne(double[][] tab) {
		double Tab2[][] = new double [ListDoc.size()][ListDoc.size()];
		for (int i = 0; i < ListDoc.size(); i++) {
			for (int k = 0; k < ListDoc.size(); k++) {
				Tab2[i][k] = distAll.calculateDistance(tab[i], tab[k]);
			}
		}
		return Tab2;
	}
	
	public double[][] distCosinus(double[][] tab) {
		double Tab2[][] = new double [ListDoc.size()][ListDoc.size()];
		
		for (int i = 0; i < ListDoc.size(); i++) {
			for (int k = 0; k < ListDoc.size(); k++) {
				Tab2[i][k] = distAll.calculateDistance(tab[i], tab[k]);
			}
		}
		return Tab2;
	}

	public double[][] calc() throws Exception {
		double[][] tab = new double[ListDoc.size()][Terms.size()];
		DocMapIterator wordIndex = new DocMapIterator(ListDoc);
		int i = 0, j = 0;
		while (wordIndex.hasNext()) {
			ArrayList<FrequenceParDoc> wordData = wordIndex.nextWord();
			Iterator<FrequenceParDoc> wordTF = wordData.iterator();
			while (wordTF.hasNext()) {
				FrequenceParDoc  wordTemp = wordTF.next();
				tab[wordTemp.getDocument()][i] = wordTemp.getFrequency();
				j++;
			}
			i++;
		}
		return tab;
	}

	/*public double[][] calcMTf() throws InterruptedException {
		double Tab[][] = new double[ListDoc.size()][Terms.size()];
		
		Iterator<String> it = Terms.iterator();
		int i = 0, ind = 0;
		System.out.println("in distancesMethod.calcMtf avant boucle term");
		while (it.hasNext()) {
			String str = it.next();
			for (int j = 0; j < ListDoc.size(); j++) {
				ind = containe(str, ListDoc.get(j));
				if (ind != -1) {
					Tab[j][i] = ListDoc.get(j).getMotCle().get(ind).getNbrTFIDF();
				}
			}
			i++;
		}
		System.out.println("in distancesMethod.calcMTf apres boucle term");
		return Tab;
	}

	public double[][] Inverse() {
		double Tab[][] = new double[ListDoc.size()][Terms.size()];
		double N = ListDoc.size();

		DecimalFormat df = new DecimalFormat("#,######");
		Iterator<String> it = Terms.iterator();
		int k = 0;
		int count;
		while (it.hasNext()) {
			String str = it.next();
			count = 0;
			for (int j = 0; j < ListDoc.size(); j++) {
				if (containe(str, ListDoc.get(j)) != -1) {
					count++;
				}
			}
			double idf = N / (double) count;
			for (int i = 0; i < ListDoc.size(); i++) {
				Tab[i][k] = Math.log10(idf);
			}
			k++;
		}
		return Tab;
	}

	public double[][] Tf_Idf(double[][] t) {
		double Tab[][] = new double[ListDoc.size()][Terms.size()];
		Iterator<String> it = Terms.iterator();
		int k = 0;
		while (it.hasNext()) {
			String str = it.next();
			for (int j = 0; j < ListDoc.size(); j++) {
				Tab[j][k] = (t[j][k] * occurance(str, ListDoc.get(j)));
			}
			k++;
		}
		return Tab;
	}
	
*/
	public double[][] matriceOccurence() {
		double Tab[][] = new double[ListDoc.size()][Terms.size()];
		Iterator<String> it = Terms.iterator();
		int k = 0;
		System.out.println("\n\n la matrice d'occurence !");
		while (it.hasNext()) {
			String str = it.next();
			for (int j = 0; j < ListDoc.size(); j++) {
				Tab[j][k] = occurance(str, ListDoc.get(j));
				System.out.print(Tab[j][k] + "  ");
			}
			System.out.print("\n");
			k++;
		}
		return Tab;
	}

	/*public int containe(String str, AfDocument d) {
		List<MotCle> l = d.getMotCle();
		boolean ok = false;
		int i = 0;
		ok = (l.get(i).getMot().equals(str));
		while (i < l.size()-1 && !ok) {
			i++;
			ok = (l.get(i).getMot().equals(str));
		}
		if(ok)
			return l.get(i).getMot().indexOf(str);
		else
			return -1;
	}
	*/

	public double occurance(String str, AfDocument d) {
		double r = 0;
		List<MotCle> l = d.getMotCle();

		for (int i = 0; i < l.size(); i++) {

			if (l.get(i).getMot().equals(str))
				r = (l.get(i).getNbrTFIDF()); // Terms.size()
		}
		return r;
	}

}
