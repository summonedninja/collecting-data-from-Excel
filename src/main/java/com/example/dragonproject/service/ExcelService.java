package com.example.dragonproject.service;

import com.example.dragonproject.model.Contract;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public interface ExcelService {
    void save(MultipartFile file) throws IOException;
    public Optional<Contract> findContractByCode(String contractCode);
}
