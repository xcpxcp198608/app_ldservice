package com.wiatec.ldservice.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.px.common.utils.SPUtil;


/**
 * provide the following info:
 *       1.location info
 *       2.weather info
 * other app use:
 *      1.add the permission: "com.wiatec.ldservice.provider.LDProvider"
 *      2.uri setting: sp -- "content://com.wiatec.ldservice.provider.LDProvider/ld_service_sp/*"
 */

public class LDProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String AUTHORITY = "com.wiatec.ldservice.provider.LDProvider";
    private static final String SP_PATH = "ld_service_sp/*";
    private static final int SP_MATCH_CODE = 0x19283;

    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/ld_service");

    static {
        uriMatcher.addURI(AUTHORITY, SP_PATH, SP_MATCH_CODE);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if(uriMatcher.match(uri) != SP_MATCH_CODE) return "";
        String [] paths = uri.getPath().split("/");
        String key = paths[2];
        return (String) SPUtil.get(key, "");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
