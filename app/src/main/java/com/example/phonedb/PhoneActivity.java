package com.example.phonedb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
    private EditText manufacturerEditText;
    private EditText modelEditText;
    private EditText aVersionEditText;
    private EditText urlEditText;
    private  Long phoneId=0L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        this.manufacturerEditText = findViewById(R.id.manufacturerEditText);
        this.aVersionEditText=findViewById(R.id.aVersionEditText);
        this.urlEditText=findViewById(R.id.urlEditText);
        this.modelEditText=findViewById(R.id.modelEditText);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view->saveNewPhone());

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> CancelAction());

        Button urlButton = findViewById(R.id.urlButton);
        urlButton.setOnClickListener(view -> goWebPage());

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle =intent.getExtras();
            if(bundle != null){
                this.manufacturerEditText.setText(bundle.getString(MANUFACTURER_KEY));
                this.modelEditText.setText(bundle.getString(MODEL_KEY));
                this.aVersionEditText.setText(bundle.getString(ANDROIDVERSION_KEY));
                this.urlEditText.setText(bundle.getString(WEBSITE_KEY));
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

            bundle.putString(MANUFACTURER_KEY, this.manufacturerEditText.getText().toString());
            bundle.putString(MODEL_KEY, this.modelEditText.getText().toString());
            bundle.putString(ANDROIDVERSION_KEY, this.aVersionEditText.getText().toString());
            bundle.putString(WEBSITE_KEY, this.urlEditText.getText().toString());
            bundle.putLong(PHONE_ID_KEY, this.phoneId);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);

            Toast.makeText(this, "A record has been saved", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_LONG).show();
        }
    }
    private void goWebPage() {

        if (this.urlEditText.getText().toString().startsWith("https://")) {
            Intent intentionBrowser = new Intent("android.intent.action.VIEW", Uri.parse(this.urlEditText.getText().toString()));
            startActivity(intentionBrowser);

        }else{
            Toast errorToast = Toast.makeText(PhoneActivity.this, "Incorrect URL address!", Toast.LENGTH_LONG);
            errorToast.show();
        }
    }
    private boolean checkEditTexts(EditText editText, String info){
        if("".equals(editText.getText().toString())){
            editText.setError(info);
            Toast.makeText(this, info, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean isFieldsValid() {
        boolean isManufacturerValid = checkEditTexts(manufacturerEditText, getString(R.string.manufacturerErr));
        boolean isModelValid = checkEditTexts(modelEditText, getString(R.string.modelErr));
        boolean isAndroidVersionValid = checkEditTexts(aVersionEditText, getString(R.string.androidVersionErr));
        boolean isUrlValid = checkEditTexts(urlEditText, getString(R.string.urlErr));

        return isManufacturerValid && isModelValid && isAndroidVersionValid && isUrlValid;
    }
}

