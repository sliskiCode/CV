package com.slesarew.cv.cvscreen.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.CVAction.OnScreenCreateAction
import com.slesarew.cv.cvscreen.model.CVViewModel
import com.slesarew.cv.cvscreen.view.renderer.CVRenderer
import kotlinx.android.synthetic.main.activity_content.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CVActivity : AppCompatActivity() {

    private val cvViewModel by viewModel<CVViewModel>()

    private val cvRenderer by inject<CVRenderer>()

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content)
        cvRenderer.prepare(content)

        cvViewModel.connect { cvRenderer.render(it) }

        if (savedInstanceState == null) cvViewModel.sendAction(OnScreenCreateAction)
    }
}
