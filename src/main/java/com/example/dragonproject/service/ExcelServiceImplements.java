package com.example.dragonproject.service;

import com.example.dragonproject.model.Contract;
import com.example.dragonproject.model.ContractStage;
import com.example.dragonproject.repository.ContractRepository;
import com.example.dragonproject.repository.ContractStageRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

@Service
public class ExcelServiceImplements implements ExcelService {
    private static final Logger logger = Logger.getLogger(ExcelServiceImplements.class.getName());

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractStageRepository contractStageRepository;

    @Override
    public void save(MultipartFile file) throws IOException {
        try {
            InputStream stream = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            List<Contract> contracts = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String contractCode = row.getCell(0).getStringCellValue();
                    if (contractRepository.existsByContractCode(contractCode)) {
                        logger.warning("Contract with code " + contractCode + " already exists. Skipping row " + row.getRowNum());
                        continue;
                    }
                    Contract contract = new Contract();
                    contract.setContractCode(row.getCell(0).getStringCellValue());
                    contract.setContractName(row.getCell(1).getStringCellValue());
                    contract.setCustomer(row.getCell(2).getStringCellValue());

                    ContractStage contractStage = new ContractStage();
                    contractStage.setStageName(row.getCell(3).getStringCellValue());

                    if (row.getCell(4) != null) {
                        if (DateUtil.isCellDateFormatted(row.getCell(4))) {
                            contractStage.setStartDate(row.getCell(4).getDateCellValue());
                        } else {
                            // Handle the case where the date is not properly formatted
                            logger.warning("Invalid date format in row: " + row.getRowNum() + ", cell: 4");
                        }
                    }

                    if (row.getCell(5) != null) {
                        if (DateUtil.isCellDateFormatted(row.getCell(5))) {
                            contractStage.setEndDate(row.getCell(5).getDateCellValue());
                        } else {
                            // Handle the case where the date is not properly formatted
                            logger.warning("Invalid date format in row: " + row.getRowNum() + ", cell: 5");
                        }
                    }

                    contractStage.setContract(contract);
                    List<ContractStage> stages = new ArrayList<>();
                    stages.add(contractStage);
                    contract.setContractStage(stages);

                    contracts.add(contract);
                    contractRepository.saveAll(contracts);


                }

            }
            workbook.close();
        } catch (Exception e) {
            logger.severe("Error processing Excel file: " + e.getMessage());
            throw new IOException("Failed to process Excel file", e);
        }
    }

    @Override
    public Optional<Contract> findContractByCode(String contractCode) {
        return contractRepository.findByContractCode(contractCode);
    }
}