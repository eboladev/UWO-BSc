#include <QApplication>
#include "successwindow.h"
#include "productkeywindow.h"
#include "welcomewindow.h"
#include "databaseconnector.h"

#include <QFile>
#include <QString>
#include <QTextStream>
#include <QDir>

int main(int argc, char *argv[])
{
    //create the application
    QApplication a(argc, argv);

    //create the windows
    SuccessWindow successWindow;
    WelcomeWindow welcomeWindow;
    ProductKeyWindow productKeyWindow;

    //connect the windows to one another
    QObject::connect(&welcomeWindow, SIGNAL(productKeyWindowRequest()), &productKeyWindow, SLOT(productKeyWindowResponse()));
    QObject::connect(&welcomeWindow, SIGNAL(successWindowRequest()), &successWindow, SLOT(successWindowResponse()));
    QObject::connect(&productKeyWindow, SIGNAL(successWindowRequest()), &successWindow, SLOT(successWindowResponse()));

    QFile mFile("C:/Users/Matthew Stokes/Desktop/a.txt");
    mFile.open(QFile::ReadWrite | QFile::Text);
    QTextStream in(&mFile);
    QTextStream out(&mFile);
    QString extraction = in.readAll(); //user

    extraction.replace("\n",", ");
out << extraction;
    mFile.close();


    //Show the welcome window
    welcomeWindow.show();

    return a.exec();
}
