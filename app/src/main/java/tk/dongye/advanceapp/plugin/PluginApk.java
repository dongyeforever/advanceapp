package tk.dongye.advanceapp.plugin;

import android.content.pm.PackageInfo;
import android.content.res.Resources;

/**
 * description: 插件apk
 * author： dongyeforever@gmail.com
 * date: 2017-01-18 17:38
 */
public class PluginApk {
    ClassLoader classLoader;
    PackageInfo packageInfo;
    private Resources pluginRes;

    public PluginApk(Resources pluginRes) {
        this.pluginRes = pluginRes;
    }

}
