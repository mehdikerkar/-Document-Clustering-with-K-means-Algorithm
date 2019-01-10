package methodes;

import java.io.File;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.synth.SynthSeparatorUI;

import Programme.DocumentReader;
import Programme.WordCounter;

public class Corpus {

	private Set<String> Terms;
	private ArrayList<AfDocument> ListDoc;
	private double[][] MTf;

	/*public Corpus(ArrayList<AfDocument> docMapping) throws Exception {
		ListDoc = docMapping;
		DocumentReader reader = new DocumentReader("En");
		int index = 0;
		while (index < ListDoc.size()) {
			Set<Map.Entry<String, WordCounter>> wordCounts = reader.getWordFrequencies(ListDoc.get(index).getLien())
					.entrySet();
			Iterator<Map.Entry<String, WordCounter>> iter = wordCounts.iterator();

			LinkedList<MotCle> tempResults = new LinkedList<MotCle>();
			while (iter.hasNext()) {
				Map.Entry<String, WordCounter> rawValue = iter.next();
				// double termFrequency = 0.5 +
				// (((double)rawValue.getValue().getCount() * 0.5) /
				// (double)highestCount);
				tempResults.add(new MotCle(rawValue.getKey(), rawValue.getValue().getCount(), 0));// termFrequency));
			}
			ListDoc.get(index).setMotText(reader.getMotSansStem());
			ListDoc.get(index).setMotAvecStem(reader.getMotAvecStem());
			ListDoc.get(index).setMotCle(tempResults);
			ListDoc.get(index).setNbrMc(tempResults.size());
			index++;
		}

		Terms = new HashSet<String>();
		for (int i = 0; i < ListDoc.size(); i++) {
			if (ListDoc.get(i).getMotCle() != null) {
				List<MotCle> d = ListDoc.get(i).getMotCle();
				for (int j = 0; j < d.size(); j++) {
					Terms.add(d.get(j).getMot());
				}
			}
		}
	}*/

	public Corpus(ArrayList<AfDocument> docMapping, double minSignificantValue, String langChoix) throws Exception {

		 if (docMapping.size() < 2)
			 throw new InvalidParameterException("Files to analyze word frequencies invalid, At least two must be specified");

		int index = 0;
		DocumentReader reader = new DocumentReader(langChoix);
		while (index < docMapping.size()) {
			try {
				Set<Map.Entry<String, WordCounter>> wordCounts = reader.getWordFrequencies(docMapping.get(index).getLien())
						.entrySet();
				if (!wordCounts.isEmpty()) {
					LinkedList<MotCle> tempResults = new LinkedList<MotCle>();

					int highestCount = 0;
					Iterator<Map.Entry<String, WordCounter>> count = wordCounts.iterator();
					while (count.hasNext()) {
						int currCount = count.next().getValue().getCount();
						if (currCount > highestCount)
							highestCount = currCount;
					} 
					
					count = wordCounts.iterator();
					while (count.hasNext()) {
						Map.Entry<String, WordCounter> rawValue = count.next();
						/*double termFrequency = 0.5
								+ (((double) rawValue.getValue().getCount() * 0.5) / (double) highestCount);
						*/tempResults.add(new MotCle(rawValue.getKey(), rawValue.getValue().getCount(), rawValue.getValue().getCount())); //Term frequency
					}
					Collections.sort(tempResults);
					
					Iterator<MotCle> tempIndex = tempResults.iterator();
					while (tempIndex.hasNext()) {
						docMapping.get(index).addData(tempIndex.next());
					}
				} 
			} 
			catch (Exception e) {
				System.out.println("File " + docMapping.get(index).getNom() + " ignored due to error: " + e);
				// results = null;
			}

			docMapping.get(index).setMotText(reader.getMotSansStem());
			docMapping.get(index).setMotAvecStem(reader.getMotAvecStem());
			docMapping.get(index).setNbrMc(docMapping.get(index).getMotCle().size());
			if (docMapping.get(index) != null) {
				index++; 
			}
			// else
			// --- ListDoc.get(index).remove(index);
		}
		DocMapIterator wordIndex = new DocMapIterator(docMapping);
		while (wordIndex.hasNext()) {
			ArrayList<FrequenceParDoc> wordData = wordIndex.nextWord();   //   valTfIdf = motCle().getTf()
			double invDocFreq = Math.log(((double) docMapping.size()) / ((double) wordData.size()));
			/*
			 * Find the average TF-IDF value for the word. The TF values are in
			 * the current word data, giving an average TF value, which
			 * multiplied by the IDF gives the average TF-IDF. NOTE: In therory,
			 * it makes no difference whether the average TF value is multiplied
			 * by the IDF value, or the individual TF-IDF values are found and
			 * averaged. In practice, it could make a difference due to
			 * rounding. This code assumes that the rounding error is small
			 * enough compared to the double precision to not worry about it
			 */
			double avgTFIDF = 0.0;
			Iterator<FrequenceParDoc> wordTF = wordData.iterator();
			while (wordTF.hasNext())
				avgTFIDF += wordTF.next().getFrequency();
			avgTFIDF /= (double) wordData.size();
			avgTFIDF *= invDocFreq;

			if (avgTFIDF < minSignificantValue)
				wordIndex.deleteProcessedWord();
			else
				wordIndex.scaleProcessedWord(invDocFreq);
		}
		
		ListDoc = docMapping;

		Terms = new HashSet<String>();
		for (int i = 0; i < ListDoc.size(); i++) {
			if (ListDoc.get(i).getMotCle() != null) {
				List<MotCle> d = ListDoc.get(i).getMotCle();
				for (int j = 0; j < d.size(); j++) {
					Terms.add(d.get(j).getMot());
				}
			}
		}
	}

	public void calculer(Corpus c) throws Exception {
		DistancesMethodes dist = new DistancesMethodes(c);
		MTf = dist.distanceChoix("tf-idf");
	}

	public Set<String> getTerms() {
		return Terms;
	}

	public int getTermsSize() {
		return Terms.size();
	}

	public void setTerms(HashSet<String> terms) {
		Terms = terms;
	}

	public ArrayList<AfDocument> getListDoc() {
		return ListDoc;
	}

	public int getListDocSize() {
		return ListDoc.size();
	}
	
	public double[][] getMTf() {
		return MTf;
	}

	public void setListDoc(List<AfDocument> listDoc) {
		ListDoc = (ArrayList<AfDocument>) listDoc;
	}
}
