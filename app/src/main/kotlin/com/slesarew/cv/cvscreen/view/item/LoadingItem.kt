package com.slesarew.cv.cvscreen.view.item

import android.view.ViewGroup
import com.slesarew.cv.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.loading_item.*

class LoadingItem : Item() {

    override fun getId() = hashCode().toLong()

    override fun getLayout() = R.layout.loading_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val layoutParams = viewHolder.container.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
