package com.wiatec.ldservice.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.px.common.constant.CommonApplication;
import com.wiatec.ldservice.pojo.ResourceAppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * channel dao
 */

public class ResourceAppDao {

    private SQLiteDatabase sqLiteDatabase;
    private static ResourceAppDao instance;

    private ResourceAppDao(){
        sqLiteDatabase = new SQLiteHelper(CommonApplication.context).getWritableDatabase();
    }

    public static ResourceAppDao getInstance(){
        if(instance == null){
            synchronized (ResourceAppDao.class){
                if(instance == null){
                    instance = new ResourceAppDao();
                }
            }
        }
        return instance;
    }

    public boolean insertOrUpdate(ResourceAppInfo appInfo){
        if(exists(appInfo)){
            return update(appInfo);
        }else{
            return insert(appInfo);
        }
    }

    private boolean exists(ResourceAppInfo appInfo){
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.RESOURCE_TABLE_NAME, null, "packageName=?",
                new String[]{appInfo.getPackageName()}, null, null, null);
        boolean exists = cursor.moveToNext();
        cursor.close();
        return exists;
    }

    private boolean insert(ResourceAppInfo appInfo){
        boolean flag = true;
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("label", appInfo.getLabel());
            contentValues.put("packageName", appInfo.getPackageName());
            contentValues.put("versionCode", appInfo.getVersionCode());
            contentValues.put("url", appInfo.getUrl());
            contentValues.put("icon", appInfo.getIcon());
            contentValues.put("type", appInfo.getType());
            sqLiteDatabase.insert(SQLiteHelper.RESOURCE_TABLE_NAME, null, contentValues);
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }



    private boolean update(ResourceAppInfo appInfo){
        boolean flag = true;
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("label", appInfo.getLabel());
            contentValues.put("packageName", appInfo.getPackageName());
            contentValues.put("versionCode", appInfo.getVersionCode());
            contentValues.put("url", appInfo.getUrl());
            contentValues.put("icon", appInfo.getIcon());
            contentValues.put("type", appInfo.getType());
            sqLiteDatabase.update(SQLiteHelper.RESOURCE_TABLE_NAME, contentValues, "packageName=?",
                    new String[]{appInfo.getPackageName()});
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    public boolean batchInsert(List<ResourceAppInfo> resourceAppInfoList){
        boolean flag = true;
        try{
            sqLiteDatabase.beginTransaction();
            for(ResourceAppInfo resourceAppInfo: resourceAppInfoList){
                insert(resourceAppInfo);
            }
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }


    public boolean deleteAll(){
        boolean flag = true;
        try{
            sqLiteDatabase.delete(SQLiteHelper.RESOURCE_TABLE_NAME, "_id>?", new String[]{"0"});
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    public List<ResourceAppInfo> queryAll(){
        List<ResourceAppInfo> resourceAppInfoList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(SQLiteHelper.RESOURCE_TABLE_NAME, null, "_id>?",
                    new String[]{"0"}, null, null, "label");
            while (cursor.moveToNext()) {
                ResourceAppInfo resourceAppInfo = new ResourceAppInfo();
                resourceAppInfo.setLabel(cursor.getString(cursor.getColumnIndex("label")));
                resourceAppInfo.setPackageName(cursor.getString(cursor.getColumnIndex("packageName")));
                resourceAppInfo.setVersionCode(cursor.getInt(cursor.getColumnIndex("versionCode")));
                resourceAppInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                resourceAppInfo.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                resourceAppInfo.setType(cursor.getInt(cursor.getColumnIndex("type")));
                resourceAppInfoList.add(resourceAppInfo);
            }
            cursor.close();
        }catch (Exception e){

        }finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return resourceAppInfoList;
    }
}
