package com.jz.Enum;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class PaymentMethod {

    private String name;
    private String code;
    private int idx;

    public PaymentMethod(String name, String code, int idx) {
        this.name = name;
        this.code = code;
        this.idx = idx;
    }

    @Override
    public String toString() {
        return "Name : " + name + " ;\r\n" + "Code : " + code + " ;\r\n" + "Index : " + idx + "\r\n";
    }

    public static void main(String[] args) throws IOException, Exception {
        Map<String, PaymentMethod> map = getPaymentMethodMap("/Users/jzfeng/Desktop/PaymentMethods.txt");
        System.out.print("Please enter 3 letter payment code : ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine().trim().toUpperCase();
            if (line != null && line.length() == 0) {
                break;
            }
            if (map.get(line) != null) {
                System.out.println(map.get(line));
                System.out.print("Please enter 3 letter payment code : ");
            } else {
                System.out.print("Your input is incorrect, please enter 3 letter payment code : ");
            }
        }

        br.close();

    }

    public static Map<String, PaymentMethod> getPaymentMethodMap(String filename) throws IOException {
        Map<String, PaymentMethod> map = new LinkedHashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
        String line = br.readLine().trim();
        while (line != null && line.length() > 0) {
            String[] res = line.split(",");
            map.put(res[1], new PaymentMethod(res[0], res[1], Integer.parseInt(res[2])));
            line = br.readLine();
        }

        br.close();

        return map;
    }

}


