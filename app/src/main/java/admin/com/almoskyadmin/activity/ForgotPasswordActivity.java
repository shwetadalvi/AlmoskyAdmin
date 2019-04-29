package admin.com.almoskyadmin.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.common.BaseActivity;
import admin.com.almoskyadmin.databinding.ActivityForgotPasswordBinding;
import admin.com.almoskyadmin.utils.api.ApiCalls;
import admin.com.almoskyadmin.utils.constants.ApiConstants;

public class ForgotPasswordActivity extends BaseActivity {

    private ActivityForgotPasswordBinding mBinding;
    ApiCalls mApiCalls;

    private SimpleArcDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        mApiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);
        mBinding.fgtBtnSendVerification.setOnClickListener(v -> onClickedBtnSendVerification());
        mBinding.fgtBtnVerifyCode.setOnClickListener(v -> onClickedBtnStartVerification());
        mBinding.changePassword.setOnClickListener(v -> onClickedBtnChangePassword());
    }

    private void onClickedBtnStartVerification() {
        String code = mBinding.fgtCode.getText().toString();
        if (code.isEmpty()) {
            mBinding.fgtCodeCont.setErrorEnabled(true);
            mBinding.fgtCode.setError(getString(R.string.code_cannot_be_empty));
            return;
        }
        RequestParams params = new RequestParams();
        String url = ApiConstants.forgotPassCodeVerification;
        params.put(ApiConstants.code, code);
        params.put(ApiConstants.email, mBinding.fgtEmail.getText().toString());
        mApiCalls.callApiPost(this, params, dialog, url, 12);
    }

    private void onClickedBtnChangePassword() {
        String pass1 = mBinding.pass1.getText().toString();
        String pass2 = mBinding.pass2.getText().toString();
        if (pass1.isEmpty() || pass2.isEmpty()) {
            Toast.makeText(this, getString(R.string.fields_cannot_be_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass1.equals(pass2)){
            Toast.makeText(this, getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        String url = ApiConstants.forgotPasswordUpdation;
        params.put(ApiConstants.password, pass1);
        params.put(ApiConstants.code, mBinding.fgtCode.getText().toString());
        params.put(ApiConstants.email, mBinding.fgtEmail.getText().toString());
        mApiCalls.callApiPost(this, params, dialog, url, 13);
    }

    private void onClickedBtnSendVerification() {
        String email = mBinding.fgtEmail.getText().toString();
        if (email.isEmpty()) {
            mBinding.fgtEmailCont.setErrorEnabled(true);
            mBinding.fgtEmailCont.setError(getString(R.string.email_cannot_be_empty));
            return;
        }
        RequestParams params = new RequestParams();
        String url = ApiConstants.forgotPassEmailVerification;
        params.put(ApiConstants.email, email);
        mApiCalls.callApiPost(this, params, dialog, url, 11);
    }

    @Override public void getResponse(String response, int requestId) {
        super.getResponse(response, requestId);
        switch (requestId) {
            case 11:
                try {
                    JSONObject json = new JSONObject(response);
                    String result = json.get("Result").toString();
                    if (result.equals("email send")) {
                        mBinding.fgtStatus.setText("Code sent to yout email, and please verify it here..");
                        mBinding.emailContainer.setVisibility(View.GONE);
                        mBinding.codeContainer.setVisibility(View.VISIBLE);
                    }
                } catch (Exception ex) {
                    Toast.makeText(this,"Sending Code failed",Toast.LENGTH_SHORT).show();
                }
                break;
            case 12:
                try {
                    JSONObject json = new JSONObject(response);
                    String result = json.get("Result").toString();
                    if (result.equals("document found")) {
                        mBinding.fgtStatus.setText("");
                        mBinding.emailContainer.setVisibility(View.GONE);
                        mBinding.codeContainer.setVisibility(View.GONE);
                        mBinding.passwordContainer.setVisibility(View.VISIBLE);
                    }
                } catch (Exception ex) {
                    Toast.makeText(this,"Check your code again",Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
                break;
            case 13:
                try {
                    Toast.makeText(this, getString(R.string.password_changed), Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    Toast.makeText(this,"Changing password failed",Toast.LENGTH_SHORT).show();
                }
                finish();
                break;

        }
    }
}
