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
import android.graphics.Rect;

public class Tile {

	private Context context;
	private Bitmap bitmap;
	private int x, y;
	private boolean isCollideable;
	private Rect collisionRect;
	private int width, height;

	public Tile(Context context, Bitmap bitmap, int x, int y, int width,
			int height) {
		this.context = context;
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		collisionRect = new Rect(x, y, x + width, y + height);
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isCollideable() {
		return isCollideable;
	}

	public void setCollideable(boolean isCollideable) {
		this.isCollideable = isCollideable;
	}

	public Rect getCollisionRect() {
		return collisionRect;
	}

	public void setCollisionRect(int left, int top, int right, int bottom) {

		if (collisionRect != null) {
			collisionRect.set(left, top, right, bottom);
		} else {
			collisionRect = new Rect(left, top, right, bottom);
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
