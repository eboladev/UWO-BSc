#ifndef WELCOMEWINDOW_H
#define WELCOMEWINDOW_H

#include <QMainWindow>

namespace Ui {
class WelcomeWindow;
}

class WelcomeWindow : public QMainWindow
{
    Q_OBJECT
    
public:
    explicit WelcomeWindow(QWidget *parent = 0);
    ~WelcomeWindow();
    bool fileCheck();
    bool contentCheck();
    bool keysMatch(const char *, const char *);

signals:
    void productKeyWindowRequest();
    void successWindowRequest();

public slots:
void licenseCheck();

private:
    Ui::WelcomeWindow *ui;
};

#endif // WELCOMEWINDOW_H

