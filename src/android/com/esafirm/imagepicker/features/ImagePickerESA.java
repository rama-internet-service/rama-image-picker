package com.esafirm.imagepicker.features;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;

import com.esafirm.imagepicker.features.cameraonly.ImagePickerCameraOnly;
import com.esafirm.imagepicker.helper.ConfigUtils;
import com.esafirm.imagepicker.helper.IpLogger;
import com.esafirm.imagepicker.helper.LocaleManager;
import com.esafirm.imagepicker.model.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ImagePickerESA {

    private ImagePickerConfig config;

    public abstract void start();

    public abstract void start(int requestCode);

    public static class ImagePickerWithActivity extends ImagePickerESA {

        private Activity activity;

        public ImagePickerWithActivity(Activity activity) {
            this.activity = activity;
            init(activity);
        }

        @Override
        public void start(int requestCode) {
            activity.startActivityForResult(getIntent(activity), requestCode);
        }

        @Override
        public void start() {
            activity.startActivityForResult(getIntent(activity), IpCons.RC_IMAGE_PICKER);
        }
    }

    public static class ImagePickerWithFragment extends ImagePickerESA {

        private Fragment fragment;

        public ImagePickerWithFragment(Fragment fragment) {
            this.fragment = fragment;
            init(fragment.requireContext());
        }

        @Override
        public void start(int requestCode) {
            fragment.startActivityForResult(getIntent(fragment.getActivity()), requestCode);
        }

        @Override
        public void start() {
            fragment.startActivityForResult(getIntent(fragment.getActivity()), IpCons.RC_IMAGE_PICKER);
        }
    }

    /* --------------------------------------------------- */
    /* > Stater */
    /* --------------------------------------------------- */

    public void init(Context context) {
        config = ImagePickerConfigFactory.createDefault(context);
    }

    public static ImagePickerWithActivity create(Activity activity) {
        return new ImagePickerWithActivity(activity);
    }

    public static ImagePickerWithFragment create(Fragment fragment) {
        return new ImagePickerWithFragment(fragment);
    }

    public static ImagePickerCameraOnly cameraOnly() {
        return new ImagePickerCameraOnly();
    }

    /* --------------------------------------------------- */
    /* > Builder */
    /* --------------------------------------------------- */

    public ImagePickerESA single() {
        config.setMode(IpCons.MODE_SINGLE);
        return this;
    }

    public ImagePickerESA multi() {
        config.setMode(IpCons.MODE_MULTIPLE);
        return this;
    }

    public ImagePickerESA returnMode(@NonNull ReturnMode returnMode) {
        config.setReturnMode(returnMode);
        return this;
    }

    public ImagePickerESA limit(int count) {
        config.setLimit(count);
        return this;
    }

    public ImagePickerESA showCamera(boolean show) {
        config.setShowCamera(show);
        return this;
    }

    public ImagePickerESA toolbarArrowColor(@ColorInt int color) {
        config.setArrowColor(color);
        return this;
    }

    public ImagePickerESA toolbarFolderTitle(String title) {
        config.setFolderTitle(title);
        return this;
    }

    public ImagePickerESA toolbarImageTitle(String title) {
        config.setImageTitle(title);
        return this;
    }

    public ImagePickerESA toolbarDoneButtonText(String text) {
        config.setDoneButtonText(text);
        return this;
    }

    public ImagePickerESA origin(ArrayList<Image> images) {
        config.setSelectedImages(images);
        return this;
    }

    public ImagePickerESA exclude(ArrayList<Image> images) {
        config.setExcludedImages(images);
        return this;
    }

    public ImagePickerESA excludeFiles(ArrayList<File> files) {
        config.setExcludedImageFiles(files);
        return this;
    }

    public ImagePickerESA folderMode(boolean folderMode) {
        config.setFolderMode(folderMode);
        return this;
    }


    public ImagePickerESA includeVideo(boolean includeVideo) {
        config.setIncludeVideo(includeVideo);
        return this;
    }

    public ImagePickerESA onlyVideo(boolean onlyVideo) {
        config.setOnlyVideo(onlyVideo);
        return this;
    }

    public ImagePickerESA includeAnimation(boolean includeAnimation) {
        config.setIncludeAnimation(includeAnimation);
        return this;
    }

    public ImagePickerESA imageDirectory(String directory) {
        config.setImageDirectory(directory);
        return this;
    }

    public ImagePickerESA imageFullDirectory(String fullPath) {
        config.setImageFullDirectory(fullPath);
        return this;
    }

    public ImagePickerESA theme(@StyleRes int theme) {
        config.setTheme(theme);
        return this;
    }

    public ImagePickerESA enableLog(boolean isEnable) {
        IpLogger.getInstance().setEnable(isEnable);
        return this;
    }

    public ImagePickerESA language(String language) {
        config.setLanguage(language);
        return this;
    }

    public ImagePickerConfig getConfig() {
        LocaleManager.setLanguage(config.getLanguage());
        return ConfigUtils.checkConfig(config);
    }

    public Intent getIntent(Context context) {
        ImagePickerConfig config = getConfig();
        Intent intent = new Intent(context, ImagePickerActivity.class);
        intent.putExtra(ImagePickerConfig.class.getSimpleName(), config);
        return intent;
    }

    /* --------------------------------------------------- */
    /* > Helper */
    /* --------------------------------------------------- */

    public static boolean shouldHandle(int requestCode, int resultCode, Intent data) {
        /*return resultCode == Activity.RESULT_OK
                && requestCode == IpCons.RC_IMAGE_PICKER
                && data != null;*/

      return resultCode == Activity.RESULT_OK
        && data != null;
    }

    public static List<Image> getImages(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getParcelableArrayListExtra(IpCons.EXTRA_SELECTED_IMAGES);
    }

    public static Image getFirstImageOrNull(Intent intent) {
        List<Image> images = getImages(intent);
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.get(0);
    }
}
