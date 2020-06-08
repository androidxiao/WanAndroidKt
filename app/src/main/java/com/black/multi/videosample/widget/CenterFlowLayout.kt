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
    private var mChildSpacing = DEFAULT_CHILD_SPACING
    private var mRowSpacing = DEFAULT_ROW_SPACING
    private val itemLineWidth: MutableList<Int> = ArrayList()
    private val itemLineNum: MutableList<Int> = ArrayList()
    private var mRowTotalCount = 0

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childLeft = paddingLeft
        val childTop = paddingTop
        val childRight = measuredWidth - paddingRight
        val availableWidth = childRight - childLeft
        var curLeft: Int
        var curTop = childTop
        var maxHeight = 0
        var childHeight: Int
        var childWidth: Int
        var childIndex = 0
        for (j in 0 until mRowTotalCount) {
            val childNum = itemLineNum[j]
            curLeft = childLeft + (availableWidth - itemLineWidth[j]) / 2
            var verticalMargin = 0
            for (i in 0 until childNum) {
                val child = getChildAt(childIndex++)
                if (child.visibility === View.GONE) {
                    continue
                }
                childWidth = child.measuredWidth
                childHeight = child.measuredHeight
                val params: MarginLayoutParams = child.layoutParams as CenterLayoutParams
                var marginRight = 0
                var marginTop = 0
                var marginBottom: Int
                if (params is MarginLayoutParams) {
                    marginRight = params.rightMargin
                    marginTop = params.topMargin
                    marginBottom = params.bottomMargin
                    if (childNum > 1 && i == 0) {
                        verticalMargin = marginTop + marginBottom
                    }
                }
                child.layout(curLeft, curTop, curLeft + childWidth, curTop + childHeight)
                if (maxHeight < childHeight) {
                    maxHeight = childHeight
                }
                curLeft += childWidth + mChildSpacing + marginRight
            }
            curTop += maxHeight + mRowSpacing + verticalMargin
            maxHeight = 0
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        itemLineNum.clear()
        itemLineWidth.clear()
        mRowTotalCount = 0
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val rowLength = widthSize - paddingLeft - paddingRight
        var measuredWidth = 0
        var measuredHeight = 0
        var maxWidth = 0
        var maxHeight = 0
        var rowCount = 0
        val childCount = childCount
        var rowWidth = 0
        var childWidth: Int
        var childHeight: Int
        var childNumInRow = 0
        var tempIndex = 0
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
            childWidth = child.measuredWidth + mChildSpacing + marginRight
            childHeight = child.measuredHeight + mRowSpacing + marginBottom + marginTop
            rowWidth += childWidth
            maxWidth += Math.max(maxWidth, childWidth)
            if (measuredWidth + childWidth > rowLength) {
                tempIndex = i
                rowWidth = rowWidth - childWidth - mChildSpacing - marginRight
                itemLineWidth.add(rowWidth)
                rowWidth = childWidth
                ++rowCount
                measuredWidth = childWidth
                maxHeight += childHeight
                itemLineNum.add(childNumInRow)
                exceptLastRowNum += childNumInRow
                childNumInRow = 1
            } else {
                measuredWidth += childWidth
                ++childNumInRow
                maxHeight = Math.max(maxHeight, childHeight)
            }
        }
        var lastRowWidth = 0
        var singleHorizalMargin = 0
        for (i in tempIndex until childCount) {
            val child = getChildAt(i)
            var horizalMargin = 0
            val params = child.layoutParams as CenterLayoutParams
            if (params is MarginLayoutParams) {
                horizalMargin = params.rightMargin
                singleHorizalMargin = horizalMargin
            }
            lastRowWidth += child.measuredWidth + mChildSpacing + horizalMargin
        }
        val lastChildCount = childCount - exceptLastRowNum
        lastRowWidth -= if (mChildSpacing == 0) singleHorizalMargin else mChildSpacing
        itemLineWidth.add(lastRowWidth)
        itemLineNum.add(lastChildCount)
        mRowTotalCount = rowCount + 1
        maxWidth = maxWidth.coerceAtLeast(suggestedMinimumWidth) + paddingRight + paddingLeft
        maxHeight = maxHeight.coerceAtLeast(suggestedMinimumHeight) + paddingTop + paddingBottom
        measuredWidth = if (widthMode == MeasureSpec.EXACTLY) widthSize else maxWidth
        measuredHeight = if (heightMode == MeasureSpec.EXACTLY) heightSize else maxHeight
        setMeasuredDimension(View.resolveSize(measuredWidth, widthMeasureSpec),
                View.resolveSize(measuredHeight, heightMeasureSpec))
    }

    fun setChildSpacing(childSpacing: Int) {
        mChildSpacing = childSpacing
        requestLayout()
    }

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

    class CenterLayoutParams : MarginLayoutParams {
        constructor(source: MarginLayoutParams?) : super(source) {}
        constructor(source: LayoutParams?) : super(source) {}
        constructor(c: Context?, attrs: AttributeSet?) : super(c, attrs) {}
        constructor(width: Int, height: Int) : super(width, height) {}
    }

    companion object {
        private const val DEFAULT_CHILD_SPACING = 0
        private const val DEFAULT_ROW_SPACING = 0
    }

    init {
        val typedArray: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CenterFlowLayout, 0, 0)
        mChildSpacing = typedArray.getDimensionPixelSize(R.styleable.CenterFlowLayout_center_flow_layout_childSpacing, DEFAULT_CHILD_SPACING)
        mRowSpacing = typedArray.getDimensionPixelSize(R.styleable.CenterFlowLayout_center_flow_layout_rowSpacing, DEFAULT_ROW_SPACING)
        typedArray.recycle()
    }
}