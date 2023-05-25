package com.example.phonedb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
    public abstract class PhonesDatabase extends RoomDatabase
    {
        public abstract PhoneDao phoneDao();
        private static volatile PhonesDatabase INSTANCE;
        static PhonesDatabase getDatabase(final Context context) {

            if (INSTANCE == null) {
                synchronized (PhonesDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PhonesDatabase.class, "phones_database")
                                .addCallback(sRoomDatabaseCallback)
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
        private static final int NUMBER_OF_THREADS = 4;
        static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

//uruchamiane przy tworzeniu bazy (pierwsze
//uruchomienie aplikacji, gdy baza nie istnieje)

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                databaseWriteExecutor.execute(() -> {
                    PhoneDao dao = INSTANCE.phoneDao();
                    Phone[] phones = {
                            new Phone(
                                    "Google",
                                    "Pixel 7a",
                                    "13",
                                    "https://store.google.com/us/product/pixel_7a?hl=en-US"),
                            new Phone("Samsung",
                                    "Galaxy S23 Ultra",
                                    "13",
                                    "http://samsung.com/ua/smartphones/galaxy-s23-ultra"),
                            new Phone("Xiaomi",
                                    "Redmi Note 10 Pro",
                                    "11",
                                    "https://www.mi.com/ua/product/redmi-note-10-pro/specs"),
                            new Phone("Nothing",
                                    "Phone (1)",
                                    "12",
                                    "https://pl.nothing.tech/pages/phone-1")
                    };

                    for(Phone p:phones){
                        dao.insert(p);
                    }
                });
            }
        };
    }

