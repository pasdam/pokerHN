
HEADERS += \
    $$BASE_DIR/Card.hpp \
    $$BASE_DIR/CommunityCards.hpp \
    $$BASE_DIR/Deck.hpp \
    $$BASE_DIR/GameDebug.hpp \
    $$BASE_DIR/GameLogic.hpp \
    $$BASE_DIR/HoleCards.hpp \
    $$BASE_DIR/Player.hpp \
    $$BASE_DIR/../system/Logger.h \
    $$BASE_DIR/../system/ConfigParser.hpp \
    $$BASE_DIR/../system/Tokenizer.hpp \
    $$BASE_DIR/../system/SysAccess.h \
    $$BASE_DIR/../system/Network.h

#win32: HEADERS += $$BASE_DIR/../system/dirent.h

SOURCES += \
    $$BASE_DIR/Card.cpp \
    $$BASE_DIR/CommunityCards.cpp \
    $$BASE_DIR/Deck.cpp \
    $$BASE_DIR/GameDebug.cpp \
    $$BASE_DIR/GameLogic.cpp \
    $$BASE_DIR/HoleCards.cpp \
    $$BASE_DIR/Player.cpp \
    $$BASE_DIR/../system/Logger.c \
    $$BASE_DIR/../system/ConfigParser.cpp \
    $$BASE_DIR/../system/Tokenizer.cpp \
    $$BASE_DIR/../system/SysAccess.c \
    $$BASE_DIR/../system/Network.c

#win32: SOURCES += $$BASE_DIR/../system/dirent.c
