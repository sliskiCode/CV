package com.slesarew.cv.cvscreen.view.item

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.PersonalDevelopment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.personal_development_item.*

data class PersonalDevelopmentItem(private val personalDevelopment: PersonalDevelopment) : Item() {

    private val section = Section()

    override fun getId() = hashCode().toLong()

    override fun getLayout() = R.layout.personal_development_item

    override fun createViewHolder(itemView: View): GroupieViewHolder {
        val viewHolder = super.createViewHolder(itemView)

        val space = itemView.context.resources.getDimension(R.dimen.card_content_margin).toInt()
        val decoration = SpaceItemDecoration(verticalSpacing = space)
        viewHolder.items.addItemDecoration(decoration)

        viewHolder.items
            .apply {
                layoutManager = LinearLayoutManager(context)
                adapter = GroupAdapter<GroupieViewHolder>().apply {
                    add(section)
                }
            }

        return viewHolder
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        personalDevelopment.descriptions
            .map(::CheckItem)
            .also(section::update)
    }
}
