#### -*- Mode: Makefile -*-
#### Author: Matthew Danish <mrd@debian.org>
#### C glue GNU makefile

#### Modified by: Luke Crook <luke@balooga.com>

	# cp $(SHLIB_NAME).$(SHLIB_SUFFIX) ../bin
	# mv $(SHLIB_NAME).$(SHLIB_SUFFIX) ../build

### Modified for Android cross compile by David O'Toole <dto@xelf.me>

LIB_NAME=lispbuilder-sdl-ttf-glue

SHLIB_NAME=lib$(LIB_NAME)
OBJ_SUFFIX=o

CC=/opt/toolchains/android-ndk/bin/arm-linux-androideabi-gcc
SHFLAGS=-shared -fPIC
CFLAGS+=$(SHFLAGS) -g -Wall -std=gnu99
#LDFLAGS+=$(SHFLAGS) -L/usr/X11R6/lib/
SHLIB_SUFFIX=so
SDL_CONFIG=sdl-config

SDL_CFLAGS= -I/home/dto/src/sdl-android/project/jni/sdl-1.2/include/ -D_GNU_SOURCE=1 -D_REENTRANT
SDL_LDFLAGS=-L./SDL -L./SDL_ttf
# SDL_CFLAGS=$(CFLAGS) 
# SDL_LDFLAGS=$(LDFLAGS)

SDL_GLUE_CFLAGS=$(SDL_CFLAGS)
SDL_GLUE_LDFLAGS=$(SDL_LDFLAGS)

all: $(SHLIB_NAME).$(SHLIB_SUFFIX)

$(LIB_NAME).$(OBJ_SUFFIX): $(LIB_NAME).c
	$(CC) $(CPPFLAGS) $(SDL_GLUE_CFLAGS) -c -o $@ $<

$(SHLIB_NAME).$(SHLIB_SUFFIX): $(LIB_NAME).$(OBJ_SUFFIX)
	$(CC) $(SDL_GLUE_LDFLAGS) -o $@ $<

