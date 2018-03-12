package com.scme.order.adpater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scme.order.model.Rzxx;
import com.scme.order.ui.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sysu.zyb.panellistlibrary.AbstractPanelListAdapter;
import sysu.zyb.panellistlibrary.PanelListLayout;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/05/23
 *     desc   : 整个页面的Adapter，内部使用了两个子Adapter
 *              开发者可自行定义两个子Adapter
 *     version: 1.0
 * </pre>
 * @author zyb
 */

public class MyRzxxListAdapter extends AbstractPanelListAdapter {

    private Context context;

    private ListView lv_content;
    private int contentResourceId;
    private Set<Rzxx> contentList =new HashSet<Rzxx>();

    /**
     * constructor
     *
     * @param context 上下文
     * @param pl_root 根布局（PanelListLayout）
     * @param lv_content content 部分的布局（ListView）
     * @param contentResourceId content 部分的 item 布局
     * @param contentList content 部分的数据
     */
    public MyRzxxListAdapter(Context context, PanelListLayout pl_root, ListView lv_content,
                             int contentResourceId, Set<Rzxx> contentList) {
        super(context, pl_root, lv_content);
        this.context = context;
        this.lv_content = lv_content;
        this.contentResourceId = contentResourceId;
        this.contentList = contentList;
    }

    /**
     * 给该方法添加实现，返回Content部分的适配器
     *
     * @return adapter of content
     */
    @Override
    protected BaseAdapter getContentAdapter() {
        return new ContentAdapter(context,contentResourceId,contentList);
    }

    /**
     * content部分的adapter
     *
     * 这里可以自由发挥，和普通的 ListView 的 Adapter 没区别
     */
    private class ContentAdapter extends ArrayAdapter {

        private Set<Rzxx> contentList;
        private int resourceId;

        ContentAdapter(Context context, int resourceId, Set<Rzxx> contentList) {
            super(context, resourceId);
            this.contentList = contentList;
            this.resourceId = resourceId;
        }

        @Override
        public int getCount() {
            return contentList.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Map<String, Rzxx> data = contentList.get(position);
            Log.d("ybz-get", "getView: Content getView");
            View view;
            ViewHolder viewHolder;

            if (convertView == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tv_01.setText(data.get("rzxx").getRznd());
            viewHolder.tv_02.setText(data.get("rzxx").getRzjk());
            viewHolder.tv_03.setText(data.get("rzxx").getRzzb());
            viewHolder.tv_04.setText(data.get("rzxx").getRzsj());
            viewHolder.tv_05.setText(data.get("rzxx").getRzdd());


            if (lv_content.isItemChecked(position)){
                view.setBackgroundColor(context.getResources().getColor(R.color.colorSelected));
            } else {
                view.setBackgroundColor(context.getResources().getColor(R.color.colorDeselected));
            }
            Log.d("ybz", "getView: itemview = "+ view.toString());

            return view;
        }

        private class ViewHolder {
            TextView tv_01;
            TextView tv_02;
            TextView tv_03;
            TextView tv_04;
            TextView tv_05;


            ViewHolder(View itemView) {
                tv_01 = (TextView) itemView.findViewById(R.id.id_tv_01);
                tv_02 = (TextView) itemView.findViewById(R.id.id_tv_02);
                tv_03 = (TextView) itemView.findViewById(R.id.id_tv_03);
                tv_04 = (TextView) itemView.findViewById(R.id.id_tv_04);
                tv_05 = (TextView) itemView.findViewById(R.id.id_tv_05);

            }
        }
    }
}
