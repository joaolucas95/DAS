package com.example.lucas.edit.draw;

import com.example.lucas.edit.EditUtils;

import android.graphics.Canvas;
import android.graphics.Paint;

public class OrtogonalDraw implements IDrawStrategy {

    @Override
    public void drawLine(int startX, int startY, int stopX, int stopY, Canvas canvas) {
        if (startY == stopY) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(EditUtils.LINE_THICKNESS);
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }

        int middleX = Math.abs(stopX - startX) / 2 + Math.min(startX, stopX);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(EditUtils.LINE_THICKNESS);
        canvas.drawLine(startX, startY, middleX, startY, paint);
        canvas.drawLine(middleX, startY, middleX, stopY, paint);
        canvas.drawLine(middleX, stopY, stopX, stopY, paint);
    }
}