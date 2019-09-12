package com.prongbang.signcapture.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat


class StoragePermissionHelper(private val activity: Activity) : PermissionHelper {

	override fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
		when (requestCode) {
			REQUEST_EXTERNAL_STORAGE -> {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.isEmpty() || grantResults[0] !== PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(activity, "Cannot write images to external storage",
							Toast.LENGTH_SHORT)
							.show()
				}
			}
		}
	}

	override fun verifyPermissions() {
		// Check if we have write permission
		val permission = ActivityCompat.checkSelfPermission(activity,
				Manifest.permission.WRITE_EXTERNAL_STORAGE)

		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(
					activity,
					PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE
			)
		}
	}

	companion object {
		private val REQUEST_EXTERNAL_STORAGE = 1
		private val PERMISSIONS_STORAGE = arrayOf<String>(
				Manifest.permission.WRITE_EXTERNAL_STORAGE)
	}
}