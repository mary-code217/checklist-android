package com.hoho.cheklist.db.repository;

import com.hoho.cheklist.db.AppDBHelper;

public class ChecklistRepository {

    private final AppDBHelper helper;

    public ChecklistRepository(AppDBHelper helper) {
        this.helper = helper;
    }

}
