/****************************************************************************
** Meta object code from reading C++ file 'productkeywindow.h'
**
** Created: Thu Nov 8 21:53:14 2012
**      by: The Qt Meta Object Compiler version 63 (Qt 4.8.3)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../mstokesAsn2CS3325/productkeywindow.h"
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'productkeywindow.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 63
#error "This file was generated using the moc from 4.8.3. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
static const uint qt_meta_data_ProductKeyWindow[] = {

 // content:
       6,       // revision
       0,       // classname
       0,    0, // classinfo
       3,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       1,       // signalCount

 // signals: signature, parameters, type, tag, flags
      18,   17,   17,   17, 0x05,

 // slots: signature, parameters, type, tag, flags
      41,   17,   17,   17, 0x0a,
      68,   17,   17,   17, 0x0a,

       0        // eod
};

static const char qt_meta_stringdata_ProductKeyWindow[] = {
    "ProductKeyWindow\0\0successWindowRequest()\0"
    "productKeyWindowResponse()\0checkKey()\0"
};

void ProductKeyWindow::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        Q_ASSERT(staticMetaObject.cast(_o));
        ProductKeyWindow *_t = static_cast<ProductKeyWindow *>(_o);
        switch (_id) {
        case 0: _t->successWindowRequest(); break;
        case 1: _t->productKeyWindowResponse(); break;
        case 2: _t->checkKey(); break;
        default: ;
        }
    }
    Q_UNUSED(_a);
}

const QMetaObjectExtraData ProductKeyWindow::staticMetaObjectExtraData = {
    0,  qt_static_metacall 
};

const QMetaObject ProductKeyWindow::staticMetaObject = {
    { &QMainWindow::staticMetaObject, qt_meta_stringdata_ProductKeyWindow,
      qt_meta_data_ProductKeyWindow, &staticMetaObjectExtraData }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &ProductKeyWindow::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *ProductKeyWindow::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *ProductKeyWindow::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_ProductKeyWindow))
        return static_cast<void*>(const_cast< ProductKeyWindow*>(this));
    return QMainWindow::qt_metacast(_clname);
}

int ProductKeyWindow::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
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
void ProductKeyWindow::successWindowRequest()
{
    QMetaObject::activate(this, &staticMetaObject, 0, 0);
}
QT_END_MOC_NAMESPACE
