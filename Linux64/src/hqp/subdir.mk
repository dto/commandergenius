################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/hqp/CHQBitmap.cpp \
../src/hqp/CMusic.cpp \
../src/hqp/hq_sound.cpp \
../src/hqp/musicdrv.cpp 

OBJS += \
./src/hqp/CHQBitmap.o \
./src/hqp/CMusic.o \
./src/hqp/hq_sound.o \
./src/hqp/musicdrv.o 

CPP_DEPS += \
./src/hqp/CHQBitmap.d \
./src/hqp/CMusic.d \
./src/hqp/hq_sound.d \
./src/hqp/musicdrv.d 


# Each subdirectory must supply rules for building sources it contributes
src/hqp/%.o: ../src/hqp/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -DBUILD_SDL -DBUILD_WITH_OGG -DTARGET_LNX -I/usr/include/SDL -I/usr/include/vorbis -O3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


