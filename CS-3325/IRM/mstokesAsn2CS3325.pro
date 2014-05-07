#-------------------------------------------------
#
# Project created by QtCreator 2012-11-04T11:43:50
#
#-------------------------------------------------

QT       += core gui sql

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = mstokesAsn2CS3325
TEMPLATE = app

CONFIG += static

SOURCES += main.cpp\
        successwindow.cpp \
    productkeywindow.cpp \
    welcomewindow.cpp

HEADERS  += successwindow.h \
    productkeywindow.h \
    welcomewindow.h

FORMS    += successwindow.ui \
    productkeywindow.ui \
    welcomewindow.ui
