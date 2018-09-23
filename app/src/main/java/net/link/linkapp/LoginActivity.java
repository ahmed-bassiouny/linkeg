package net.link.linkapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tapadoo.alerter.Alerter;

import bassiouny.ahmed.genericmanager.SharedPrefManager;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {


    private TextInputEditText email;
    private TextInputEditText password;
    private CircularProgressButton btnId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnId = findViewById(R.id.btn_id);
        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
        btnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnId.startAnimation();
                login();
            }
        });
    }

    private void login() {
        DataCall.signIn(email.getText().toString(), password.getText().toString(), new Result() {
            @Override
            public void success(User user) {
                int type = user.getType();
                SharedPrefManager.setInteger("type",type);
                Intent i;
                if(type == 1 || type == 2)
                    i = new Intent(LoginActivity.this, ProductActivity.class);
                else if(type == 3)
                    i = new Intent(LoginActivity.this, ThanksActivity.class);
                else
                    i = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void failed(String msg) {
                Alerter.create(LoginActivity.this)
                        .setTitle(getString(R.string.error))
                        .setText(msg)
                        .setBackgroundColorRes(R.color.red)
                        .show();
                btnId.revertAnimation();
            }
        });
    }
}
