/*
 * FileName:	WordIterator.cpp
 * Author:	Matthew Stokes
 * UserName:	mstokes5
 *
 * Description:
 *
 * WordIterator class allows us to iterator over each word in a document.
 * 
 */
#include "WordIterator.h"

WordIterator::WordIterator(Document* d, int index): _docPointer(d), _index(index)
{
}

bool WordIterator::operator==(const WordIterator& other)
{
	if(this->_docPointer == other._docPointer && this->_index == other._index)
	{
		return true;
	}
	return false;
}

bool WordIterator::operator!=(const WordIterator& other)
{
	if(this->_docPointer != other._docPointer || this->_index !=other._index)
	{
		return true;
	}
	return false;
}

WordIterator& WordIterator::operator++()
{
	this->_index += 1;
	return *this;
}

WordIterator WordIterator::operator++(int unused)
{
	WordIterator past(this->_docPointer,this->_index);
	this->_index += 1;
	return past;
}

WordIterator& WordIterator::operator--()
{
	this->_index -= 1;
	return *this;
}

WordIterator WordIterator::operator--(int unused)
{
	WordIterator past(this->_docPointer,this->_index);
	this->_index -= 1;
	return past;
}

std::string* WordIterator::operator->()
{
	return &(this->_docPointer->wordAt(this->_index));
}

std::string& WordIterator::operator*()
{
	return (this->_docPointer->wordAt(this->_index));
}

