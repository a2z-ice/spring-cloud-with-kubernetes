package com.example;

public class Constants {
    public static final String RELOAD_EMUTATION_DATA_TOPIC = "reload.emutation.data.topic";

    public static final String RELOAD_EMUTATION_QUEUE = "reload.emutation.queue";
    public static final String RELOAD_DATA_DEBUG_QUEUE = "reload.data.debug.queue";
    public static final String RELOAD_MUKTAPATH_QUEUE = "reload.muktapath.queue";

    //for reload.emutation.queue on reload.emutation.data.topic
    public static final String RELOAD_DATA_SYNC_EMUTATION_KEY = "reload.data.sync.emutation";
    //for reload.data.debug.queue on reload.emutation.data.topic
    public static final String RELOAD_DATA_SYNC_KEY = "reload.data.sync.*";
    //for reload.muktapath.queue on reload.emutation.data.topic
    public static final String RELOAD_DATA_SYNC_MUKTAPATH_KEY = "reload.data.sync.muktapath";

    public static final String SYNC_EMUTATION_DATA_TOPIC = "sync.emutation.data.topic";
    public static final String SYNC_MUKTAPATH_DATA_TOPIC = "sync.muktapath.data.topic";


    public static final String SYNC_EMUTATION_DATA_QUEUE = "sync.emutation.data.queue";
    public static final String DEBUG_SYNC_DATA_QUEUE = "debug.sync.data.queue";
    public static final String SYNC_MUKTAPATH_DATA_QUEUE = "sync.muktapath.data.queue";

    //for sync.emutation.data.queue
    public static final String SYNC_EMUTATION_DATA_KEY = "sync.emutation.data";
    //for sync.emutation.data.queue queue
    public static final String DEBUG_SYNC_DATA_KEY = "sync.*.data";
    //for sync.muktapath.data.queue
    public static final String SYNC_MUKTAPATH_DATA_KEY = "sync.muktapath.data";
}
