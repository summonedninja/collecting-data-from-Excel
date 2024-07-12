package com.example.dragonproject.controller;

import com.example.dragonproject.model.Contract;
import com.example.dragonproject.model.ContractStage;
import com.example.dragonproject.repository.ContractRepository;
import com.example.dragonproject.repository.ContractStageRepository;
import com.example.dragonproject.service.ExcelServiceImplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class AllMethodsMainController {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ContractStageRepository contractStageRepository;
    @Autowired
    private ExcelServiceImplements excelServiceImplements;

    @GetMapping("/contract/import")
    public String showImportPage() {
        return "import";
    }

    @GetMapping("/contracts")
    public String showContracts(Model model) {
        List<Contract> contracts = contractRepository.findAll();
        model.addAttribute("contracts", contracts);
        return "contracts";
    }

    @GetMapping("contract/{code}")
    public String showContractCode(@PathVariable String code, Model modelContract){
        Optional<Contract> contractOptional = excelServiceImplements.findContractByCode(code);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            modelContract.addAttribute("contract", contract);
            return "contract_details";
        }else {
            return "not found ";
        }
    }
}

