package com.example.foodapp.daohandlers;

import androidx.annotation.NonNull;

import com.example.foodapp.entity.Ingredient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public abstract class MainHandlerDAO implements DAOGlobalDbConnector {

    protected DatabaseReference databaseReference;
    protected FirebaseDatabase db = FirebaseDatabase.getInstance("https://foodappinz-default-rtdb.europe-west1.firebasedatabase.app");

    public Task<Void> globalAdd(Object obj){
        return databaseReference.push().setValue(obj);
    }

    public Task<Void> globalUpdate(String key, HashMap<String, Object> obj){
        return databaseReference.child(key).updateChildren(obj);
    }

    public Task<Void> globalDelete(String key){
        return databaseReference.child(key).removeValue();
    }

}
