package com.bpp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class TrackableHorizontalScrollView extends HorizontalScrollView {

    private HorizontalScrollViewListener scrollViewListener = null;

    public TrackableHorizontalScrollView(Context context) {
        super(context);
    }

    public TrackableHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TrackableHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(HorizontalScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}