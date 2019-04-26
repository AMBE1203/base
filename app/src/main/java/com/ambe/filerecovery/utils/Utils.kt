package com.ambe.filerecovery.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import com.ambe.filerecovery.model.FileObject
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by AMBE on 10/4/2019 at 14:09 PM.
 */
object Utils {
    // utility function
    private fun bytesToHexString(bytes: ByteArray): String {
        // http://stackoverflow.com/questions/332079
        val sb = StringBuffer()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }

    // generate a hash
    fun sha256(s: String): String {
        val digest: MessageDigest
        val hash: String

        return try {
            digest = MessageDigest.getInstance("SHA-256")
            digest.update(s.toByteArray())

            hash = bytesToHexString(digest.digest())

            hash
        } catch (e1: NoSuchAlgorithmException) {
            s
        }

    }


    fun animateDialog(viewGroup: ViewGroup) {
        val set = AnimatorSet()
        val animatorX = ObjectAnimator.ofFloat(viewGroup, ViewGroup.SCALE_X, 0.7f, 1f)
        val animatorY = ObjectAnimator.ofFloat(viewGroup, ViewGroup.SCALE_Y, 0.7f, 1f)
        set.playTogether(animatorX, animatorY)
        set.interpolator = BounceInterpolator()
        set.duration = 500
        set.start()
    }

    fun getFolder(folder: String): String {
        return Environment.getExternalStorageDirectory().toString() + File.separator + folder + File.separator
    }

    fun formatDate(date: Long): String {
        val format: SimpleDateFormat = SimpleDateFormat("MMM d")
        return format.format(Date(date))
    }


    fun getAllAudios(c: Context): List<File> {
        val files = ArrayList<File>()
        val projection =
            arrayOf(MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.Media.DISPLAY_NAME)
        val selection = MediaStore.Audio.Media.DATA + " like ? "
        var arg: Array<String>? = arrayOf("%${Constants.FR_SOUND}%")

        val orderBy = MediaStore.Audio.Media.DATE_ADDED//order data by date
        val orderByName = "upper(" + MediaStore.Audio.Media.DISPLAY_NAME + ")"
        var cursor: Cursor? = c.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            arg,
            "$orderBy ASC"
        )

        try {
            cursor?.moveToFirst()
            do {
                files.add(File(cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))))
            } while (cursor!!.moveToNext())
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return files
    }


    fun getFileImages(c: Context): List<File> {
        val files = ArrayList<File>()
        val projection =
            arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)
        val selection = MediaStore.Images.Media.DATA + " like ? "
        var arg: Array<String>? = arrayOf("%${Constants.FR_IMAGE}%")
        val orderBy = MediaStore.Images.Media.DATE_ADDED//order data by date
        val orderByName = "upper(" + MediaStore.Images.Media.DISPLAY_NAME + ")"
        var cursor: Cursor? = c.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            arg,
            "$orderBy ASC"
        )
        try {
            cursor?.moveToFirst()
            do {
                files.add(File(cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))))
            } while (cursor!!.moveToNext())
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return files
    }

    fun getAllImages(c: Context): ArrayList<FileObject> {
        var fileObject = arrayListOf<FileObject>()
        if (!getFileImages(c).isEmpty()) {
            for (mFile in getFileImages(c)) {
                val nameFile = mFile.name
                val path = mFile.absolutePath
                val date = mFile.lastModified()
                val dateFormat = formatDate(date)
                val mFileObject = FileObject(nameFile, path, dateFormat, false, false)
                fileObject.add(mFileObject)
            }
            return fileObject
        }
        return fileObject
    }

    fun getFileVideo(c: Context): List<File> {
        val files = ArrayList<File>()
        val projection =
            arrayOf(MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME)
        val selection = MediaStore.Video.Media.DATA + " like ? "
        var arg: Array<String>? = arrayOf("%${Constants.FR_VIDEO}%")
        val orderBy = MediaStore.Video.Media.DATE_ADDED//order data by date
        val orderByName = "upper(" + MediaStore.Video.Media.DISPLAY_NAME + ")"
        var cursor: Cursor? = c.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            arg,
            "$orderBy ASC"
        )
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    files.add(File(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))))

                } while (cursor.moveToNext())
                cursor?.close()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return files
    }

    fun getAllVideos(c: Context): ArrayList<FileObject> {
        var fileObject = arrayListOf<FileObject>()
        if (!getFileVideo(c).isEmpty()) {
            for (mFile in getFileVideo(c)) {
                val nameFile = mFile.name
                val path = mFile.absolutePath
                val date = mFile.lastModified()
                val dateFormat = formatDate(date)
                val mFileObject = FileObject(nameFile, path, dateFormat, false, false)
                fileObject.add(mFileObject)
            }
            return fileObject
        }
        return fileObject
    }

    fun getAllDocument(c: Context): List<File> {
        val files = ArrayList<File>()
        val projection =
            arrayOf(MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME)
        val selection = MediaStore.Video.Media.DATA + " like ? "
        var arg: Array<String>? = arrayOf("%${Constants.FR_DOCUMENT}%")
        val orderBy = MediaStore.Video.Media.DATE_ADDED//order data by date
        val orderByName = "upper(" + MediaStore.Video.Media.DISPLAY_NAME + ")"
        var cursor: Cursor? = c.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            arg,
            "$orderBy ASC"
        )
        try {
            cursor?.moveToFirst()
            do {
                files.add(File(cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))))
            } while (cursor!!.moveToNext())
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return files
    }


}
