#include "successwindow.h"
#include "ui_successwindow.h"

SuccessWindow::SuccessWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::SuccessWindow)
{
    ui->setupUi(this);
}

SuccessWindow::~SuccessWindow()
{
    delete ui;
}

void SuccessWindow::successWindowResponse()
{
    this->show();
}
