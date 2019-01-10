package methodes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import distances.DistanceCorrelation;
import distances.DistanceCosine;
import distances.DistanceEuclidian;
import distances.DistanceFunction;
import distances.DistanceManathan;
import distances.Similarity;
import ihm.HomeFrame;

import java.io.StringReader;

public class Kmeans2 {
	// Data members
	private String CenterChoix;
	private double[][] mtf;
	private TousTraitement something;
	private int[] ticket;
	private double[][] _centroids; 
	private static int nbrDoc;
	private static int nbrTerm; 
	private int nbrClusters; 
	private String distName;
	DistanceFunction dist;

	public Kmeans2(TousTraitement something, double[][] mtf, int nbrClusters, int nbrTerm, int nbrDoc, String CenterChoix, String distName) {
		this.something = something;
		this.mtf = mtf;
		this.nbrClusters = nbrClusters;
		this.nbrTerm = nbrTerm;
		this.nbrDoc = nbrDoc;
		this.CenterChoix = CenterChoix;
		this.distName = distName;
		
		if (distName.equals("Similarity")) {
			dist = new Similarity();
		} else if (distName.equals("Cos")) {
			dist = new DistanceCosine();
		} else if (distName.equals("Man")) {
			dist = new DistanceManathan();
		} else if (distName.equals("Euc")) {
			dist = new DistanceEuclidian();
		} else if (distName.equals("Correlation")) {
			dist = new DistanceCorrelation();
		}
		System.out.println("la valeur de dist est (dans kmeans2) : " + dist);
	}

	public void chooseCentroids(int numClusters, double[][] centroids) {
		nbrClusters = numClusters;
		if (centroids != null)
			_centroids = centroids;
		else {
			_centroids = new double[nbrClusters][];
			Random r = new Random();
			ArrayList idx = new ArrayList();
			for (int i = 0; i < numClusters; i++) {
				int c;
				do {
					c = r.nextInt(nbrDoc);
				} while (idx.contains(c)); // avoid duplicates
				idx.add(c);

				_centroids[i] = new double[nbrTerm];

				for (int j = 0; j < nbrTerm; j++) {
					_centroids[i][j] = mtf[c][j];
				}
			}
			System.out.println("selected random centroids\n");
		}
	}
	
	public void clustering(int numClusters, int niter, double[][] centroids) throws Exception {
		// choix des centroids de depart
		if (CenterChoix.equals("random"))
			chooseCentroids(numClusters, centroids);
		else {
			int val = (int) Double.NaN;
			if (CenterChoix.equals("first")) {
				val = 0;
			} else if (CenterChoix.equals("last")) {
				val = numClusters - 1;
				;
			}
			_centroids = new double[nbrClusters][];
			for (int ss = 0; ss < numClusters; ss++) {
				_centroids[ss] = new double[nbrTerm];
				for (int j = 0; j < nbrTerm; j++) {
					_centroids[ss][j] = mtf[Math.abs(val - ss)][j];
				}
			}
		}

		double[][] c1 = _centroids;
		double threshold = 0.001;
		int round = 0;
		while (true) {
			_centroids = c1;

			ticket = new int[nbrDoc];
			for (int i = 0; i < nbrDoc; i++) {
				ticket[i] = findClosestCluster(mtf[i]);
			}

			c1 = updateCentroids();
			round++;
			if ((niter > 0 && round >= niter) || converge(_centroids, c1, threshold))
				break;
		}
	}

	private int findClosestCluster(double[] testDoc) throws Exception {
		int index;
		int bestCluster = 0;
		double bestSimilarity = dist.calculateDistance(testDoc, _centroids[0]);
		for (index = 1; index < _centroids.length; index++) {
			double newSimilarity = dist.calculateDistance(testDoc, _centroids[index]);
			if (distName.equals("Cos") && newSimilarity > bestSimilarity) {
				bestSimilarity = newSimilarity;
				bestCluster = index;
			} else if ((distName.equals("Euc")||distName.equals("Man")) && newSimilarity < bestSimilarity){
				bestSimilarity = newSimilarity;
				bestCluster = index;
			}
		} // boucle sur les clusters
		return bestCluster;
	}

