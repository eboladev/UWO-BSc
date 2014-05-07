#ifndef DATABASECONNECTOR_H
#define DATABASECONNECTOR_H

#include <QtSql/QSqlDatabase>       // For interacting with a database
#include <QSortFilterProxyModel>    // Needed to make sortable models from query results
#include <QString>

#include <QString>
#include <QStringList>

// MySql login parameters
#define HOST        "group5.cs3307.ca"
#define DATABASE    "group5"
#define USERNAME    "group5_user"
#define PASSWORD    "oZ9Wxifg*G=sDJY"

/**
 * \class DatabaseConnector
 * \author Sean Watson
 * \author Gaul Username: swatso33
 * \author Group 5
 *
 * CS 3307a
 * Group Project
 *
 * \brief A class for making queries to a MySql database
 *
 * Provides wrapper methods for common SQL queries
 */
class DatabaseConnector
{

public:

    /**
     * Initializes various database parameters such as the hostname,
     * username, database name, and password.
     */
    static void init();

    /**
     * Queries the database for a username/password combination to
     * authenticate an agent attempting to log in.
     *
     * \param username Username of the agent logging in
     * \param password Password of the agent logging in
     * \return True if the username/password combo is valid agent, false otherwise
     */
    static bool authenticate(QString username, QString password);

    /**
     * Retrieves a given number of customers ID, first name, last name,
     * and emails from the database
     *
     * \param number The number of customer records to retrieve
     * \return A sortable model of the returned records or NULL if there is an error
     */
    static QSortFilterProxyModel* getCustomers(int number);

    static QSqlQuery* runQuery(QString q);
    static QSqlQuery* runQuery(QString q, QStringList vars);

private:

    // The database to run queries against
    static QSqlDatabase _db;
};

#endif // DATABASECONNECTOR_H
