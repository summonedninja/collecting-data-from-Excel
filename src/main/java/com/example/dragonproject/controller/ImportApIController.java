package com.example.dragonproject.controller;

import com.example.dragonproject.service.ExcelServiceImplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/import")
public class ImportApIController {

    @Autowired
    private ExcelServiceImplements excelService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            excelService.save(file);
            return new ResponseEntity<>("File uploaded and data saved successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload file." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }
}
