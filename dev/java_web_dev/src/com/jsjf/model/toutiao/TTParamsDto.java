package com.jsjf.model.toutiao;

/**
 * 接口一和接口二的参数DTO基类
 */
public class TTParamsDto {

    private Integer os;             //客户端类型,0-Android,1-IOS,2-WP,3-Others
    private String idfa;            //IOS唯一标识（IOS9和IOS10当开启了限制广告跟踪时，该值不能作为唯一标识）
    private String imei;            //安卓唯一标识（APP需要授权才能获取到）
    private String androidId;       //安卓唯一标识（恢复出厂设置会改变）
    private String ip;

    public Integer getOs() {
        return os;
    }

    public void setOs(Integer os) {
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

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
