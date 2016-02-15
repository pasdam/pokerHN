BASE_DIR = ../src/server

TEMPLATE = app
TARGET = server
DESTDIR = ../build

QT += core network

DEFINES += QT_DLL QT_CORE_LIB QT_NETWORK_LIB

CONFIG(debug, debug|release) {
    DEFINES += DEBUG
}

INCLUDEPATH += \
    ../src/general \
    ../src/libpoker \
    ../src/system

LIBS += \
    -L"$$DESTDIR" \
    -llibpoker

win32 {
    DEFINES += __WIN__

    LIBS += \
        -ladvapi32 \
        -lws2_32
}

DEPENDPATH += .
OBJECTS_DIR += obj
UI_DIR += $$OUT_PWD/GeneratedFiles
RCC_DIR += $$OUT_PWD/GeneratedFiles
MOC_DIR += $$OUT_PWD/GeneratedFiles

QMAKE_CLEAN += $$OUT_PWD/$$DESTDIR/server.*

include(server.pri)
win32:RC_FILE = $$BASE_DIR/pserver.rc