	private double[][] updateCentroids() {
		// initialize centroids and set to 0
		double[][] newc = new double[nbrClusters][]; // new centroids
		int[] counts = new int[nbrClusters]; // sizes of the clusters

		// intialize
		for (int i = 0; i < nbrClusters; i++) {
			counts[i] = 0;
			newc[i] = new double[nbrTerm];
			for (int j = 0; j < nbrTerm; j++)
				newc[i][j] = 0;
		}

		for (int i = 0; i < nbrDoc; i++) {
			int cn = ticket[i]; // the cluster membership id for record i
			for (int j = 0; j < nbrTerm; j++) {
				newc[cn][j] += mtf[i][j]; // update that centroid by adding the
											// member data record
			}
			counts[cn]++;
		}

		// finally get the average
		for (int i = 0; i < nbrClusters; i++) {
			for (int j = 0; j < nbrTerm; j++) {
				newc[i][j] /= counts[i];
			}
		}

		return newc;
	}

	// check convergence condition
	// max{dist(c1[i], c2[i]), i=1..numClusters < threshold
	private boolean converge(double[][] c1, double[][] c2, double threshold) {				
		double bestSimilarity = dist.calculateDistance(c1[0], c2[0]);
		
		for (int i = 0; i < nbrClusters; i++) {
			
			double newSimilarity = dist.calculateDistance(c1[i], c2[i]);
			if (distName.equals("Cos") && newSimilarity > bestSimilarity) {
				bestSimilarity = newSimilarity;
			} else if ((distName.equals("Euc")||distName.equals("Man")) && newSimilarity < bestSimilarity){
				bestSimilarity = newSimilarity;
			}
		}
		
		if (bestSimilarity < threshold)
			return true;
		else
			return false;

	}

	public double[][] getCentroids() {
		return _centroids;
	}

	public int[] getTicket() {
		return ticket;
	}

	public int nbrDoc() {
		return nbrDoc;
	}

	public void printResults() {
		System.out.println("Tickets:");
		for (int i = 0; i < nbrDoc; i++)
			System.out.println("doc[" + i + "] :" + ticket[i]);

		System.out.println("Centroids:");
		for (int i = 0; i < nbrClusters; i++) {
			for (int j = 0; j < nbrTerm; j++)
				System.out.print(_centroids[i][j] + " ");
			System.out.println();
		}

	}

	public static void main(String[] args) {

		Random r = new Random();
		int nbrC = 5;
		double[][] md = new double[nbrDoc][nbrDoc];
		double[][] mt = new double[nbrDoc][nbrTerm];
		float rand;
		AfDocument[] center = new AfDocument[nbrC];

		AfDocument[] datatest = new AfDocument[nbrTerm];
		// remplissage de la matrice des distances
		System.out.println("md[][] =");
		for (int i = 0; i < nbrDoc; i++) {
			for (int j = 0; j <= i; j++) {
				rand = r.nextFloat();
				if (i == j)
					md[i][j] = 0;
				else {
					md[j][i] = rand;
					md[i][j] = rand;
				}
				// System.out.print(" " + md[i][j]);
			}
			// System.out.print("\n");
		}
		///// affichage de md
		for (int i = 0; i < nbrDoc; i++) {
			for (int j = 0; j < nbrDoc; j++) {
				System.out.print("    " + md[i][j]);
				if (i == j)
					System.out.print("              ");
			}
			System.out.print("\n");
		}

		// remplisage de la matrice des tf-idf
		System.out.println("mt[][] =");
		for (int i = 0; i < nbrDoc; i++) {
			for (int j = 0; j < nbrTerm; j++) {
				rand = r.nextFloat();
				mt[i][j] = rand;
				// l'affichaeg de mtf
				System.out.print("  " + mt[i][j]);
			}
			System.out.print("\n");
		}

		System.out.println("###################################### \n");
		datatest[0] = new AfDocument(null);
		datatest[1] = new AfDocument(null);
		datatest[2] = new AfDocument(null);
		datatest[3] = new AfDocument(null);
		datatest[4] = new AfDocument(null);

		datatest[0].setNom("doc0");
		datatest[1].setNom("doc1");
		datatest[2].setNom("doc2");
		datatest[3].setNom("doc3");
		datatest[4].setNom("doc4");

		/*
		 * Kmeans2 k = new Kmeans2(mt, nbrC); double c[][] = null;
		 * k.chooseCentroids(nbrC, c); k.clustering(nbrC, 100, null);
		 * k.printResults();
		 */

		/*
		 * System.out.println("###################################### \n");
		 * 
		 * Doc d = new Doc("hakim", 15); Doc d1;
		 * 
		 * System.out.println("le nom du 1er document est : " + d.getNomDoc() +
		 * " le id est : " + d.getIdDoc());
		 * 
		 * d1 = (Doc) d.clone();
		 * 
		 * System.out.println("le nom du 1er document est : " + d1.getNomDoc() +
		 * " le id est : " + d1.getIdDoc());
		 */
	}
}