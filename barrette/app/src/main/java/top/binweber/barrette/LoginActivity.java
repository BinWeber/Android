package top.binweber.barrette;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

/**
 * Created by wangb on 2017/10/25.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameView;

    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        final TextView mLoginButton = (TextView) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mLoginButton.setTextColor(getResources().getColor(R.color.colorLoginDark));
                attemptLogin();
            }
        });

        CardView mRegisterButton = (CardView) findViewById(R.id.button_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LoginActivity.this.finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private void attemptLogin(){
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        final String username = mUsernameView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(username)){
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if(TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>(){
                @Override
                public void done(AVUser avUser, AVException e){
                    if(e == null){
                        LoginActivity.this.finish();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }
}
