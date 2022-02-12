package com.tracker.code.controller;

import com.tracker.code.dto.PackageDto;
import com.tracker.code.dto.PostOfficeDto;
import com.tracker.code.model.Package;
import com.tracker.code.service.PackageService;
import org.hibernate.annotations.MapKeyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1.0")
public class PackageController {
    @Autowired
    private PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @Operation(summary = "Registering a package in the postal system")
    @ApiResponse(responseCode = "200", description = "Successful registration of the package")
    @PostMapping("/register")
    public String createShipment(
            @Parameter(description = "Information about package for register")
            @RequestBody PackageDto packageDto) {
        this.packageService.registerShipment(packageDto);
        return "Your package has been successfully registered and will be sent soon.";
    }

    @Operation(summary = "Get a specific package by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Package found"),
            @ApiResponse(responseCode = "404", description = "Package with that id not found")
    })
    @GetMapping("/shipments/{packageId}")
    public Package getInfoAboutPackage(
            @Parameter(description = "Id of package to be found")
            @PathVariable long packageId) {
        return this.packageService.findPackageById(packageId);
    }

    @Operation(summary = "Update the status (location) of the package to a temporary post office by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated (arrived)"),
            @ApiResponse(responseCode = "404", description = "Package with that id not found," +
                    " cannot be arrived")
    })
    @PatchMapping("/shipments/{packageId}")
    public String arriveTempPostOffice(
            @Parameter(description = "Id of package to be found and updated")
            @PathVariable long packageId,
            @Parameter(description = "Temporary post office information")
            @RequestBody PostOfficeDto postOfficeDto) {
        this.packageService.arriveTempPostOffice(postOfficeDto, packageId);
        return "Your package has arrived at the new temporary post office.";
    }

    @Operation(summary = "Update the status of the package to shipped by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated (shipped)"),
            @ApiResponse(responseCode = "404", description = "Package with that id not found," +
                    " cannot be shipped")
    })
    @PatchMapping("/shipments/{packageId}/shipped")
    public String shippingPackage(
            @Parameter(description = "Id of package to be found and updated")
            @PathVariable long packageId) {
        this.packageService.shipPackageFromTempPostOffice(packageId);
        return "Your package has left the temporary post office";
    }

    @Operation(summary = "Update the status of the package to received by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated (received)"),
            @ApiResponse(responseCode = "404", description = "Package with that id not found," +
                    " cannot be received")
    })
    @PatchMapping("/shipments/{packageId}/received")
    public void receivePackage(
            @Parameter(description = "Id of package to be found and updated (received)")
            @PathVariable long packageId) {
        this.packageService.receivePackageByReceiver(packageId);
    }
}
