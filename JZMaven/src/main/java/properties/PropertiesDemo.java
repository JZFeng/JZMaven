package properties;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

public class PropertiesDemo {

    public static String getValue(String key ) throws Exception {
        Properties prop = new Properties();
        InputStream input =  new FileInputStream("/Users/jzfeng/Desktop/testdata.properties");;
        prop.load(input);

        return prop.getProperty(key);
    }

    public static  void main(String[] args) throws Exception {
        String user = getValue("user");
        String statusMsg = getValue("statusMsg");
        String s = MessageFormat.format(statusMsg, user );
        System.out.println(s);
    }
}
