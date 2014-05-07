/********************************************************************************
** Form generated from reading UI file 'welcomewindow.ui'
**
** Created: Thu Nov 8 18:09:59 2012
**      by: Qt User Interface Compiler version 4.8.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_WELCOMEWINDOW_H
#define UI_WELCOMEWINDOW_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QHeaderView>
#include <QtGui/QLabel>
#include <QtGui/QMainWindow>
#include <QtGui/QMenuBar>
#include <QtGui/QPushButton>
#include <QtGui/QSpacerItem>
#include <QtGui/QStatusBar>
#include <QtGui/QVBoxLayout>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_WelcomeWindow
{
public:
    QWidget *centralwidget;
    QLabel *lblError;
    QWidget *layoutWidget;
    QVBoxLayout *verticalLayout;
    QLabel *label;
    QSpacerItem *verticalSpacer;
    QPushButton *btnStart;
    QLabel *test;
    QMenuBar *menubar;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *WelcomeWindow)
    {
        if (WelcomeWindow->objectName().isEmpty())
            WelcomeWindow->setObjectName(QString::fromUtf8("WelcomeWindow"));
        WelcomeWindow->resize(413, 260);
        centralwidget = new QWidget(WelcomeWindow);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        lblError = new QLabel(centralwidget);
        lblError->setObjectName(QString::fromUtf8("lblError"));
        lblError->setGeometry(QRect(70, 200, 281, 16));
        lblError->setStyleSheet(QString::fromUtf8("QLabel {color: red;}"));
        layoutWidget = new QWidget(centralwidget);
        layoutWidget->setObjectName(QString::fromUtf8("layoutWidget"));
        layoutWidget->setGeometry(QRect(71, 31, 283, 156));
        verticalLayout = new QVBoxLayout(layoutWidget);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        verticalLayout->setContentsMargins(0, 0, 0, 0);
        label = new QLabel(layoutWidget);
        label->setObjectName(QString::fromUtf8("label"));
        QFont font;
        font.setPointSize(48);
        font.setBold(true);
        font.setWeight(75);
        label->setFont(font);

        verticalLayout->addWidget(label);

        verticalSpacer = new QSpacerItem(278, 48, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer);

        btnStart = new QPushButton(layoutWidget);
        btnStart->setObjectName(QString::fromUtf8("btnStart"));

        verticalLayout->addWidget(btnStart);

        test = new QLabel(centralwidget);
        test->setObjectName(QString::fromUtf8("test"));
        test->setGeometry(QRect(150, 0, 181, 41));
        WelcomeWindow->setCentralWidget(centralwidget);
        menubar = new QMenuBar(WelcomeWindow);
        menubar->setObjectName(QString::fromUtf8("menubar"));
        menubar->setGeometry(QRect(0, 0, 413, 21));
        WelcomeWindow->setMenuBar(menubar);
        statusbar = new QStatusBar(WelcomeWindow);
        statusbar->setObjectName(QString::fromUtf8("statusbar"));
        WelcomeWindow->setStatusBar(statusbar);

        retranslateUi(WelcomeWindow);

        QMetaObject::connectSlotsByName(WelcomeWindow);
    } // setupUi

    void retranslateUi(QMainWindow *WelcomeWindow)
    {
        WelcomeWindow->setWindowTitle(QApplication::translate("WelcomeWindow", "MainWindow", 0, QApplication::UnicodeUTF8));
        lblError->setText(QString());
        label->setText(QApplication::translate("WelcomeWindow", "Welcome", 0, QApplication::UnicodeUTF8));
        btnStart->setText(QApplication::translate("WelcomeWindow", "Click Here to Start", 0, QApplication::UnicodeUTF8));
        test->setText(QString());
    } // retranslateUi

};

namespace Ui {
    class WelcomeWindow: public Ui_WelcomeWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_WELCOMEWINDOW_H
