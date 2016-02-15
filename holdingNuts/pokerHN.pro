TEMPLATE = subdirs

PokerHN_libpoker.subdir		= libpoker
PokerHN_libpoker.target		= sub-PokerHN-libpoker

PokerHN_client.subdir		= client
PokerHN_client.target		= sub-PokerHN-client
PokerHN_client.depends		= sub-PokerHN-libpoker

PokerHN_server.subdir		= server
PokerHN_server.target		= sub-PokerHN-server
PokerHN_server.depends		= sub-PokerHN-libpoker

SUBDIRS += \
        PokerHN_libpoker \
        PokerHN_client \
        PokerHN_server
