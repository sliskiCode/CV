package com.slesarew.cv.cvscreen.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.CvAction
import com.slesarew.cv.cvscreen.model.CvAction.OnMediumClickedAction
import com.slesarew.cv.cvscreen.model.CvAction.OnScreenCreateAction
import com.slesarew.cv.cvscreen.model.CvAction.OnStackOverflowClickedAction
import com.slesarew.cv.cvscreen.model.CvAction.OnYouTubeClickedAction
import com.slesarew.cv.cvscreen.model.CvViewModel
import com.slesarew.cv.cvscreen.view.renderer.CvRenderer
import kotlinx.android.synthetic.main.activity_content.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CvActivity : AppCompatActivity() {

    private val viewModel by viewModel<CvViewModel> { parametersOf(this@CvActivity) }

    private val renderer by inject<CvRenderer>()

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content)
        renderer.prepare(content)

        viewModel.connect { renderer.render(it) }

        if (savedInstanceState == null) viewModel.sendAction(OnScreenCreateAction)
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

    private fun sendAction(action: CvAction) = viewModel.sendAction(action).let { true }
}
