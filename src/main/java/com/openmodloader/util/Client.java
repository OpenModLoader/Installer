
package com.openmodloader.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("sha1")
    @Expose
    private String sha1;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("url")
    @Expose
    private String url;

    public String getSha1() {
        return sha1;
    }

    public Integer getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

}
