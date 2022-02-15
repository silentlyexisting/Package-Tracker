package com.tracker.code.controller;

import com.tracker.code.dto.ParcelDto;
import com.tracker.code.dto.PostOfficeDto;
import com.tracker.code.model.Parcel;
import com.tracker.code.service.ParcelService;
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
@RequestMapping("/api/v1.0/shipments")
public class ParcelController {
    @Autowired
    private ParcelService parcelService;

    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @Operation(summary = "Registrate a parcel in the postal system")
    @ApiResponse(responseCode = "200", description = "Successful registration of the parcel")
    @PostMapping("/registrate")
    public String registrateShipment(
            @Parameter(description = "Information about parcel for register")
            @RequestBody ParcelDto parcelDto) {
        this.parcelService.registrateShipment(parcelDto);
        return "Your parcel has been successfully registered and will be sent soon.";
    }

    @Operation(summary = "Get a specific parcel by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parcel found"),
            @ApiResponse(responseCode = "404", description = "Parcel with that id not found")
    })
    @GetMapping("/{parcelId}")
    public Parcel getInfoAboutParcel(
            @Parameter(description = "Id of parcel to be found")
            @PathVariable long parcelId) {
        return this.parcelService.findParcelById(parcelId);
    }

    @Operation(summary = "Update the status (location) of the parcel to a temporary post office by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated (arrived)"),
            @ApiResponse(responseCode = "404", description = "Parcel with that id not found," +
                    " cannot be arrived")
    })
    @PatchMapping("/{parcelId}/temp-office")
    public String arriveAtTempPostOffice(
            @Parameter(description = "Id of parcel to be found and updated")
            @PathVariable long parcelId,
            @Parameter(description = "Temporary post office information")
            @RequestBody PostOfficeDto postOfficeDto) {
        this.parcelService.arriveTempPostOffice(postOfficeDto, parcelId);
        return "Your parcel has arrived at the new temporary post office.";
    }

    @Operation(summary = "Update the status of the parcel to shipped by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated (shipped)"),
            @ApiResponse(responseCode = "404", description = "Parcel with that id not found," +
                    " cannot be shipped")
    })
    @PatchMapping("/{parcelId}/shipped")
    public String shipParcel(
            @Parameter(description = "Id of parcel to be found and updated")
            @PathVariable long parcelId) {
        this.parcelService.shipParcelFromTempPostOffice(parcelId);
        return "Your parcel has left the temporary post office";
    }

    @Operation(summary = "Update the status of the parcel to received by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated (received)"),
            @ApiResponse(responseCode = "404", description = "Parcel with that id not found," +
                    " cannot be received")
    })
    @PatchMapping("/{parcelId}/received")
    public String receiveParcel(
            @Parameter(description = "Id of parcel to be found and updated (received)")
            @PathVariable long parcelId) {
        this.parcelService.receiveParcelByReceiver(parcelId);
        return "Parcel was successfully received by the recipient";
    }
}
