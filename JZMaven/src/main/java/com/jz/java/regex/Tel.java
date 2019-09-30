package com.jz.java.regex;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tel {

    private String areaCode;
    private String phoneNum;
    private static final Pattern pattern = Pattern.compile("^(0\\d{2,3})\\-([1-9]\\d{5,7})$");

    public Tel(String areaCode, String phoneNum) {
        this.areaCode = areaCode;
        this.phoneNum = phoneNum;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public static Tel parse(String num) {
        if (num == null || num.length() == 0) {
            return null;
        }

        Matcher matcher = pattern.matcher(num);
        if (matcher.matches()) {
            String areaCode = matcher.group(1);
            String phoneNum = matcher.group(2);
            return new Tel(areaCode, phoneNum);
        }

        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.areaCode, this.phoneNum);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Tel) {
            Tel tmp = (Tel) obj;
            return Objects.equals(this.areaCode, tmp.areaCode) && Objects.equals(this.phoneNum, tmp.phoneNum);
        }

        return false;
    }

    @Override
    public String toString() {
        return this.areaCode + "-" + this.phoneNum;
    }
}
