/**********************************************************************************************
*
* scheduler.cpp
* Assignment #2
* Author: Matthew Stokes
*
* Description:
* implementation of a very basic CPU scheduler
*
* Assumptions:                - for a process I/O duraton is the same for each I/O frequency 
*                             - user must enter commandline arguments in proper order and must
*                               be space delimited (without -i, -o options unable to distinguish
*                               between input & output files other than in sequential order)
*                               ex. scheduler in_file out_file algorithm
*                             - input document is sorted by arrival times begining with 0
*                             - changing states takes 0 time
*                             - Processes are loaded based on arrival time into the ready queue
*                               before running processes or blocked processes. The order is
*                               process->ready, running->ready, blocked->ready
*
* Additional Features:
*                             - Informs if input or output files are invalid
*                             - Informs which line an error has occured in input file
*                             - Prompts if RR timeslice chosen is greater than largest burst
*                               time (there FIFO before expected) allowing for user to reenter
*                               a rr time value
*                             - debug(vector) to print out all structure information for all
*                               processes within given vector
*
**********************************************************************************************/

/**********************************************************************************************
* Includes
**********************************************************************************************/
#include <stdio.h>
#include <string.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>

/**********************************************************************************************
* Constants
**********************************************************************************************/
#define SUCCESS                0
#define ERROR_COMMANDLINE     -1
#define ERROR_INPUT           -2
#define ERROR_EXPIRED_TIME    -3
#define ERROR_TIME_ALLOWANCE   100000 // amount of time passed before program assumes an error
                                      // has occured (purposes of this assignment rather
                                      // than stalling)

/**********************************************************************************************
* Using
**********************************************************************************************/
using std::cout;
using std::cin;
using std::string;
using std::vector;
using std::istringstream;
using std::ifstream;
using std::ofstream;

/**********************************************************************************************
* Globals
**********************************************************************************************/
std::string algorithms[2]=            // algorithms accepted
{"FIFO", "RR"};

/**********************************************************************************************
* Structures
**********************************************************************************************/
struct Process {
  int process_id;                     // unique identifier for the process
  int arrival_time;                   // time that the process is created
  int cpu_time;                       // total amount of CPU time needed
  int io_freq;                        // number of times requesting an I/O operation
  int io_duration;                    // duration of an I/O
  int ticks;                          // amount of running time completed from blocking state
  int cpu_consump;                    // overall CPU consumption
  int cpu_burst_size;                 // current burst size
  int io_calls;                       // total calls having been made to i/o
  int cpu_burst_slice;                // amount of burst already consumed (RR)
};

struct Time {
  int time;                           // clock to keep track of time
  int checkout_cpu;                   // when the running process is set to finish
};
/**********************************************************************************************
* Functions
**********************************************************************************************/
/*
 *    Function:         create_processes
 *    Parameters In:    ifstream const *file_input - input stream
 *                      vector<Process*> process - vector to store created processes
 *    Parameters Out:   0 success -1 error 
 *    Purpose:          form processes from the input file. Also validates that input file
 *                      has correct number of arguments/line
 *    Author:           Matthew Stokes
*/
int create_processes(ifstream &file_input, vector<Process*>& process_queue, int &max_burst)
{
  string line;
  int line_count=0;                             // keeping track of line number in input file

  while (file_input.good())                     // loop through lines in input file
  {
    int value;
    int count=0;                                // error checking that input file has proper args
    getline(file_input,line);
    
    /* error checking for proper input file */
    istringstream file_check(line);
    while(file_check >> value)
    {
      count++;  
    }
    if(count != 5 && count != 0)                // wrong number of inputs in file
    {
      printf("ERROR in input file on line %d\ninvalid number of inputs",line_count+1);
      file_input.close();
      return -1;
    }

    else if(count !=0)                          // proper input file        
    {
      /* create process */
      process_queue.push_back(new Process);     // create new process and push onto back of process_queue
      istringstream info(line);
      info >> process_queue.back()->process_id;
      info >> process_queue.back()->arrival_time;
      info >> process_queue.back()->cpu_time;
      info >> process_queue.back()->io_freq;
      info >> process_queue.back()->io_duration;

      if(process_queue.back()->cpu_time/process_queue.back()->io_freq > max_burst)
      {
        max_burst = process_queue.back()->cpu_time/process_queue.back()->io_freq;
      }
      
      line_count++;
    }
  }
  file_input.close();
  return 0;
}

