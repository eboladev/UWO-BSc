/*
 * FileName:	PirateFilter.cpp
 * Author:	Matthew Stokes
 * UserName:	mstokes5
 *
 * Description:
 *
 * PirateFilter is a subclass of DocumentFilter and provides a filter that translates the words of a document to Pirate Language.
 * 
 */

#include "PirateFilter.h"
#include <fstream>

class WordIterator;

#define PIRATEFILE "pirate.dict"
PirateFilter::PirateFilter()
{
	string eWord;
	string pPhrase;
	string line;

	ifstream myfile (PIRATEFILE);
	if(myfile.is_open())
	{
		while(myfile.good()) //loop through lines in pirate.dict
		{
			getline (myfile,line);
			eWord=""; //reset

			//english word
			for(int i=0;i<(int)line.length();++i)
			{
				if(line.at(i)==' ') //first space ends the english word
				{
					pPhrase=""; //reset

					//pirate word
					for(int j=i+1;j<(int)line.length();++j)
					{
						pPhrase += line.at(j);
					}
					break;
				}
				eWord += line.at(i);
			}
			_e2p[eWord]=pPhrase;
		}
		myfile.close();
	}
	else { //nothing was specified to do if this errors
	}
}

void PirateFilter::apply(Document& doc)
{
Document* _doc;
_doc=&doc;
WordIterator::WordIterator it(_doc,0);
	while(it!=doc.end()) //iterator length
	{
		for (int i=0;i<(int)it->length();++i) //word length
		{
			/*
			 * convert to lower case
			 */
			if(it->at(i) >= 'A' && it->at(i)<='Z')
			{
				int difference = 'a' - 'A';
				it->at(i) += difference;
			}
		}
		if(_e2p.find(*it)!=_e2p.end()) //if found
		{
			*it=_e2p[*it];
		}
	
		++it;
	}
}
