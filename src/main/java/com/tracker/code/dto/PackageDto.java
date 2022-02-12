package com.tracker.code.dto;

import com.tracker.code.model.Package;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PackageDto {
    private Package.PackageType packageType;
    private String receiverIndex;
    private String receiverAddress;
    private String receiverName;
    private Package.ShipmentStatus shipmentStatus;
    private PostOfficeDto postOffice;
}


