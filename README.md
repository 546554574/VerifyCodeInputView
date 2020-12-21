"# VerifyCodeInputView" 
## 验证码的输入组件，包含盒子样式和下划线样式，属性可自定义！话不多说，上图
<pre>
 <code>
  <img src="https://raw.githubusercontent.com/546554574/VerifyCodeInputView/main/img/img01.jpg"  height="330" width="495">
  <img src="https://raw.githubusercontent.com/546554574/VerifyCodeInputView/main/img/img02.jpg"  height="330" width="495">
 </code>
</pre>


## 使用：
### step1

```
 implementation 'cn.toune:verifyCodeInputView:1.0.0'
```

### step2

```
 
<com.toune.verifycodeinputview.DLVerifyCodeInputView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:inputStyle="line"    //下划线样式
    app:textColor="#00ff00"  //文本颜色
    app:textSize="13sp" //文本字体大小
    app:lineWidth="10dp" //下划线的线长，默认按照总宽度根据输入框数量平分
    app:lineHeight="3dp" //下划线的线宽
    app:lineSelectColor="@color/design_default_color_primary_variant"//输入数字之后下划线的颜色
    app:lineDefColor="@color/design_default_color_secondary_variant" //没有输入数字（默认状态）下划线的颜色
    app:lineVerPadding="15dp" //文本和下划线的间距
    app:lineHorPadding="15dp" //每一个输入框的间距（包含下划线）
    app:num="6"//输入框数量
    />

    <com.toune.verifycodeinputview.DLVerifyCodeInputView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        app:inputStyle="box" //盒子样式
        app:boxLineWidth="3dp" //线宽
        app:textColor="#ff0000" //文本颜色
        app:textSize="13sp" //文本大小
        app:boxLineColor="@color/black" //线的颜色
        app:num="6" //输入框数量
        />

```

### step3

```
 var dlVerifyCodeInputView = findViewById<DLVerifyCodeInputView>(R.id.dlVerifyCodeInputView)
        dlVerifyCodeInputView.finishListener = object :DLVerifyCodeInputView.FinishListener{
            override fun finish(code: String) {
                //输入完成之后调用，code为输入内容
            }
        }
```

### 说明：
觉得好用麻烦star
