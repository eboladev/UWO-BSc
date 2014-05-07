/*
 * FileName:	ROT13Filter.cpp
 * Author:	Matthew Stokes
 * UserName:	mstokes5
 *
 * Description:
 *
 * This class subclasses DocumentFilter and provides a filter that applies the ROT13 algorithm to the words of a document.
 * 
 */
#include "ROT13Filter.h"
#include <string>

class WordIterator;

using std::string;

#define DIFFERENCE 13 //13 Character shift

void ROT13Filter::apply(Document& doc)
{

Document* _doc = &doc;
WordIterator::WordIterator it(_doc,0);

while(it!=doc.end()) //loop through the document
{
	for(int i=0;i<(int)it->length();++i) //loop through the word
	{
		if(it->at(i)>='a' && it->at(i)<='m')
		{
			it->at(i) += DIFFERENCE;
		}
		else if(it->at(i)>='n' && it->at(i)<='z')
		{
			it->at(i) -= DIFFERENCE;
		}
		else if(it->at(i)>='A' && it->at(i)<='M')
		{
			it->at(i) += DIFFERENCE;
		}
			
		else if(it->at(i)>='M' && it->at(i)<='Z')
		{
			it->at(i) -= DIFFERENCE;
		}
	}
	++it;
}
}
