package com.slesarew.cv.cvscreen.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.CVAction
import com.slesarew.cv.cvscreen.model.CVAction.OnMediumClickedAction
import com.slesarew.cv.cvscreen.model.CVAction.OnScreenCreateAction
import com.slesarew.cv.cvscreen.model.CVAction.OnStackOverflowClickedAction
import com.slesarew.cv.cvscreen.model.CVAction.OnYouTubeClickedAction
import com.slesarew.cv.cvscreen.model.CVViewModel
import com.slesarew.cv.cvscreen.view.renderer.CVRenderer
import kotlinx.android.synthetic.main.activity_content.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CVActivity : AppCompatActivity() {

    private val cvViewModel by viewModel<CVViewModel> { parametersOf(this@CVActivity) }

    private val cvRenderer by inject<CVRenderer>()

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content)
        cvRenderer.prepare(content)

        cvViewModel.connect { cvRenderer.render(it) }

        if (savedInstanceState == null) cvViewModel.sendAction(OnScreenCreateAction)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cv_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.medium -> sendAction(OnMediumClickedAction)
            R.id.stackoverflow -> sendAction(OnStackOverflowClickedAction)
            R.id.youtube -> sendAction(OnYouTubeClickedAction)
            else -> super.onOptionsItemSelected(item)
        }

    private fun sendAction(action: CVAction) = cvViewModel.sendAction(action).let { true }
}
