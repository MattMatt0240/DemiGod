package high.caliber.productions.demigod;

import java.util.ArrayList;

import android.content.Context;

public class Map {

	Context context;

	private ArrayList<Tile> tiles, objects;
	private String mapName;
	private int width, height;

	public Map() {
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	public ArrayList<Tile> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<Tile> objects) {
		this.objects = objects;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
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
