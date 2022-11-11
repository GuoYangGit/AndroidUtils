//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tencent.mars.xlog;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

public class Log {
    private static final String TAG = "mars.xlog.log";
    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARNING = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;
    public static final int LEVEL_NONE = 6;
    private static int level = 6;
    public static Context toastSupportContext = null;
    private static LogImp debugLog = new LogImp() {
        private Handler handler = new Handler(Looper.getMainLooper());

        public void logV(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
            if (Log.level <= 0) {
                android.util.Log.v(tag, log);
            }

        }

        public void logI(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
            if (Log.level <= 2) {
                android.util.Log.i(tag, log);
            }

        }

        public void logD(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
            if (Log.level <= 1) {
                android.util.Log.d(tag, log);
            }

        }

        public void logW(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
            if (Log.level <= 3) {
                android.util.Log.w(tag, log);
            }

        }

        public void logE(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
            if (Log.level <= 4) {
                android.util.Log.e(tag, log);
            }

        }

        public void logF(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, final String log) {
            if (Log.level <= 5) {
                android.util.Log.e(tag, log);
                if (Log.toastSupportContext != null) {
                    this.handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(Log.toastSupportContext, log, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }

        public int getLogLevel() {
            return Log.level;
        }

        public void appenderClose() {
        }

        public void appenderFlush(boolean isSync) {
        }
    };
    private static LogImp logImp;
    private static final String SYS_INFO;

    public Log() {
    }

    public static void setLogImp(LogImp imp) {
        logImp = imp;
    }

    public static LogImp getImpl() {
        return logImp;
    }

    public static void appenderClose() {
        if (logImp != null) {
            logImp.appenderClose();
        }

    }

    public static void appenderFlush(boolean isSync) {
        if (logImp != null) {
            logImp.appenderFlush(isSync);
        }

    }

    public static int getLogLevel() {
        return logImp != null ? logImp.getLogLevel() : 6;
    }

    public static void setLevel(int level, boolean jni) {
        Log.level = level;
        android.util.Log.w("mars.xlog.log", "new log level: " + level);
        if (jni) {
            Xlog.setLogLevel(level);
        }

    }

    public static void f(String tag, String msg) {
        f(tag, msg, (Object[])null);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, (Object[])null);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, (Object[])null);
    }

    public static void i(String tag, String msg) {
        i(tag, msg, (Object[])null);
    }

    public static void d(String tag, String msg) {
        d(tag, msg, (Object[])null);
    }

    public static void v(String tag, String msg) {
        v(tag, msg, (Object[])null);
    }

    public static void f(String tag, String format, Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            logImp.logF(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }

    }

    public static void e(String tag, String format, Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }

            logImp.logE(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }

    }

    public static void e(String tag, String filename, String funcname, int line, String log) {
        if (logImp != null) {
            logImp.logE(tag, filename, funcname, line, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log == null ? "" : log);
        }

    }

    public static void w(String tag, String format, Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }

            logImp.logW(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }

    }

    public static void w(String tag, String filename, String funcname, int line, String log) {
        if (logImp != null) {
            logImp.logW(tag, filename, funcname, line, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log == null ? "" : log);
        }

    }

    public static void i(String tag, String format, Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }

            logImp.logI(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }

    }

    public static void i(String tag, String filename, String funcname, int line, String log) {
        if (logImp != null) {
            logImp.logI(tag, filename, funcname, line, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log == null ? "" : log);
        }

    }

    public static void d(String tag, String format, Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }

            logImp.logD(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }

    }

    public static void d(String tag, String filename, String funcname, int line, String log) {
        if (logImp != null) {
            logImp.logD(tag, filename, funcname, line, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log == null ? "" : log);
        }

    }

    public static void v(String tag, String format, Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }

            logImp.logV(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }

    }

    public static void v(String tag, String filename, String funcname, int line, String log) {
        if (logImp != null) {
            logImp.logV(tag, filename, funcname, line, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log == null ? "" : log);
        }

    }

    public static void printErrStackTrace(String tag, Throwable tr, String format, Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }

            log = log + "  " + android.util.Log.getStackTraceString(tr);
            logImp.logE(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }

    }

    public static String getSysInfo() {
        return SYS_INFO;
    }

    static {
        logImp = debugLog;
        StringBuilder sb = new StringBuilder();

        try {
            sb.append("VERSION.RELEASE:[" + VERSION.RELEASE);
            sb.append("] VERSION.CODENAME:[" + VERSION.CODENAME);
            sb.append("] VERSION.INCREMENTAL:[" + VERSION.INCREMENTAL);
            sb.append("] BOARD:[" + Build.BOARD);
            sb.append("] DEVICE:[" + Build.DEVICE);
            sb.append("] DISPLAY:[" + Build.DISPLAY);
            sb.append("] FINGERPRINT:[" + Build.FINGERPRINT);
            sb.append("] HOST:[" + Build.HOST);
            sb.append("] MANUFACTURER:[" + Build.MANUFACTURER);
            sb.append("] MODEL:[" + Build.MODEL);
            sb.append("] PRODUCT:[" + Build.PRODUCT);
            sb.append("] TAGS:[" + Build.TAGS);
            sb.append("] TYPE:[" + Build.TYPE);
            sb.append("] USER:[" + Build.USER + "]");
        } catch (Throwable var2) {
            var2.printStackTrace();
        }

        SYS_INFO = sb.toString();
    }

    public interface LogImp {
        void logV(String var1, String var2, String var3, int var4, int var5, long var6, long var8, String var10);

        void logI(String var1, String var2, String var3, int var4, int var5, long var6, long var8, String var10);

        void logD(String var1, String var2, String var3, int var4, int var5, long var6, long var8, String var10);

        void logW(String var1, String var2, String var3, int var4, int var5, long var6, long var8, String var10);

        void logE(String var1, String var2, String var3, int var4, int var5, long var6, long var8, String var10);

        void logF(String var1, String var2, String var3, int var4, int var5, long var6, long var8, String var10);

        int getLogLevel();

        void appenderClose();

        void appenderFlush(boolean var1);
    }
}
