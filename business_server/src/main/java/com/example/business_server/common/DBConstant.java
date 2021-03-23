package com.example.business_server.common;

public class DBConstant {
    //*************** for mongodb *****************//
    public static final String MONGODB_DATABASE="recom";

    public static final String MONGODB_USER_COLLECTION="User";

    public static final String MONGODB_POST_COLLECTION="Post";

    public static final String MONGODB_USER_RECS_COLLECTION = "UserRecs";

    //*************** for elastic search***********************//

    //*************** for concurrent***********************//
    public static final Integer INIT_THREAD_NUM=10;

    public static final Integer MAX_THREAD_NUM=20;

    public static final Integer QUEUE_CAPACITY=500;;

    public static final Integer ALIVED_SECONDS=60;

    ;



}
