package net.link.linkapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.tapadoo.alerter.Alerter;

import java.io.File;

import bassiouny.ahmed.genericmanager.SharedPrefManager;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class SignUpActivity extends AppCompatActivity {

    private ImageView back;
    private ImageView logo;
    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirm;
    private TextInputEditText phone;
    private Spinner spAccountType;
    private Spinner spCategory;
    private CircularProgressButton btnId;
    
    private File image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back = findViewById(R.id.back);
        logo = findViewById(R.id.logo);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        phone = findViewById(R.id.phone);
        spAccountType = findViewById(R.id.sp_account_type);
        spCategory = findViewById(R.id.sp_category);
        btnId = findViewById(R.id.btn_id);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openChooserWithGallery(SignUpActivity.this, "", EasyImage.REQ_SOURCE_CHOOSER);
            }
        });
        btnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().isEmpty()){
                    name.setError(getString(R.string.field_required));
                }else if(!email.getText().toString().trim().contains("@") || !email.getText().toString().trim().contains(".")){
                    email.setError(getString(R.string.email_invalid));
                }else if(password.getText().toString().trim().length()<6){
                    password.setError(getString(R.string.invalid_password_lenght));
                }else if(!password.getText().toString().equals(confirm.getText().toString())){
                    confirm.setError(getString(R.string.invalid_confirm_password));
                }else if(phone.getText().toString().length() != 11){
                    phone.setError(getString(R.string.invalid_phone));
                }else {
                    btnId.startAnimation();
                    signUp();
                }
            }
        });
    }

    private void signUp() {
        DataCall.signUp(name.getText().toString(),
                image, email.getText().toString(),
                password.getText().toString(),
                phone.getText().toString(), String.valueOf((spAccountType.getSelectedItemPosition()+1)),
                spCategory.getSelectedItem().toString(), new Result() {
                    @Override
                    public void success(User user) {
                        int type = user.getType();
                        SharedPrefManager.setInteger("type",type);
                        Intent i;
                        if(type == 1 || type == 2)
                            i = new Intent(SignUpActivity.this, ProductActivity.class);
                        else if(type == 3)
                            i = new Intent(SignUpActivity.this, ThanksActivity.class);
                        else
                            i = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void failed(String msg) {
                        Alerter.create(SignUpActivity.this)
                                .setTitle(getString(R.string.error))
                                .setText(msg)
                                .setBackgroundColorRes(R.color.red)
                                .show();
                        btnId.revertAnimation();
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
                image = imageFile;
                logo.setImageBitmap(myBitmap);

            }
        });
    }
}
