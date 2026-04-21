package com.custom_dress.demo.gateway.service.admin;

import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.repository.DressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllDressService {

    @Autowired
    DressRepository dressRepository;

    public List<Dress> getAllDressesData(){
        return dressRepository.findAll();
    }
}
