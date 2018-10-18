package com.jsjf.model.toutiao;

/**
 * 监测接口参数DTO
 */
public class MonitoringParamsDto extends TTParamsDto{

    private String aid;            //广告计划id,原值
    private String cid;             //广告创意id,原值
    private String mac;             //eth0网卡mac客户
    private String timestamp;       //时间戳
    private String convertId;       //转化跟踪id
    private String callback;        //回调参数

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getConvertId() {
        return convertId;
    }

    public void setConvertId(String convertId) {
        this.convertId = convertId;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
