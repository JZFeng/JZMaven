package com.jz.java.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

abstract class BaseClass {
  protected static Log log = LogFactory.getLog(BaseClass.class);

}
