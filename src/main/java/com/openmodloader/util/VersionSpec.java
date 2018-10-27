
package com.openmodloader.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionSpec {

    @SerializedName("assets")
    @Expose
    private String assets;
    @SerializedName("downloads")
    @Expose
    private Downloads downloads;

    public String getAssets() {
        return assets;
    }

    public Downloads getDownloads() {
        return downloads;
    }
}
