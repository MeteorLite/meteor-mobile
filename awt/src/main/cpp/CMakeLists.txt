cmake_minimum_required(VERSION 3.4.1)

set(SOURCES
    awtcompat_native_components.c
    headers/awtcompat_native_components.h

    common/exceptions.c
    common/exceptions.h

    lcmm/cmmapi.h
    lcmm/cmmerror.h
    lcmm/cmmerror.c
    lcmm/cmmio.c
    lcmm/cmmxforms.c
    lcmm/NativeCMM.c
    lcmm/NativeCMM.h
    lcmm/NativeImageFormat.c
    lcmm/NativeImageFormat.h

    lcms/cmscam02.c
    lcms/cmscam97.c
    lcms/cmscam97.c.rej
    lcms/cmscgats.c
    lcms/cmscnvrt.c
    lcms/cmserr.c
    lcms/cmsgamma.c
    lcms/cmsgmt.c
    lcms/cmsintrp.c
    lcms/cmsio0.c
    lcms/cmsio1.c
    lcms/cmslut.c
    lcms/cmsmatsh.c
    lcms/cmsmtrx.c
    lcms/cmsnamed.c
    lcms/cmspack.c
    lcms/cmspcs.c
    lcms/cmsps2.c
    lcms/cmssamp.c
    lcms/cmsvirt.c
    lcms/cmswtpnt.c
    lcms/cmsxform.c
    lcms/icc34.h
    lcms/lcms.h
)

add_library( # Specifies the name of the library.
             awtcompat-native-components

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             ${SOURCES} )

include_directories(
    headers/
    common/
    lcms/
    lcmm/
)