/*
 *    Function:         commandline_validation
 *    Parameters In:    int const *argc - total number of arguments
 *                      char *argv[] - array containing arguments
 *                      ifstream &file_input - input stream
 *                      ofstream &file_output - output stream
 *    Parameters Out:   0 - success, -1 - error
 *    Purpose:          checks that program was run with correct number of arguments. Opens the
 *                      filestreams as specificed from arguments and checks these are valid
 *                      returning -1 if they are invalid
 *    Author:           Matthew Stokes
*/
int commandline_validation(int const *argc, char *argv[], ifstream &file_input, ofstream &file_output)
{
  file_input.open(argv[1]);
  file_output.open(argv[2]);

  /* handle proper number of arguments */
  if(*argc==1 || *argc>4)
  {
    printf("ERROR please refer to specifications as %d arguments is invalid\n",*argc-1);  
    return -1;
  }

  /* check & open input */
  if(!file_input.is_open())
  {
    printf("ERROR input file %s failed to open\n",argv[1]);  
    return -1;
  }

  /* check & open output */
  else if(!file_output.is_open())
  {
    printf("ERROR output file %s failed to open\n",argv[2]);  
    return -1;
  }

  /* check algorithm */
  int flag=0;
  int i;
  for(i=0;i<sizeof(algorithms)/sizeof(int);i++)
  {
    if(argv[3] == algorithms[i])
    {
      flag = 1;
      break;
    }
  }
  if(flag==0) //not found
  {
    printf("ERROR please select either 'FIFO' or 'RR' (note capitalization)\n");
    return -1;
  }
  else
  {
    return 0;
  }
}

/*
 *    Function:         print_to_output
 *    Parameters In:    ofstream &file_output - output stream
 *                      int process_id - process ID of process
 *                      int time - current time
 *                      string old_state - Old State of process
 *                      string new_state - New State of process
 *    Purpose:          prints information to the output file in the format:
 *                      Process id: Time, Old State, New State
 *    Author:           Matthew Stokes
*/
void print_to_output(ofstream& file_output, int process_id, int time, string old_state, string new_state)
{
  file_output << process_id << ": " << time << ", " << old_state << ", " << new_state << "\n";
}

/*
 *    Function:         debug
 *    Parameters In:    vector<Process*> - vector to examine
 *    Purpose:          prints to screen all structure information about all processes
 *                      within the given vector
 *    Author:           Matthew Stokes
*/
void debug(vector<Process*>& printme)
{
  for(int j=0; j<40; j++)                       // spacers
  {
    cout << "*";
  }
  cout << "\n";

  cout << "Printing Content, Queue Size: " << printme.size() << "\n";

  for(int i=0;i<printme.size();i++)
  {
    cout << "\n";
    cout << "Vector Item Number: " << i << "\n";
    cout << "Process ID: " << printme.at(i)->process_id << "\n";           // unique identifier for the process
    cout << "Arrival Time: " << printme.at(i)->arrival_time << "\n";       // time that the process is created
    cout << "CPU Time: " << printme.at(i)->cpu_time << "\n";               // total amount of CPU time needed
    cout << "IO Frequency: " << printme.at(i)->io_freq << "\n";            // number of times requesting an I/O operation
    cout << "IO Duration: " << printme.at(i)->io_duration << "\n";         // duration of an I/O
    cout << "Ticks: " << printme.at(i)->ticks << "\n";                     // amount of running time completed from blocking state
    cout << "CPU Consumption: " << printme.at(i)->cpu_consump << "\n";     // overall CPU consumption
    cout << "CPU Burst Size: " << printme.at(i)->cpu_burst_size << "\n";   // current burst size
    cout << "IO Calls: " << printme.at(i)->io_calls << "\n";               // total calls to IO currently completed
    cout << "CPU Burst Slice: " << printme.at(i)->cpu_burst_slice << "\n"; // amount of burst consumed (RR)
  }
    for(int i=0; i<2; i++)                      //spacers
    {
      for(int j=0; j<40; j++)
      {
        cout << "*";
      }
      cout << "\n";
    }
}

