package com.esafirm.imagepicker.features.imageloader;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.model.Image;
import com.esafirm.imagepicker.view.FakeR;

//import com.esafirm.imagepicker.R;

public class DefaultImageLoader implements ImageLoader {
  private FakeR fakeR;
    @Override
    public void loadImage(Image image, ImageView imageView, ImageType imageType) {
        Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image.getId());

      fakeR = new FakeR(imageView.getContext());
        /*Glide.with(imageView.getContext())
                .load(uri)
                .apply(RequestOptions
                        .placeholderOf(imageType == ImageType.FOLDER
                                ? R.drawable.ef_folder_placeholder
                                : R.drawable.ef_image_placeholder)
                        .error(imageType == ImageType.FOLDER
                                ? R.drawable.ef_folder_placeholder
                                : R.drawable.ef_image_placeholder)
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);*/
        Glide.with(imageView.getContext())
        .load(uri)
        .apply(RequestOptions
          .placeholderOf(imageType == ImageType.FOLDER
            ? fakeR.getId("drawable", "ef_folder_placeholder")
            : fakeR.getId("drawable", "ef_image_placeholder"))
          .error(imageType == ImageType.FOLDER
            ? fakeR.getId("drawable", "ef_folder_placeholder")
            : fakeR.getId("drawable", "ef_image_placeholder"))
        )
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView);

    }
}
