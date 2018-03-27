package com.jz.Exception;

/**
 * ??????(???????)
 *
 * @author jzfeng
 * @version 2016/11/26
 */
public class MyException extends RuntimeException {

    public static void main(String[] args) {

        String[] sexs = {"MAN", "WOMAN", "NEUTRAL"};
        for (int i = 0; i < sexs.length; i++) {
            if ("NEUTRAL".equals(sexs[i])) {
                throw new MyException("You are neutral.");
            } else {
                System.out.println(sexs[i]);
            }
        }
    }

    private static final long serialVersionUID = 1L;

    /**
     * ????
     */
    private String errorCode;

    /**
     * ???????????Key
     */
    private boolean propertiesKey = true;

    /**
     * ????????.
     *
     * @param message ????
     */
    public MyException(String message) {
        super(message);
    }

    /**
     * ????????.
     *
     * @param errorCode ????
     * @param message   ????
     */
    public MyException(String errorCode, String message) {
        this(errorCode, message, true);
    }

    /**
     * ????????.
     *
     * @param errorCode ????
     * @param message   ????
     */
    public MyException(String errorCode, String message, Throwable cause) {
        this(errorCode, message, cause, true);
    }

    /**
     * ????????.
     *
     * @param errorCode     ????
     * @param message       ????
     * @param propertiesKey ???????????Key
     */
    public MyException(String errorCode, String message, boolean propertiesKey) {
        super(message);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    /**
     * ????????.
     *
     * @param errorCode ????
     * @param message   ????
     */
    public MyException(String errorCode, String message, Throwable cause, boolean propertiesKey) {
        super(message, cause);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    /**
     * ????????.
     *
     * @param message ????
     * @param cause   ??????????????
     */
    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isPropertiesKey() {
        return propertiesKey;
    }

    public void setPropertiesKey(boolean propertiesKey) {
        this.propertiesKey = propertiesKey;
    }

}
