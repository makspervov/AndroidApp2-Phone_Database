package com.example.phonedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView phoneListRv;
    private PhoneViewModel phoneViewModel;
    private PhoneAdapter adapter;
    private ActivityResultLauncher<Intent> launcher;
    private FloatingActionButton addPhoneFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.phoneListRv = findViewById(R.id.phone_list_rv);
        this.adapter = new PhoneAdapter(this.getLayoutInflater());
        this.adapter.setOnPhoneClickListener(phone -> editSelectedPhone(phone));
        this.phoneListRv.setAdapter(this.adapter);

        this.addPhoneFab = findViewById(R.id.add_phone_fab);
        this.addPhoneFab.setOnClickListener(view -> newPhone());
        this.phoneListRv.setLayoutManager(new LinearLayoutManager(this));
        this.phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        this.phoneViewModel.getPhoneList().observe(this, phones -> {
            this.adapter.setPhoneList(phones);
        });
        this.launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> insertOrUpdatePhone(result));
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Phone phone = MainActivity.this.adapter.getPhoneList().get(viewHolder.getAdapterPosition());
                MainActivity.this.phoneViewModel.delete(phone);
            }

        };
    ItemTouchHelper itemTouchHelper =new ItemTouchHelper(callback);
    itemTouchHelper.attachToRecyclerView(this.phoneListRv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_examole, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                this.phoneViewModel.deleteAllPhones();
                Toast.makeText(this, "Usunięto wszystkie rekordy" , Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void newPhone() {
        Intent intent =new Intent(this, PhoneActivity.class);
        setResult(RESULT_OK,intent);
        this.launcher.launch(intent);
    }

    private void insertOrUpdatePhone(ActivityResult result) {
           if(result.getResultCode()==RESULT_OK){
                Bundle bundle =result.getData().getExtras();
                String manufacturer = bundle.getString(PhoneActivity.MANUFACTURER_KEY);
                String androidVersion = bundle.getString(PhoneActivity.ANDROIDVERSION_KEY);
                String webSite = bundle.getString(PhoneActivity.WEBSITE_KEY);
                String model = bundle.getString(PhoneActivity.MODEL_KEY);
                Long idPhone = bundle.getLong(PhoneActivity.PHONE_ID_KEY);
                if(idPhone==0) {
                    this.phoneViewModel.insert(new Phone(manufacturer, model, androidVersion, webSite));
                }else{
                    this.phoneViewModel.update(new Phone(idPhone,manufacturer, model, androidVersion, webSite));
                }
          }
    }
    private void editSelectedPhone(Phone phone){
        Bundle bundle = new Bundle();
        bundle.putString(PhoneActivity.MANUFACTURER_KEY,phone.getManufacturer());
        bundle.putString(PhoneActivity.MODEL_KEY,phone.getModel());
        bundle.putString(PhoneActivity.ANDROIDVERSION_KEY,phone.getAndroidVersion());
        bundle.putString(PhoneActivity.WEBSITE_KEY,phone.getWebSite());
        bundle.putLong(PhoneActivity.PHONE_ID_KEY,phone.getId());
        Intent intent =new Intent(this,PhoneActivity.class);
        intent.putExtras(bundle);
        this.launcher.launch(intent);
    }
}