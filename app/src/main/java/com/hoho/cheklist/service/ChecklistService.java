package com.hoho.cheklist.service;

import com.hoho.cheklist.db.repository.ChecklistRepository;

public class ChecklistService {
    private final ChecklistRepository checklistRepository;

    public ChecklistService(ChecklistRepository checklistRepository) {
        this.checklistRepository = checklistRepository;
    }
}
