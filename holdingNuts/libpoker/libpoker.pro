BASE_DIR = ../src/libpoker

TEMPLATE = lib      # creates a Makefile for building libraries
TARGET = libpoker
DESTDIR = ../build

win32: DEFINES += __WIN__

CONFIG += staticlib

CONFIG(debug, debug|release) {
    DEFINES += DEBUG
}

INCLUDEPATH += \
    ../src/general \
    ../src/system

LIBS += \
    -L"$$DESTDIR"
    -ladvapi32

DEPENDPATH += .
OBJECTS_DIR += obj
UI_DIR += "$$OUT_PWD/GeneratedFiles"
RCC_DIR += "$$OUT_PWD/GeneratedFiles"
MOC_DIR += "$$OUT_PWD/GeneratedFiles"

QMAKE_CLEAN += "$$OUT_PWD/$$DESTDIR/libpoker.*"

include(libpoker.pri)
