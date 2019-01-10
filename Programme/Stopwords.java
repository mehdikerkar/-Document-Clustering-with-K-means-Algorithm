package Programme;
/* This file is part of DocumentCluster, a program for clustering text
   documents based on similarity. To use, specify the number of clusters
   followed by the documents, which must be located in the data subdirectory.
   Stopwords are eliminated by filtering the document contents against
   stopwords.txt in the same directory. Words are stemmed using the Porter
   Stemming algorithm. k-means clustering based on cosine similarity is used
   for the clustering.

    Copyright (C) 2013   Ezra Erb

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License version 3 as published
    by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    I'd appreciate a note if you find this program useful or make
    updates. Please contact me through LinkedIn or github (my profile also has
    a link to the code depository)
*/
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/* This file manages a singleton containing the list of stop words. These are
   words that are so common in documents that they are useless for English
   test analysis. No agreed on list exists; it gets tweaked by each
   application. This code deals with the issue by reading the list from a
   configuration file */
public final class Stopwords
{
    // The singleton
    private static Stopwords _words = null;

    // The actual words
    private Set<String> _wordList = null;

    /* File used to load the words. Inside the class to ensure it is always
       closed and deallocated */
    private BufferedReader _file;

    // Getter. If not created, create it, then return it
    // WARNING: NOT THREADSAFE!
    public static Stopwords getStopWords(String langChoix) throws Exception
    {
        if (_words == null) {
            try {
                _words = new Stopwords(langChoix);
            }
            catch (Exception e) {
                // Delete partially formed object to ensure consistency
                _words = null;
                throw e;
            }
        } // Singleton not already created
        return _words;
    }

    public Stopwords(String langChoix) throws Exception
    {
        _wordList = new HashSet<String>();

        /* Ouvrez le fichier de données. Si elle n'est pas trouvée, supposons qu'aucun mot d'arrêt(stop words) ne soit défini.
            C'est probablement une mauvaise configuration */
        try {
        	// Le choix entre 3 langue le français, l'anglais et l'arabe
        	String fullName = null;
        	if(langChoix.equals("En"))
    			fullName = new String("C:\\Users\\Mehdi\\workspace\\Project test [Tous ou Rien]\\Module\\stop-word - en.txt");
            else if(langChoix.equals("Fr")){
        		fullName = new String("C:\\Users\\Mehdi\\workspace\\Project test [Tous ou Rien]\\Module\\Fr\\Stop-Mot.txt");
        		System.out.println("je suis dans langue francais !");
            }
	        else if(langChoix.equals("Ar"))
        		fullName = new String("C:\\Users\\Mehdi\\workspace\\Project test [Tous ou Rien]\\Module\\stop-word - arabic.txt");
	        else{
	        	System.out.println("ATTENTION: Il n'y a pas de telle langue \"" + langChoix + "\" !");
        	}
            _file = new BufferedReader(new FileReader(fullName));
            if (_file == null) {
                // Failed to open. 
                System.out.println("No stop words file " + fullName + " found. Assuming none defined");
            }
            else {
                // Les mots sont spécifiés sur une ligne divisée par des virgules
            	String [] newData;
            	newData = _file.readLine().split(",");
            	//System.out.println(newData[0] +" "+ newData[1] +" "+ newData[2]);
                if (newData.length <= 0)
                    // Aucune donnée trouvée
                    System.out.println("Stop words file " + fullName + " corrupt; no data");
                else {
                    int index;
                    for (index = 0; index < newData.length; index++)
                        _wordList.add(newData[index]);
                }
                _file.close();
                _file = null;
            }
        }
        catch (Exception e) {
            // Assurer un état cohérent
            _wordList.clear();

            // nettoyer le fichier
            _file.close();
            _file = null;
            throw e;
        }
    }

    /** This method ensures the file is always closed before the object dies.
        In general, if the file gets to here, something has gone wrong and
        resources have been held far longer than needed. A warning is issued
        to handle this case */
    public void finailize()
    {
        if (_file != null) {
            System.out.println("WARNING: File not properly closed");
            try {
                _file.close();
            }
            /* Catch and dispose of any IO exception, since the object is going
               away soon anyway. This is normally an anti-pattern, but needed
               in this case */
            catch (IOException e) {}
            _file = null;
        }
    }

    // Output the stopwords list
    public String toString()
    {
        if (_wordList == null)
            return "NONE";
        else
            return Arrays.toString(_wordList.toArray());
    }

    public boolean isStopWord(String word)
    {
        if (_wordList == null)
            return false;
        else
            return (_wordList.contains(word));
    }

