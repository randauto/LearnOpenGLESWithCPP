package com.bip.mediax

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bip.learnopengleswithcpp.R
import com.bip.utils.PathUtils
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class VideoSnapActivity : AppCompatActivity() {
    var pathVideo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_snap_activity)

        findViewById<Button>(R.id.btnSelectVideo).setOnClickListener {
            Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                        val intent = Intent()
                        intent.type = "video/*"
                        intent.action = Intent.ACTION_PICK
                        startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */

                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) { /* ... */

                    }
                }).check()

        }
    }


    private fun getPath(context: Context, uri: Uri): String {
        val pathUtils = PathUtils()
        return pathUtils.getUriRealPath(context, uri)
    }

    companion object {
        const val REQUEST_TAKE_GALLERY_VIDEO = 1000
    }

    private fun getImageVideoFromPath(pathVideo: String) {
        val media = MediaMetadataRetriever()
        try {
            media.setDataSource(pathVideo)
            // get frame rate of video.
            val frameRate = getFrameRate(pathVideo)
            Log.d("VideoSnapActivity", "frameRate = $frameRate")
            var fpsTime = 1000 / frameRate
            val duration = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            Log.d("VideoSnapActivity", "duration = $duration")
            val totalFrame = fpsTime * (duration!!.toLong() / 1000)
            Log.d("VideoSnapActivity", "totalFrame = $totalFrame")

            val looper = duration.toLong() / 1000
            val listImageBitmap = ArrayList<Bitmap>()
            var loop = 1000000
            for (i in 0..looper) {
                val extractedImage = media.getFrameAtTime(loop.toLong(), MediaMetadataRetriever.OPTION_CLOSEST)
                extractedImage?.let {
                    listImageBitmap.add(extractedImage)
                    Log.d("VideoSnapActivity", "width = ${extractedImage!!.width} height = ${extractedImage!!.height}")
//                    imgFrame?.setImageBitmap(extractedImage)
                    loop += 1000000
                }
            }

            Log.d("VideoSnapActivity", "list image size: ${listImageBitmap.size}")

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun getFrameRate(pathVideo: String): Int {
        val extractor = MediaExtractor()
        var frameRate = 24
        try {
            extractor.setDataSource(pathVideo)
            val numTracks = extractor.trackCount
            for (i in 0..numTracks) {
                val format = extractor.getTrackFormat(i)
                val mime = format.getString(MediaFormat.KEY_MIME)
                if (mime != null && mime.startsWith("video/")) {
                    if (format.containsKey(MediaFormat.KEY_FRAME_RATE)) {
                        frameRate = format.getInteger(MediaFormat.KEY_FRAME_RATE)
                    }
                }
            }
        } catch (ex: Exception) {

        }

        return frameRate
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                val selectedImageUri = data!!.data

                selectedImageUri?.let {
                    pathVideo = getPath(this@VideoSnapActivity, selectedImageUri)
                    getImageVideoFromPath(pathVideo)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}