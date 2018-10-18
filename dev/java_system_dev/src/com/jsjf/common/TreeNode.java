package com.jsjf.common;

import java.util.List;
import java.util.Map;
public class TreeNode {  
    private String id; //节点标识  
    private String text;//节点显示名称  
    private String state;//状态,'open' or  'closed'  
    private String iconCls; //图标样式(该插件有个样式,可以自己修改或添加图标样式,如'icon-ok')  
    private Map<String,Object> attributes;//附加信息(该字段说明树的信息是可扩展的,你可以将一个pojo的其它数据,也附给这个对象,下面会有说明)  
    private List<TreeNode> children;//子节点信息  
    
    public String getId() {  
        return id;  
    }  
    public void setId(String id) {  
        this.id = id;  
    }  
    public String getText() {  
        return text;  
    }  
    public void setText(String text) {  
        this.text = text;  
    }  
    public String getState() {  
        return state;  
    }  
    public void setState(String state) {  
        this.state = state;  
    }  
    public Map<String, Object> getAttributes() {  
        return attributes;  
    }  
    public void setAttributes(Map<String, Object> attributes) {  
        this.attributes = attributes;  
    }  
    public List<TreeNode> getChildren() {  
        return children;  
    }  
    public void setChildren(List<TreeNode> children) {  
        this.children = children;  
    }  
    public void setIconCls(String iconCls) {  
        this.iconCls = iconCls;  
    }  
    public String getIconCls() {  
        return iconCls;  
    }  
      
}  