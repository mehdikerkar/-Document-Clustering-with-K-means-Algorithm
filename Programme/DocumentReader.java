package Programme;
import java.io.*;
import java.util.*;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.frenchStemmer;

public final class DocumentReader
{

    private BufferedReader _file;
    private Stopwords _stopwords;
    private PorterStemmer stemmerEn = null;
    private SnowballStemmer stemmerFr = null;
    private ArrayList<HashSet<String>> motSansStem = new ArrayList<HashSet<String>>();
    private ArrayList<String> motAvecStem = new ArrayList();
    
    public DocumentReader(String langChoix) throws Exception
    {
        _stopwords = Stopwords.getStopWords(langChoix);
        if(langChoix.equals("En"))
        	stemmerEn = PorterStemmer.getStemmer();
        else if(langChoix.equals("Fr"))
        	stemmerFr = new frenchStemmer();
    }

    public HashMap<String, WordCounter> getWordFrequencies(String fileName) throws Exception
    {
        HashMap<String, WordCounter> results = new HashMap<String, WordCounter>();
        try {
            //String directory = new String("D:\\eclipse\\workspace\\DocumentCluster-master\\src\\data");
            String fullName =/*directory +"\\"+*/ fileName ;
            _file = new BufferedReader(new FileReader(fullName));
            if (_file == null) {
                // Failed to open. 
                System.out.println("ERROR: File to analyze " + fullName + " not found.");
                throw new IOException("ERROR: File to analyze " + fullName + " not found.");
            }
            String buffer = _file.readLine();
            String hyphenate = null;
            boolean haveHyphenate = false;
            while (buffer != null) {
                haveHyphenate = buffer.matches(".*[A-Za-z]-$");

                buffer = buffer.replaceAll("[^A-Za-z -]", " ").trim();
                buffer = buffer.replaceAll("[ ]-+[ ]"," ").trim().replaceAll(" +", " ");
                buffer = buffer.replaceAll("-+[ ]"," ").trim().replaceAll(" +", " ");
                buffer = buffer.replaceAll("[ ]-+"," ").trim().replaceAll(" +", " ");

                buffer = buffer.toLowerCase();

                if (!buffer.isEmpty()) { 

                    String [] procWords = buffer.split(" ");
                    if (hyphenate != null)

                        procWords[0] = hyphenate + procWords[0];
                    hyphenate = null;

                    int index;
                    for (index = 0; index < procWords.length; index++) {

                        if (haveHyphenate && (index >= procWords.length - 1)) {
                            hyphenate = procWords[index];
                            haveHyphenate = false;
                        }

                        else if (!_stopwords.isStopWord(procWords[index])) {
                        	String stem;
                        	if(stemmerFr == null)
                        		stem = stemmerEn.getStem(procWords[index]);
                            else{
                            	stemmerFr.setCurrent(procWords[index]);
                    		    stemmerFr.stem();
                    		    stem = stemmerFr.getCurrent();
                    	    }
                            // Update count as needed
                            WordCounter count = results.get(stem);
                            if (count == null) {
                                count = new WordCounter();
                                results.put(stem, count);
                            }
                            count.increment();
                            
                            if(!motAvecStem.contains(stem))
                            	motAvecStem.add(stem);
                            if(motAvecStem.contains(stem)){ 
                            	if((motAvecStem.indexOf(stem)) < motSansStem.size()){
                            		motSansStem.get(motAvecStem.indexOf(stem)).add(procWords[index]);
                                }else if(motSansStem.size() == (motAvecStem.indexOf(stem))){
                                	HashSet<String> newAL= new HashSet<String>();
                                	motSansStem.add(motAvecStem.indexOf(stem),newAL);
                                	newAL.add(procWords[index]);
                                }
                            }
                        } 
                    } 
                } 
                buffer = _file.readLine();
            } 

            if (hyphenate != null)
                System.out.println("WARNING: Badly formed file " + fullName + ". Hypenated word on last line: " + hyphenate + " ignored");
        }
        catch (Exception e) {
            // Clean up file
            if (_file != null)
                _file.close();
            _file = null;
            throw e;
        }
        return results;
    }

    public void finailize()
    {
        if (_file != null) {
            System.out.println("WARNING: File not properly closed");
            try {
                _file.close();
            }
            catch (IOException e) {}
            _file = null;
        }
    }
    
    public ArrayList<HashSet<String>> getMotSansStem(){
    	return motSansStem;
    }
    
    public ArrayList<String> getMotAvecStem(){
    	return motAvecStem;
    }

    public static void main(String[] args) throws Exception
    {
        try {
            if (args.length != 1)
                System.out.println("Single file for test must be specified");
            else {
                DocumentReader test = new DocumentReader("En");
                System.out.println(test.getWordFrequencies(args[0]));
            }
        }
        catch (Exception e) {
            System.out.println("Exception " + e + " caught");
            throw e;
        }
    }
}
