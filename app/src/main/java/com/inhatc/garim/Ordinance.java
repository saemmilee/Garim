package com.inhatc.garim;

import java.util.Date;

public class Ordinance {

    private int num;  //PK
    private String writer;
    private String date;
    private String address1;  //시도:: select box value
    private String address2;  //시군구:: select box value
    private String title;
    private String reason;  //제안이유
    private String ordinance;  //주요내용
    private String certificate;  //증명서
    private String status;  //상태:: wait, fail, success
    private String classification;  //구분:: enactment, abolition, revision
    private String term;  //서명기간
    private String availability;  //서명가능여부:: yes, no

    public Ordinance() {
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setString(String date) {
        this.date = date;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOrdinance() {
        return ordinance;
    }

    public void setOrdinance(String ordinance) {
        this.ordinance = ordinance;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setAvailability(String availability) {
        this.status = availability;
    }

    public String getAvailability() {
        return availability;
    }

    //게시글
    public Ordinance(int num, String title, String term, String availability) {
        this.num = num;
        this.title = title;
        this.term = term;
        this.availability = availability;
    }
}
