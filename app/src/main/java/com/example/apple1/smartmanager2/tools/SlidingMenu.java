package com.example.apple1.smartmanager2.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by draft on 2015/7/25.
 */
public class SlidingMenu extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;

    private int mMenuWidth;
    //Menu拉出后离右边的距离
    private int mMenuRightPadding=50;//50单位默认dp,真正设置在后面
    //为了布局只加载一次
    private boolean once=false;
    private boolean isOpen=false;

    /**
     * 未使用自定义属性时调用
     * @param context
     * @param attrs
     */

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取屏幕长宽，传给WindowManager
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        //宽和高赋值
        DisplayMetrics outMetrics =new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        //屏幕宽度
        mScreenWidth=outMetrics.widthPixels;
        //用TypedValue把dp转化为一个px(像素值)  ,TypedValue.COMPLEX_UNIT_DIP指50的类型是dp
        mMenuRightPadding= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80,context.getResources().getDisplayMetrics());

    }

    /**
     * 设置子View的宽和高，设置自己的宽和高
     */

    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
    {
        //用if使布局只加载一次
        if (!once){
            //mWapper赋值给找到的第一个子View
            mWapper=(LinearLayout)getChildAt(0);
            //mMenu,mContent分别对应mWapper中的第一第二子View
            mMenu=(ViewGroup)mWapper.getChildAt(0);
            mContent= (ViewGroup) mWapper.getChildAt(1);
            //设置menu,content的宽度
            mMenuWidth=mMenu.getLayoutParams().width=mScreenWidth-mMenuRightPadding;
            mContent.getLayoutParams().width=mScreenWidth;
            once=true;
        }

        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

    }

    /**
     * 通过设置偏移量将menu隐藏
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed)
        {
            //隐藏左边的东西，是瞬间隐藏的，如果动画隐藏式另一个方法
            this.scrollTo(mMenuWidth,0);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action=ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_UP:
                //得到内容左边隐藏的内容的长度（还未拉出来的）：getScrollX()
                int scrollX=getScrollX();

                if(scrollX>=mMenuWidth/2)
                {
                    //使mMenuWidth完全隐藏在左边
                    this.smoothScrollTo(mMenuWidth,0);
                    isOpen=false;
                }else{
                    //使左边隐藏的为0
                    this.smoothScrollTo(0,0);
                    isOpen=true;
                }
                return true;


        }

        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu(){

        if (isOpen)return;
        this.smoothScrollTo(0,0);
        isOpen=true;
    }

    /**
     * 关闭菜单
     */

    private void closeMenu()
    {
        if (!isOpen)return;;
        this.smoothScrollTo(mMenuWidth,0);
        isOpen=false;
    }

    /**
     * 切换菜单
     */

    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale=l*1.0f/mMenuWidth;//1~0


        float rightScale=0.7f+0.3f*scale;
        float leftScale=1.0f-scale*0.3f;
        float leftAlpha=0.6f+0.4f*(1-scale);
        // 调用属性动画，设置TranslationX
        ViewHelper.setTranslationX(mMenu,mMenuWidth*scale*0.8f);
        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu,leftAlpha);
        //设置缩放中心
        ViewHelper.setPivotX(mContent,0);
        ViewHelper.setPivotY(mContent,mContent.getHeight()/2);

        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);


    }
}















