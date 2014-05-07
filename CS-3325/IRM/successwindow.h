#ifndef SUCCESSWINDOW_H
#define SUCCESSWINDOW_H

#include <QMainWindow>

namespace Ui {
class SuccessWindow;
}

class SuccessWindow : public QMainWindow
{
    Q_OBJECT
    
public:
    explicit SuccessWindow(QWidget *parent = 0);
    ~SuccessWindow();
    
public slots:
    void successWindowResponse();

private:
    Ui::SuccessWindow *ui;
};

#endif // SUCCESSWINDOW_H
