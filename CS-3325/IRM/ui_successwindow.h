/********************************************************************************
** Form generated from reading UI file 'successwindow.ui'
**
** Created: Wed Nov 7 12:04:36 2012
**      by: Qt User Interface Compiler version 4.8.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_SUCCESSWINDOW_H
#define UI_SUCCESSWINDOW_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QHeaderView>
#include <QtGui/QLabel>
#include <QtGui/QMainWindow>
#include <QtGui/QMenuBar>
#include <QtGui/QPushButton>
#include <QtGui/QStatusBar>
#include <QtGui/QToolBar>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_SuccessWindow
{
public:
    QWidget *centralWidget;
    QLabel *label;
    QPushButton *pushButton;
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *SuccessWindow)
    {
        if (SuccessWindow->objectName().isEmpty())
            SuccessWindow->setObjectName(QString::fromUtf8("SuccessWindow"));
        SuccessWindow->resize(382, 179);
        centralWidget = new QWidget(SuccessWindow);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        label = new QLabel(centralWidget);
        label->setObjectName(QString::fromUtf8("label"));
        label->setGeometry(QRect(30, 0, 361, 111));
        QFont font;
        font.setPointSize(36);
        font.setBold(true);
        font.setItalic(true);
        font.setWeight(75);
        label->setFont(font);
        pushButton = new QPushButton(centralWidget);
        pushButton->setObjectName(QString::fromUtf8("pushButton"));
        pushButton->setGeometry(QRect(150, 110, 92, 24));
        SuccessWindow->setCentralWidget(centralWidget);
        menuBar = new QMenuBar(SuccessWindow);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 382, 21));
        SuccessWindow->setMenuBar(menuBar);
        mainToolBar = new QToolBar(SuccessWindow);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        SuccessWindow->addToolBar(Qt::TopToolBarArea, mainToolBar);
        statusBar = new QStatusBar(SuccessWindow);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        SuccessWindow->setStatusBar(statusBar);

        retranslateUi(SuccessWindow);
        QObject::connect(pushButton, SIGNAL(clicked()), SuccessWindow, SLOT(close()));

        QMetaObject::connectSlotsByName(SuccessWindow);
    } // setupUi

    void retranslateUi(QMainWindow *SuccessWindow)
    {
        SuccessWindow->setWindowTitle(QApplication::translate("SuccessWindow", "SuccessWindow", 0, QApplication::UnicodeUTF8));
        label->setText(QApplication::translate("SuccessWindow", "Success Screen", 0, QApplication::UnicodeUTF8));
        pushButton->setText(QApplication::translate("SuccessWindow", "Close", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class SuccessWindow: public Ui_SuccessWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_SUCCESSWINDOW_H
