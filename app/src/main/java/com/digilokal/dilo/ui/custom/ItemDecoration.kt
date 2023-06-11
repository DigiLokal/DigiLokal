package com.digilokal.dilo.ui.custom

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.digilokal.dilo.R

class ItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        // Apply left padding to all items except the first one
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.left = spacing
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val divider = ContextCompat.getDrawable(parent.context, R.drawable.divider)

        for (i in 1 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val dividerLeft = child.left - params.leftMargin
            val dividerRight = child.right + params.rightMargin
            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + divider?.intrinsicHeight!!

            divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider.draw(c)
        }
    }
}