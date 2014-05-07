#include "DocumentFilter.h"
#include "Document.h"

class Document;

/*
 * \class UppercaseFilter
 * \author Matthew Stokes
 * \author Gaul Username: mstokes5
 *
 * Computer Science 3307a
 * Assignment: Individual Assignment
 *
 * \brief This class subclasses DocumentFilter and provides a filter for converting all words in a document to
 * uppercase.
 * 
 */
class UppercaseFilter : public DocumentFilter
{
	public:
		/*********************************************************************************************
		 * Miscellaneous
		 *********************************************************************************************/

		/*
		 * Iterates over the words in the document, converting them to uppercase. Numbers and other non-letters
		 * should be left untouched.
		 *
		 * \param doc document to iterate over
		 *
		 */
		virtual void apply(Document& doc);
};
