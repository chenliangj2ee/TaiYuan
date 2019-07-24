package com.glhd.tb.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {
	//启动activity
	public static void startActivity(Context mContext, Class<?> toClass){
		startActivity(mContext, toClass, null);
	} 
	public static void startActivity(Context mContext, Class<?> toClass, Bundle mBundle){
		Intent intent=new Intent();
		if(mBundle!=null){
			intent.putExtras(mBundle);
		}
		intent.setClass(mContext, toClass);
		mContext.startActivity(intent);  
	}


	public static void startActivityForResult(Activity mContext, Class<?> toActivity, int requestCode){
		Intent intent=new Intent(mContext,toActivity);
		mContext.startActivityForResult(intent, requestCode); 
	} 
	public static void startActivityForResult(Activity mContext, Class<?> toActivity, Bundle mBundle, int requestCode){
		Intent intent=new Intent();
		if(mBundle!=null){
			intent.putExtras(mBundle);
		}
		intent.setClass(mContext,toActivity);
		
		mContext.startActivityForResult(intent, requestCode); 
	} 
	
	
	public static void startActivity(Fragment fragment, Class<?> toClass){
		startActivity(fragment, toClass, null);
	}
	public static void startActivity(Fragment fragment, Class<?> toClass, Bundle mBundle){
		Intent intent=new Intent();
		if(mBundle!=null)
			intent.putExtras(mBundle);
		intent.setClass(fragment.getContext(), toClass);
		fragment.startActivity(intent);
	}
	//end
	
	public static <T> T fromJson(String json, Class<T> type){
		Gson g = new Gson();
		return g.fromJson(json, type);
	}
	public static <T> T fromJson(JsonElement json, Class<T> type){
		Gson g = new Gson();
		return g.fromJson(json, type);
	}

	public static <T> T fromJson(JsonElement json, Type type){
		Gson g = new Gson();
		return g.fromJson(json,type);
	}	
	public static <T> T fromJson(String json, Type type){
		Gson g = new Gson();
		return g.fromJson(json,type);
	}

	/**
	 * dateformat
	 */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat();
	public static String formatDate(String format, long time){
		Date date = new Date(time);
		dateFormat.applyPattern(format);
		return dateFormat.format(date);
	}


	//文本验证，操作
	/**
	 * 验证邮箱地址是否正确 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		} 
		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean checkPhone(String mobiles) {
		String telRegex = "[1][3578]\\d{9}";
		if (TextUtils.isEmpty(mobiles)) return false;
		else return mobiles.matches(telRegex);
	}
	/**
	 * 去掉手机号码中的空格，再验证
	 * @param phone
	 * @return
	 */
	public static boolean checkPhoneWithSpase(String phone){
		return checkPhone(phone.replaceAll("\\s", ""));
	}


	/**
	 * 手机号加*
	 */
	public static String getPhoStar(String phone) {
		if (phone == null || phone.length() < 11)
			return phone;
		char[] chs = phone.toCharArray();
		for (int i = 3; i < 7; i++)
			chs[i] = '*';
		return String.valueOf(chs);
	}

	/**
	 * 邮箱加*
	 */
	public static String getEmailStar(String email) {
		int index;
		if (email == null || (index = email.indexOf("@")) < 4)
			return email;
		int len = Math.min(index / 2, 4);
		char[] chs = email.toCharArray();
		for (int i = index - len - 1; i < index - 1; i++)
			chs[i] = '*';
		return String.valueOf(chs);
	}

	public static String getIdentityStar(String identity) {
		if (identity == null || identity.length() < 15)
			return identity;
		char[] chs = identity.toCharArray();
		for (int i = 6; i < 14; i++)
			chs[i] = '*';
		return String.valueOf(chs);
	}



	//end
	/**
	 * 获取屏幕宽度
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm =	context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}
	/**
	 * 获取屏幕高度
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics dm =	context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}
	/**
	 * dp2px
	 */
	public static int dipTopx(Context context, float dpValue) {
		return Math.round(dpValue*context.getResources().getDisplayMetrics().density);
	}
	/**
	 * px2dp
	 */
	public static int pxTodip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	/**
	 * px2sp
	 */
	public static int pxTosp(Context context, float pxValue, float fontScale){
		return (int)(pxValue/fontScale + 0.5f);
	}
	/**
	 * sp2px
	 */
	public static int spTopx(float spValue,float fontScale){
		return (int)(spValue * fontScale + 0.5f);
	}

	/** 
	 * 获取屏幕宽度和高度，单位为px 
	 * @param context 
	 * @return 
	 */  
	public static Point getScreenMetrics(Context context){
		DisplayMetrics dm =context.getResources().getDisplayMetrics();
		int w_screen = dm.widthPixels;  
		int h_screen = dm.heightPixels;  
		Log.i("ScreenMetric", "Screen---Width = " + w_screen + " Height = " + h_screen + " densityDpi = " + dm.densityDpi);
		return new Point(w_screen, h_screen);

	}  

	/** 
	 * 获取屏幕长宽比 
	 * @param context 
	 * @return 
	 */  
	public static float getScreenRate(Context context){
		Point P = getScreenMetrics(context);
		float H = P.y;  
		float W = P.x;  
		return (H/W);  
	}  

	public static CharSequence getColoredString(CharSequence str, int color){
		SpannableString ss = new SpannableString(str);
		ss.setSpan(new ForegroundColorSpan(color), 0, ss.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static String getMetaData(Context con, String name){
		String value = "";
		try {
			value = con.getPackageManager().getApplicationInfo(con.getPackageName(), PackageManager.GET_META_DATA).metaData.getString(name);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return value;
	}
	/**
	 * 软键盘相关
	 * view 接受软键盘输入
	 */
	public static void showSoftInput(Context context, View view){
		InputMethodManager imm=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(!imm.isActive()){//如果软键盘隐藏
			imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		}
	}
	public static void hideSoftInput(Context context, View view){
		InputMethodManager imm=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive()){//如果软件盘显示
			imm.hideSoftInputFromWindow(view.getWindowToken(),0);
		}
	}
	public static void toogleSoftInput(Context context, View view){
		InputMethodManager imm=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	public static boolean isShowSoftInput(Context context){
		InputMethodManager imm=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		return imm.isActive();
	}


	/**
	 * 返回 x天前 样式的时间
	 * @param time
	 * @param suffix 是否带后缀
	 * @return
	 */
	public static String getPastTime(long time, boolean suffix){
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTimeInMillis(System.currentTimeMillis() - time + cal.getTimeInMillis());
		int year = cal.get(Calendar.YEAR);
		if (year - 1970 > 0)
			return (year - 1970) + (suffix?"年前":"年");
		int[] field = new int[] { Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE };
		String[] sfield = new String[] { "个月", "天", "小时", "分钟" };
		short[] shot= {0,1,0,0};
		for (int i = 0; i < field.length; i++) {
			int x = cal.get(field[i]) - shot[i];
			if (x > 0)
				return x + sfield[i] + (suffix?"前":"");
		}
		return "刚刚";
	}

	public static String getPastTime(long time){
		return getPastTime(time,true);
	}

	/**
	 * 没有关注返回true
	 * @param relation
	 * @return
	 */
	public static boolean isNotFollow(int relation){
		return Math.abs(relation -3) == 1;
	}

	public static String getFollow(int relation){
		return isNotFollow(relation)?"1":"2";
	}
	/**
	 * Bundle
	 */
//	public static Bundle  getBundStr(String value){
//		Bundle b = new Bundle();
//		b.putString(CM.ExtraString, value);
//		return b;
//	}
//	public static Bundle  getBundParcel(Parcelable value){
//		Bundle b = new Bundle();
//		b.putParcelable(CM.ExtraParcel, value);
//		return b;
//	}
//	public static Bundle  getBundInt(int value){
//		Bundle b = new Bundle();
//		b.putInt(CM.ExtraInt, value);
//		return b;
//	}
	/**
	 * 登出
	 * @param
	 */
//	public static void loginOut(final Activity ac){
//		Map<String,String> params= DataHandler.getNewParams("/account/logout");
//		params.put(CM.USER_ID, App.getInstance(ac).getuserid()+"");
//		params.put(CM.TOKEN,App.getInstance(ac).gettoken());
//		DataHandler.sendTrueRequest(params,ac, new VolleyRequest.CallResult() {
//			@Override
//			public void handleMessage(JsonObject msg) {
//				ac.getSharedPreferences(CM.Prefer, 0).edit().remove(CM.SAVE_USER).commit();
//				App.getInstance(ac).setUser(new User());
//				//YUtils.startActivity(ac, LoginActivity.class);
//				ac.finish();
//			}
//		}, true);
//	}



	public static SharedPreferences getCachePrefer(Context context){
		return context.getSharedPreferences("spcs",0);
	}

	public static boolean checkId(String idNum){
		String idRegex15 = "[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}";
		String idRegex18 = "[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)";
		if (TextUtils.isEmpty(idNum)) return false;
		else return idNum.matches(idRegex15) || idNum.matches(idRegex18);
	}

	public static boolean nickNameValid(String nickName){
		String regex = "^[\\u4E00-\\u9FA5A-Za-z0-9_-]{2,16}$";
		return nickName.matches(regex);
	}


	public static String getDuration(long time){
		if (time <= 0) {
			return "数据有误";
		}
		StringBuilder duration = new StringBuilder("");
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTimeInMillis(time + cal.getTimeInMillis());
		int[] field = new int[] {Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND};
		String[] sfield = new String[] {"年", "个月", "天", "小时", "分", "秒"};
		short[] shot= {1970,0,1,0,0,0};
		int index = field.length;
		boolean saved = false;
		for (int i = 0; i < field.length; i++) {
			int x = cal.get(field[i]) - shot[i];
			if (x > 0){
				if (!saved) {
					index = i;
					saved = true;
				}
				if (i > index + 1)
					break;
				duration.append(x + sfield[i]);
			}
		}
		return duration.toString();
	}


}
