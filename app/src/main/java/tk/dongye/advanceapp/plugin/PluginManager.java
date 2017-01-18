package tk.dongye.advanceapp.plugin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * description: 加载插件
 * author： dongyeforever@gmail.com
 * date: 2017-01-18 17:06
 */
public class PluginManager {

    static class PluginMgrHolder {
        static PluginManager sManger = new PluginManager();
    }

    // Application Context
    private static Context mContext;

    // 插件Apk包名为key、PluginApk为值的HashMap
    Map<String, PluginApk> sMap = new HashMap<>();

    // 在使用PluginManger之前需要初始化一词
    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }


    /**
     * description: 加载插件Apk相关信息，包含插件Apk资源、classloader等 <br>
     * author： dongyeforever@gmail.com <br>
     * date: 17-1-18 下午5:32
     */
    public final void loadApk(String apkPath) {
        PackageInfo packageInfo = queryPackageInfo(apkPath);
        if (packageInfo == null || TextUtils.isEmpty(packageInfo.packageName)) {
            return;
        }
        // 通过包名查看是否有缓存
        PluginApk pluginApk = sMap.get(packageInfo.packageName);
        if (pluginApk == null) {
            pluginApk = createApk(apkPath);
            if (pluginApk != null) {
                pluginApk.packageInfo = packageInfo;
                sMap.put(packageInfo.packageName, pluginApk);
            } else {
                throw new NullPointerException("PluginApk is null!");
            }

        }
    }

    private PackageInfo queryPackageInfo(String apkPath) {

        return null;
    }

    private PluginApk createApk(String apkPath) {
        PluginApk pluginApk = null;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            // 2.将Apk目录添加到AssetManger的资源路径下
            addAssetPath.invoke(assetManager, apkPath);
            // 3.以assetManager和设备配置来构建Resource对象
            Resources pluginRes = new Resources(assetManager, mContext.getResources().getDisplayMetrics(), mContext.getResources().getConfiguration());
            // 4.创建PluginApk对象，存储插件Apk资源对象
            pluginApk = new PluginApk(pluginRes);
            // 5.存储DexClassLoader
            pluginApk.classLoader = createClassLoader(apkPath);

        } catch (Exception e) {
        }
        return pluginApk;
    }

    private ClassLoader createClassLoader(String apkPath) {
        File dexOutputDir = mContext.getDir("dex", Context.MODE_PRIVATE);
        ClassLoader classLoader = new DexClassLoader(apkPath, dexOutputDir.getAbsolutePath(), null, mContext.getClassLoader());
        return classLoader;
    }

}