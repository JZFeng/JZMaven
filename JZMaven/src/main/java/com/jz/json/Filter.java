package com.jz.json;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Filter {
    List<FieldFailureType> types = new ArrayList<FieldFailureType>();
    List<String> fields = new ArrayList<String>();

    Filter() {

    }


    Filter(String[] types, String[] fields) {
        Set<String> set = new HashSet<>();
        for (FieldFailureType type : FieldFailureType.values()) {
            set.add(type.name());
        }
        for(String type : types) {
            if(set.contains(type)) {
                this.types.add(FieldFailureType.valueOf(type));
            }
        }

        for (String field : fields) {
            if(field.length() > 0) {
                this.fields.add(field);
            }
        }
    }


    Filter(FieldFailureType[] types, String[] fields) {
        for (FieldFailureType type : types) {
            this.types.add(type);
        }

        for (String field : fields) {
            this.fields.add(field);
        }

    }

    Filter(List<FieldFailureType> types, List<String> fields) {
        this.types = types;
        this.fields = fields;
    }

}


