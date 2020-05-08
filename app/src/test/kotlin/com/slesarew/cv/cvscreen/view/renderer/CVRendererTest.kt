package com.slesarew.cv.cvscreen.view.renderer

import android.content.Context
import android.os.Build
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.slesarew.cv.cvscreen.model.CVState
import com.slesarew.cv.cvscreen.model.JobPosition
import com.slesarew.cv.cvscreen.model.Status
import com.slesarew.cv.cvscreen.view.item.ErrorItem
import com.slesarew.cv.cvscreen.view.item.HeaderItem
import com.slesarew.cv.cvscreen.view.item.JobPositionItem
import com.slesarew.cv.cvscreen.view.item.LoadingItem
import com.slesarew.cv.cvscreen.view.item.PersonalDevelopmentItem
import com.slesarew.cv.cvscreen.view.item.SpaceItemDecoration
import com.slesarew.cv.cvscreen.view.item.TitleBodyItem
import com.xwray.groupie.GroupAdapter
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class CVRendererTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val recyclerView = RecyclerView(context)

    private val tested = CVRenderer(context)

    @After
    fun tearDown() = stopKoin()

    @Test
    fun `checks that recycler has correct spacing`() {
        tested.prepare(recyclerView)

        assertThat(recyclerView.getItemDecorationAt(0)).isInstanceOf(SpaceItemDecoration::class.java)
    }

    @Test
    fun `checks that recycler has layout manager`() {
        tested.prepare(recyclerView)

        assertThat(recyclerView.layoutManager).isInstanceOf(LinearLayoutManager::class.java)
    }

    @Test
    fun `checks that recycler has adapter`() {
        tested.prepare(recyclerView)

        assertThat(recyclerView.adapter).isInstanceOf(GroupAdapter::class.java)
        assertThat((recyclerView.adapter as GroupAdapter).getGroupCount()).isEqualTo(1)
    }

    @ExperimentalStdlibApi
    @Test
    fun `renders Ready state correctly`() {
        val state = CVState(
            status = Status.Ready,
            jobPositions = listOf(
                JobPosition(
                    title = "Godfather",
                    dates = "Since the beginning of Android till now",
                    description = "Doing great things",
                    technologies = "All of them"
                )
            )
        )

        val items = tested.render(state)

        assertThat(items).containsExactly(
            HeaderItem(state.headerData),
            TitleBodyItem("Summary", state.summaryData.summary),
            PersonalDevelopmentItem(state.personalDevelopment),
            JobPositionItem(state.jobPositions[0]),
            TitleBodyItem("Hobbies", state.hobbiesData.hobbies)
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun `renders Loading state correctly`() {
        val state = CVState(
            status = Status.Loading
        )

        val items = tested.render(state)

        assertThat(items).containsExactly(
            LoadingItem()
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun `renders Error state correctly`() {
        val state = CVState(
            status = Status.Error("Error")
        )

        val items = tested.render(state)

        assertThat(items).containsExactly(
            ErrorItem("Error")
        )
    }
}
