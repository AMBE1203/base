package com.ambe.filerecovery.ui.layouts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ambe.filerecovery.R
import com.ambe.filerecovery.model.FileObject
import kotlinx.android.synthetic.main.fragment_delete_recently.view.*
import kotlinx.android.synthetic.main.item_video_image.view.*

class FileAdapter(files: ArrayList<FileObject>) : RecyclerView.Adapter<FileAdapter.FileHolder>() {
    private var fileObjects: ArrayList<FileObject> = files


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FileAdapter.FileHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemView = inflater.inflate(R.layout.item_video_image, viewGroup, false)
        return FileHolder(itemView)
    }

    override fun getItemCount(): Int {
        return fileObjects.size
    }

    override fun onBindViewHolder(viewHolder: FileAdapter.FileHolder, position: Int) {
        var fileObject = fileObjects[position]
        //viewHolder.imgImgVideo
        viewHolder.nameImgVideo.text = fileObject.name
        viewHolder.dateImgVideo.text = fileObject.date

    }


    inner class FileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var imgImgVideo: ImageView = itemView.findViewById(R.id.img_imgvideo)
         var nameImgVideo: TextView = itemView.findViewById(R.id.name_imgvideo)
         var dateImgVideo: TextView = itemView.findViewById(R.id.date_imgvideo)
    }


}