    public static void main(String[] args) throws Exception
    {
		String str = "a,à,â,à demi,à peine,à peu près,Absolu,absolument,actuellement,Admirable,Agréable,ai,aie,aient,aies,Aimable,ainsi,ait,alors,Amusant,Apocalyptique,apparemment,Approximatif,approximativement,après,après-demain,as,assez,assurément,Attachant,au,aucun,aucunement,aucuns,aujourd'hui,auparavant,auparavant,aura,aurai,auraient,aurais,aurait,auras,aurez,auriez,aurions,aurons,auront,aussi,aussitôt,autant,autre,autrefois,autrement,aux,avaient,avais,avait,avant,avant-hier,avec,avez,aviez,avions,avoir,avons,ayant,ayez,ayons,b,Banal,Bas,Bavarois,beaucoup,Bien,bientôt,Bof,Bon,Bouleversant,Boute-en-train,c,ç,c',ca,ça,Captivant,car,Caractériel,carrément,Cataclysmique,Catastrophique,ce,ceci,cela,celà,Céleste,cependant,certainement,certes,ces,cet,cette,ceux,chaque,Charmant,Chef-d’œuvre,Chouette,ci,cm,comme,comment,Commun,complètement,convenable,Convivial,Coquet,Correct,Crédible,Croquante,Cynique,d,d',da,d'abord,dans,davantage,de,début,dedans,Dégueulasse,dehors,déjà,Délectable,Délicieuse,demain,depuis,derechef,des,désormais,deux,devrait,diablement,Disjoncté,Divin,divinement,doit,donc,dorénavant,dos,Douce,Doué ,droite,Drôle,drôlement,du,e,é,è,ê,ë,Éblouissant,Ébouriffé,Efficace,elle,elles,Emballant,Émouvant,en,en vérité,encore,Endiablé,enfin,Ennuyant,Enragé,ensuite,Enthousiasmant,entièrement,entre-temps,environ,Épatant,Époustouflant,Épouvantable,Équitable,es,essai,est,et,étaient,étais,était,étant,état,été,étée,étées,êtes,étés,étiez,étions,être,eu,eue,eues,eûmes,eurent,eus,eusse,eussent,eusses,eussiez,eussions,eut,eût,eûtes,eux,évérant,Exaltant,Exceptionnel,Excusable,Exemplaire,Extra-moelleux,extrêmement,f,fait,faites,Féru,Festif,Flamboyante,fois,font,force,Formidable,fûmes,furent,fus,fusse,fussent,fusses,fussiez,fussions,fut,fût,fûtes,g,grandement,Grandiose,guère,h,habituellement,Hardi,haut,hier,Honnête,Horrible,hors,i,î,ï,ici,il,ils,Important,Impressionnant,Inconnu,Incrédule,Indépendant,Infernal,infiniment,Innommable ,Insignifiant ,insuffisamment,Insuffisant ,Insupportable ,Intenable ,Intéressant ,Irrésistible ,itôt,j,jadis,jamais,je,joliment,k,ka,km,l,la,là,le,les,leur,leurs,Libidineux ,lol,longtemps,lors,Louable ,lui,m,ma,Magistral ,Magnifique ,maintenant,mais,Majestueux ,mdr,me,Médiocre ,même,Merdique ,Merveilleux ,mes,Mignon ,Minable ,Mirobolante ,moi,moins,mon,Mortel,mot,Moyen ,n,naguère,ne,Négligeable ,ni,nommés,non,nos,notre,nous,nouveaux,Nul ,nullement,o,ô,on,ont,or,Ordinaire ,Original ,ou,où,oui,p,par,parce,Parfait,parfois,parole,pas,pas mal,Pas pire ,Passable ,passablement,Passionnant ,Percutant ,Pers,personne,personnes,peu,peut,peut-être,Phénoménal ,pièce,Placide ,Plaisant,plupart,plus,plutôt,point,pour,pourquoi,précisément,premièrement,presque,Prestant ,probablement,Prodigieux ,prou,Proverbial ,puis,q,qu,quand,quasi,quasiment,que,quel,Quelconque ,quelle,quelles,quelque,quelquefois,quels,qui,quoi,quotidiennement,r,Ravissant ,Recyclé,Relatif ,Remarquable ,Renversant ,Revendicatrice ,Révolutionnaire ,rien,Rocambolesque ,rudement,Rutilant,s,s',sa,Saint ,sans,sans doute,Satisfaisant ,se,Séduisant ,sera,serai,seraient,serais,serait,seras,serez,seriez,serions,serons,seront,ses,seulement,Sexy ,si,sien,sitôt,soi,soient,sois,soit,sommes,Somptueux ,son,sont,soudain,sous,souvent,soyez,soyons,Spiritueux ,Splendide ,Suave ,subitement,Sublime ,suffisamment,suis,Sulfureuse ,Superbe,Supportable ,Suprême ,sur,t,t',ta,Talentueux ,tandis,tant,tantôt,tard,te,tellement,tel,tels,terriblement,tes,toi,Tolérable ,ton,tôt,totalement,toujours,tous,tout,tout à fait,toutefois,tragique,Tragique ,Trépidant,très,Très bon ,trop,Troublant ,tu,u,ù,û,ü,un,une,v,valable,valeur,Valeureux ,Vénérable ,vers,Vitaminés ,Vivable ,voie,voient,volontiers,vont,vos,votre,vous,vraiment,vraisemblablement,vulgaire,w,x,y,ÿ";
		str.toLowerCase();
		str.replaceAll("[a-b][ ]", "");
		str.replaceAll(" ,", "");
		System.out.println("line est : " + str);
    	
    	/*try {
            Stopwords test = getStopWords("en");
            System.out.println(test);
            System.out.println("Test is a stopword:" + test.isStopWord("test"));
            System.out.println("The is a stopword:" + test.isStopWord("the"));
        }
        catch (Exception e) {
            System.out.println("Exception " + e + " caught");
            throw e; // Rethrow so improper temination is obvious
        }*/
    }
}