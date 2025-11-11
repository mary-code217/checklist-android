package com.hoho.cheklist.service;

import com.hoho.cheklist.db.repository.MasterRepository;

public class MasterService {

    private final MasterRepository masterRepository;

    public MasterService(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }
}
