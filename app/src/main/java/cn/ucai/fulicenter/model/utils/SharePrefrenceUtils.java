package cn.ucai.fulicenter.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class SharePrefrenceUtils {
    private static final String SHARE_PREFRENCE_NAME="cn.user.fulicenter_user";
    private static final String SHARE_PREFRENCE_NAME_USERNAME="cn.user.fulicenter_username";
    private static SharePrefrenceUtils instance;
    private static SharedPreferences preferences;
    public SharePrefrenceUtils(Context context){
        preferences=context.getSharedPreferences(SHARE_PREFRENCE_NAME,Context.MODE_PRIVATE);
    }
    public static SharePrefrenceUtils getInstance(Context context){
        if (instance==null){
            instance=new SharePrefrenceUtils(context);
        }
        return instance;
    }
    public static void saveUser(String username){
        preferences.edit().putString(SHARE_PREFRENCE_NAME_USERNAME,username).commit();
    }
    public static String getUser(){
        return preferences.getString(SHARE_PREFRENCE_NAME_USERNAME,null);
    }
}
