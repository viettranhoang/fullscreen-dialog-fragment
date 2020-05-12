package com.vit.fullscreendialogfragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialogFragment<B : ViewDataBinding> : DialogFragment() {

    abstract val layoutResource: Int

    open val isFullScreen: Boolean = false

    open val isFullScreenHideStatusBar: Boolean = false

    open fun onBackPressed() {}

    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isFullScreen) {
            setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
        } else if (isFullScreenHideStatusBar) {
            setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenHideStatusBarDialog)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
                this@BaseDialogFragment.onBackPressed()
                dialog?.window?.setWindowAnimations(R.style.FragmentAnimation)
                postDelay(50) {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        applyAnimation()
    }

    override fun onPause() {
        clearAnimation()
        super.onPause()
    }

    override fun dismiss() {
        applyAnimation()
        postDelay(50) {
            super.dismiss()
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val fragment: Fragment? = manager.findFragmentByTag(tag)
        if (fragment != null) return
        super.show(manager, tag)
    }

    private fun applyAnimation() {
        if (isFullScreen || isFullScreenHideStatusBar) {
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog?.window?.setWindowAnimations(R.style.FragmentAnimation)
        }
    }

    private fun clearAnimation() {
        if (isFullScreen || isFullScreenHideStatusBar) {
            dialog?.window?.setWindowAnimations(0)
        }
    }

    companion object {
        val TAG = BaseDialogFragment::class.java.simpleName
    }
}

inline fun postDelay(time: Long = 0, crossinline block: () -> Unit) {
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({ block() }, time)
}
