#ifndef WORDITERATOR_H
#define WORDITERATOR_H

#include <string>
#include <vector>
#include <fstream>
#include "Document.h"

using std::string;
using std::vector;
using std::endl;
using std::ofstream;
using std::ifstream;

class Document;

/*
 * \class WordIterator
 * \author Matthew Stokes
 * \author Gaul Username: mstokes5
 *
 * Computer Science 3307a
 * Assignment: Individual Assignment
 *
 * \brief The WordIterator class allows us to iterate over each word in a document.
 *
 */
class WordIterator{

	public:
		/*********************************************************************************************
		 * Constructors
		 *********************************************************************************************/

		/*
		 * Constructor. Takes and stores the document over which iteration is to take place, along with the
		 * index of the word at which iteration should begin.
		 *
		 * \parama d document over with iterator is to take place
		 * \param index to begin
		 *
		 */
		WordIterator(Document* d, int index);

		/*********************************************************************************************
		 * Miscellaneous Methods
		 *********************************************************************************************/
		
		/*
		 * Returns true if both iterators point to the same Document and are pointing at the same word.
		 *
		 * \param other second WordIterator
		 *
		 */
		bool operator==(const WordIterator& other);

		/*
		 * Returns true if the iterators point to dirent Document s or point to dirent words
		 * within the same Document.
		 *
		 * \param other second WordIterator
		 *
		 */
		bool operator!=(const WordIterator& other);

		/*
		 * Pre-increment operator.++it.
		 * Moves the iterator to the next word in the document and returns a reference to itself.
		 *
		 * \return WordIterator& 
		 *
		 */
		WordIterator& operator++();

		/*
		 * Post-increment operator. it++.
		 * . Makes a copy of itself, and then moves to the next word in the document.
		 * /return copy it made of itself before moving to the next word.
		 *
		 */
		WordIterator operator++(int unused);

		/*
		 * Pre-decrement operator. --it.
		 * Moves the iterator to the previous word in the document and returns a reference to itself.
		 *
		 */
		WordIterator& operator--();

		/*
		 * Post-decrement operator. it--.
		 * Makes a copy of itself, and then moves to the previous word in the document.
		 *
		 * \return copy it made of itself before moving to the previous word.
		 *
		 */
		WordIterator operator--(int unused);

		/*
		 * Pointer operator.
		 *
		 * \return a pointer to the current word pointed at by the iterator.
		 *
		 */
		std::string* operator->();

		/*
		 * Dereference operator.
		 *
		 *\return a reference to the current word pointed at by the iterator.
		 *
		 */
		std::string& operator*();
		
	private:
		/*********************************************************************************************
		 * Instance Variables
		 *********************************************************************************************/
		Document* _docPointer; //pointer to the document being iterated over
		int _index; //index representing the word in the document currently being pointed to
};
#endif
