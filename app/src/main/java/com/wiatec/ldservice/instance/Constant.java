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
        public static final String blive_channels = blive_base + "channel/btv";
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
        public static final String market = "com.px.bmarket";
        public static final String net_resources = "org.xbmc.kodi";
        public static final String btv= "org.xbmc.kodi";
        public static final String setting = "com.android.tv.settings";
        public static final String cloud = "com.legacydirect.tvphoto";
        public static final String legacy_antivirus =  "com.legacydirect.security.suite";
        public static final String legacy_privacy =  "com.legacydirect.privacyadvisor";
        public static final String file = "com.droidlogic.FileBrower";
        public static final String tvplus = "com.elinkway.tvlive2";
        public static final String joinme = "com.logmein.joinme";
        public static final String spotify = "com.spotify.tv.android";
        public static final String happy_chick = "com.xiaoji.tvbox";
        public static final String bplay = "com.wiatec.bplay";
        public static final String live_net = "com.livenet.iptv";
        public static final String show_box = "com.tdo.showbox";
        public static final String tv_house = "com.fanshi.tvvideo";
        public static final String mx_player = "com.mxtech.videoplayer.ad";
        public static final String terrarium_tv = "com.nitroxenon.terrarium";
        public static final String popcom = "pct.droid";
        public static final String ldservice = "com.wiatec.ldservice";
        public static final String lddream = "com.wiatec.lddream";
        public static final String dianshijia3 = "com.dianshijia.newlive";
        public static final String live_net_new = "com.streams.androidnettv";
        public static final String bluetooth_remote = "com.droidlogic.BluetoothRemote";
        public static final String factory_test = "com.amlogic.factorytest";
        public static final String remote_control = "com.droidlogic.service.remotecontrol";
        public static final String read_log = "com.droidlogic.readlog";
        public static final String input_method = "com.android.inputmethod.latin";
        public static final String web_view = "com.google.android.webview";
        public static final String clock = "com.android.deskclock";
        public static final String live_play = "com.live.play";
        public static final String mobdro = "com.mobdro.android";
        public static final String morpheustv = "com.android.morpheustv";
        public static final String nitrotv = "com.myspecial.launcher.nitrotv";
    }

    public static final class route{
        public static final String main = "/ac/main";
        public static final String resources = "/ac/resources";
        public static final String net_resources = "/ac/net_resources";
        public static final String bvision = "/ac/bvision";
        public static final String apps = "/ac/apps";
        public static final String ad_video = "/ac/ad_video";
    }

    public static final class path{
        public static final String ad_video = Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/Android/data/com.wiatec.btv_launcher/files/download/btvad.mp4";
    }
}
