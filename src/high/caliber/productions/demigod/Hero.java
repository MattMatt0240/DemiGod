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

package high.caliber.productions.demigod;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Hero {

	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	public static final int DIRECTION_RIGHT = 4;

	private int x;
	private int y;
	private Bitmap bitmap;
	private Rect sourceRect;
	private int frameNr;
	private int currentFrame;
	private long frameTicker;
	private int framePeriod;
	private int spriteWidth;
	private int spriteHeight;
	private int direction;
	private Rect collisionRect;

	public Hero(Context context) {
		x = 0;
		y = 0;
	}

	public Hero(Context context, Bitmap bitmap, int x, int y, int fps,
			int frameCount) {
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
		Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY()
				+ spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);

	}

	public void update(long gameTime) {
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			currentFrame++;
			if (currentFrame >= frameNr) {
				currentFrame = 0;
			}
		}
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

	public void setAnimBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap getAnimBitmap() {
		return this.bitmap;
	}
}
