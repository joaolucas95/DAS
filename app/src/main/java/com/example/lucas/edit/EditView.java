package com.example.lucas.edit;

import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.List;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();

        if (mBitmap == null) {
            init();
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Point point = new Point((int) event.getX(), (int) event.getY());
                int[] position = new int[]{point.x, point.y};
                getActivity().addComponent(position);
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

        throw new IllegalStateException();
    }

    private ComponentType getSelectedType() {
        //noinspection ConstantConditions
        return getActivity().getSelectedType();
    }

    /* Draw methods */

    void drawProject(Component component) {
        mCanvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
        ComponentModule module = (ComponentModule) component;
        List<Component> components = module.getData();

        if (components.isEmpty()) {
            setImageBitmap(mBitmap);
            return;
        }

        for (Component cmp : components) {
            drawDataComponent(cmp);
        }
    }

    private void drawDataComponent(Component component) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getColor(component.getType()));
        mCanvas.drawRect(getRectangle(component.getPosition()), paint);
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

    private Rect getRectangle(int[] position) {
        int left = position[0] - 50;
        int right = position[0] + 50;
        int top = position[1] - 50;
        int bottom = position[1] + 50;

        return new Rect(left, top, right, bottom);
    }
}
