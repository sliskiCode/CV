package com.slesarew.cv.cvscreen.view.item

import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.JobPosition
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.job_position_item.view.*

data class JobPositionItem(private val jobPosition: JobPosition) : Item() {

    override fun getId() = hashCode().toLong()

    override fun getLayout() = R.layout.job_position_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder.containerView) {
            title.text = jobPosition.title
            dates.text = jobPosition.dates
            description.text = jobPosition.description
            technologies.text = context.getString(R.string.technologies, jobPosition.technologies)
        }
    }
}
