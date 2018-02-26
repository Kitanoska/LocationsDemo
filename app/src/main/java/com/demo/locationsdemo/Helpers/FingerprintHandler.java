package com.demo.locationsdemo.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.locationsdemo.Activity.GenerateCodeActivity;
import com.demo.locationsdemo.R;
import com.demo.locationsdemo.Activity.UsersDataActivity;

/**
 * Created by Natalija on 11/1/2017.
 */

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private final String TAG = this.getClass().getName();
    private Context context;
    private boolean canceled = false;
    CancellationSignal cancellationSignal;

    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //if(canceled)
            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        if(!canceled)
            this.update(context.getString(R.string.fingerprintAuthenticationError) + errString, false);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update(context.getString(R.string.fingerprintAuthenticationHelp) + helpString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update(context.getString(R.string.fingerprintAuthenticationFailed), false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Log.i(TAG,"Fingerprint Authentication succeeded.");
        if(ApplicationClass.isAppFirstStarted()) {
            Intent userDataActivity = new Intent(context, UsersDataActivity.class);
            context.startActivity(userDataActivity);
        }else{
            Intent generateCodeActivity = new Intent(context, GenerateCodeActivity.class);
            context.startActivity(generateCodeActivity);
        }

    }

    public void stopListening() {
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            cancellationSignal = null;
            canceled = true;
        }
    }

    public void update(String e, Boolean success){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorTextView);
        textView.setText(e);
        textView.setVisibility(View.VISIBLE);
    }
}



