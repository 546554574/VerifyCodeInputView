package com.toune.verifycodeinputview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText


/**
 * @Author Dong Lei
 * @Date 2020/12/18 0018-下午 15:40
 * @Info 描述：
 */
class DLVerifyCodeInputView : LinearLayout {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        readAttr(context, attr)
        initView(context)
    }

    var boxLineColor = Color.parseColor("#EDEEF2")  //box样式的线颜色
    var textColor = Color.parseColor("#333333") //文字颜色
    var textSize = 25f //文字大小

    val inputStyleLine = 0 //横线样式
    val inputStyleBox = 1   //方框样式

    var inputStyle = 0
    var inputNum = 5    //输入框的数量
    var boxLineWidth = 1f   //box样式的线宽

    var lineWidth = 0f //line线宽
    var lineHeight = 3f //line线高
    var lineSelectColor = Color.parseColor("#108EE9")   //line选中的颜色
    var lineDefColor = Color.parseColor("#E0E0E0")  //line默认的颜色
    var lineVerPadding = 3f //line样式文字和横线的间距
    var lineHorPadding = 15f    //line样式两个横线的间距
    private fun readAttr(context: Context, attr: AttributeSet) {
        val obtainStyledAttributes = context.obtainStyledAttributes(attr, R.styleable.verify_code)
        inputStyle =
            obtainStyledAttributes.getInteger(R.styleable.verify_code_inputStyle, inputStyle)
        inputNum = obtainStyledAttributes.getInteger(R.styleable.verify_code_num, inputNum) - 1
        boxLineWidth = obtainStyledAttributes.getDimension(R.styleable.verify_code_boxLineWidth, 1f)
        boxLineColor =
            obtainStyledAttributes.getColor(R.styleable.verify_code_boxLineColor, boxLineColor)
        textColor = obtainStyledAttributes.getColor(R.styleable.verify_code_textColor, textColor)
        lineWidth =
            obtainStyledAttributes.getDimension(R.styleable.verify_code_lineWidth, lineWidth)
        lineHeight =
            obtainStyledAttributes.getDimension(R.styleable.verify_code_lineHeight, lineHeight)
        lineSelectColor = obtainStyledAttributes.getColor(
            R.styleable.verify_code_lineSelectColor,
            lineSelectColor
        )
        lineDefColor =
            obtainStyledAttributes.getColor(R.styleable.verify_code_lineDefColor, lineDefColor)
        lineVerPadding = obtainStyledAttributes.getDimension(
            R.styleable.verify_code_lineVerPadding,
            lineVerPadding
        )
        lineHorPadding = obtainStyledAttributes.getDimension(
            R.styleable.verify_code_lineHorPadding,
            lineHorPadding
        )
        obtainStyledAttributes.recycle()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView(context: Context) {
        for (c in 0..inputNum) {
            when (inputStyle) {
                inputStyleLine -> {
                    createLineEt(c)
                }
                inputStyleBox -> {
                    if (background == null) {
                        val gradientDrawable = GradientDrawable()
                        gradientDrawable.setColor(Color.TRANSPARENT)
                        gradientDrawable.setStroke(boxLineWidth.toInt(), boxLineColor)
                        gradientDrawable.cornerRadius = 5f
                        background = gradientDrawable
                    }
                    createBoxEt(c)
                }
            }
        }
        initListener()
    }

    private fun initListener() {
        for ((index, editText) in editList.withIndex()) {
            editText.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL
                    && event.action == KeyEvent.ACTION_DOWN
                ) {
                    if (editText.text.isEmpty()) {
                        //如果是空的，删除上一个
                        //监听删除按钮
                        if (index > 0) {
                            var pos = index - 1
                            editList[pos].setText("")
                            editList[pos].requestFocus()
                        }
                    } else {
                        editText.text.clear()
                    }
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (inputStyle==inputStyleLine){
                        //横线的时候修改lineView的颜色
                        setLineColor(index,count)
                    }
                    editTextChangedListener(index, s, count)
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }
    }

    private fun setLineColor(index: Int, count: Int) {
        if(count>0) {
            lineViews[index].setBackgroundColor(lineSelectColor)
        }else{
            lineViews[index].setBackgroundColor(lineDefColor)
        }
    }

    private fun editTextChangedListener(index: Int, s: CharSequence?, count: Int) {
        if (index < editList.size - 1) {
            //长度大于1，焦点给下一个
            if (count > 0) {
                var pos = index + 1
                editList[pos].requestFocus()
            }
        } else {
            //输入完成之后执行完成
            if (finishListener != null) {
                val inputSb = StringBuilder()
                for (editText in editList) {
                    inputSb.append(editText.text.toString())
                }
                if (inputSb.length == editList.size) {
                    finishListener!!.finish(inputSb.toString())
                }
            }
        }
    }

    val editList: MutableList<EditText> = ArrayList()

    /**
     * 创建方格ET
     */
    private fun createBoxEt(pos: Int) {
        val editText = AppCompatEditText(context)
        val layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
        editText.layoutParams = layoutParams
        editText.maxWidth = editText.layoutParams.height
        editText.minWidth = editText.layoutParams.height
        editText.gravity = Gravity.CENTER
        editText.setTextColor(textColor)
        editText.isSingleLine = true//不可换行
        editText.isCursorVisible = false//隐藏光标
        editText.filters = arrayOf<InputFilter>(LengthFilter(1))//设置文字长度为1
        editText.setBackgroundColor(Color.TRANSPARENT)//背景透明
        editText.inputType = EditorInfo.TYPE_CLASS_NUMBER//输入数字
        editList.add(editText)
        addView(editText)
        if (pos <= inputNum - 1) {
            addView(createVerLineTv())
        }
    }

    /**
     * box样式下的分割线
     * @return View?
     */
    private fun createVerLineTv(): View? {
        var v = View(context)
        var layoutParams = LayoutParams(boxLineWidth.toInt(), LayoutParams.MATCH_PARENT)
        v.layoutParams = layoutParams
        v.setBackgroundColor(boxLineColor)
        return v
    }


    /**
     * 创建横线ET
     */
    private fun createLineEt(pos: Int) {
        var linearLayout = LinearLayout(context)
        linearLayout.gravity = Gravity.CENTER
        val parentLayoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
        if (pos <= inputNum - 1) {
            parentLayoutParams.setMargins(0,0,lineHorPadding.toInt(),0)
        }
        linearLayout.layoutParams = parentLayoutParams
        linearLayout.orientation = VERTICAL

        val editText = AppCompatEditText(context)
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f)
        layoutParams.setMargins(0, 0, 0, lineVerPadding.toInt())
        editText.layoutParams = layoutParams
        editText.maxWidth = editText.layoutParams.height
        editText.minWidth = editText.layoutParams.height
        editText.gravity = Gravity.CENTER
        editText.setTextColor(textColor)
        editText.isSingleLine = true//不可换行
        editText.isCursorVisible = false//隐藏光标
        editText.filters = arrayOf<InputFilter>(LengthFilter(1))//设置文字长度为1
        editText.setBackgroundColor(Color.TRANSPARENT)//背景透明
        editText.inputType = EditorInfo.TYPE_CLASS_NUMBER//输入数字
        editList.add(editText)
        linearLayout.addView(editText)

        linearLayout.addView(createHorLineTv())

        addView(linearLayout)
    }

    /**
     * line样式下的底线
     * @return View?
     */
    val lineViews:MutableList<View> = ArrayList()
    private fun createHorLineTv(): View? {
        var v = View(context)
        var layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, lineHeight.toInt())
        if (lineWidth > 0) {
            layoutParams.width = lineWidth.toInt()
        }
        v.setBackgroundColor(lineDefColor)
        v.layoutParams = layoutParams
        v.setBackgroundColor(boxLineColor)
        lineViews.add(v)
        return v
    }


    var finishListener: FinishListener? = null

    interface FinishListener {
        fun finish(code: String)
    }

}