package com.example.apiuser.ui.toutiao

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(val space: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = space
        outRect.bottom = space
        outRect.left = space
        outRect.right = space
    }

}