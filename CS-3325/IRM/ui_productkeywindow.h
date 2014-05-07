/********************************************************************************
** Form generated from reading UI file 'productkeywindow.ui'
**
** Created: Thu Nov 8 18:43:16 2012
**      by: Qt User Interface Compiler version 4.8.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_PRODUCTKEYWINDOW_H
#define UI_PRODUCTKEYWINDOW_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QHBoxLayout>
#include <QtGui/QHeaderView>
#include <QtGui/QLabel>
#include <QtGui/QLineEdit>
#include <QtGui/QMainWindow>
#include <QtGui/QMenuBar>
#include <QtGui/QPushButton>
#include <QtGui/QSpacerItem>
#include <QtGui/QStatusBar>
#include <QtGui/QWidget>

QT_BEGIN_NAMESPACE

class Ui_ProductKeyWindow
{
public:
    QWidget *centralwidget;
    QLabel *label;
    QWidget *layoutWidget;
    QHBoxLayout *horizontalLayout;
    QLineEdit *l1;
    QSpacerItem *horizontalSpacer;
    QLineEdit *l2;
    QSpacerItem *horizontalSpacer_2;
    QLineEdit *l3;
    QSpacerItem *horizontalSpacer_3;
    QLineEdit *l4;
    QWidget *layoutWidget1;
    QHBoxLayout *horizontalLayout_2;
    QPushButton *btnClose;
    QPushButton *btnSubmit;
    QLabel *lblError;
    QLabel *lblInUse;
    QLabel *lblInvalid;
    QMenuBar *menubar;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *ProductKeyWindow)
    {
        if (ProductKeyWindow->objectName().isEmpty())
            ProductKeyWindow->setObjectName(QString::fromUtf8("ProductKeyWindow"));
        ProductKeyWindow->resize(719, 245);
        centralwidget = new QWidget(ProductKeyWindow);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        label = new QLabel(centralwidget);
        label->setObjectName(QString::fromUtf8("label"));
        label->setGeometry(QRect(20, 10, 1131, 91));
        QFont font;
        font.setPointSize(36);
        font.setBold(true);
        font.setItalic(true);
        font.setWeight(75);
        label->setFont(font);
        layoutWidget = new QWidget(centralwidget);
        layoutWidget->setObjectName(QString::fromUtf8("layoutWidget"));
        layoutWidget->setGeometry(QRect(20, 110, 651, 25));
        horizontalLayout = new QHBoxLayout(layoutWidget);
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));
        horizontalLayout->setContentsMargins(0, 0, 0, 0);
        l1 = new QLineEdit(layoutWidget);
        l1->setObjectName(QString::fromUtf8("l1"));
        l1->setMaxLength(5);
        l1->setAlignment(Qt::AlignCenter);

        horizontalLayout->addWidget(l1);

        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);

        l2 = new QLineEdit(layoutWidget);
        l2->setObjectName(QString::fromUtf8("l2"));
        l2->setMaxLength(5);
        l2->setAlignment(Qt::AlignCenter);

        horizontalLayout->addWidget(l2);

        horizontalSpacer_2 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_2);

        l3 = new QLineEdit(layoutWidget);
        l3->setObjectName(QString::fromUtf8("l3"));
        l3->setMaxLength(5);
        l3->setAlignment(Qt::AlignCenter);

        horizontalLayout->addWidget(l3);

        horizontalSpacer_3 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_3);

        l4 = new QLineEdit(layoutWidget);
        l4->setObjectName(QString::fromUtf8("l4"));
        l4->setMaxLength(5);
        l4->setAlignment(Qt::AlignCenter);

        horizontalLayout->addWidget(l4);

        layoutWidget1 = new QWidget(centralwidget);
        layoutWidget1->setObjectName(QString::fromUtf8("layoutWidget1"));
        layoutWidget1->setGeometry(QRect(270, 170, 161, 26));
        horizontalLayout_2 = new QHBoxLayout(layoutWidget1);
        horizontalLayout_2->setObjectName(QString::fromUtf8("horizontalLayout_2"));
        horizontalLayout_2->setContentsMargins(0, 0, 0, 0);
        btnClose = new QPushButton(layoutWidget1);
        btnClose->setObjectName(QString::fromUtf8("btnClose"));

        horizontalLayout_2->addWidget(btnClose);

        btnSubmit = new QPushButton(layoutWidget1);
        btnSubmit->setObjectName(QString::fromUtf8("btnSubmit"));

        horizontalLayout_2->addWidget(btnSubmit);

        lblError = new QLabel(centralwidget);
        lblError->setObjectName(QString::fromUtf8("lblError"));
        lblError->setGeometry(QRect(274, 150, 161, 20));
        lblError->setStyleSheet(QString::fromUtf8("QLabel {color: red;}"));
        lblInUse = new QLabel(centralwidget);
        lblInUse->setObjectName(QString::fromUtf8("lblInUse"));
        lblInUse->setGeometry(QRect(230, 140, 201, 16));
        lblInUse->setStyleSheet(QString::fromUtf8("QLabel {color: red;}"));
        lblInvalid = new QLabel(centralwidget);
        lblInvalid->setObjectName(QString::fromUtf8("lblInvalid"));
        lblInvalid->setGeometry(QRect(230, 140, 211, 21));
        lblInvalid->setStyleSheet(QString::fromUtf8("QLabel {color: red;}"));
        ProductKeyWindow->setCentralWidget(centralwidget);
        layoutWidget->raise();
        layoutWidget->raise();
        label->raise();
        lblError->raise();
        lblInUse->raise();
        lblInvalid->raise();
        menubar = new QMenuBar(ProductKeyWindow);
        menubar->setObjectName(QString::fromUtf8("menubar"));
        menubar->setGeometry(QRect(0, 0, 719, 21));
        ProductKeyWindow->setMenuBar(menubar);
        statusbar = new QStatusBar(ProductKeyWindow);
        statusbar->setObjectName(QString::fromUtf8("statusbar"));
        ProductKeyWindow->setStatusBar(statusbar);

        retranslateUi(ProductKeyWindow);
        QObject::connect(btnClose, SIGNAL(clicked()), ProductKeyWindow, SLOT(close()));

        QMetaObject::connectSlotsByName(ProductKeyWindow);
    } // setupUi

    void retranslateUi(QMainWindow *ProductKeyWindow)
    {
        ProductKeyWindow->setWindowTitle(QApplication::translate("ProductKeyWindow", "MainWindow", 0, QApplication::UnicodeUTF8));
        label->setText(QApplication::translate("ProductKeyWindow", "Please Enter Your Product Key", 0, QApplication::UnicodeUTF8));
        btnClose->setText(QApplication::translate("ProductKeyWindow", "Close", 0, QApplication::UnicodeUTF8));
        btnSubmit->setText(QApplication::translate("ProductKeyWindow", "Submit", 0, QApplication::UnicodeUTF8));
        lblError->setText(QString());
        lblInUse->setText(QString());
        lblInvalid->setText(QString());
    } // retranslateUi

};

namespace Ui {
    class ProductKeyWindow: public Ui_ProductKeyWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_PRODUCTKEYWINDOW_H
