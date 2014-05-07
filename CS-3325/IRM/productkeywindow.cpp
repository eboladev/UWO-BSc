#include "productkeywindow.h"
#include "ui_productkeywindow.h"
#include <QFile>
#include <QTextStream>
#include <QString>
#include <QCryptographicHash>
#include <QStringList>


ProductKeyWindow::ProductKeyWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::ProductKeyWindow)
{
    ui->setupUi(this);
     connect(this->ui->btnSubmit, SIGNAL(clicked()), this, SLOT(checkKey()));
}

ProductKeyWindow::~ProductKeyWindow()
{
    delete ui;
}

void ProductKeyWindow::productKeyWindowResponse()
{
    this->show();
}

void ProductKeyWindow::checkKey()
{
    /* Clear Error Text */
    this->ui->lblError->setText("");
    this->ui->lblInvalid->setText("");

    /* Get Key Entered */
    QString key = this->ui->l1->text();
    key.append(this->ui->l2->text());
    key.append(this->ui->l3->text());
    key.append(this->ui->l4->text());

    if(sqlKeyMatch(key))
    {
        if(sqlValidUser())
        {
            QString myPath = qApp->applicationDirPath();
            myPath += "/mstokes5.txt";
            /* Create the file on computer */
            QFile mFile(myPath);
            mFile.open(QFile::WriteOnly | QFile::Text);
            QTextStream out(&mFile);

            /* Get User Information */
            QString user= getenv("USERNAME"); //for windows
            if(user.isEmpty())
            {
                user = getenv("USER"); ///for MAc or Linux
            }
            QByteArray block(user.toAscii());

            QCryptographicHash l_crpto( QCryptographicHash::Md5) ;
            l_crpto.addData(block);
            block = l_crpto.result();

            QString myString(block);

            const char* abc = myString.toStdString().c_str();

            /*Write to File*/
            out <<abc;

            /*Closing Out */
            mFile.flush();
            mFile.close();

            this->close();
            emit successWindowRequest();
        }

        else
        {
            this->ui->lblInvalid->setText("Product Key Already In Use");
        }
    }

    else
    {
        this->ui->lblError->setText("Incorrect Product Key");
    }
}

bool ProductKeyWindow::sqlKeyMatch(QString key) //check if valid key
{
    /* SQL CALL */
 //   QStringList vars;
 //   vars.append(key);

 //   QSqlQuery* query = DatabaseConnector::runQuery("SELECT body FROM comments WHERE ticket_id = :key",vars);
//    return query->size() > 0;


    QString myPath = qApp->applicationDirPath();
    myPath += "/mstokesDB.txt";
    QFile mFile(myPath); //path for file

    /* Check that DB Exists */
    if(!mFile.open(QFile::ReadOnly | QFile::Text))
    {

       return false;
    }

    else //exists
    {
        QTextStream in(&mFile);
        in.readLine(); //discard header
        QString dataLine = in.readLine(); //mysql row
        QStringList sql_row = dataLine.split("\t"); //first row (sake of proj only 1 license)
        mFile.close();

        if(sql_row[0].compare(key)==0) //if valid key
        {
            return true;
        }
        return false;
    }
}

bool ProductKeyWindow::sqlValidUser()
{
    /* Sql check if key already taken by personal & match this personal */
    QString myPath = qApp->applicationDirPath();
    myPath += "/mstokesDB.txt";

    QFile mFile(myPath); //path for file

    /* Check that DB Exists */
    if(!mFile.open(QIODevice::ReadWrite))
    {
   //    this->ui->lblDB2->setText("Please Put mstokesDB on Desktop");
        mFile.close();
       return false;
    }

    else
    {
        QTextStream in(&mFile);
        QString first = in.readLine(); //discard the headers
        QString dataLine = in.readLine(); //mysql row
        QStringList sql_row = dataLine.split("\t"); //first row (sake of proj only 1 row)
        mFile.close();

        if(sql_row[1].isEmpty()) //no user name registered (1st install)
        {
            mFile.open(QIODevice::WriteOnly);
            QTextStream out(&mFile);

            /* Get User Info */
            QByteArray user= getenv("USERNAME"); //for windows
            if(user.isEmpty())
                {
                    user = getenv("USER"); ///for MAc or Linux
                }

            /* Insert User Info & Write */
            dataLine = dataLine.insert(dataLine.indexOf("\t")+ 1,user);
            out << first << "\r" << endl << dataLine;

            /* Close */
            mFile.flush();
            mFile.close();

            return true;
        }

        else //user name registered
        {
            /* Get User Info */
            QByteArray user= getenv("USERNAME"); //for windows
            if(user.isEmpty())
                {
                   user = getenv("USER"); ///for MAc or Linux
                }

            QByteArray block(sql_row[1].toAscii());

            if(block == user) //check if current user and DB user are the same
            {
                return true;
            }
        return false; //doesn't match current user
        }
    }
}
