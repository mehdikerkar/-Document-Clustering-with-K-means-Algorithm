package methodes;
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
import java.util.*;

/* This class lists a word frequency value combined with the index of the
   document the value was calculated from. It mainly exists because Java has
   no generic pair class that can handle primitive types */
public final class FrequenceParDoc
{
	//private double valTfIdf;
    private double _frequency;
    private int _document; // Index to document in corpus

    public FrequenceParDoc(double frequency, int document)
    {
        _frequency = frequency;
        _document = document;
    }

    public double getFrequency()
    {
        return _frequency;
    }
    
    /*public void setValTfIdf(double valTfIdf)
    {
    	this.valTfIdf = valTfIdf;
    }
    
    public double getValTfIdfy()
    {
        return valTfIdf;
    }*/

    public int getDocument()
    {
        return _document;
    }

    public String toString()
    {
        return "\nDocument " + String.valueOf(_document) + ": frequency : " + String.valueOf(_frequency);// + "\n	valTfIdf : "+ String.valueOf(valTfIdf);
    }
}