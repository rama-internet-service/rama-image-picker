package com.esafirm.imagepicker.helper;

import android.content.Context;

import com.esafirm.imagepicker.features.ImagePickerConfig;
import com.esafirm.imagepicker.features.IpCons;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.features.common.BaseConfig;
import com.esafirm.imagepicker.view.FakeR;

//import com.esafirm.imagepicker.R;

public class ConfigUtils {
  static FakeR fakeR;
    public static ImagePickerConfig checkConfig(ImagePickerConfig config) {
        if (config == null) {
            throw new IllegalStateException("ImagePickerConfig cannot be null");
        }
        if (config.getMode() != IpCons.MODE_SINGLE
                && (config.getReturnMode() == ReturnMode.GALLERY_ONLY
                || config.getReturnMode() == ReturnMode.ALL)) {
            throw new IllegalStateException("ReturnMode.GALLERY_ONLY and ReturnMode.ALL is only applicable in Single Mode!");
        }
        return config;
    }

    public static boolean shouldReturn(BaseConfig config, boolean isCamera) {
        ReturnMode mode = config.getReturnMode();
        if (isCamera) {
            return mode == ReturnMode.ALL || mode == ReturnMode.CAMERA_ONLY;
        } else {
            return mode == ReturnMode.ALL || mode == ReturnMode.GALLERY_ONLY;
        }
    }

    public static String getFolderTitle(Context context, ImagePickerConfig config) {
        final String folderTitle = config.getFolderTitle();
        /*return ImagePickerUtils.isStringEmpty(folderTitle)
                ? context.getString(R.string.ef_title_folder)
                : folderTitle;*/
     /* return ImagePickerUtils.isStringEmpty(folderTitle)
        ? context.getString(fakeR.getId("string", "ef_title_folder"))
        : folderTitle;
*/
     return "Album";
    }

    public static String getImageTitle(Context context, ImagePickerConfig config) {
        final String configImageTitle = config.getImageTitle();
      /*  return ImagePickerUtils.isStringEmpty(configImageTitle)
                ? context.getString(R.string.ef_title_select_image)
                : configImageTitle;*/
      return ImagePickerUtils.isStringEmpty(configImageTitle)
        ? context.getString(fakeR.getId("string", "ef_title_select_image"))
        : configImageTitle;


    }

    public static String getDoneButtonText(Context context, ImagePickerConfig config) {
      fakeR = new FakeR(context);
        final String doneButtonText = config.getDoneButtonText();

        /*return ImagePickerUtils.isStringEmpty(doneButtonText)
                ? context.getString(R.string.ef_done)
                : doneButtonText;*/
      return ImagePickerUtils.isStringEmpty(doneButtonText)
        ? context.getString(fakeR.getId("string", "ef_title_select_image"))
        : doneButtonText;

    }
}
