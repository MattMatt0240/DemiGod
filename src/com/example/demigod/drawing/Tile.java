/*
 Copyright (C) 2013  

 @author High Caliber Productions

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.example.demigod.drawing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Matt Matt on 8/25/13.
 */

public class Tile {

    private static final String TAG = Tile.class.getSimpleName();
    private int x;                // the X coordinate of the object (top left of the image)
    private int y;                // the Y coordinate of the object (top left of the image)
    private Bitmap bitmap;        // the animation sequence
    private int tileWidth;    // the width of the sprite to calculate the cut out rectangle
    private int tileHeight;    // the height of the sprite
    private Resources res;       // Access to Resources outside of non-Activity Class
    private Rect collisionRect;  // Rect to determine if sprite collides with another rect

    public Tile(Context context) {
        res = context.getResources();
        x = 0;
        y = 0;
    }

    public Tile(Context context, Bitmap bitmap, int x, int y, int width, int height) {
        this.res = context.getResources();
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.collisionRect = new Rect(x, y, x + width, y + height);
    }

    public void draw(Canvas canvas) {
        // where to draw the sprite
        canvas.drawBitmap(bitmap, getX(), getY(), null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rect getCollisionRect() {
        return this.collisionRect;
    }
}
