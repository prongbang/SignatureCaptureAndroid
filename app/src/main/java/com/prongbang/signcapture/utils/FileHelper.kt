package com.prongbang.signcapture.utils

import android.graphics.Bitmap
import java.io.File

interface FileHelper {
	fun addSvgSignatureToGallery(signatureSvg: String): Boolean
	fun addJpgSignatureToGallery(signature: Bitmap): Boolean
	fun scanMediaFile(photo: File)
	fun getAlbumStorageDir(albumName: String): File
	fun saveBitmapToJPG(bitmap: Bitmap, photo: File)
}