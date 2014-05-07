#ifndef DOCUMENT_H
#define DOCUMENT_H

#include "WordIterator.h"
#include "DocumentFilter.h"
#include <string>
#include <vector>

class DocumentFilter;
class WordIterator;

/*
 * \class Document
 * \author Matthew Stokes
 * \author Gaul Username: mstokes5
 *
 * Computer Science 3307a
 * Assignment: Individual Assignment
 *
 * \brief represents a text document, allowing a file to be loaded, modified using filters, and later saved
 *
 */
class Document{
	public:
		/*********************************************************************************************
		 * Constructors
		 *********************************************************************************************/
		
		/*
		 * Constructor. Takes the name of the file to be loaded and stores it. Does NOT load the file.
		 *
		 * \param filename name of the input file to be loaded.
		 *
		 */
		Document(const std::string filename);

		/*
		 * Destructor. Frees memory allocated to any filters stored in the Document.
		 * 
		 */
		~Document();

		/*********************************************************************************************
		 * Miscellaneous Methods
		 *********************************************************************************************/

		/*
		 * Loads the file word-by-word into the Document's vector of words.
		 *
		 * \return false if the file cannot be read for some reason; otherwise, returns true.
		 *
		 */
		bool  load();

		/*
		 * Iterates over the filters in the Document, applying each filter. Once all lters have been
		 * applied, iterates over the words in the Document, writing them to the specified file. As
		 * words are written, this method should keep track of the number of characters written to
		 * each line. Once a line has at least 80 characters, a new line should be written to the
		 * file. A word should not be broken up over lines, however. At the end, a new line should be
		 * written to the file.
		 *
		 * \param filename name of output filename.
		 * 
		 * \return true if the operation  was successful. If the file could not be written for some
		 * reason, returns false.
		 *
		 */
		bool save(const std::string& filename);

		/*
		 * Adds the specified filter to the vector of filters in the Document.
		 *
		 * \param f DocumentFilter pointer.
		 *
		 */
		void addFilter(DocumentFilter* f);

		/*
		 * Returns a WordIterator , initialized to point to the first word in the document.
		 *
		 * \return WordIterator.
		 *
		 */
		WordIterator begin();

		/*
		 * Returns a WordIterator , initialized to point one position past the last word in the document.
		 *
		 * \return WordIterator.
		 *
		 */
		WordIterator end();

		/*
		 * Returns the word at the specified index in the document. Words are indexed from 0.
		 *
		 * \param index specified index.
		 *
		 * \return string& the word at the specified index
		 *
		 */
		std::string& wordAt(int index);

	private:
		/*********************************************************************************************
		 * Instance Variables
		 *********************************************************************************************/
		std::string _nameOfFile; //name of the input file
		std::vector<std::string> _words; //vector containing all the words in the file
		std::vector<DocumentFilter*> _filter; //vector containing all the filters to be applied
};
#endif
