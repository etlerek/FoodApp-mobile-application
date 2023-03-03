package com.example.foodapp.daohandlers;

import com.google.android.gms.tasks.Task;

import java.util.HashMap;

public interface DAOGlobalDbConnector {

    Task<Void> globalAdd(Object obj);

    Task<Void> globalUpdate(String key, HashMap<String, Object> obj);

    Task<Void> globalDelete(String key);

}
