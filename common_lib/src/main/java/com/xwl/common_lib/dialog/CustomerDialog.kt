package com.xwl.common_lib.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.xwl.common_lib.R
import com.xwl.common_lib.databinding.DialogCustomerBinding
import com.xwl.common_lib.ext.gone
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.visible
import java.util.*

/**
 * @author  lxw
 * @date 2023/9/23
 * descripe
 */
class CustomerDialog private constructor(builder: Builder) : DialogFragment() {
    private var mCancelOutsideTouchable = false
    private var mIsCancelVisible = true
    private var mIsTitleVisible = true
    private var mTitle = ""
    private var mContent = ""
    private var mCancel = ""
    private var mConfirm = ""
    private var mOnItemClickListener: OnItemClickListener? = null
    private val mBinding by lazy { DialogCustomerBinding.inflate(layoutInflater) }
    init {
        this.mCancelOutsideTouchable = builder.cancelOutsideTouchable
        this.mIsCancelVisible = builder.isCancelVisible
        this.mIsTitleVisible = builder.isTitleVisible
        this.mTitle = builder.title
        this.mContent = builder.content
        this.mCancel = builder.cancel
        this.mConfirm = builder.confirm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.customerDialog)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = mBinding.root
        initView(view)

        // 启用窗体的扩展特性。
        Objects.requireNonNull(dialog)?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return view
    }

    private fun initView(view: View) {
          val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
          val tvContent = view.findViewById<TextView>(R.id.tvContent)
          val tvCancel = view.findViewById<TextView>(R.id.tvCancel)
          val tvConfirm = view.findViewById<TextView>(R.id.tvConfirm)
          val viewVertical = view.findViewById<View>(R.id.viewVertical)

        if(!mIsCancelVisible) {
            tvCancel.gone()
            viewVertical.gone()
        } else if (mCancel.isNotEmpty()) {
            tvCancel.visible()
            viewVertical.visible()
            tvCancel.text = mCancel
        }

        if(mContent.isNotEmpty()) {
            tvContent.text = mContent
        }

        if(mConfirm.isNotEmpty()) {
            tvConfirm.text = mConfirm
        }
        if(mTitle.isNotEmpty()) {
            tvTitle.text = mTitle
        }
        tvTitle.visibility =  if (mIsTitleVisible) View.VISIBLE else View.GONE

       tvCancel.onClick {
           mOnItemClickListener?.onCancel()
           dismiss()
       }

        tvConfirm.onClick {
            mOnItemClickListener?.onConfirm()
        }
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = true
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(mCancelOutsideTouchable)
        val window = dialog.window
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return dialog
    }


    class Builder {
         internal var cancelOutsideTouchable:Boolean = false
         internal var isCancelVisible = true
         internal var isTitleVisible = true
         internal var title = ""
         internal var content = ""
         internal var cancel = ""
         internal var confirm = ""
         fun build(): CustomerDialog {
             return CustomerDialog(this)
         }

        fun setCancelOusideTouchable(cancelOutsideTouchable: Boolean):Builder{
            this.cancelOutsideTouchable = cancelOutsideTouchable
            return this
        }
        //设置标题是否可见
        fun setIsTitleVisible(isTitleVisible: Boolean):Builder {
            this.isTitleVisible = isTitleVisible
            return this
        }
        //设置取消按钮是否可见
        fun setIsCancelVisible(isCancelVisible: Boolean):Builder {
            this.isCancelVisible = isCancelVisible
            return this
        }

        //设置标题文字
        fun setTitleText(title: String):Builder {
            this.title = title
            return this
        }
        //设置内容文字
        fun setContentText(content: String):Builder {
            this.content = content
            return this
        }
        //设置取消文字
        fun setCancelText(cancel: String):Builder {
            this.cancel = cancel
            return this
        }

        //设置确认文字
        fun setConfirmText(confirm: String):Builder {
            this.confirm = confirm
            return this
        }
    }

    interface OnItemClickListener {
        fun onCancel()
        fun onConfirm()
    }
}