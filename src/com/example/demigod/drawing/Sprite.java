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

public class Sprite {

    private static final String TAG = Sprite.class.getSimpleName();
    private int x;                // the X coordinate of the object (top left of the image)
    private int y;                // the Y coordinate of the object (top left of the image)
    private Bitmap bitmap;        // the animation sequence
    private Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    private int frameNr;        // number of frames in animation
    private int currentFrame;    // the current frame
    private long frameTicker;    // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)
    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;    // the height of the sprite
    private Resources res;       // Access to Resources outside of non-Activity Class
    private int direction;       //Used to determine which spritesheet is animated
    private Rect collisionRect;  // Rect to detect collisions

    public Sprite(Context context) {
        res = context.getResources();
        x = 0;
        y = 0;
    }

    public Sprite(Context context, Bitmap bitmap, int x, int y, int fps, int frameCount) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        currentFrame = 0;
        frameNr = frameCount;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0l;
        collisionRect = new Rect(x, y, x + spriteWidth, y + spriteHeight);
    }

    public void draw(Canvas canvas) {
        // where to draw the sprite
        Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);

    }

    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            // increment the frame
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }
        }
        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;
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

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Rect getCollisionRect() {
        return this.collisionRect;
    }
}
