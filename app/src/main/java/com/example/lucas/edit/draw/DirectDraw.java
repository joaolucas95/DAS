package com.example.lucas.edit.draw;

import com.example.lucas.edit.EditUtils;

import android.graphics.Canvas;
import android.graphics.Paint;

public class DirectDraw implements IDrawStrategy {

    @Override
    public void drawLine(int startX, int startY, int stopX, int stopY, Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(EditUtils.LINE_THICKNESS);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }
}
