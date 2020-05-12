package com.vit.fullscreendialogfragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.vit.fullscreendialogfragment.databinding.FullscreenDialogFragmentBinding
import kotlinx.android.synthetic.main.fullscreen_dialog_fragment.*

class FullscreenDialogFragment: BaseDialogFragment<FullscreenDialogFragmentBinding>() {

    override val layoutResource: Int = R.layout.fullscreen_dialog_fragment

    override val isFullScreenHideStatusBar: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textOpen.setOnClickListener {
            startActivity(Intent(activity, BActivity::class.java))
        }
        buttonDismiss.setOnClickListener { dismiss() }
    }

    override fun onBackPressed() {
        Toast.makeText(activity, "OnBack", Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }

    companion object {

        @JvmField
        val TAG = FullscreenDialogFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = FullscreenDialogFragment()
    }


}