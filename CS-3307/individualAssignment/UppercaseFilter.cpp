/*
 * FileName:	UppercaseFilter.cpp
 * Author:	Matthew Stokes
 * UserName:	mstokes5
 *
 * Description:
 *
 * The class subclasses DocumentFilter and provides a filter for converting all words in a document to uppercase.
 * 
 */
#include "UppercaseFilter.h"
#include <string>

class WordIterator;

using std::string;

void UppercaseFilter::apply(Document& doc)
{
	Document* _doc = &doc;
	WordIterator::WordIterator it(_doc,0);
	
while(it!=doc.end()) //loop through words
{
	for(int i=0;i<(int)it->length();++i) //characters in words
	{
		if(it->at(i)>='a' && it->at(i)<='z')
		{
			int difference = 'A'-'a';
			it->at(i) += difference;
		}
	}
	++it;
}
}
