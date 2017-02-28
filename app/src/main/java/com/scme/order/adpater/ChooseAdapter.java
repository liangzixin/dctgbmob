package com.scme.order.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scme.order.interfaces.ItemClickListener;
import com.scme.order.model.Photoimage;

import com.bumptech.glide.Glide;
//import com.litao.android.lib.entity.PhotoEntry;
import com.scme.order.ui.R;
//import com.litao.android.lib.entity.Photoimage;
//import com.xiangmu.lzx.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李涛 on 16/4/30.
 */
public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.MViewHolder> {

    private List<Photoimage> list = new ArrayList<Photoimage>();

    private Context mContext;

    private LayoutInflater mInflater;

    private OnItmeClickListener mlistener;
    private ItemClickListener mItemClickListener;

    public  interface OnItmeClickListener{
        void onItemClicked(int position);

    }

    public ChooseAdapter(Context mContext, List<Photoimage> mList) {
        this.mContext = mContext;
        this.list=mList;
//        mlistener = (OnItmeClickListener) mContext;
        mItemClickListener= (ItemClickListener) mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        list.add(createAddEntry());
    }

    public void reloadList(List<Photoimage> data) {
        if (data != null) {
            list.clear();
            list.addAll(data);
            list.add(createAddEntry());
        } else {
            list.clear();
        }
        notifyDataSetChanged();

    }

    public void appendList(List<Photoimage> data) {
        if (data != null) {
            list.addAll(list.size()-1,data);
        } else {
            list.clear();
        }
        notifyDataSetChanged();

    }
    public void setItemClickListener(ItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;
    }

    public void appendPhoto(Photoimage entry) {
        if (entry != null) {
            list.add(list.size()-1,entry);
        }
        notifyDataSetChanged();
    }

    public List<Photoimage> getData(){
        return list.subList(0,list.size()-1);
    }
    public Photoimage getEntry(int position) {
        return list.get(position);
    }

    private Photoimage createAddEntry(){
        return new Photoimage();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        ViewHolder vh = new ViewHolder(mInflater.inflate(R.layout.item_selected_photo, viewGroup, false), i);
//        return vh;
//    }
//
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int i) {
//        if (i==list.size()-1){
//            viewHolder.mImageView.setImageResource(R.drawable.add);
//        }else {
//            Photoimage entry = list.get(i);
//            Glide.with(mContext)
//                    .load(new File(entry.getPath()))
//                    .centerCrop()
//                    .placeholder(com.litao.android.lib.R.mipmap.default_image)
//                    .into(viewHolder.mImageView);
//        }
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;

        private int position;

        public ViewHolder(View itemView, int position) {
            super(itemView);
            this.position = position;
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mlistener.onItemClicked(position);
        }
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {

        View view = View.inflate(viewGroup.getContext(),
                R.layout.item_user_friend_nod, null);
        // 创建一个ViewHolder
        MViewHolder holder = new MViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MViewHolder mViewHolder,
                                 final int postion) {

        mViewHolder.mTextView.setText(list.get(postion).getPath());
        mViewHolder.image.setBackgroundResource(R.drawable.add);
        // 为image添加监听回调
        mViewHolder.image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != mItemClickListener) {
                    mItemClickListener.onItemSubViewClick(mViewHolder.image,
                            postion);
                }

            }

        });

    }

    public class MViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView image;

        public MViewHolder(final View view) {
            super(view);
            this.mTextView = (TextView) view.findViewById(R.id.tv_friend_name);
            this.image = (ImageView) itemView
                    .findViewById(R.id.img_friend_avatar);
            // 为item添加普通点击回调
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (null != mItemClickListener) {
                        mItemClickListener.onItemClick(view, getPosition());
                    }

                }
            });

            // 为item添加长按回调
            view.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    if (null != mItemClickListener) {
                        mItemClickListener.onItemLongClick(view, getPosition());
                    }
                    return true;
                }
            });

        }
    }

}
