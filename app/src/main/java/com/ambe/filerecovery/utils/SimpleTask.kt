package com.ambe.filerecovery.utils

import android.os.AsyncTask

/**
 *  Created by AMBE on 10/4/2019 at 10:42 AM.
 */
class SimpleTask<T> : AsyncTask<Unit,Unit,T>() {
    private var action: TaskAction<T>? = null
    private var listener: OnTaskListener<T>? = null

    override fun onPreExecute() {
        super.onPreExecute()
        listener?.onStart()
    }

    override fun doInBackground(vararg params: Unit?): T? {
        return action?.action()
    }

    override fun onPostExecute(result: T) {
        super.onPostExecute(result)
        listener?.onComplete(result)
    }

    fun setAction(action: (() -> T)): SimpleTask<T> {
        this.action = object :TaskAction<T>{
            override fun action(): T {
                return action.invoke()
            }
        }
        return this
    }

    fun setAction(action: TaskAction<T>): SimpleTask<T> {
        this.action = action

        return this
    }

    fun setTaskListener(listener: OnTaskListener<T>): SimpleTask<T> {
        this.listener = listener
        return this
    }

    interface TaskAction<T> {
        fun action(): T
    }

    interface OnTaskListener<T> {
        fun onStart()

        fun onComplete(result: T)
    }
}