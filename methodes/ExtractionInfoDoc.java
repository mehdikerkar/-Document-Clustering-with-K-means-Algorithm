package methodes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.Doc;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.frenchStemmer;

import Programme.DocumentReader;
import Programme.WordCounter;
import distances.Pourcentage;
import methodes.AfDocument;
import methodes.MotCle;

public class ExtractionInfoDoc {
	// je vais l'utiliser pour savoir comment manipuler snowball dans une autre class
	public static List<String> stem(List<String> l){
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < l.size(); i++){
			SnowballStemmer stemmer = new frenchStemmer();
			stemmer.setCurrent(l.get(i));
		    for (int j = 1; j != 0; j--) {
		    	stemmer.stem();
		    }
		    result.add(stemmer.getCurrent());
		}
		return result;
	}
	
	private static int calculNbrMc(List<MotCle> l){
		List<MotCle> lp=new ArrayList<MotCle>();
		int nbrMc = 0;
		for (int i = 0; i < l.size(); i++) 
		{	int count=1;
			if(l.get(i).getMot()!=null)
			{
				nbrMc = (int) (nbrMc +l.get(i).getNbrF());
			}
		}
		System.out.println("le nombre Mot cle :"+nbrMc);
		return nbrMc;
	}
	
	private static String NomDocument(String Lien){
		File f=new File(Lien);
		return f.getName();
	}
	
	private static  String TitreDocument(String Lien){
		File f=new File(Lien);
		FileReader in;
		BufferedReader buf;
		String line=null;
		
		try {
			in=new FileReader(f);
			buf=new BufferedReader(in);
			line=buf.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	
	private static int NbrCar(String Lien){
		File f=new File(Lien);
		FileReader in;
		BufferedReader buf;
		@SuppressWarnings("unused")
		int ch=0;
		int i=0;
		
		try {
			in=new FileReader(f);
			buf=new BufferedReader(in);
			while ((ch=buf.read())!=-1) {
			 i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	private static int NbrPhra(String Lien){
		File f=new File(Lien);
		FileReader in;
		BufferedReader buf;
		int ch=0;
		int i=0;
		
		try {
			in=new FileReader(f);
			buf=new BufferedReader(in);
			while ((ch=buf.read())!=-1) {
			 if((char)ch=='.')i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	private static int NbrMot(String Lien){
		File f=new File(Lien);
		FileReader in;
		BufferedReader buf;
		int ch=0;
		int i=0;
		
		try {
			in=new FileReader(f);
			buf=new BufferedReader(in);
			while ((ch=buf.read())!=-1) {
				 if((char)ch==' ' || (char)ch==',' || (char)ch=='.' )i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	private static String AffText( String Lien){
		FileReader in;
		String text=null;
		BufferedReader buf;
		
		try {
			in=new FileReader(Lien);
			buf = new BufferedReader(in);
			String line=buf.readLine();
			while(line!=null){
				text+=line+"\n";
				line=buf.readLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;	
	}
	
	private static int NbrLigne( String Lien){
		FileReader in;
		int nbrline=0;
		BufferedReader buf;
		
		try {
			in=new FileReader(Lien);
			buf = new BufferedReader(in);
			String line=buf.readLine();
			while(line!=null){
				nbrline++;
				line=buf.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nbrline;	
	}
	
	public static AfDocument  EtractInfoSimple (String Lien){
		AfDocument doc = new AfDocument(Lien);
		doc.setNom(NomDocument(Lien));
		doc.setTitre(TitreDocument(Lien));
		doc.setText(AffText(Lien));
		doc.setNbrLigne(NbrLigne(Lien));
		doc.setNbrCar(NbrCar(Lien));
		doc.setNbrPhra(NbrPhra(Lien));
		doc.setNbrMot(NbrMot(Lien));
		return doc;
	}
	
	public static void main (String[] args){
		HashMap<String, WordCounter> map = new HashMap<String, WordCounter>();
		WordCounter s = new WordCounter();
		map.put("value 1", s);
		s.increment();
		map.put("value 2", s); 
		s.increment();
		map.put("value 3", s);
        Set<Map.Entry<String, WordCounter>> t = map.entrySet();
        Iterator<Map.Entry<String, WordCounter>> iter = t.iterator();
        //while(iter.hasNext()){
        	System.out.println("the value 1 : " + iter.next().getKey());
        	System.out.println("the value 2 : " + iter.next().getKey());
        //}
	}
}
