package com.custom_dress.demo.gateway.controller.admin;

import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.service.admin.GetAllDressService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class GetAllDress extends MessageController {

    private final GetAllDressService getAllDress;

    public GetAllDress(GetAllDressService getAllDress) {
        this.getAllDress = getAllDress;
    }

    @GetMapping("/admin/dresses")
    public List<Dress> getAllDresses() {
        return getAllDress.getAllDressesData();
    }
}
