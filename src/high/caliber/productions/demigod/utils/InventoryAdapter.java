package high.caliber.productions.demigod.utils;

import high.caliber.productions.demigod.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InventoryAdapter extends ArrayAdapter<Item> {

	private Activity myContext;
	private ArrayList<Item> items;

	public InventoryAdapter(Context context, int textViewResourceId,
			ArrayList<Item> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		myContext = (Activity) context;
		items = objects;
	}

	static class ViewHolder {
		ImageView listIcon;
		TextView listItem;
		TextView listQty;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = myContext.getLayoutInflater();
			convertView = inflater.inflate(R.layout.inventory_rows, null);

			viewHolder = new ViewHolder();
			viewHolder.listIcon = (ImageView) convertView
					.findViewById(R.id.ivInventoryIcon);
			viewHolder.listItem = (TextView) convertView
					.findViewById(R.id.tvInventoryItem);
			viewHolder.listQty = (TextView) convertView
					.findViewById(R.id.tvInventoryQty);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.listIcon.setImageBitmap(items.get(position).icon);
		viewHolder.listItem.setText(items.get(position).item);
		viewHolder.listQty
				.setText(String.valueOf(items.get(position).quantity));

		return convertView;
	}

}
