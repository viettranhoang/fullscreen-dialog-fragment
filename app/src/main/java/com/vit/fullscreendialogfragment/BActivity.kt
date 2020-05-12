package com.vit.fullscreendialogfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_b.*

class BActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        buttonOpenFragment.setOnClickListener {
            FullscreenDialogFragment.newInstance().show(supportFragmentManager, FullscreenDialogFragment.TAG)
        }
    }
}