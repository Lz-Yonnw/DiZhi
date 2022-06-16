package com.ruoyi.common.utils;

/**
 * 处理并记录日志文件
 *
 * @author liaozan8888@163.com
 */
public class LogUtils
{
    public static String getBlock(Object msg)
    {
        if (msg == null)
        {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
