package com.ambe.filerecovery.ui.layouts

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.PopupWindow
import android.widget.TextView
import com.ambe.filerecovery.R
import com.ambe.filerecovery.databinding.FragmentDeleteRecentlyBinding
import com.ambe.filerecovery.model.FileObject
import com.ambe.filerecovery.ui.BaseFragment
import com.ambe.filerecovery.utils.Constants
import com.ambe.filerecovery.utils.PrefUtils
import com.ambe.filerecovery.utils.Utils
import kotlinx.android.synthetic.main.fragment_delete_recently.*
import kotlinx.android.synthetic.main.popup_menu_search.view.*


class LayoutsFragment : BaseFragment() {
    private lateinit var binding: FragmentDeleteRecentlyBinding
    private val layoutHandler = LayoutHandler()
    private val TAG = LayoutsFragment::class.java.simpleName
    var popupWindown: PopupWindow? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentDeleteRecentlyBinding>(
            inflater,
            R.layout.fragment_delete_recently,
            container,
            false
        ).apply {
            handler = layoutHandler
        }

        var type = arguments?.getString("type")

        Log.e(TAG, type)
        controlAdapter(type!!)
        return binding.root
    }


    fun controlAdapter(type: String) {
        when (type) {
            Constants.IMAGES -> {
                var listImages = Utils.getAllImages(context!!)
                setAdapterFolder(listImages)
                binding.titleText.text = "Images"
            }
            Constants.VIDEOS -> {
                var listVideos = Utils.getAllVideos(context!!)
                setAdapterFolder(listVideos)
                binding.titleText.text = "Videos"
            }
            Constants.SOUNDS -> {
                var listSounds = Utils.getAllAudios(context!!)
                binding.titleText.text = "Sounds"
            }
            Constants.OTHER -> {
                var listSounds = Utils.getAllAudios(context!!)
                binding.titleText.text = "Other"
            } Constants.APP -> {
            var listSounds = Utils.getAllAudios(context!!)
            binding.titleText.text = "App"
        } Constants.DOCUMENT -> {
            var listSounds = Utils.getAllAudios(context!!)
            binding.titleText.text = "Document"
        }
        }
    }

    fun setAdapterFolder(files: ArrayList<FileObject>) {
        binding.rcvLayoutFragment.adapter = FileAdapter(files)

    }

    companion object {
        @JvmStatic
        fun getInstance() = LayoutsFragment()
    }


    inner class LayoutHandler {
        fun clickBack() {

        }

        fun clickText() {
            showPopupWindown()
        }

        fun clickCancel() {
            hideIconOption()
        }

        fun clickSearch() {
            showPopupSearch()
        }
    }

    fun showIconOption() {
        binding.imgEdit.visibility = View.GONE
        binding.imgSearch.visibility = View.GONE
        binding.imgCancel.visibility = View.VISIBLE
        binding.imgBack.visibility = View.INVISIBLE
        binding.opacityTop.visibility = View.VISIBLE
    }

    fun showPopupWindown() {
        var inflater: LayoutInflater =
            activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var layout = inflater.inflate(R.layout.popup_menu_option, null)
        popupWindown = PopupWindow(
            layout,
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popupWindown?.showAsDropDown(tbDeleted, Gravity.CENTER, 0, 0)
        }

        popupWindown?.setOnDismissListener { hideIconOption() }
        showIconOption()
    }

    fun hideIconOption() {
        binding.imgEdit.visibility = View.VISIBLE
        binding.imgBack.visibility = View.VISIBLE
        binding.imgCancel.visibility = View.GONE
        binding.imgSearch.visibility = View.VISIBLE
        binding.opacityTop.visibility = View.GONE
        binding.tabBarLayout.visibility = View.GONE
    }

    fun showPopupSearch() {
        var inflater: LayoutInflater =
            activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var layout = inflater.inflate(R.layout.popup_menu_search, null)
        popupWindown = PopupWindow(
            layout,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )
        popupWindown?.showAtLocation(binding.fragmentList, Gravity.TOP, 0, 0)
        binding.opacityBottom.visibility = View.VISIBLE
        popupWindown?.setOnDismissListener {
            binding.opacityBottom.visibility = View.GONE
            PrefUtils.newInstance(context!!)
                .setStatusBarColor(activity!!, Color.WHITE, PrefUtils.StatusBarState.Light)
        }
        PrefUtils.newInstance(context!!).setStatusBarColor(
            activity!!,
            Color.parseColor("#AF000000"),
            PrefUtils.StatusBarState.Dark
        )
        layout.edit_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //performSearch()
                    return true
                }
                return false
            }

        })
    }


}