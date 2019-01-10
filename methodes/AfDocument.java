	package methodes;

	import java.io.BufferedReader;
	import java.io.File;
	import java.text.DecimalFormat;
	import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
	import java.util.Map;
	import java.util.Set;

	import Programme.DocumentReader;
	import Programme.WordCounter;
import Programme.WordFrequencyData;
import methodes.MotCle;
	import methodes.ExtractionInfoDoc;
	import distances.Tfidf;
	
	public class AfDocument {
		private String Nom;
		private String Titre;
		private String Text="";
		private List<HashSet<String>> MotText;
		private List<String> motAvecStem;
		private LinkedList<MotCle>motCle;
		private int NbrCar;
		private int NbrMot;
		private int NbrPhra;
		private int NbrLigne;
		private int NbrMc;
		private String Lien;
		private int idDoc = -1;
		//private BufferedReader read;
		
		public AfDocument(){
			motCle = new LinkedList<MotCle>();
		}
		
		public void removeData(int index) throws Exception
	    {
	        if ((index < 0) || (index >= motCle.size()))
	            throw new IndexOutOfBoundsException("Index " + index + " out of bounds (" + 0 + " - " + (motCle.size() - 1) + ")");
	        motCle.remove(index);
	    }
		
		public void scaleData(int index, double scaleAmt) throws Exception
	    {
	        if ((index < 0) || (index >= motCle.size()))
	            throw new IndexOutOfBoundsException("Index " + index + " out of bounds (" + 0 + " - " + (motCle.size() - 1) + ")");
	        MotCle oldData = motCle.get(index);
	        motCle.set(index, new MotCle(oldData.getMot(),
	                                               oldData.getNbrTFIDF() * scaleAmt, motCle.get(index).getNbrF()));
	    }
		
		public void addData(MotCle newData)
	    {
	        // If the list is empty, just insert
	        if (motCle == null){
	        	motCle = new LinkedList();
	        	motCle.add(newData);
	        }
	        // Otimization: Often, words will be inserted in order
	        else if (motCle.getLast().compareTo(newData) < 0){
	        	motCle.addLast(newData);
	        }
	        else {
	        	//System.out.println("check 3!" );
	            // Hunt for where the new entry belongs
	            int index = 0;
	            while ((index < motCle.size()) &&
	                   (motCle.get(index).compareTo(newData) < 0))
	                index++;
	            if (index >= motCle.size()) // Scanned the entire list
	            	motCle.addLast(newData);
	            else if (motCle.get(index).compareTo(newData) == 0)
	                // Overwrite
	            	motCle.set(index, newData);
	            else
	                // Have data just beyond wanted position. Insert it here
	            	motCle.add(index, newData);
	        }
	    }

		public <File> AfDocument(String Lien){
			this.Lien=Lien;
			Nom=null;
			Titre=null;
			Text=null;
			MotText=null;
			motCle=null;
			NbrCar=0;
			NbrMot=0;
			NbrPhra=0;
			NbrLigne=0;
			idDoc = idDoc + 1;
		/*	try {
				FileReader in=new FileReader(Lien);
				read = new BufferedReader(in);
				String line=read.readLine();
				Titre=line;
				while(line!=null){
					Text+=line+"\n";NbrLigne++;
					for(int i=0 ;i<line.length();i++){
					if(line.charAt(i)==' ')
						NbrMot++;
					else if(line.charAt(i)=='.')
						NbrPhra++;
					else NbrCar++;
					}
					line=read.readLine();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}
		
	              //////----------------Les Setters---------------------------///////
		
		public void setMotText(List<HashSet<String>> motText) {
			MotText = motText;
		}
		public void setMotAvecStem(ArrayList<String> motAvecStem) {
			this.motAvecStem = motAvecStem;
		}
		public void setNom(String nom) {
			Nom = nom;
		}
		public void setTitre(String titre) {
			Titre = titre;
		}
		public void setText(String text) {
			Text = text;
		}
		public void setNbrCar(int nbrCar) {
			NbrCar = nbrCar;
		}
		public void setNbrMot(int nbrMot) {
			NbrMot = nbrMot;
		}
		public void setNbrPhra(int nbrPhra) {
			NbrPhra = nbrPhra;
		}
		public void setNbrLigne(int nbrLigne) {
			NbrLigne = nbrLigne;
		}
		public void setLien(String lien) {
			Lien = lien;
		}
		public void setMotCle(LinkedList<MotCle> mc) {
			this.motCle = mc;
		}
		public void setNbrMc(int nbrMc) {
			this.NbrMc = nbrMc;
		}
		public void setIdDoc(int idDoc) {
			this.idDoc = idDoc;
		}
                   //////----------------Les Getters---------------------------///////
		
		public List<String> getMotAvecStem() {
			return motAvecStem;
		}
		public int getIdDoc() {
			return idDoc;
		}
		public int getNbrMc() {
			return NbrMc;
		}
		public String getNom() {
			return Nom;
		}
		public String getTitre() {
			return Titre;	
		}
		public int getNbrCar() {
			return NbrCar;
		}
		public int getNbrMot() {
			return NbrMot;
		}
		public int getNbrPhra() {
			return NbrPhra;
		}
		public String getLien() {
			return Lien;
		}
		public String  getText (){
			return Text;
		}
		public int getNbrLigne() {
			return NbrLigne;
		}
		public List<HashSet<String>> getMotText() {
			return MotText;
		}
		public List<MotCle> getMotCle() {
			return motCle;
		}
	/*	
		Arrays.toString(monTableauADeuxDimensions);
		*/
		public static void main(String argv[]) throws Exception{
			
			ArrayList<String> doc = new ArrayList<String>();
			AfDocument doc1 = new AfDocument("D:\\eclipse\\workspace\\DocumentCluster-master\\src\\data\\009t.txt");
			doc.add("mine1.txt");
			AfDocument doc2 = new AfDocument("D:\\eclipse\\workspace\\DocumentCluster-master\\src\\data\\110t.txt");
			doc.add("110t.txt");
			AfDocument doc3 = new AfDocument("D:\\eclipse\\workspace\\DocumentCluster-master\\src\\data\\111p.txt");
			doc.add("111p.txt");
			//AfDocument alph = new AfDocument("C:/Users/Mehdi/workspace/Project test [Tous ou Rien]/Module/Fr/Alphabet.txt");
			//AfDocument stopMot = new AfDocument("C:\\Users\\Mehdi\\workspace\\Clustering_memoire\\Module\\Fr\\Stop-Mot.txt");
			
			List<AfDocument> listdoc =  new ArrayList<AfDocument>();
			listdoc.add(0, doc1);
			listdoc.add(1, doc2);
			listdoc.add(2, doc3);
			
			DocumentReader d = new DocumentReader("en");
			int i=0;
			while(i<listdoc.size())
			{
				//ExtractionInfoDoc.EtractInformation(listdoc.get(i), alph.getLien(), stopMot.getLien());
				Set<Map.Entry<String, WordCounter>> wordCounts = d.getWordFrequencies(doc.get(i)).entrySet();
				System.out.println("######################### doc"+i+" #########################");
				System.out.println("Nom : "+doc.get(i));
				System.out.println(wordCounts);
				/*
				//System.out.println("Lien : "+listdoc.get(i).getLien());
				System.out.println("Le resume : "+listdoc.get(i).getMotText());
				System.out.println("Les mots cles : \n"+listdoc.get(i).getMotCle());
				//System.out.println("Nombre de lignes : "+listdoc.get(i).getNbrLigne());
				//System.out.println("Nombre de caractéres : "+listdoc.get(i).getNbrCar());
				System.out.println("Nombre de  tout les mots : "+listdoc.get(i).getNbrMot() );
				System.out.println("Nombre des mots cle sans percentile : "+listdoc.get(i).getNbrMc()+"\n\n");
				//System.out.println("Nombre de phrases : "+listdoc.get(i).getNbrPhra());
				*/  i++;
			}
		}
	}


