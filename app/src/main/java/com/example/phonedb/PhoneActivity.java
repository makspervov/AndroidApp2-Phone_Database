package com.example.phonedb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PhoneActivity extends AppCompatActivity {
    public static final String MANUFACTURER_KEY = "MANUFACTURER_KEY";
    public static final String MODEL_KEY = "MODEL_KEY";
    public static final String ANDROIDVERSION_KEY = "ANDROIDVERSION_KEY";
    public static final String WEBSITE_KEY = "WEBSITE_KEY";
    public static final String PHONE_ID_KEY ="PHONE_ID_KEY";
    private EditText manufacturerEt;
    private EditText modelEt;
    private EditText androidVersionEt;
    private EditText webSiteEt;
    private Button saveBt;
    private Button cancelBt;
    private Button web_site_bt;
    private  Long phoneId=0L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        this.manufacturerEt = findViewById(R.id.manufacturer_et);
        this.androidVersionEt=findViewById(R.id.android_version_et);
        this.webSiteEt=findViewById(R.id.webpage_et);
        this.modelEt=findViewById(R.id.model_et);
        this.saveBt = findViewById(R.id.save_bt);
        this.saveBt.setOnClickListener(view->saveNewPhone());
        this.cancelBt = findViewById(R.id.cancel_bt);
        this.cancelBt.setOnClickListener(view -> CancelAction());
        this.web_site_bt=findViewById(R.id.web_site_bt);
        this.web_site_bt.setOnClickListener(view -> goWebPage());
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle =intent.getExtras();
            if(bundle != null){
                this.manufacturerEt.setText(bundle.getString(MANUFACTURER_KEY));
                this.modelEt.setText(bundle.getString(MODEL_KEY));
                this.androidVersionEt.setText(bundle.getString(ANDROIDVERSION_KEY));
                this.webSiteEt.setText(bundle.getString(WEBSITE_KEY));
                this.phoneId = bundle.getLong(PHONE_ID_KEY);
            }
        }
    }
    private void CancelAction() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }
    private void saveNewPhone() {
        if (isFieldsValid()) {
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putString(this.MANUFACTURER_KEY, this.manufacturerEt.getText().toString());
            bundle.putString(this.MODEL_KEY, this.modelEt.getText().toString());
            bundle.putString(this.ANDROIDVERSION_KEY, this.androidVersionEt.getText().toString());
            bundle.putString(this.WEBSITE_KEY, this.webSiteEt.getText().toString());
            bundle.putLong(this.PHONE_ID_KEY, this.phoneId);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            Toast.makeText(this, "Zapisano rekord", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Proszę wypełnić wszystkie pola!", Toast.LENGTH_LONG).show();
        }
    }
    private void goWebPage() {

        if (this.webSiteEt.getText().toString().startsWith("https://")) {
            Intent zamiarPrzegladarki = new Intent("android.intent.action.VIEW", Uri.parse(this.webSiteEt.getText().toString()));
            startActivity(zamiarPrzegladarki);

        }else{
            Toast errorToast = Toast.makeText(PhoneActivity.this, "Niewłaściwy adres URL!", Toast.LENGTH_LONG);
            errorToast.show();
        }
    }
    private boolean sprawdzEt(EditText editText, String info){
        if("".equals(editText.getText().toString())){
            editText.setError(info);
            Toast.makeText(this, info, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean isFieldsValid() {
        boolean isManufacturerValid = sprawdzEt(manufacturerEt, getString(R.string.manufacturer_et_err));
        boolean isModelValid = sprawdzEt(modelEt, getString(R.string.model_et_err));
        boolean isAndroidVersionValid = sprawdzEt(androidVersionEt, getString(R.string.android_version_et_err));
        boolean isWebSiteValid = sprawdzEt(webSiteEt, getString(R.string.webpage_et_err));

        return isManufacturerValid && isModelValid && isAndroidVersionValid && isWebSiteValid;
    }

}

