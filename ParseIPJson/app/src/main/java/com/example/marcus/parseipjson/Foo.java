package com.example.marcus.parseipjson;

/**
 * Created by marcus on 16/6/9.
 */
public class Foo {
    public int code;
    public ReturnData data;

    public class ReturnData {
        public String country;
        public String country_id;
        public String area;
        public String area_id;
        public String region;
        public String region_id;
        public String isp;
        public String isp_id;
        public String ip;
    }

    public int getCode() {
        return code;
    }



}
