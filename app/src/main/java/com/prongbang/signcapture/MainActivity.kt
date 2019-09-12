package com.prongbang.signcapture

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.gcacace.signaturepad.views.SignaturePad
import com.prongbang.signcapture.utils.FileHelper
import com.prongbang.signcapture.utils.ImageFileHelper
import com.prongbang.signcapture.utils.PermissionHelper
import com.prongbang.signcapture.utils.StoragePermissionHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	private val storagePermissionHelper: PermissionHelper by lazy { StoragePermissionHelper(this) }

	private val imageFileHelper: FileHelper by lazy {
		ImageFileHelper(this)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		storagePermissionHelper.verifyStoragePermissions()
		setContentView(R.layout.activity_main)

		// disable both buttons at start
		saveButton.isEnabled = false
		clearButton.isEnabled = false

		// change screen orientation to landscape mode
		requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

		signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
			override fun onStartSigning() {
				Toast.makeText(this@MainActivity, "OnStartSigning", Toast.LENGTH_SHORT)
						.show()
			}

			override fun onSigned() {
				saveButton.isEnabled = true
				clearButton.isEnabled = true
			}

			override fun onClear() {
				saveButton.isEnabled = false
				clearButton.isEnabled = false
			}
		})

		saveButton.setOnClickListener {
			saveSignatureToImage(signaturePad)
		}

		clearButton.setOnClickListener { signaturePad.clear() }
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
	                                        grantResults: IntArray) {
		storagePermissionHelper.onRequestPermissionsResult(requestCode, grantResults)
	}

	private fun saveSignatureToImage(signaturePad: SignaturePad?) {
		signaturePad?.let { pad ->

			val signatureBitmap = pad.signatureBitmap
			if (imageFileHelper.addJpgSignatureToGallery(signatureBitmap)) {
				Toast.makeText(this@MainActivity, "Signature saved into the Gallery",
						Toast.LENGTH_SHORT)
						.show()
			} else {
				Toast.makeText(this@MainActivity, "Unable to store the signature",
						Toast.LENGTH_SHORT)
						.show()
			}
			if (imageFileHelper.addSvgSignatureToGallery(pad.signatureSvg)) {
				Toast.makeText(this@MainActivity, "SVG Signature saved into the Gallery",
						Toast.LENGTH_SHORT)
						.show()
			} else {
				Toast.makeText(this@MainActivity, "Unable to store the SVG signature",
						Toast.LENGTH_SHORT)
						.show()
			}
		}
	}
}
