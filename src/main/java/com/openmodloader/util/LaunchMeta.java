
package com.openmodloader.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaunchMeta {

    @SerializedName("latest")
    @Expose
    private Latest latest;
    @SerializedName("versions")
    @Expose
    private List<Version> versions = null;

    public Latest getLatest() {
        return latest;
    }

    public List<Version> getVersions() {
        return versions;
    }

}
