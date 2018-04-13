[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/)

PicGenerator是一个Android上的图片生成器，可以通过xml布局用View排版好图片样式，在子线程生成一张图片，以满足生成用来分享的图片等需求(生成图片前设置可变元素，如用户的头像，昵称等)。

## 效果
- 点击按钮生成图片：
  ![](https://github.com/homgwu/picgenerator/blob/master/pic_generate.gif?raw=true)
<!--more-->
## 特性

- 通过布局和View的方式设计图片样式。
- 在子线程中生成和保存图片。
- 已在Library module中封装好工具类，直接使用即可。

## 更多详情:
> 博客：http://zhuchen.vip/2018/04/13/android-pic-generator.html
> 作者：竹尘居士

## 核心代码

```java
    private Bitmap createBitmap(View view) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(view.getLayoutParams().width, View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(view.getLayoutParams().height, View.MeasureSpec.EXACTLY);
        view.measure(widthSpec, heightSpec);
        int measureWidth = view.getMeasuredWidth();
        int measureHeight = view.getMeasuredHeight();
        view.layout(0, 0, measureWidth, measureHeight);
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
```
###  原理说明

  通过走一遍ViewGroup的测量(measure)，布局(layout)，draw流程，把布局展示的界面画到我们准备好的bitmap上(这一过程可在非UI线程完成)，再把bitmap保存在文件或显示到界面上。

1. 在布局中写好图片的样子，然后把布局inflate成View，当然也可以直接代码编写View，设置好里面的可变元素，如头像，昵称等。
2. 通过调用View的measure,layout方法使之测量出内部各控件的大小和排列好各控件。
3. 创建一个和View大小相同的空Bitmap，新建一个画布传入该bitamp(new Canvas(bitmap))，调用view的draw(canvas)方法，view会把图片绘制在该bitmap上。
4. 保存到文件或直接使用图片。



## 使用方法

1. 在xml中布局图片样式:

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="200dp"
       android:layout_height="200dp"
       android:background="#ECAA0A">

       <ImageView
           android:layout_width="160dp"
           android:layout_height="94dp"
           android:layout_gravity="center_horizontal"
           android:src="@mipmap/pic_bg" />

       <ImageView
           android:id="@+id/invitation_share_link_pic_avatar_iv"
           android:layout_width="80dp"
           android:layout_height="80dp"
           android:layout_gravity="center_horizontal|bottom"
           android:layout_marginBottom="10dp"
           android:src="@mipmap/ic_launcher" />
   </FrameLayout>
   ```

2. 写一个自己的Model继承自GenerateModel，设置可变元素并使用GeneratePictureManager单例的generate方法开始生成:

   ```java
       private void generate() {
           SharePicModel sharePicModel = new SharePicModel((ViewGroup) getWindow().getDecorView());
           sharePicModel.setAvatarResId(R.mipmap.ic_launcher);
           GeneratePictureManager.getInstance().generate(sharePicModel, (throwable, bitmap) -> {
               if (throwable != null || bitmap == null) {
                   Toast.makeText(this, getString(R.string.generate_pic_error), Toast.LENGTH_SHORT).show();
               } else {
                   Toast.makeText(this, getString(R.string.generate_pic_success), Toast.LENGTH_SHORT).show();
                   mResultIv.setImageBitmap(bitmap);
               }
           });
       }
   ```
