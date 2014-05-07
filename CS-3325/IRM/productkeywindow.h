#ifndef PRODUCTKEYWINDOW_H
#define PRODUCTKEYWINDOW_H

#include <QMainWindow>
#include <QString>

namespace Ui {
class ProductKeyWindow;
}

class ProductKeyWindow : public QMainWindow
{
    Q_OBJECT
    
public:
    explicit ProductKeyWindow(QWidget *parent = 0);
    ~ProductKeyWindow();
    bool sqlKeyMatch(QString);
    bool sqlValidUser();

signals:
    void successWindowRequest();


public slots:
    void productKeyWindowResponse();
    void checkKey();


private:
    Ui::ProductKeyWindow *ui;
};

#endif // PRODUCTKEYWINDOW_H
