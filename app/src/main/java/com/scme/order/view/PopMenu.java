package  com.scme.order.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scme.order.ui.MainMenuActivity;
import com.scme.order.ui.R;

public class PopMenu {
	private ArrayList<String> itemList;
	private Context context;
	private PopupWindow popupWindow ;
	private ListView listView;
	//private OnItemClickListener listener;


	public PopMenu(Context context,String str) {
		// TODO Auto-generated constructor stub
		this.context = context;

		itemList = new ArrayList<String>(5);

		View view = LayoutInflater.from(context).inflate(R.layout.popmenu, null);

		//设置 listview
		listView = (ListView)view.findViewById(R.id.listView);
		listView.setAdapter(new PopAdapter());
		listView.setFocusableInTouchMode(true);
		listView.setFocusable(true);

//		popupWindow = new PopupWindow(view, 100, LayoutParams.WRAP_CONTENT);
//		if(str.equals("0")) {
//			popupWindow = new PopupWindow(view,
//					context.getResources().getDimensionPixelSize(R.dimen.popmenu_width1),
//					LayoutParams.WRAP_CONTENT);
//		}else {
			popupWindow = new PopupWindow(view,
					context.getResources().getDimensionPixelSize(R.dimen.popmenu_width),
					LayoutParams.WRAP_CONTENT);
//		}
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	//设置菜单项点击监听器
	public void setOnItemClickListener(OnItemClickListener listener) {
		//this.listener = listener;
		listView.setOnItemClickListener(listener);
	}

	//批量添加菜单项
	public void addItems(String[] items) {
		for (String s : items)
			itemList.add(s);
	}

	//单个添加菜单项
	public void addItem(String item) {
		itemList.add(item);
	}

	//下拉式 弹出 pop菜单 parent 右下角
	public void showPopUp(View v) {
//	public void showAsDropDown(View parent) {
//		popupWindow.showAsDropDown(parent, Gravity.TOP,20,0);
//			popupWindow.setFocusable(true);
//			popupWindow.setOutsideTouchable(true);
//			popupWindow.update();
//		LinearLayout layout = new LinearLayout(context);
//		layout.setBackgroundColor(Color.GRAY);
//		TextView tv = new TextView(context);
//		tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		tv.setText("I'm a pop -----------------------------!");
//		tv.setTextColor(Color.WHITE);
//		layout.addView(tv);

//		popupWindow = new PopupWindow(layout,120,120);

		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		int[] location = new int[2];
//		v.getLocationOnScreen(location);
		v.getLocationInWindow(location);
		int rows=itemList.size();
    System.out.println("x="+location[0]+" ; "+"location[1]="+(location[1]));
		System.out.println("x="+location[0]+" ; "+"popupWindow.getHeight()="+popupWindow.getHeight());
		System.out.println("rows="+rows);
//		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,location[0],location[1]-500);
		popupWindow.showAtLocation(v,Gravity.NO_GRAVITY,location[0]-50,location[1]-rows*150);
	}

	//隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	}

	// 适配器
	private final class PopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return itemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.pomenu_item, null);
				holder = new ViewHolder();

				convertView.setTag(holder);

				holder.groupItem = (TextView) convertView.findViewById(R.id.textView);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.groupItem.setText(itemList.get(position));

			return convertView;
		}

		private final class ViewHolder {
			TextView groupItem;
		}
	}
}
