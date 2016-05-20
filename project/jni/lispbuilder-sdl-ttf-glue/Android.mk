LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := lispbuilder-sdl-ttf-glue

LOCAL_C_INCLUDES := $(LOCAL_PATH) $(LOCAL_PATH)/../sdl-$(SDL_VERSION)/include $(LOCAL_PATH)/../freetype/include $(LOCAL_PATH)/include
LOCAL_CFLAGS := -Os

LOCAL_CPP_EXTENSION := .cpp

LOCAL_SRC_FILES := lispbuilder-sdl-ttf-glue.c

LOCAL_SHARED_LIBRARIES := sdl-$(SDL_VERSION) sdl_ttf
LOCAL_STATIC_LIBRARIES := freetype
LOCAL_LDLIBS := -lz 

include $(BUILD_SHARED_LIBRARY)

