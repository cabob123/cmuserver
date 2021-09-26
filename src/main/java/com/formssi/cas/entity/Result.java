package com.formssi.cas.entity;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Result{
    /*错误码*/
    private Integer code;
    /*提示信息*/
    private String msg;
    /*具体的内容*/
    private  List<Map<String,String>> data;
    /**返回的data string*/
    private String content;
    /**单例模式*/
    private static Result  result;
    /**几种消息类型**/
    public final static Integer RESULT_CODE_TGT_NULL = 100;
    public final static Integer RESULT_CODE_NOT_REGISTRY_SESSION = 101;
    public final static Integer RESULT_CODE_TGT_ERROR = 102;
    public final static Integer RESULT_CODE_SUCCESS = 103;

    private Result(){}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return JSON.toJSONString(data);
    }

//    public static synchronized  Result getDatas() {
//        if(null == result) {
//            result = new Result();
//        }
//        return result;
//    }

    public static synchronized  Result  setDatas(String key1,String value1) {
        if(null == result) {
            result = new Result();
            if(null == result.data) {
                result.data = new ArrayList<>();
            }
        }
        HashMap hm = new HashMap();
        hm.put(key1,value1);
        result.data.add(hm);
        return result;
    }

    public static synchronized Result ok() {
        if(null == result) {
            result = new Result();
            if(null == result.data) {
                result.data = new ArrayList<>();
            }
        }
        result.setCode(0);
        result.setMsg("成功");
        //result.setData(object);
        return result;
    }


//    public static Result success() {
//        return success(null);
//    }


    public static synchronized Result error(Integer code, String msg) {
        if(null == result) {
            result = new Result();
            if(null == result.data) {
                result.data = new ArrayList<>();
            }
        }
        result.setCode(code);
        result.setMsg(msg);
        //result.setData(null);
        return result;
    }
}
