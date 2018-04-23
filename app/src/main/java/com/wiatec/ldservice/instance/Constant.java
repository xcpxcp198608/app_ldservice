package com.wiatec.ldservice.instance;

import android.os.Environment;

/**
 * Created by patrick on 21/07/2017.
 * create time : 10:46 AM
 */

public final class Constant {

    public static final class url{

        private static final String base_url = "http://ldservice.ldlegacy.com/ldservice/";

        public static final String remote_apk = base_url + "remote/";
        public static final String remove_apk = base_url + "remove/";
        public static final String resource_apk = base_url + "resource/";
        public static final String resource_apk_by_package_name = base_url + "resource/p";
        public static final String automatic_play = base_url + "play/";
        public static final String upgrade = base_url + "upgrade/";


        public static final String ad_image = base_url +"ad_image/";
        public static final String channel = base_url +"bvision/channel/";
        public static final String channel_type = base_url +"bvision/channel_type/";
        public static final String channel_type1 = base_url +"bvision/channel_type1/";
        public static final String channel_type2 = base_url +"bvision/channel_type2/";
        public static final String channel_search = base_url +"bvision/search/";
        public static final String channel_send_error_report = base_url +"bvision/report";
        public static final String start_view = base_url +"bvision/log/start";
        public static final String stop_view = base_url +"bvision/log/end";
        public static final String token = "/9B67E88314F416F2092AB8ECA6A7C8EDCCE3D6D85A816E6E6F9F919B2E6C277D";


        public static final String lp_channel = "http://lp.ldlegacy.com/liveplay/channels/list/";


        public static final String ld_fam = "http://www.ldlegacy.com/LDFAM/get";

        public static final String blive_base = "https://blive.bvision.live/";
        public static final String blive_channels = blive_base + "channel/";
        public static final String blive_ws_live = "wss://blive.bvision.live/live/";
        public static final String blive_danmu_url = "http://blive.bvision.live:8804/html/danmu.html";

    }

    public static final class key{
        public static final String channel_type = "CHANNEL_TYPE";
        public static final String key_search = "KEY_SEARCH";
        public static final String type_favorite = "FAVORITE";
        public static final String type_history = "HISTORY";
        public static final String type_search = "SEARCH";
        public static final String type_live_channel = "CHANNEL";
        public static final String key_url = "KEY_URL";
        public static final String key_duration = "KEY_DURATION";
        public static final String SP_USER_LEVEL = "userLevel";
        public static final String SP_IS_EXPERIENCE = "experience";
    }

    public static final class activity{
        public static final String automatic_play = "com.wiatec.bplay.view.activity.AutomaticPlayActivity";
    }

    public static final class package_name{
        public static final String live_play = "com.wiatec.bplay";
        public static final String market = "com.px.bmarket";
        public static final String net_resources = "org.xbmc.kodi";
    }

    public static final class route{
        public static final String main = "/ac/main";
        public static final String resources = "/ac/resources";
        public static final String net_resources = "/ac/net_resources";
        public static final String bvision = "/ac/bvision";
        public static final String ad_video = "/ac/ad_video";
    }

    public static final class path{
        public static final String ad_video = Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/Android/data/com.wiatec.btv_launcher/files/download/btvad.mp4";
    }
}
