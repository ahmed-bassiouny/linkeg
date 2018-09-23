package net.link.linkapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.tapadoo.alerter.Alerter;

import java.io.File;
import java.util.List;

import bassiouny.ahmed.genericmanager.SharedPrefManager;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class ProductActivity extends AppCompatActivity {

    private ImageView img;
    private TextInputEditText name;
    private TextInputEditText description;
    private Spinner type;
    private TextInputEditText price;
    private CircularProgressButton btnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        img = findViewById(R.id.img);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        type = findViewById(R.id.type);
        price = findViewById(R.id.price);
        btnId = findViewById(R.id.btn_id);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openChooserWithGallery(ProductActivity.this, "", EasyImage.REQ_SOURCE_CHOOSER);
            }
        });
        btnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnId.startAnimation();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        img.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));
                        name.setText("");
                        price.setText("");
                        description.setText("");
                        type.setSelection(0);
                        btnId.revertAnimation();
                        Alerter.create(ProductActivity.this)
                                .setTitle(getString(R.string.thanks))
                                .setText(getString(R.string.add_item_successful))
                                .setBackgroundColorRes(R.color.green)
                                .show();
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                img.setImageBitmap(myBitmap);

            }
        });
    }
}
