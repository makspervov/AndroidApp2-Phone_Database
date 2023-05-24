package com.example.phonedb;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneRepository {
    private PhoneDao dao;
    LiveData<List<Phone>> phoneList;
    public void deleteAllPhones() {
        for (int i=0;i<this.phoneList.getValue().toArray().length;i++)
        {
            this.delete(this.phoneList.getValue().get(i));
        }

    }
    public PhoneRepository(Application application){
        PhonesDatabase database = PhonesDatabase.getDatabase(application);
        this.dao=database.phoneDao();
        this.phoneList=dao.findAllPhones();
    }
    public LiveData<List<Phone>> getPhoneList(){
        return phoneList;
    }
    public void insert(Phone p){

        PhonesDatabase.databaseWriteExecutor.execute(()->this.dao.insert(p));
    }
    public void update(Phone p){
        PhonesDatabase.databaseWriteExecutor.execute(()->this.dao.update(p));
    }

    public void udpate(Phone phone) {
        PhonesDatabase.databaseWriteExecutor.execute(() -> this.dao.update(phone));
    }
    public void delete(Phone p){
        PhonesDatabase.databaseWriteExecutor.execute(()->this.dao.delete(p));
    }

}
