/*
 * FileName:	Document.cpp
 * Author:	Matthew Stokes
 * UserName:	mstokes5
 *
 * Description:
 *
 * The Document class represents a text document, allowing a file to be loaded, modified using filters, and later saved.
 * 
 */
#include "Document.h"
#include <fstream>
#include <iostream>

Document::Document(string filename) : _nameOfFile(filename)
{
}

Document::~Document(){
	vector<DocumentFilter*>::iterator it; 
	
	/*
	 * Iterator over the filters and free stored memeory
	 */
	for(it=_filter.begin(); it<_filter.end(); ++it)
	{
		delete *it;
	}										
}

bool Document::load(){
		std::ifstream in_file;

		in_file.open(_nameOfFile.c_str()); //input file
	
		if(!in_file) //if file not found
		{
			return false;
		}
		else{
			std::string x;
			while(in_file>>x)
			{
				_words.push_back(x); //add the word in file to _words vector
			}
			in_file.close();
			return true; //successful
		}
}

bool Document::save(const std::string& filename)
{
	vector<DocumentFilter*>::iterator it;
	for(it=_filter.begin();it<_filter.end();++it) //iterator over the filters specified
	{
		(*it)->apply(*this); //apply filter
	}
	
		ofstream outputFile;
		outputFile.open(filename.c_str()); //output file
		if(!outputFile) //if file not found
		{
			return false;
		}
		else{
			int count80=0;
			int position=0;
			vector<string>::iterator it2;
			
			for(it2=_words.begin();it2<_words.end();++it2)
			{
				if(count80>80){ //80 characters or more have been printed
					outputFile<<endl;
					count80=0;
				}
				count80+=_words[position].length(); //incrimenting word count
				outputFile<<_words[position]; //write word to file
				outputFile<<" ";
				position=position+1;
			}
			outputFile<<endl;
			outputFile.close();
			return true;
		}
}

void Document::addFilter(DocumentFilter* f)
{
	_filter.push_back(f);
}

WordIterator Document::begin()
{
	return WordIterator(this,0);		
}

WordIterator Document::end()
{
	return WordIterator(this,_words.size());
}

std::string& Document::wordAt(int index)
{
	return _words[index];	
}

