package com.prongbang.signcapture.utils

interface PermissionHelper {
	fun verifyStoragePermissions()
	fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray)
}