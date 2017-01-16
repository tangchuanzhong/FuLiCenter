package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.view.MFGT;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    IModelUser model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivReturn, R.id.btnLogin, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivReturn:
                MFGT.finish(this);
                break;
            case R.id.btnLogin:
                checkInput();
                break;
            case R.id.btnRegister:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkInput() {
        String username =etUserName.getText().toString().trim();
        String password=etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            etUserName.setError(getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            etPassword.setError(getString(R.string.password_connot_be_empty));
            etPassword.requestFocus();
        }else {
            login(username,password);
        }
    }

    private void login(String username, String password) {
        model=new ModelUser();
    }
}
