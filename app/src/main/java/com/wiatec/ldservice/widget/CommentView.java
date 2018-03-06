package com.wiatec.ldservice.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrick on 03/03/2018.
 * create time : 11:59 AM
 */

public class CommentView extends AppCompatTextView {

    private List<String> commentList;
    private TextPaint textPaint;

    public CommentView(Context context) {
        this(context, null);
    }

    public CommentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        getTextColors().describeContents();
        commentList = new ArrayList<>();
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void add(String comment){
        commentList.add(comment);
        postInvalidate();
    }
}
