package com.example.lucas.edit;

import com.example.mainpackage.logic.project.component.ComponentType;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class EditView extends AppCompatImageView {

    private Bitmap mBitmap;
    private Canvas mCanvas;

    public EditView(Context context) {
        super(context);
    }

    public EditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();

        if (mBitmap == null) {
            init();
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Point point = new Point((int) event.getX(), (int) event.getY());
                drawComponent(point);
                break;

            default:
                break;

        }


        return true;
    }

    private EditActivity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof EditActivity) {
                return (EditActivity) context;
            }

            context = ((ContextWrapper) context).getBaseContext();
        }

        return null;
    }

    private ComponentType getSelectedType() {
        //noinspection ConstantConditions
        return getActivity().getSelectedType();
    }

    /* Draw methods */

    private void drawComponent(Point point) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getColor(getSelectedType()));
        mCanvas.drawRect(getRectangle(point), paint);
        setImageBitmap(mBitmap);
    }

    private int getColor(ComponentType type) {
        switch (type) {
            case LOGIC_AND:
                return Color.RED;

            case LOGIC_OR:
                return Color.GREEN;

            case OUTPUT:
                return Color.BLACK;

            case INPUT:
                return Color.GRAY;

            case MODULE:
                return Color.BLUE;

            default:
                return Color.WHITE;
        }
    }

    private Rect getRectangle(Point point) {
        int left = point.x - 50;
        int right = point.x + 50;
        int top = point.y - 50;
        int bottom = point.y + 50;

        return new Rect(left, top, right, bottom);
    }
}
