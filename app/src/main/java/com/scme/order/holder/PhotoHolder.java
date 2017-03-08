package com.scme.order.holder;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.scme.order.model.Photo;
import com.scme.order.ui.R;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.XutilsGetData;
import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
//import com.xiangmu.lzx.Modle.Photo;
//import com.xiangmu.lzx.R;
//import com.xiangmu.lzx.utils.XutilsGetData;

/**
 * Created by twiceYuan on 3/4/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 */
@LayoutId(R.layout.item_photolzx)
public class PhotoHolder extends CommonHolder<Photo> {

    @ViewId(R.id.imagePicture) ImageView imagePicture;
    @ViewId(R.id.textDesc)     TextView  textDesc;

    @Override public void bindData(Photo photo) {
        Context context = getItemView().getContext();
     //   imagePicture.setImageDrawable(ContextCompat.getDrawable(context, photo.photoId));
        XutilsGetData.xUtilsImageiv( imagePicture, HttpUtil.BASE_URL+"upload/"+photo.path,context,false);
//        XutilsGetData.xUtilsImageiv( imagePicture, "http://www.dctgb.com:8086/upload/"+photo.path,context,false);
//        XutilsGetData.xUtilsImageiv( imagePicture,photo.path,context,false);
      //  imagePicture=photo.imagePicture;
        textDesc.setText(photo.description);
    }
}
