package com.example.apple1.smartmanager2.entity;

/**
 * Created by draft on 2015/7/17.
 */
public class RepairDetail {
    private String location;
    private String phonenumber;
    private String type;
    private String source;
    private String representation;
    private String remark;
    private String state;//维修状态：待维修，维修中，维修完成
    private String evaluate;
    private String suggestion;
    private String imgurl;

    public RepairDetail() {

    }

    public RepairDetail(String location, String phonenumber, String type, String source, String representation, String remark, String state, String evaluate, String suggestion,String imgurl) {
        this.location = location;
        this.phonenumber = phonenumber;
        this.type = type;
        this.source = source;
        this.representation = representation;
        this.remark = remark;
        this.state = state;
        this.evaluate = evaluate;
        this.suggestion = suggestion;
        this.imgurl=imgurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRepresentation() {
        return representation;
    }

    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public String toString() {
        return "RepairDetail{" +
                "location='" + location + '\'' +
                ", phonenumber=" + phonenumber +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                ", representation='" + representation + '\'' +
                ", remark='" + remark + '\'' +
                ", state='" + state + '\'' +
                ", evaluate='" + evaluate + '\'' +
                ", suggestion='" + suggestion + '\'' +
                '}';
    }
}
