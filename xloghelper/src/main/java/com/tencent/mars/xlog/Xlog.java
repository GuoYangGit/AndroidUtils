//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tencent.mars.xlog;

import com.tencent.mars.xlog.Log.LogImp;

public class Xlog implements LogImp {
    public static final int LEVEL_ALL = 0;
    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARNING = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;
    public static final int LEVEL_NONE = 6;
    public static final int AppednerModeAsync = 0;
    public static final int AppednerModeSync = 1;

    public Xlog() {
    }

    public static void open(boolean isLoadLib, int level, int mode, String cacheDir, String logDir, String nameprefix, String pubkey) {
        if (isLoadLib) {
            System.loadLibrary("c++_shared");
            System.loadLibrary("marsxlog");
        }

        appenderOpen(level, mode, cacheDir, logDir, nameprefix, 0, pubkey);
    }

    private static String decryptTag(String tag) {
        return tag;
    }

    public void logV(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(0, decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    public void logD(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(1, decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    public void logI(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(2, decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    public void logW(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(3, decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    public void logE(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(4, decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    public void logF(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(5, decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    public static native void logWrite(XLoggerInfo var0, String var1);

    public static native void logWrite2(int var0, String var1, String var2, String var3, int var4, int var5, long var6, long var8, String var10);

    public native int getLogLevel();

    public static native void setLogLevel(int var0);

    public static native void setAppenderMode(int var0);

    public static native void setConsoleLogOpen(boolean var0);

    public static native void setErrLogOpen(boolean var0);

    public static native void appenderOpen(int var0, int var1, String var2, String var3, String var4, int var5, String var6);

    public static native void setMaxFileSize(long var0);

    public static native void setMaxAliveTime(long var0);

    public native void appenderClose();

    public native void appenderFlush(boolean var1);

    static class XLoggerInfo {
        public int level;
        public String tag;
        public String filename;
        public String funcname;
        public int line;
        public long pid;
        public long tid;
        public long maintid;

        XLoggerInfo() {
        }
    }
}
