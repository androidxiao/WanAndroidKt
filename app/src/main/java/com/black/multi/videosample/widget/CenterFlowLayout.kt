package com.black.multi.videosample.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.black.multi.videosample.R


/**
 * Created by wei.
 * Date: 2020/6/8 15:13
 * Desc: 居中的流式布局
 */
class CenterFlowLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ViewGroup(context, attrs, defStyleAttr) {
    //子 View 之间的间距
    private var mChildSpacing = DEFAULT_CHILD_SPACING
    //子 View 的 marginTop 值
    private var mRowSpacing = DEFAULT_ROW_SPACING
    //用来存储每行 item 所占用的宽度的总和
    private val itemLineWidth: MutableList<Int> = ArrayList()
    //用来存储每行 item 的个数
    private val itemLineNum: MutableList<Int> = ArrayList()
    //用来记录 item 所占用的总行数
    private var mRowTotalCount = 0

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //获取父 View 的 paddingLeft 值
        val childLeft = paddingLeft
        //获取父 View 的 paddingTop 值
        val childTop = paddingTop
        //获取去除 paddingRight 之后的宽度
        val childRight = measuredWidth - paddingRight
        //获取实际子 View 可用的宽度
        val availableWidth = childRight - childLeft
        //子 View 初始时 left 值
        var curLeft: Int
        //子 View 初始时的 top 值
        var curTop = childTop
        //单行中子 View 的最大高度
        var maxHeight = 0
        //子 View 的高度
        var childHeight: Int
        //子 View 宽度
        var childWidth: Int
        //父 View 中子 View 的 index
        var childIndex = 0
        for (j in 0 until mRowTotalCount) {
            //获取单行中子 View 的个数
            val childNum = itemLineNum[j]
            //初始化子 View 的 left 值
            curLeft = childLeft + (availableWidth - itemLineWidth[j]) / 2
            //竖直方向上的 margin 值
            var verticalMargin = 0
            for (i in 0 until childNum) {
                //获取 ViewGroup 中的子 View
                val child = getChildAt(childIndex++)
                //跳过可见性为 GONE 的子 View
                if (child.visibility === View.GONE) {
                    continue
                }
                //获取子 View 的宽度
                childWidth = child.measuredWidth
                //获取子 View 的高度
                childHeight = child.measuredHeight
                //获取子 View 的 LayoutParams
                val params: MarginLayoutParams = child.layoutParams as CenterLayoutParams
                var marginRight = 0
                var marginTop = 0
                var marginBottom: Int
                if (params is MarginLayoutParams) {
                    //获取子 View 的 marginRight 值
                    marginRight = params.rightMargin
                    //获取子 View 的 marginTop 值
                    marginTop = params.topMargin
                    //获取子 View 的 marginBottom 值
                    marginBottom = params.bottomMargin
                    //获取子 View 上下间距的和
                    if (childNum > 1 && i == 0) {
                        verticalMargin = marginTop + marginBottom
                    }
                }
                //对子 View 进行布局
                child.layout(curLeft, curTop, curLeft + childWidth, curTop + childHeight)
                //找到单行中子 View 的最高高度
                if (maxHeight < childHeight) {
                    maxHeight = childHeight
                }
                //叠加 left 值，向右依次排列
                curLeft += childWidth + mChildSpacing + marginRight
            }
            //换行时的高度
            curTop += maxHeight + mRowSpacing + verticalMargin
            //最大高度重置
            maxHeight = 0
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //将属性值重置
        itemLineNum.clear()
        itemLineWidth.clear()
        mRowTotalCount = 0
        //获取 ViewGroup 的宽度
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        //获取 ViewGroup 的宽度 mode
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        //获取 ViewGroup 的高度
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        //获取 ViewGroup 的高度 mode
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        //获取 ViewGroup 的可用宽度
        val rowLength = widthSize - paddingLeft - paddingRight
        //测量的宽度
        var measuredWidth = 0
        //测量的高度
        var measuredHeight = 0
        //子 View 最大的宽度
        var maxWidth = 0
        //子 View 最大的高度
        var maxHeight = 0
        //子 View 的总行数
        var rowCount = 0
        //子 View 的个数
        val childCount = childCount
        //单行子 View 的宽度
        var rowWidth = 0
        //子 View 宽度
        var childWidth: Int
        //子 View 高度
        var childHeight: Int
        //单行中子 View 的个数
        var childNumInRow = 0
        //最后一行第一个子 View 的 index
        var tempIndex = 0
        //除最后一行外，其他单行中子 View 的总和
        var exceptLastRowNum = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility === View.GONE) {
                continue
            }
            val params = child.layoutParams as CenterLayoutParams
            var marginRight = 0
            var marginTop = 0
            var marginBottom = 0
            if (params is MarginLayoutParams) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, measuredHeight)
                marginRight = params.rightMargin
                marginTop = params.topMargin
                marginBottom = params.bottomMargin
            } else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec)
            }
            //子 View 本身的宽度+子 View 之间的间距+子 View 的 marginRight 值（我这么写偷懒了，mChildSpacing 和 marginRight 不用同时设置值）
            childWidth = child.measuredWidth + mChildSpacing + marginRight
            //子 View 本身的高度+子 View 的 marginTop 值+子 View 的 marginBottom + marginTop 值（我这么写偷懒了，mRowSpacing 和 marginBottom + marginTop 不用同时设置值）
            childHeight = child.measuredHeight + mRowSpacing + marginBottom + marginTop
            //叠加子 View 的宽度
            rowWidth += childWidth
            //取出最大的宽度
            maxWidth += maxWidth.coerceAtLeast(childWidth)
            //判断是否需要换行
            if (measuredWidth + childWidth > rowLength) {
                //循环后，就可获取，最后一行中第一个子 View 的 index
                tempIndex = i
                //获取单行的宽度
                rowWidth = rowWidth - childWidth - mChildSpacing - marginRight
                //存储单行的宽度
                itemLineWidth.add(rowWidth)
                //设置下一行宽度为第一个子 View 的宽度
                rowWidth = childWidth
                //行数自增
                ++rowCount
                //保存测量的宽度
                measuredWidth = childWidth
                //叠加子 View 的高度
                maxHeight += childHeight
                //存储单行中子 View 的个数
                itemLineNum.add(childNumInRow)
                //叠加获取除了最后一行外，其他行子 View 的个数的总和
                exceptLastRowNum += childNumInRow
                //重置子 View 的个数，因为已经要换行了
                childNumInRow = 1
            } else {
                //叠加宽度
                measuredWidth += childWidth
                //叠加单行中的子 View 个数
                ++childNumInRow
                //计算出最大的高度
                maxHeight = maxHeight.coerceAtLeast(childHeight)
            }
        }
        //最后一行的宽度
        var lastRowWidth = 0
        var singleHorizalMargin = 0
        for (i in tempIndex until childCount) {
            val child = getChildAt(i)
            var horizalMargin = 0
            val params = child.layoutParams as CenterLayoutParams
            if (params is MarginLayoutParams) {
                //获取子 View 的 marginRight
                horizalMargin = params.rightMargin
                singleHorizalMargin = horizalMargin
            }
            //叠加最后一行中所有子 View 的宽度
            lastRowWidth += child.measuredWidth + mChildSpacing + horizalMargin
        }
        //获取最后一行子 View 的个数
        val lastChildCount = childCount - exceptLastRowNum
        //减去多余的 marginRight 或 mChildSpacing，得到最终的宽度
        lastRowWidth -= if (mChildSpacing == 0) singleHorizalMargin else mChildSpacing
        //保存最后一行的宽度值
        itemLineWidth.add(lastRowWidth)
        //保存最后一行子 View 的个数
        itemLineNum.add(lastChildCount)
        //子View的总行数
        mRowTotalCount = rowCount + 1
        //子 View 的最大宽度 + ViewGroup 中的 paddingLeft 和 paddingRight 值
        maxWidth = maxWidth.coerceAtLeast(suggestedMinimumWidth) + paddingRight + paddingLeft
        //子 View 的最大高度 + ViewGroup 中的 paddingTop 和 paddingBottom 值
        maxHeight = maxHeight.coerceAtLeast(suggestedMinimumHeight) + paddingTop + paddingBottom
        //根据 widthMode 设置 width 值
        measuredWidth = if (widthMode == MeasureSpec.EXACTLY) widthSize else maxWidth
        //根据 heightMode 设置 height 值
        measuredHeight = if (heightMode == MeasureSpec.EXACTLY) heightSize else maxHeight
        //设置 ViewGroup 的宽高
        setMeasuredDimension(View.resolveSize(measuredWidth, widthMeasureSpec),
                View.resolveSize(measuredHeight, heightMeasureSpec))
    }

    /**
     * 设置子View的间距
     * @param childSpacing
     */
    fun setChildSpacing(childSpacing: Int) {
        mChildSpacing = childSpacing
        requestLayout()
    }

    /**
     * 设置子View的marginTop值
     * @param rowSpacing
     */
    fun setRowSpacing(rowSpacing: Int) {
        mRowSpacing = rowSpacing
        requestLayout()
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return CenterLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return CenterLayoutParams(context, attrs)
    }

    /**
     * @param p
     * @return
     */
    override fun checkLayoutParams(p: LayoutParams?): Boolean {
        return p is CenterLayoutParams
    }

    /**
     * 因为需要获取子 View 的 margin 值，所以这里需要重写一下该方法
     */
    class CenterLayoutParams : MarginLayoutParams {
        constructor(source: MarginLayoutParams?) : super(source) {}
        constructor(source: LayoutParams?) : super(source) {}
        constructor(c: Context?, attrs: AttributeSet?) : super(c, attrs) {}
        constructor(width: Int, height: Int) : super(width, height) {}
    }

    companion object {
        //默认item的spacing
        private const val DEFAULT_CHILD_SPACING = 0
        //默认item的topMargin值
        private const val DEFAULT_ROW_SPACING = 0
    }

    init {
        val typedArray: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CenterFlowLayout, 0, 0)
        mChildSpacing = typedArray.getDimensionPixelSize(R.styleable.CenterFlowLayout_center_flow_layout_childSpacing, DEFAULT_CHILD_SPACING)
        mRowSpacing = typedArray.getDimensionPixelSize(R.styleable.CenterFlowLayout_center_flow_layout_rowSpacing, DEFAULT_ROW_SPACING)
        typedArray.recycle()
    }
}