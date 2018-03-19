package com.jz.json;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    List<String> fields;
    List<FieldFailureType> types;

    Filter() {
        this.fields = new ArrayList<String>();
        this.types = new ArrayList<FieldFailureType>();
    }

    Filter(String[] fields, FieldFailureType[] types, boolean isOr) {
        List<String> f = new ArrayList<>();
        for (String field : fields) {
            f.add(field);
        }

        List<FieldFailureType> t = new ArrayList<>();
        for (FieldFailureType type : types) {
            t.add(type);
        }

        this.fields = f;
        this.types = t;
    }

    Filter(String[] fields, FieldFailureType[] types) {
        List<String> f = new ArrayList<>();
        for (String field : fields) {
            f.add(field);
        }

        List<FieldFailureType> t = new ArrayList<>();
        for (FieldFailureType type : types) {
            t.add(type);
        }

        this.fields = f;
        this.types = t;
    }

    Filter(List<String> fields, List<FieldFailureType> types, boolean isOr) {
        this.fields = fields;
        this.types = types;
    }

    Filter(List<String> fields, List<FieldFailureType> types) {
        this.fields = fields;
        this.types = types;
    }

}


