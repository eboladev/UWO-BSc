#include "DocumentFilter.h"
#include "Document.h"

class Document;

/*
 * \class ROT13Filter
 * \author Matthew Stokes
 * \author Gaul Username: mstokes5
 *
 * Computer Science 3307a
 * Assignment: Individual Assignment
 *
 * \brief This class subclasses DocumentFilter and provides a filter that applies the ROT13 algorithm
 * to the words of a document.
 *
 */
class ROT13Filter : public DocumentFilter
{
	public:
		/*********************************************************************************************
		 * Miscellaneous
		 *********************************************************************************************/

		/*
		 * Iterates over the words in the document, applying the ROT13 algorithm to them. Non-letters should be
		 * left untouched.
		 *
		 * \param doc document to iterate over.
		 *
		 */
		virtual void apply(Document& doc);
};
