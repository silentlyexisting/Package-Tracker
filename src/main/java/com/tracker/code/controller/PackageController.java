package com.tracker.code.controller;

import com.tracker.code.dto.PackageDto;
import com.tracker.code.dto.PostOfficeDto;
import com.tracker.code.model.Package;
import com.tracker.code.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1.0")
public class PackageController {
    @Autowired
    private PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping("/register")
    public String createShipment(@RequestBody PackageDto packageDto) {
        this.packageService.registerShipment(packageDto);
        return "Your package has been successfully registered and will be sent soon.";
    }

    @GetMapping("/shipments/{packageId}")
    public Package getInfoAboutPackage(@PathVariable(required = false) long packageId) {
        return this.packageService.findPackageById(packageId);
    }

    @PatchMapping("/shipments/{packageId}")
    public String arriveTempPostOffice(@PathVariable long packageId,
                                        @RequestBody PostOfficeDto postOfficeDto) {
        this.packageService.arriveTempPostOffice(postOfficeDto, packageId);
        return "Your package has arrived at the new temporary post office.";
    }

    @PatchMapping("/shipments/{packageId}/shipped")
    public String shippingPackage(@PathVariable long packageId) {
        this.packageService.shipPackageFromTempPostOffice(packageId);
        return "Your package has left the temporary post office";
    }

    @PatchMapping("/shipments/{packageId}/received")
    public void receivePackage(@PathVariable long packageId) {
        this.packageService.receivePackageByReceiver(packageId);
    }
}
