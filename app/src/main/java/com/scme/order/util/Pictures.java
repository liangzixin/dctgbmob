package com.scme.order.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Log;



public class Pictures {
	
	public static final String PIC_URL = HttpUtil.BASE_URL + "Images/";
	public static final String PIC_LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "foods" + File.separator;

	private static Resources res;
	
	public static void setRes(Resources r){
		res = r;
	}
	
	/**
	 * 取图片文件。
	 * @param picName
	 * @return
	 */
	public static Bitmap getMenuPic(String picName){
		if(picName==null || picName.equals("")){
//			BitmapFactory.decodeResource(res, R.drawable.blank);
		}
		File pic = new File(PIC_LOCAL_PATH + picName);
		Bitmap bitmap = null;
		if(pic.exists()){
			bitmap = getBitmapFromLocal(pic);
		} else {
			bitmap = getBitmapFromUrl(PIC_URL + picName);
			saveImage(bitmap, pic);
			return bitmap;
		}
		return toRoundCorner(bitmap,30);
	}
	
	//从SDCard读取图片文件
	public static Bitmap getBitmapFromLocal(String path){
		return getBitmapFromLocal(new File(path));
	}
	
	public static Bitmap getBitmapFromLocal(File file){
		Bitmap bmp=null;
		try {
			FileInputStream fis=new FileInputStream(file);
			BufferedInputStream bis=new BufferedInputStream(fis,8*1024);//自定义缓存
			BitmapFactory.Options options=new Options();
			bmp=BitmapFactory.decodeStream(bis);
			bis.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bmp;
	}

	//从网络中读取图片文件
	public static Bitmap getBitmapFromUrl(String bmpUrl){
		Bitmap bmp=null;
		try {
			URL url=new URL(bmpUrl);
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			InputStream is=conn.getInputStream();
			BufferedInputStream bis=new BufferedInputStream(is,8*1024);//自定义缓存
			BitmapFactory.Options options=new Options();
			options.inSampleSize=10;
			bmp=BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmp;
	}
	
	// 保存图片到指定的文件中
	public static Boolean saveImage(Bitmap image, File file) {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file, true));
			image.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
			return true;
		} catch (Exception ex) {
			Log.v("---------saveFileBUG---------", ex.getMessage());
			return false;
		}
	}
	
	/**     
	    * 把图片变成圆角       
	    * @param bitmap 需要修改的图片       
	    * @param pixels 圆角的弧度       
	    * @return 圆角图片       
	    */          
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {          
	       Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);          
	       Canvas canvas = new Canvas(output);          
	       final int color = 0xff424242;          
	       final Paint paint = new Paint();          
	       final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());          
	       final RectF rectF = new RectF(rect);          
	       final float roundPx = pixels;          
	       paint.setAntiAlias(true);          
	       canvas.drawARGB(0, 0, 0, 0);          
	       paint.setColor(color);          
	       canvas.drawRoundRect(rectF, roundPx, roundPx, paint);          
	           
	       paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));          
	       canvas.drawBitmap(bitmap, rect, rect, paint);          
	       return output;          
	} 
	
}
