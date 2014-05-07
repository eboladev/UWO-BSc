/**********************************************************************************
 *
 * File: databaseconnector.cpp
 * Author: Sean Watson
 * Gaul Username: swatso33
 *
 * Description: Provides wrapper methods for executing and processing the results
 *              of common SQL queries
 *
 **********************************************************************************/

#include "databaseconnector.h"
#include <QtSql/QSqlQuery>          // Needed for creating queries
#include <QtSql/QSqlQueryModel>     // Needed for creating models from query results
#include <QDebug>
// Initialize the static database as a MySql database
QSqlDatabase DatabaseConnector::_db = QSqlDatabase::addDatabase("QMYSQL");

/**
 * Initializes various database parameters with hardcoded values
 */
void DatabaseConnector::init()
{
    _db.setHostName(HOST);
    _db.setDatabaseName(DATABASE);
    _db.setUserName(USERNAME);
    _db.setPassword(PASSWORD);

}

/**
 * Queries the database for a username/password combo to authenticate
 * an agent trying to log into the system
 */
bool DatabaseConnector::authenticate(QString username, QString password)
{
    // Try and open a connection to the database
    if(!_db.open()) return false;

    // Prepare a query to search for the username/password combo
    QSqlQuery query(_db);
    query.prepare("SELECT id FROM users WHERE role = 'agent' AND username = :user AND password = :pass");
    query.bindValue(":user", username);
    query.bindValue(":pass", password);

    // Try to execute the query
    if (!query.exec())
    {
        // If there was an error close the connection
        _db.close();
        return false;
    }
    _db.close();                // Close the connection
    return query.size() > 0;    // Return whether the username/password was found
}

/**
 * Retrieves a given number of customers from the database and
 * returns them in a sortable model
 */
QSortFilterProxyModel* DatabaseConnector::getCustomers(int number)
{
    // Try and open a connection to the database
    if(!_db.open()) return NULL;

    // Prepare a query to retrieve the first "number" of customers
    QSqlQuery query(_db);
    query.prepare("SELECT id, first_name, last_name, email FROM users WHERE role = 'customer' LIMIT :num");
    query.bindValue(":num", number);

    // Try to execute the query
    if (!query.exec())
    {
        // If there was an error close the connection
        _db.close();
        return NULL;
    }
    _db.close();    // Close the connection

    // Generate a model from the query results
    QSqlQueryModel* qmodel = new QSqlQueryModel();
    qmodel->setQuery(query);

    // Generate a sortable model from the regular model
    QSortFilterProxyModel* sorted = new QSortFilterProxyModel();
    sorted->setSourceModel(qmodel);

    // Return the sortable model
    return sorted;

}

QSqlQuery* DatabaseConnector::runQuery(QString q){
        // Try and open a connection to the database
       if(!_db.open()){
           qDebug() << "Couldn't open database";
           return NULL;
       }

       QSqlQuery * query = new QSqlQuery();
       query->prepare(q);

       // Try to execute the query
       if (!query->exec())
       {
           // If there was an error close the connection
           delete query;
           _db.close();
           qDebug() << "Error running query";
           return NULL;
       }
       _db.close();    // Close the connection
       return query;
}

QSqlQuery* DatabaseConnector::runQuery(QString q, QStringList vars){
    // Try and open a connection to the database
   if(!_db.open()){
       qDebug() << "Couldn't open database";
       return NULL;
   }

   QSqlQuery * query = new QSqlQuery();
   query->prepare(q);
   for(int i=0; i < vars.size(); ++i){
       query->bindValue(i, vars.at(i));
   }

   // Try to execute the query
   if(!query->exec())
   {
       // If there was an error close the connection
       delete query;
       _db.close();
       qDebug() << "Error running query";
       return NULL;
   }
   _db.close();    // Close the connection
   return query;
}
