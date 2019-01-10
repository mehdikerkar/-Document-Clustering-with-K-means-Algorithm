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

import javax.swing.plaf.synth.SynthSeparatorUI;

import java.security.InvalidParameterException;

/* This class takes a document corpus data set, organized by document, and
   manipulates it as though it was organized by word instead. 
   WARNING: If multiple objects are created on the same document corpus, they
   are not synchronized! Deleting with one while reading with the other will
   create data races */
public class DocMapIterator
{
    /* This class implements the classic synchronized scanning algorithm. The
       data lists for each document are sorted in word order, so finding words
       in order requires keeping a pointer into the current word for each
       document, and sorting them. Multiple pointers may point to the same word
       in different documents, so they are sorted in groups. The first group is
       the current word. To advance to the next, move the pointers in the group,
       and then resort them in the list. */
	
    // Pointer to a word entry for a document
    private class DocIndex
    {
        private AfDocument _docPointer; // Cache of pointer to document data
        private int _document; // index document current
        private int _index;  // index du mot cle
        /* True, have done a deletion since the last time the pointer was
           advanced, and it already points to the next entry */
        private boolean _deleteSinceLastAdvance; 

        // Set index to first word of a document
        public DocIndex(int document) throws Exception
        {
            if ((document < 0) || (document > _corpusData.size()))
                throw new InvalidParameterException("Document " + document + " does not exist in data set");
            _document = document;
            _index = 0;
            _deleteSinceLastAdvance = false;
            _docPointer = _corpusData.get(document);
        }

        // Return the word it points to
        public String getWord() throws Exception
        {
            if ((!hasData()) || _deleteSinceLastAdvance)
                return null;
            else
                return _docPointer.getMotCle().get(_index).getMot();
        }

        // Return the frequency data it points to
        public FrequenceParDoc getData() throws Exception
        {
            if ((!hasData()) || _deleteSinceLastAdvance)
                return new FrequenceParDoc(0.0, _document);
            else
                return new FrequenceParDoc(_docPointer.getMotCle().get(_index).getNbrTFIDF(),
                                          _document);
        }

        /* Erase the word this index points to. This automatically moves the
           next word for the document into its place */
        public void removeData() throws Exception
        {
            /* If data was already deleted, don't delete it again because the
               index points to the wrong place! Calling advance will reset
               the pointer to consistent state */
            if (!_deleteSinceLastAdvance) {
                if (hasData())
                    _docPointer.removeData(_index);
                _deleteSinceLastAdvance = true;
            }
        }

        // Scale the frequency value this index points to
        public void scaleData(double scaleAmt) throws Exception
        {
            if (hasData() && (!_deleteSinceLastAdvance))
                _docPointer.scaleData(_index, scaleAmt);
        }
        
        // Return true if the index is valid
        public boolean hasData()
        {
        	return (_index < _docPointer.getMotCle().size());
        }

        // Returns true if the index points to the last word for the document
        public boolean isLastWord()
        {
            /* If the last word was deleted, the index still effectively
               points to that word, but the underlying data actually points
               to the NEXT word to process (advancing the index restores
               consistent state). This means the if something was deleted, the
               index points to the last word if the pointer is now invalid.
               Otherwise, check whether the indexed word is the last one in
               the list */
            if (_deleteSinceLastAdvance)
                return (!hasData()); // Now invalid means deleted word was last
            else
                return ((_index + 1) == _docPointer.getMotCle().size());
        }

        // Moves index to next document entry
        public void advance()
        {
            /* If the current entry was removed, the next entry automatically
               replaced it, so no advance is actually needed; the pointer is
               already there! */
        	//System.out.println("la valeur de _deleteSinceLastAdvance in advance is = " + _deleteSinceLastAdvance);
            if (_deleteSinceLastAdvance)
                _deleteSinceLastAdvance = false;
            else if (hasData())
                _index++;
            //System.out.println("la valeur de _index in advance is = " + _index);
        }

        public String toString()
        {
            try {
                if (_deleteSinceLastAdvance)
                    return "DocIndex:" + _document + " WordIndex:" + _index + " Data:DELETED";
                else
                    return "DocIndex:" + _document + " WordIndex:" + _index + " Data:" + getWord() + "-" + getData();
            }
            catch (Exception e) {
                return "DocIndex:" + _document + " WordIndex:" + _index + " Data:INVALID";
            }
        }
    }

    // Frequency data from the document corpus
    private ArrayList<AfDocument> _corpusData;

    /* List of current pointer locations, sorted by the words they point to.
       First item in the map is the next word to process */
    private TreeMap<String, ArrayList<DocIndex>> _wordList;

    // True, at least one word has been accessed
    boolean _haveProcessedWord;

    public DocMapIterator(ArrayList<AfDocument> corpusData) throws Exception
    {
    	//corpusData.get(0).getMotCle().get(0).getNbrTFIDF();
        _corpusData = corpusData;

        /* Set initial pointers, one per document. If the pointer is invalid
           (indicating the document data is empty) discard it. If ALL are
           discarded, the corpus is invalid */
        _wordList = new TreeMap<String, ArrayList<DocIndex>>();
        int index;
        for (index = 0; index < _corpusData.size(); index++)
            // Ignore documents with no data
            if (_corpusData.get(index) != null)
                insertDocIndex(new DocIndex(index));
        if (_wordList.isEmpty())
            throw new InvalidParameterException("Corpus to analyze " + _corpusData + " has no data");
        _haveProcessedWord = false;
    }

