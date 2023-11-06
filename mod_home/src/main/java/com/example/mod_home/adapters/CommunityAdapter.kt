package com.example.mod_home.adapters

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_home.R
import com.example.mod_home.views.DisCussPopWindow
import com.xwl.common_base.adapter.BaseAdapter
import com.xwl.common_lib.bean.CommunityBean
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.setUrlRound
import com.xwl.common_lib.utils.ScreenUtil
import com.xwl.common_lib.views.ThreeImageView

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class CommunityAdapter : BaseAdapter<CommunityBean>() {

    private val ITEM_TYPE_ONE_PICS = 1
    private val ITEM_TYPE_MORE_PICS = 2

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: CommunityBean?) {
        val screenWidth = ScreenUtil.getScreenWidth()

        if (holder.itemViewType == ITEM_TYPE_ONE_PICS) {
            val tvTitle = holder.getView<TextView>(R.id.tvTitle)
            val tvAuthor = holder.getView<TextView>(R.id.tvAuthor)
            val img = holder.getView<ImageView>(R.id.img)
            val imgWidth = screenWidth / 3 - 20
            val imgHeight = imgWidth * 3 / 5
            val params = ConstraintLayout.LayoutParams(imgWidth, imgHeight)
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            params.marginEnd = ScreenUtil.px2dip(10f)
            img.layoutParams = params
            item?.let {
                tvTitle.text = it.title
                tvAuthor.text = it.author
                it.imgList?.let { it1 ->
                    if (it1.size > 0) {
                        img.setUrlRound(it1[0], 8)
                    }
                }
            }
        } else if (holder.itemViewType == ITEM_TYPE_MORE_PICS) {
            val tvTitle = holder.getView<TextView>(R.id.tvTitle)
            val tvAuthor = holder.getView<TextView>(R.id.tvAuthor)
            val imgContainer = holder.getView<ThreeImageView<String>>(R.id.imgContainer)
            val imgMore = holder.getView<ImageView>(R.id.imgMore)
            item?.let {
                tvTitle.text = it.title
                tvAuthor.text = it.author
                imgContainer.setImagesData(it.imgList)
            }
            imgMore.onClick {
                val pop = DisCussPopWindow(
                    context,
                    imgMore
                )
                pop.show(Gravity.LEFT)
            }
        }
    }

    override fun getItemViewType(position: Int, list: List<CommunityBean>): Int {
        return if (list[position].imgList?.isEmpty() == true || list[position].imgList?.size == 1) ITEM_TYPE_ONE_PICS else ITEM_TYPE_MORE_PICS
    }


    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        val holder: QuickViewHolder = if (viewType == ITEM_TYPE_ONE_PICS) {
            QuickViewHolder(R.layout.item_community_one_pics, parent)
        } else {
            QuickViewHolder(R.layout.item_community_more_than_one_pics, parent)
        }
        return holder
    }
    
}