/**********************************************************************************************
* Main Program
**********************************************************************************************/
int main (int argc, char* argv[])
{
  /* streams */
  ifstream file_input;
  ofstream file_output;

  /* queues */
  vector<Process*> process_queue;
  vector<Process*> ready_queue;
  vector<Process*> running_queue;
  vector<Process*> io_queue;
  vector<Process*> finish_queue;

  /* clock */
  Time stopwatch;
  stopwatch.time=0;

  /* variables */
  int max_burst = 0;                            // maximum burst size from input file
  int rr_time_slice=0;                          // round robbin time slice
  int enter_rr=1;                               // loop to readjust rr time slice
  bool change=true;                             // ensuring proper process transfer between states at
                                                // given time instance
    
  /* validate proper input & open input & output files */
  if(commandline_validation(&argc, argv, file_input, file_output) != 0)
  {
    return ERROR_COMMANDLINE;
  }

  /* read from input file and create processes from information, then store in process_queue */
  if(create_processes(file_input, process_queue, max_burst) != 0)
  {
    file_input.close();
    return ERROR_INPUT;
  }

  /* round robbin time slice */
  if(strcmp(argv[3],"RR")==0)
  {
    /* get RR size */
    while(enter_rr)
    {
      cout<<"Please enter a RR size: ";
      cin>>rr_time_slice;
      if(rr_time_slice > max_burst)                                   //RR appropriate sizing warning
      {
        cout << "Warning, large RR sizes will cause FIFO type behaviour\n";
        cout << "Do you wish to enter a different value? (1-yes/0-no)\n";
        cin >> enter_rr;
      }
      else
      {
        enter_rr=0;
      }
    }
  }

  /* Main Loop
   * will run until all processes are in finished queue. Else until time reaches 
   * ERROR_TIME_ALLOWANCE then it will print debugging contents for every queue */
  while(true)                                   
  {
    /* accounts for any changes between states at the given time (assuming changing states takes
     * 0 time) Ex. process can go from Blocked to Ready than Ready to Running without time being
     * incrimented (provided conditions are met of course) */
    while(change)
    {
      change=false;                                                   // initially no changes of state were made
    
      /********************************/
      /* process_queue -> ready_queue */
      /********************************/
      if(process_queue.size() != 0 && process_queue.front()->arrival_time == stopwatch.time)
      {
        /* queue transition */
        ready_queue.push_back(process_queue.front());                 // push process_queue onto back ready queue
        process_queue.erase(process_queue.begin());                   // remove from process queue
      
        /* print */
        print_to_output(file_output, ready_queue.back()->process_id, stopwatch.time, "None", "Newly Arrived");
        print_to_output(file_output, ready_queue.back()->process_id, stopwatch.time, "Newly Arrived", "Ready");
      
        /* handling changes in struct data */
        ready_queue.back()->cpu_consump = 0;
        ready_queue.back()->io_calls = 0;
        ready_queue.back()->cpu_burst_slice=0;
        ready_queue.back()->cpu_burst_size = ready_queue.back()->cpu_time/ready_queue.back()->io_freq;

        change = true;                                                // changing queues signifies a change
      }
      
      /********************************/
      /* ready_queue -> running_queue */
      /********************************/
      if(running_queue.size() == 0 && ready_queue.size() != 0)        // nothing running and ready processes
      {
        /* queue transition */
        running_queue.push_back(ready_queue.front());                 // push ready_queue onto running queue
        ready_queue.erase(ready_queue.begin());                       // remove from ready queue
       
        /* print */
        print_to_output(file_output, running_queue.back()->process_id, stopwatch.time, "Ready", "Running");

        /* remaining cpu time < burst size (NOT RR) */
        if(strcmp(argv[3],"RR") != 0 && running_queue.back()->cpu_time-running_queue.back()->cpu_consump < running_queue.back()->cpu_burst_size)
        {
          stopwatch.checkout_cpu = stopwatch.time + running_queue.back()->cpu_time-running_queue.back()->cpu_consump;
        }

        /* round robbin condition */
        else if(strcmp(argv[3],"RR")==0)
        {
          int next_time_amount;

          /* if burst time left is less than rr_time_slice */
          if(running_queue.back()->cpu_burst_size-running_queue.back()->cpu_burst_slice < rr_time_slice)
          {
            next_time_amount = running_queue.back()->cpu_burst_size-running_queue.back()->cpu_burst_slice;
            running_queue.back()->cpu_burst_slice = 0;
          }
          /* using rr_time_slice */
          else
          {
            next_time_amount = rr_time_slice;
            running_queue.back()->cpu_burst_slice += rr_time_slice;
          }

          /* check running time slice against total time left */
          if(next_time_amount > running_queue.back()->cpu_time-running_queue.back()->cpu_consump)
          {
           stopwatch.checkout_cpu = stopwatch.time + running_queue.back()->cpu_time-running_queue.back()->cpu_consump;
          }
          /* runs for full time slicet */
          else
          {
            stopwatch.checkout_cpu = stopwatch.time + next_time_amount;
          }
        }

        /* set to run full burst size (NOT RR) */
        else
        {
          stopwatch.checkout_cpu = stopwatch.time + running_queue.back()->cpu_burst_size;
        }
        change = true;                                               // changing queues signifies a change
      }

      /* running_queue 3 options */
      if(running_queue.size() != 0  && stopwatch.checkout_cpu == stopwatch.time)
      {
        /***********************/
        /* running -> finished */
        /***********************/
        /* via total cpu consumed & no remaining i/o operations */
        if(running_queue.front()->cpu_consump == running_queue.front()->cpu_time && running_queue.front()->io_calls == running_queue.front()->io_freq)
        {
          /* queue transition */
          finish_queue.push_back(running_queue.front());             // push running_queue onto finish_queue
          running_queue.erase(running_queue.begin());                // remove from running_queue
        
          /* print */
          print_to_output(file_output, finish_queue.back()->process_id, stopwatch.time, "Running", "Finished");
        }

        /**********************/
        /* running -> blocked */
        /**********************/
        else if(running_queue.front()->io_calls != running_queue.front()->io_freq && (running_queue.front()->cpu_burst_slice == 0 || running_queue.front()->cpu_burst_slice == running_queue.front()->cpu_burst_size))
        {
          /* queue transition */
          io_queue.push_back(running_queue.front());                 // push running_queue onto i/o_queue
          running_queue.erase(running_queue.begin());                // remove from running_queue

          /* print */
          print_to_output(file_output, io_queue.back()->process_id, stopwatch.time, "Running", "Blocked");

          /* handling changes in struct data */
          io_queue.back()->io_calls++;
          running_queue.front()->cpu_burst_slice = 0;
        }

        /********************/
        /* running -> ready */
        /********************/
        else
        {
          /* queue transition */
          ready_queue.push_back(running_queue.front());               // push running_queue onto ready_queue
          running_queue.erase(running_queue.begin());                 // remove from running queue
        
          /* print */
          print_to_output(file_output, ready_queue.back()->process_id, stopwatch.time, "Running", "Ready");
        }
        change = true;                                                // changing states signifies change
      }

      /* blocked 2 options */
      if(io_queue.size() != 0)
      {
        for(int i=0;i<io_queue.size();i++)                            // assumed all io handled concurrently
        {
          if(io_queue.at(i)->ticks == io_queue.at(i)->io_duration)
          {
        
            /***********************/
            /* blocked -> finished */
            /***********************/
            /* via cpu_consump */
            if(io_queue.at(i)->cpu_consump == io_queue.at(i)->cpu_time)
            {
              /* queue transition */
              finish_queue.push_back(io_queue.at(i));                 // push i/o_queue onto finished queue
              io_queue.erase(io_queue.begin()+i);                     // remove from i/o queue

              /* print */
              print_to_output(file_output, finish_queue.back()->process_id, stopwatch.time, "Blocked", "Finished");
            }

            /********************/
            /* blocked -> ready */
            /********************/
            /* via ticks */
            else
            {
              /* queue transition */
              ready_queue.push_back(io_queue.at(i));                  // push i/o_queue onto ready queue
              io_queue.erase(io_queue.begin()+i);                     // remove from i/o queue
        
              /* print */
              print_to_output(file_output, ready_queue.back()->process_id, stopwatch.time, "Blocked", "Ready");
        
              /* handling changing struct data */
              ready_queue.back()->ticks=0;
            }

            change = true;                                            // changing signified by state change
          }
        } //end for
      }
    } //completed step while loop


    change = true;

    /* completed */
    if(process_queue.size() == 0 && io_queue.size() == 0 && running_queue.size() == 0 && ready_queue.size() == 0)
    {
      break;
    }

    /* error checking */
    if(stopwatch.time > ERROR_TIME_ALLOWANCE)
    {
      cout << "ERROR (most likely stalled as " << ERROR_TIME_ALLOWANCE << " time units have passed. If proper increase ERROR_TIME_ALLOWANCE)\n";
      cout << "PROCESS QUEUE\n";
      debug(process_queue);
      cout << "READY QUEUE\n";
      debug(ready_queue);
      cout << "IO QUEUE\n";
      debug(io_queue);
      cout << "RUNNING QUEUE\n";
      debug(running_queue);
      cout << "FINISHED QUEUE\n";
      debug(finish_queue);
      return ERROR_EXPIRED_TIME;
    }

    /* 1 time unit has expired therefore cpu has been consumed an i/o ticks have passed */
    if(running_queue.size() != 0)
    {
      running_queue.back()->cpu_consump++;
    }
    if(io_queue.size() != 0)
    {
      for(int i=0;i<io_queue.size();i++)
      io_queue.at(i)->ticks++;
    }

    stopwatch.time++;                                                           // incriment time
  }
  
  return SUCCESS;
}
