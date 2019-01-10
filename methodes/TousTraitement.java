package methodes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class TousTraitement {
	
	private ArrayList<String> docNom;

    private ArrayList<AfDocument> docMapping;
    
    Corpus docCorp;
    
    boolean advancedExtraDone = false;
	
	public TousTraitement(ArrayList<String> docNom) throws Exception{
		this.docNom = docNom;
		int i = 0;
		i = 0;
	}
	
	public void simpleExtraction(){
		int i = 0;
		ArrayList<AfDocument> docMappingTemp = new ArrayList<AfDocument>(docNom.size());
		while(i < docNom.size()){
			docMappingTemp.add(ExtractionInfoDoc.EtractInfoSimple(docNom.get(i)));
			i++;
		}
		docMapping = docMappingTemp;
		advancedExtraDone = false;
	}
	
	public void advancedExtraction(double minSignificantValue, String langChoix) throws Exception{
		if(!advancedExtraDone){
			docCorp = new Corpus(docMapping, minSignificantValue, langChoix);
			docMapping = docCorp.getListDoc();
			docCorp.calculer(docCorp);
			advancedExtraDone = true;
		}
	}
	
	public ArrayList<AfDocument> getdocMapping(){
		return docMapping;
	}
	
	public Corpus getTermMatrix(){
		return docCorp;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
