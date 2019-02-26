package com.example.lucas.edit.draw;

import android.graphics.Canvas;

public interface IDrawStrategy {

    void drawLine(int startX, int startY, int stopX, int stopY, Canvas canvas);
}