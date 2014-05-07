#include <iostream>
#include <string>
#include <vector>
#include <boost/program_options.hpp>
#include "DocumentFilter.h"

#include "PirateFilter.h"
#include "ROT13Filter.h"
#include "UppercaseFilter.h"

using std::cout;
using std::endl;
using std::string;
using std::vector;
using std::exception;
namespace po = boost::program_options;

#define UNLIMITED -1
#define LOADERROR -2
#define SAVEERROR -3
#define SUCCESS 0

/*
 * \class main
 * \author Matthew Stokes
 * \author Gaul Username: mstokes
 *
 * Computer Science 3307a
 * Assignment: Individual Assignment
 *
 * \brief main class to parse through command line arguments and apply filters
 * to an input document saving the results as output document.
 * \returns -1 inproper inputs, -2 inproper load, -3 inproper save
 *
 */
bool parse_arguments(int argc, char** argv, string& input_file, string& output_file, vector<DocumentFilter*>& filters)
{
  po::options_description cmdline_options;
  po::options_description general_opts("General options");
  po::options_description filter_opts("General options");

  general_opts.add_options()
    ("input,i", po::value<string>(&input_file), "Input file")
    ("output,o", po::value<string>(&output_file), "Output file")
    ("help,h", "Display this message");

  filter_opts.add_options()
    ("uppercase,u", "Uppercase filter")
    ("rot13,r",     "ROT13 filter")
    ("pirate,p",    "Pirate filter");

  cmdline_options.add(general_opts).add(filter_opts);

  try {
      po::variables_map vm;        
      po::store(
        po::command_line_parser(argc, argv).
        options(cmdline_options).
        run(), vm
      );

      po::notify(vm);    

      if (vm.count("help") || (vm.count("input") == 0) || (vm.count("output") == 0)) {
          cout << cmdline_options << endl;
          return false;
      }

      // If --uppercase was specified, add the UppercaseFilter
       if (vm.count("uppercase"))
       filters.push_back(new UppercaseFilter());

      // If --pirate was specified, add the PirateFilter
   if (vm.count("pirate"))
    filters.push_back(new PirateFilter());

      // If --rot13 was specified, add the ROT13Filter
   if (vm.count("rot13"))
     filters.push_back(new ROT13Filter());
      
      return true;
  }
  catch(exception& e)
  {
    cout << e.what() << "\n";
    return false;
  }
}

int main(int argc, char** argv)
{
  string input_file;
  string output_file;
  vector<DocumentFilter*> filters;

  // Parse the arguments passed to the program, obtain the input and output
  // filenames, and populate the list of filters to apply
  if (! parse_arguments(argc, argv, input_file, output_file, filters))
    return -1;

  Document *mydocument = new Document (input_file); //Initialize new document with input file from command line
  
  if(!mydocument->load()) //Try and load
  {
	  std::cout<<"Unable to load file"<<std::endl;
	  return LOADERROR;
  }
  
  /*loop through filters and apply*/
  vector<DocumentFilter*>::iterator it;
  for(it=filters.begin();it<filters.end();it++)
  {
	  mydocument->addFilter(*it);
  }

  if(!mydocument->save(output_file)) //try and save
  {
	  std::cout<<"Unable to save file"<<std::endl;
	  return SAVEERROR;
  }
  return SUCCESS; //success!
}