    // Inserts a valid document pointer in the current word list
    private void insertDocIndex(DocIndex newIndex) throws Exception
    {
        // Only insert if pointer is valid
        if (newIndex.hasData()) {
            ArrayList<DocIndex> wantList = _wordList.get(newIndex.getWord());
            if (wantList == null) {
                // Create a new list
                wantList = new ArrayList<DocIndex>();
                _wordList.put(newIndex.getWord(), wantList);
            } // List for word does not already exist
            wantList.add(newIndex);
        } // Valid doc index
        //System.out.println("la valeur de wordList in insertDicIndex a chaque foix = " + _wordList);
        //Thread.sleep(10000);
    }

    // True if there are still words to process
    public boolean hasNext()
    {
        /* The word pointer must be advanced just before getting the data for
           the next word in order for delete to work properly (a delete changes
           the underlying indexes in the corpus lists). This means that the
           list is out only when there is a single word left, and advancing all
           of its pointers leads to invalid data */
        if ((_wordList.size() > 1) || (!_haveProcessedWord))
            return true;
        else if (_wordList.isEmpty())
            return false; // Avoid null pointer error
        else {
            /* Iterate through the pointers for the word. If any does not point
               to the last word for the document, still have words to process */
            Iterator<DocIndex> testIterator = _wordList.firstEntry().getValue().iterator();
            boolean lastWord = true;
            while (testIterator.hasNext() && lastWord) {
                DocIndex testIndex = testIterator.next();
                // Test on validity in case word was deleted
                lastWord = (!testIndex.hasData()) || testIndex.isLastWord();
            }
            return (!lastWord);
        } // Exactly one entry in the word list
    }

    // Returns data for the next word to process
    /* NOTE: Document order in the results has no guarantees. Client should sort
       the data if needed */
    public ArrayList<FrequenceParDoc> nextWord() throws Exception
    {
        /* The index values must be advanced just before reading the data for
           the next word, in order for delete to work correctly (it changes the
           index values in the underlying word lists) The object is constructed
           with the pointer at the first word, so advance if something was
           previously read. */
        if (!_haveProcessedWord)
            _haveProcessedWord = true; // Just about to
        else {
            /* To advance the pointers, extract those for the current first
               entry in the word list, advance them, and re-insert them at their
               new positions. If there is no next entry for a given pointer, it
               becomes invalid and the insertion operation will throw it out.
               If the word list is empty afterward, this method was called when
               no words are left to process, which is an error */
            Iterator<DocIndex> procIterator = _wordList.pollFirstEntry().getValue().iterator();
            while (procIterator.hasNext()) {
                DocIndex newIndex = procIterator.next();
                //System.out.println("la valeur de newIndex before advance in nexWord is = " + newIndex._index);
                newIndex.advance();
                insertDocIndex(newIndex);
                //System.out.println("la valeur de newIndex after advance in nexWord is = " + newIndex._index);
            }
            if (_wordList.isEmpty())
                throw new NoSuchElementException();
        } // At least one word was previously processed

        // Extract the entry and iterate through it to get the data
        Iterator<DocIndex> dataIterator = _wordList.firstEntry().getValue().iterator();
        ArrayList<FrequenceParDoc> results = new ArrayList<FrequenceParDoc>();
        while (dataIterator.hasNext()){
        	DocIndex ind = dataIterator.next();
        	FrequenceParDoc fTemp = ind.getData();
        	//fTemp.setValTfIdf(_corpusData.get(ind._document).getMotCle().get(ind._index).getNbrTFIDF());
        	/*System.out.println("\nla nom du document est : " + _corpusData.get(fTemp.getDocument()).getNom());
        	System.out.println("le Term est : " + _corpusData.get(fTemp.getDocument()).getMotCle().get(ind._index).getMot());
        	System.out.println("value of dataIterator in CorpusByWord.nextWord() : " + fTemp.toString());
        	Thread.sleep(2000);*/
            results.add(fTemp);
            
        }
        return results;
    }

    // Returns the last word to be processed
    public String getProcessedWord()
    {
        if ((!_haveProcessedWord) || (_wordList.isEmpty()))
            return null; // Haven't processed anything, or none left
        else
            return _wordList.firstKey();
    }

    // Deletes the last word for which data was returned from the dataset
    public void deleteProcessedWord() throws Exception
    {
        if (_haveProcessedWord && (!_wordList.isEmpty())) {
            // Extract the current entry and delete every index in it
            Iterator<DocIndex> dataIterator = _wordList.firstEntry().getValue().iterator();
            while (dataIterator.hasNext())
                dataIterator.next().removeData();
        } // Have processed a word when method was called
    }
    
    // Scales the frequency values for the last processed word
    public void scaleProcessedWord(double scaleValue) throws Exception
    {
        if (_haveProcessedWord && (!_wordList.isEmpty())) {
            // Extract the current entry and delete every index in it
            Iterator<DocIndex> dataIterator = _wordList.firstEntry().getValue().iterator();
            while (dataIterator.hasNext())
                dataIterator.next().scaleData(scaleValue);
        } // Have processed a word when method was called
    }

    public String toString()
    {
        return _wordList.toString();
    }
}