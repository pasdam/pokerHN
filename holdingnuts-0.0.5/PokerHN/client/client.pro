
BASE_DIR = ../../src/client

TEMPLATE = app
TARGET = client
DESTDIR = ../build

QT += core widgets network
#QT += core network xml webkit declarative uitools widgets gui qml quick webkitwidgets concurrent multimedia
DEFINES += QT_DLL QT_CORE_LIB QT_WIDGETS_LIB QT_NETWORK_LIB
#DEFINES += __WIN__ QT_LARGEFILE_SUPPORT _WINDOWS QT_DLL QT_HAVE_MMX QT_HAVE_3DNOW QT_HAVE_SSE QT_HAVE_MMXEXT QT_HAVE_SSE2 QT_GUI_LIB QT_NETWORK_LIB QT_CORE_LIB QT_THREAD_SUPPORT QT_DECLARATIVE_LIB QT_WEBKITWIDGETS_LIB NOMINMAX QT_CONCURRENT_LIB QT_WIDGETS_LIB QT_QML_LIB QT_QUICK_LIB

CONFIG(debug, debug|release) {
    DEFINES += DEBUG
} else {
}

INCLUDEPATH += \
    ../../SDL2-2.0.4/include \
    ../../src/general \
    ../../src/libpoker \
    ../../src/server \
    ../../src/system

LIBS += \
    -L"$$DESTDIR" \
    -llibpoker \
    -lSDL2
#    -luser32 \
#    -ladvapi32

DEPENDPATH += .
OBJECTS_DIR += obj
UI_DIR += $$OUT_PWD/GeneratedFiles
RCC_DIR += $$OUT_PWD/GeneratedFiles
MOC_DIR += $$OUT_PWD/GeneratedFiles

QMAKE_CLEAN += $$OUT_PWD/$$DESTDIR/client.*

include(client.pri)
win32:RC_FILE = $$BASE_DIR/pclient.rc

RESOURCES += \
    $$BASE_DIR/pclient.qrc
