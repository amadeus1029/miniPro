package com.javaex.ex02;

public class Person {
    private String name,mobile,company;

    public Person() {
    }

    public Person(String name, String mobile, String company) {
        this.name = name;
        this.mobile = mobile;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String showInfo() {
        return "\t"+this.name+"\t"+this.mobile+"\t"+this.company;
    }

    @Override
    public String toString() {
        return this.name+","+this.mobile+","+this.company+"\n";
    }
}
