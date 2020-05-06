package com.slesarew.cv.cvscreen.view.renderer

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.CVState
import com.slesarew.cv.cvscreen.model.Status
import com.slesarew.cv.cvscreen.view.item.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item

class CVRenderer(private val context: Context) {

    private val section = Section()

    fun prepare(contentContainer: RecyclerView) {
        val space = context.resources.getDimension(R.dimen.container_padding).toInt()
        val decoration = SpaceItemDecoration(horizontalSpacing = space, verticalSpacing = space)
        contentContainer.addItemDecoration(decoration)

        contentContainer.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = GroupAdapter<GroupieViewHolder>().apply {
                add(section)
            }
        }
    }

    @ExperimentalStdlibApi
    fun render(state: CVState): List<Item> =
        when (val status = state.status) {
            Status.Ready -> buildList {
                add(HeaderItem(state.headerData))
                add(TitleBodyItem(context.getString(R.string.summary), state.summaryData.summary))
                add(PersonalDevelopmentItem(state.personalDevelopment))
                addAll(state.jobPositions.map(::JobPositionItem))
                add(TitleBodyItem(context.getString(R.string.hobbies), state.hobbiesData.hobbies))
            }
            Status.Loading -> listOf(LoadingItem())
            is Status.Error -> listOf(ErrorItem(status.message))
        }.also(section::update)
}
