package com.slesarew.cv.cvscreen.view.item

import com.slesarew.cv.R
import com.slesarew.cv.cvscreen.model.HeaderData
import com.slesarew.cv.extension.loadUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.header_item.view.*

data class HeaderItem(private val headerData: HeaderData) : Item() {

    override fun getId() = hashCode().toLong()

    override fun getLayout() = R.layout.header_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder.containerView) {
            photo.loadUrl(
                url = headerData.photoUrl,
                round = true
            )
            name.text = headerData.name
            phone_number.text = headerData.phoneNumber
            e_mail.text = headerData.email
        }
    }
}
