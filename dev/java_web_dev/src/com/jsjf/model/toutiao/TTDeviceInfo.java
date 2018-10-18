package com.jsjf.model.toutiao;

import java.util.Date;

public class TTDeviceInfo {

    private Integer id;

    private String aid;

    private String cid;

    private String csite;

    private String ctype;

    private String mac;

    private String mac1;

    private String ua;

    private String ua1;

    private String idfa;

    private String androidid;

    private String androidid1;

    private String imei;

    private String uuid;

    private String openudid;

    private String udid;

    private String os;

    private String ip;

    private String ts;

    private String convertId;

    private String callbackUrl;

    private String callbackParam;

    private String sign;

    private Date createdTime;
    public TTDeviceInfo() {
    }

    public TTDeviceInfo(Integer id, String aid, String cid, String os, String idfa, String mac, String ip, String callback, String timestamp, String convertId) {
        this.id = id;
        this.aid = aid;
        this.cid = cid;
        this.os = os;
        this.idfa = idfa;
        this.imei = imei;
        this.androidid = androidid;
        this.mac = mac;
        this.ip = ip;
        this.callbackParam = callback;
        this.ts = timestamp;
        this.convertId = convertId;
    }
    public TTDeviceInfo(Integer id, String aid, String cid, String os, String imei, String androidid, String mac, String ip, String callback, String timestamp, String convertId) {
        this.id = id;
        this.aid = aid;
        this.cid = cid;
        this.os = os;
        this.idfa = idfa;
        this.imei = imei;
        this.androidid = androidid;
        this.mac = mac;
        this.ip = ip;
        this.callbackParam = callback;
        this.ts = timestamp;
        this.convertId = convertId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCsite() {
        return csite;
    }

    public void setCsite(String csite) {
        this.csite = csite;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getMac1() {
        return mac1;
    }

    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getUa1() {
        return ua1;
    }

    public void setUa1(String ua1) {
        this.ua1 = ua1;
    }

    public String getAndroidid1() {
        return androidid1;
    }

    public void setAndroidid1(String androidid1) {
        this.androidid1 = androidid1;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOpenudid() {
        return openudid;
    }

    public void setOpenudid(String openudid) {
        this.openudid = openudid;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackParam() {
        return callbackParam;
    }

    public void setCallbackParam(String callbackParam) {
        this.callbackParam = callbackParam;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAndroidid() {
        return androidid;
    }

    public void setAndroidid(String androidid) {
        this.androidid = androidid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCallback() {
        return callbackParam;
    }

    public void setCallback(String callback) {
        this.callbackParam = callback;
    }

    public String getTimestamp() {
        return ts;
    }

    public void setTimestamp(String timestamp) {
        this.ts = timestamp;
    }

    public String getConvertId() {
        return convertId;
    }

    public void setConvertId(String convertId) {
        this.convertId = convertId;
    }

    @Override
    public String toString() {
        super.toString();

        return "aid:" + this.aid + "cid:" + this.cid + "os:" + this.os +"" +
                "\n";
    }
}

