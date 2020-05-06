package com.slesarew.cv.cvscreen.view.item

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.ViewGroup.LayoutParams
import com.slesarew.cv.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.error_item.*

data class ErrorItem(private val message: String) : Item() {

    override fun getId() = hashCode().toLong()

    override fun getLayout() = R.layout.error_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder.containerView) {
            val layoutParams = layoutParams
            layoutParams.height = LayoutParams.MATCH_PARENT

            ColorMatrix()
                .apply { setSaturation(0f) }
                .let(::ColorMatrixColorFilter)
                .also {
                    viewHolder.error_icon.colorFilter = it
                }

            viewHolder.error_message.text = message
        }
    }
}
