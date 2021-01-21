package com.example.business_server.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OpLog {
    /**
    * operation type: get ,delete ,save etc
     * @return
    * */
    OpType opType();
    /**
     * business object name.
     * @return*/
    String opItem();
     /**
      * expression : describe how to get */
     String opItemExpression();
}
