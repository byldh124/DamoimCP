package com.moondroid.project01_meetingapp.ui.features.common.imagelist

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.net.toUri
import com.canhub.cropper.CropImageView
import com.moondroid.damoim.common.constant.IntentParam
import com.moondroid.damoim.common.util.logException
import com.moondroid.project01_meetingapp.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class CropImageActivity : AppCompatActivity() {
    private lateinit var binding: View

    private val cropImageView: CropImageView by lazy {
        binding.findViewById(R.id.crop_image_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = layoutInflater.inflate(R.layout.activity_crop_image, null, false)
        setContentView(binding)
        initView()
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun initView() {
        val uri = intent.data
        val ratio = intent.getIntExtra(IntentParam.CROP_IMAGE_WITH_RATIO, 1)

        cropImageView.setImageUriAsync(uri)
        cropImageView.setAspectRatio(ratio, 1)

        binding.findViewById<AppCompatImageView>(R.id.ic_back).setOnClickListener {
            finish()
        }

        binding.findViewById<AppCompatTextView>(R.id.btn_save).setOnClickListener {
            try {
                cropImageAndSaveCacheFile()
            } catch (e: Exception) {
                logException(e)
            }
        }
    }

    @Throws(FileNotFoundException::class, IOException::class, IllegalStateException::class)
    private fun cropImageAndSaveCacheFile() {
        val cropBitmap = cropImageView.getCroppedImage()
            ?: throw IllegalStateException("Cropped image must not be null")

        val cacheStorage = cacheDir
        val fileName = "profile_thumb_image_cached.jpg"
        val tempFile = File(cacheStorage, fileName)

        if (!tempFile.exists()) {
            tempFile.createNewFile()
        }
        val out = FileOutputStream(tempFile)

        cropBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)

        out.close()

        val recvIntent = Intent()
        recvIntent.data = tempFile.toUri()
        setResult(RESULT_OK, recvIntent)
        finish()
    }
}