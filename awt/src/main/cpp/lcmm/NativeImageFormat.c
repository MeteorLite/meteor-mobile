/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/**
 * @author Oleg V. Khaschansky
 * 
 */

#include "NativeImageFormat.h"
#include "exceptions.h"

jfieldID clr_NIF_cmmFormatID;
jfieldID clr_NIF_colsID;
jfieldID clr_NIF_rowsID;
jfieldID clr_NIF_scanlineStrideID;
jfieldID clr_NIF_imageDataID;
jfieldID clr_NIF_dataOffsetID;
jfieldID clr_NIF_alphaOffsetID;

ImageFormat* getImageFormat(JNIEnv* env, jobject jimft) {
    // Create the structure.
    ImageFormat *imft = malloc(sizeof(ImageFormat));

  imft->cmmFormat = (int) (*env)->GetIntField(env, jimft, clr_NIF_cmmFormatID);
    imft->cols = (int) (*env)->GetIntField(env, jimft, clr_NIF_colsID);
    imft->rows = (int) (*env)->GetIntField(env, jimft, clr_NIF_rowsID);
    imft->scanlineStride = (int) (*env)->GetIntField(env, jimft, clr_NIF_scanlineStrideID);
  imft->dataOffset = (int) (*env)->GetIntField(env, jimft, clr_NIF_dataOffsetID);
  imft->alphaOffset = (int) (*env)->GetIntField(env, jimft, clr_NIF_alphaOffsetID);

    // Get image data
  imft->jImageData = (jarray) (*env)->GetObjectField(env, jimft, clr_NIF_imageDataID);
    imft->imageData = (BYTE*) (*env)->GetPrimitiveArrayCritical(env, imft->jImageData, 0);

  if(imft->imageData == NULL) { // All is lost, we don't have C array
      throwNPException(env, "Error while accessing java image data");
      // Free resources and stop further processing...
        releaseImageFormat(env, imft);
        return NULL;
    }

    return imft;
}

void releaseImageFormat(JNIEnv* env, ImageFormat* imft) {
    if(imft == NULL) return; // nothing to do

    // Release java array
    if(imft->imageData != NULL)
    (*env)->ReleasePrimitiveArrayCritical(env, imft->jImageData, imft->imageData, 0);

    // Free memory
    free(imft);
}
