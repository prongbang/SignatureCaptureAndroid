package com.prongbang.signcapture.utils

interface PermissionHelper {
	fun verifyPermissions()
	fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray)
}