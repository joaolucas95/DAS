package com.example.lucas.edit;

import com.example.lucas.edit.draw.DirectDraw;
import com.example.lucas.edit.draw.IDrawStrategy;
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
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.List;

public class EditView extends AppCompatImageView {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private IDrawStrategy mDrawStrategy;

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
        mDrawStrategy = new DirectDraw();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                int[] position = new int[]{x, y};
                getActivity().handleTap(position);
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

    /* Draw methods */

    void drawProject(List<Component> actualComponents, Component selectedComponent) {
        if (mBitmap == null) {
            init();
        }

        mCanvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);

        if (actualComponents.isEmpty()) {
            setImageBitmap(mBitmap);
            return;
        }

        for (Component cmp : actualComponents) {
            boolean isSelected = EditUtils.isSameComponent(cmp, selectedComponent);
            drawDataComponent(cmp, isSelected, actualComponents);
        }
    }

    private void drawDataComponent(Component component, boolean isSelected, List<Component> actualComponents) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        int color = isSelected ? Color.BLACK : getColor(component.getType());
        paint.setColor(color);

        mCanvas.drawRect(getRectangle(component.getPosition()), paint);
        drawConnections(component, actualComponents);
        setImageBitmap(mBitmap);
    }

    private void drawConnections(Component component, List<Component> actualComponents) {
        int[] pos = component.getPosition();
        int stopX = pos[0] - EditUtils.COMPONENT_RADIUS;
        int stopY = pos[1];

        List<Component> previousList = component.getPrevious();
        if (previousList == null || previousList.isEmpty()) {
            return;
        }

        if (component.getType() == ComponentType.MODULE || component.getType() == ComponentType.PROJECT) {
            ComponentModule module = (ComponentModule) component;

            for (Component input : module.getInputList()) {
                if (input.getPrevious() == null || input.getPrevious().isEmpty()) {
                    continue;
                }

                for (Component previous : input.getPrevious()) {
                    drawConnectionWithModule(stopX, stopY, previous, actualComponents);
                }
            }

            return;
        }

        for (Component previous : component.getPrevious()) {
            drawConnection(stopX, stopY, previous);
        }
    }

    private void drawConnection(int stopX, int stopY, Component previous) {
        int[] pos = previous.getPosition();
        int startX = pos[0] + EditUtils.COMPONENT_RADIUS;
        int startY = pos[1];

        mDrawStrategy.drawLine(startX, startY, stopX, stopY, mCanvas);
    }

    private void drawConnectionWithModule(int stopX, int stopY, Component previous, List<Component> actualComponents) {
        Component selected = null;

        for (Component cmp : actualComponents) {
            if (!(cmp instanceof ComponentModule)) {
                if (previous.getName().equals(cmp.getName())) {
                    selected = cmp;
                    break;
                }

                continue;
            }

            ComponentModule module = (ComponentModule) cmp;
            for (Component data : module.getData()) {
                if (previous.getName().equals(data.getName())) {
                    selected = module;
                    break;
                }
            }
        }

        if (selected == null) {
            return;
        }

        drawConnection(stopX, stopY, selected);
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
            case PROJECT:
                return Color.BLUE;

            default:
                return Color.WHITE;
        }
    }

    private Rect getRectangle(int[] position) {
        int left = position[0] - EditUtils.COMPONENT_RADIUS;
        int right = position[0] + EditUtils.COMPONENT_RADIUS;
        int top = position[1] - EditUtils.COMPONENT_RADIUS;
        int bottom = position[1] + EditUtils.COMPONENT_RADIUS;

        return new Rect(left, top, right, bottom);
    }
}
