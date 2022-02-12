package com.tracker.code.service;

import com.tracker.code.exception.PackageNotFoundException;
import com.tracker.code.dto.PackageDto;
import com.tracker.code.dto.PostOfficeDto;
import com.tracker.code.model.Package;

public interface PackageService {
    void registerShipment(PackageDto packageDto);
    Package findPackageById(long packageId) throws PackageNotFoundException;
    void arriveTempPostOffice(PostOfficeDto postOfficeDto, long packageId);
    void shipPackageFromTempPostOffice(long packageId);
    void receivePackageByReceiver(long packageId);
}