package com.example.phonedb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class PhoneViewModel extends AndroidViewModel {
    private PhoneRepository repository;
    private LiveData<List<Phone>> phoneList;
    public PhoneViewModel(@NonNull Application application){
        super(application);
        this.repository = new PhoneRepository(application);
        this.phoneList = this.repository.getPhoneList();
    }

    public LiveData<List<Phone>> getPhoneList() {
        return phoneList;
    }
    public void insert(Phone phone){
        this.repository.insert(phone);

    }
    public void update(Phone phone){
        this.repository.udpate(phone);
    }
    public void delete(Phone p){this.repository.delete(p);}

    public void deleteAllPhones() {
        this.repository.deleteAllPhones();

    }
}
