package net.link.linkapp;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Utils {
    // convert normal image as file to part for upload to server
    public static MultipartBody.Part convertFileToPart(File file) {
        if(file == null)
            return null;
        return MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
    }

    public static RequestBody convertStringToPart(String str) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, str);
    }

}
