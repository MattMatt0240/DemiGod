package high.caliber.productions.demigod.utils;

import high.caliber.productions.demigod.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class InventoryAdapter extends ArrayAdapter<InventoryData> {

	private Activity myContext;
	private ArrayList<InventoryData> datas;

	public InventoryAdapter(Context context, int textViewResourceId,
			ArrayList<InventoryData> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		myContext = (Activity) context;
		datas = objects;
	}

	static class ViewHolder {
		TextView postTitleView;
		TextView postDateView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = myContext.getLayoutInflater();
			convertView = inflater.inflate(R.layout.inventory_rows, null);

			viewHolder = new ViewHolder();
			viewHolder.postTitleView = (TextView) convertView
					.findViewById(R.id.tvInventoryItem);
			viewHolder.postDateView = (TextView) convertView
					.findViewById(R.id.tvInventoryQty);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.postTitleView.setText(datas.get(position).item);
		viewHolder.postDateView.setText(datas.get(position).quantity);

		return convertView;
	}

}