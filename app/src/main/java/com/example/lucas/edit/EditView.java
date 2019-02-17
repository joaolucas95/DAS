package com.example.lucas.edit;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

public class EditView extends AppCompatImageView {

    public EditView(Context context) {
        super(context);
    }

    public EditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();
       // final int pointer = event.getActionIndex();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Point point = new Point((int) event.getX(), (int) event.getY());
                Toast.makeText(getContext(), point.toString(), Toast.LENGTH_SHORT).show();
                break;

                default:
                    break;

        }


        return true;
    }
}
