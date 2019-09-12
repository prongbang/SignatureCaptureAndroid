package com.prongbang.signcapture.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

class ImageFileHelper(private val activity: Activity) : FileHelper {

	override fun addJpgSignatureToGallery(signature: Bitmap): Boolean {
		var result = false
		try {
			val photo = File(getAlbumStorageDir("SignaturePad"),
					String.format("Signature_%d.jpg", System.currentTimeMillis()))
			saveBitmapToJPG(signature, photo)
			scanMediaFile(photo)
			result = true
		} catch (e: IOException) {
			e.printStackTrace()
		}

		return result
	}

	@Throws(IOException::class)
	override fun saveBitmapToJPG(bitmap: Bitmap, photo: File) {
		val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
		val canvas = Canvas(newBitmap)
		canvas.drawColor(Color.WHITE)
		canvas.drawBitmap(bitmap, 0f, 0f, null)
		val stream = FileOutputStream(photo)
		newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
		stream.close()
	}

	override fun getAlbumStorageDir(albumName: String): File {
		// Get the directory for the user's public pictures directory.
		val file = File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				albumName)
		if (!file.mkdirs()) {
			Log.e("SignaturePad", "Directory not created")
		}
		return file
	}

	override fun addSvgSignatureToGallery(signatureSvg: String): Boolean {
		var result = false
		try {
			val svgFile = File(getAlbumStorageDir("SignaturePad"),
					String.format("Signature_%d.svg", System.currentTimeMillis()))
			val stream = FileOutputStream(svgFile)
			val writer = OutputStreamWriter(stream)
			writer.write(signatureSvg)
			writer.close()
			stream.flush()
			stream.close()
			scanMediaFile(svgFile)
			result = true
		} catch (e: IOException) {
			e.printStackTrace()
		}

		return result
	}

	override fun scanMediaFile(photo: File) {
		val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
		val contentUri = Uri.fromFile(photo)
		mediaScanIntent.data = contentUri
		activity.sendBroadcast(mediaScanIntent)
	}

}