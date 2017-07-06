package com.example.demo.commons.constants;

/**
 * functional description
 * Created by ShengyangKong
 * on 2016/5/6.
 */
public class ResponseCodeConstants {

    //接口调用成功
    public static final int SUCCESS = -1;

    //程序运行时异常
    public static final int RUNTIME_ERROR = -2;

    //参数错误
    public static final int PARAMS_ERROR = -3;

    //无数据
    public static final int NO_DATA = -7;

    //参数为空
    public static final int PARAMS_EMPTY = -8;

    //调用其它服务接口超时
    public static final int OTHER_SERVICE_TIMEOUT = -19;

    //数据库操作失败
    public static final int DATABASE_ERROR = -22;

    //更新缓存失败
    public static final int CACHE_ERROR = -23;

    //调用其它服务接口异常
    public static final int OTHER_SERVICE_ERROR = -99;

}
