package com.slesarew.cv.cvscreen.view.item

import com.slesarew.cv.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.check_item.view.*

data class CheckItem(private val description: String) : Item() {

    override fun getId() = hashCode().toLong()

    override fun getLayout() = R.layout.check_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder.containerView) {
            description_text.text = description
        }
    }
}
