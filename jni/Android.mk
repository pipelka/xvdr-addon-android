
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_CPP_EXTENSION := .cpp
LOCAL_MODULE    := xvdraddon
LOCAL_SRC_FILES := \
	../addon/src/libxvdr/src/clientinterface.cpp \
	../addon/src/libxvdr/src/connection.cpp \
	../addon/src/libxvdr/src/dataset.cpp \
	../addon/src/libxvdr/src/demux.cpp \
	../addon/src/libxvdr/src/iso639.cpp \
	../addon/src/libxvdr/src/os-config.cpp \
	../addon/src/libxvdr/src/msgpacket.cpp \
	../addon/src/libxvdr/src/session.cpp \
	../addon/src/libxvdr/src/thread.cpp \
	../addon/src/xvdr/dialogs/GUIDialogBase.cpp \
	../addon/src/xvdr/dialogs/GUIDialogChannelScanner.cpp \
	../addon/src/xvdr/dialogs/GUIDialogOk.cpp \
	../addon/src/xvdr/dialogs/GUIDialogYesNo.cpp \
	../addon/src/xvdr/XBMCAddon.cpp \
	../addon/src/xvdr/XBMCClient.cpp \
	../addon/src/xvdr/XBMCSettings.cpp

LOCAL_CFLAGS := \
	-I$(LOCAL_PATH)/../addon/src/libxvdr/include \
	-I$(LOCAL_PATH)/../addon/src/xvdr/include \
	-I$(LOCAL_PATH)/../addon/src/xvdr/dialogs \
	-I$(LOCAL_PATH)/../addon/src/xvdr \
	-I$(LOCAL_PATH)/../addon \
	-DHAVE_ZLIB=1 -DUSE_DEMUX=1

LOCAL_LDLIBS := -lz

include $(BUILD_SHARED_LIBRARY)
