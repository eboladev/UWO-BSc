#include "welcomewindow.h"
#include "ui_welcomewindow.h"
#include <QFile>
#include <QString>
#include <QTextStream>
#include <QCryptographicHash>

WelcomeWindow::WelcomeWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::WelcomeWindow)
{
    ui->setupUi(this);
    connect(this->ui->btnStart, SIGNAL(clicked()), this, SLOT(licenseCheck()));
}

WelcomeWindow::~WelcomeWindow()
{
    delete ui;
}

void WelcomeWindow::licenseCheck()
{
    this->ui->lblError->setText(""); //hide previous error messages

    if(!fileCheck()) //if file does not exist
    {
        this->close();
        emit productKeyWindowRequest();
    }

    else //does exist
    {
        if(contentCheck()) //check contents of file against current computer
        {
            this->close();
            emit successWindowRequest();
        }
        else //corrupted file
        {
          this->ui->lblError->setText("Corrupted mstokesRegistrationFile");
        }
    }
}

bool WelcomeWindow::fileCheck()
{
    QString myPath = qApp->applicationDirPath();
    myPath += "/mstokes5.txt";

    QFile mFile(myPath); //path for file

    if(!mFile.exists()) //check if file exists
    {
       return false;
    }
    return true;
}

bool WelcomeWindow::contentCheck()
{

    QString myPath = qApp->applicationDirPath();
    myPath += "/mstokes5.txt";

    /* Get User Personal Information From File */
    QFile mFile(myPath);
    mFile.open(QFile::ReadOnly | QFile::Text);
    QTextStream in(&mFile);
    QString extraction = in.readAll(); //user
    mFile.close();


    /* Getting Personal From Host Computer */
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

    /* Unicode solution ignoring all '?' */
    for(int i=0;i<myString.length();i++){
        if(!(extraction.at(i).unicode() == 63  || extraction.at(i).unicode() == 131))
        {
            if(extraction.at(i) != myString.at(i)) return false;
        }
    }
    return true;
}
