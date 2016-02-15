
BASE_DIR = ../../src/libpoker

TEMPLATE = lib
TARGET = libpoker
DESTDIR = ../build

#DEFINES += __WIN__

CONFIG += staticlib

CONFIG(debug, debug|release) {
    DEFINES += DEBUG
} else {
}

INCLUDEPATH += \
    ../../src/general \
    ../../src/system

LIBS += \
    -L"$$DESTDIR"
#    -ladvapi32
# \
#    -lCrashRpt1402 \
#    -lcommon \
#    -lcontroller \
##    -lcontroller \
###    -lcontroller \
#    -ldevlibcomm \
#    -ldevlib \
#    -lgui \
#    -llobby \
##    -llobby \
###    -llobby \
#    -lqmltable \
#    -lxml \
#    -lkernel32 \
#    -luser32 \
#    -lgdi32 \
#    -lgdi32 \
#    -lwinspool \
#    -lcomdlg32 \
#    -lshell32 \
#    -lole32 \
#    -loleaut32 \
#    -luuid \
#    -lodbc32 \
#    -lodbccp32
#*/

DEPENDPATH += .
OBJECTS_DIR += obj
UI_DIR += $$OUT_PWD/GeneratedFiles
RCC_DIR += $$OUT_PWD/GeneratedFiles
MOC_DIR += $$OUT_PWD/GeneratedFiles

QMAKE_CLEAN += $$OUT_PWD/$$DESTDIR/libpoker.*

include(libpoker.pri)
