package com.slesarew.cv.cvscreen.view.item

import com.slesarew.cv.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.title_body_item.view.*

data class TitleBodyItem(
    private val title: String,
    private val body: String
) : Item() {

    override fun getId() = hashCode().toLong()

    override fun getLayout() = R.layout.title_body_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder.containerView) {
            title.text = this@TitleBodyItem.title
            body.text = this@TitleBodyItem.body
        }
    }
}
