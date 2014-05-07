/****************************************************************************
** Meta object code from reading C++ file 'welcomewindow.h'
**
** Created: Wed Nov 7 12:53:52 2012
**      by: The Qt Meta Object Compiler version 63 (Qt 4.8.3)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../mstokesAsn2CS3325/welcomewindow.h"
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'welcomewindow.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 63
#error "This file was generated using the moc from 4.8.3. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
static const uint qt_meta_data_WelcomeWindow[] = {

 // content:
       6,       // revision
       0,       // classname
       0,    0, // classinfo
       3,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       2,       // signalCount

 // signals: signature, parameters, type, tag, flags
      15,   14,   14,   14, 0x05,
      41,   14,   14,   14, 0x05,

 // slots: signature, parameters, type, tag, flags
      64,   14,   14,   14, 0x0a,

       0        // eod
};

static const char qt_meta_stringdata_WelcomeWindow[] = {
    "WelcomeWindow\0\0productKeyWindowRequest()\0"
    "successWindowRequest()\0licenseCheck()\0"
};

void WelcomeWindow::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        Q_ASSERT(staticMetaObject.cast(_o));
        WelcomeWindow *_t = static_cast<WelcomeWindow *>(_o);
        switch (_id) {
        case 0: _t->productKeyWindowRequest(); break;
        case 1: _t->successWindowRequest(); break;
        case 2: _t->licenseCheck(); break;
        default: ;
        }
    }
    Q_UNUSED(_a);
}

const QMetaObjectExtraData WelcomeWindow::staticMetaObjectExtraData = {
    0,  qt_static_metacall 
};

const QMetaObject WelcomeWindow::staticMetaObject = {
    { &QMainWindow::staticMetaObject, qt_meta_stringdata_WelcomeWindow,
      qt_meta_data_WelcomeWindow, &staticMetaObjectExtraData }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &WelcomeWindow::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *WelcomeWindow::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *WelcomeWindow::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_WelcomeWindow))
        return static_cast<void*>(const_cast< WelcomeWindow*>(this));
    return QMainWindow::qt_metacast(_clname);
}

int WelcomeWindow::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QMainWindow::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 3)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 3;
    }
    return _id;
}

// SIGNAL 0
void WelcomeWindow::productKeyWindowRequest()
{
    QMetaObject::activate(this, &staticMetaObject, 0, 0);
}

// SIGNAL 1
void WelcomeWindow::successWindowRequest()
{
    QMetaObject::activate(this, &staticMetaObject, 1, 0);
}
QT_END_MOC_NAMESPACE
