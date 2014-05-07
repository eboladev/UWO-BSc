#include "DocumentFilter.h"
#include <map>
#include <string>
#include "Document.h"

class Document;

using std::string;
using std::map;
/*
 * \class PirateFilter
 * \author Matthew Stokes
 * \author Gaul Username: mstokes5
 *
 * Computer Science 3307a
 * Assignment: Individual Assignment
 *
 * \brief This class subclasses DocumentFilter and provides a filter that translates the words of a document to Pirate language.
 *
 */
class PirateFilter : public DocumentFilter
{
	public:
		/*********************************************************************************************
		 * Constructors
		 *********************************************************************************************/

		/*
		 * Constructor. Reads the lines of the file pirate.dict into a map to create an English-to-Pirate 
		 * dictionary. Each line in the file is of the form:
		 * 
		 * <english_word> <pirate_phrase>
		 * 
		 * The English word is always one word, but the Pirate phrase may consist of multiple words.
		 * The map should be populated such that English words are inserted as keys that map to Pirate
		 * phrases.
		 *
		 */
		PirateFilter();

		/*********************************************************************************************
		 * Miscellaneous
		 *********************************************************************************************/
		
		/*
		 * Iterates over the words in the document, first converting them to lowercase. Once a word is in
		 * lowercase, the algorithm should check if the dictionary contains the word. If so, the word should
		 * be changed to the corresponding Pirate word in the dictionary. Otherwise, the word should be left
		 * untouched (with the exception that it has been converted to lowercase).
		 *
		 * \param doc document to iterate over.
		 *
		 */
		virtual void apply(Document& doc);
		
	private:
		/*********************************************************************************************
		 * Instance Variables
		 *********************************************************************************************/
		map<std::string, std::string> _e2p; //A map that maps English words to Pirate language phrases.
};
