BASE_DIR = ../src/client

TEMPLATE = app      # creates a Makefile for building application
TARGET = client     # name of the target file
DESTDIR = ../build  # folder where to put the target file

QT += core widgets network

DEFINES += QT_DLL QT_CORE_LIB QT_WIDGETS_LIB QT_NETWORK_LIB

CONFIG(debug, debug|release) {
    DEFINES += DEBUG
}

LIBS += \
    -L"$$DESTDIR" \
    -llibpoker \
    -L$${PWD}/../libs/libSDL/lib/x86 \
    -L$${PWD}/../libs/libSDL/lib/x64 \
    -lSDL2

INCLUDEPATH += \
    ../libs/libSDL/include \
    ../src/general \
    ../src/libpoker \
    ../src/server \
    ../src/system

win32 {
    DEFINES += __WIN__

    LIBS += \
        -luser32 \
        -ladvapi32
}

DEPENDPATH += .
OBJECTS_DIR += obj
UI_DIR += "$$OUT_PWD/GeneratedFiles"
RCC_DIR += "$$OUT_PWD/GeneratedFiles"
MOC_DIR += "$$OUT_PWD/GeneratedFiles"

QMAKE_CLEAN += "$$OUT_PWD/$$DESTDIR/client.*"

include(client.pri)
win32:RC_FILE = "$$BASE_DIR/pclient.rc"

RESOURCES += \
    "$$BASE_DIR/pclient.qrc"
