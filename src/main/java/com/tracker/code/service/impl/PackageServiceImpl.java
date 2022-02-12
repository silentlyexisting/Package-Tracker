package com.tracker.code.service.impl;

import com.tracker.code.exception.PackageNotFoundException;
import com.tracker.code.dto.PackageDto;
import com.tracker.code.dto.PostOfficeDto;
import com.tracker.code.model.Package;
import com.tracker.code.model.PostOffice;
import com.tracker.code.repository.PackageRepository;
import com.tracker.code.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageRepository packageRepository;

    @Override
    public void registerShipment(PackageDto packageDto) {
        PostOffice postOffice = new PostOffice(
                packageDto.getPostOffice().getIndex(),
                packageDto.getPostOffice().getName(),
                packageDto.getPostOffice().getAddress()
        );

        Package newPackage = new Package(
                packageDto.getPackageType(),
                packageDto.getReceiverIndex(),
                packageDto.getReceiverAddress(),
                packageDto.getReceiverName(),
                packageDto.getShipmentStatus(),
                List.of(postOffice)

        );
        packageRepository.save(newPackage);
    }

    @Override
    public Package findPackageById(long packageId) {
        return packageRepository.findById(packageId)
                .orElseThrow(() -> new PackageNotFoundException(packageId));
    }

    @Override
    public void arriveTempPostOffice(PostOfficeDto postOfficeDto, long packageId) {
        Package packageToUpdate = this.packageRepository.findById(packageId)
                .orElseThrow(() -> new PackageNotFoundException(packageId));

        PostOffice postOffice = new PostOffice(
                postOfficeDto.getIndex(),
                postOfficeDto.getName(),
                postOfficeDto.getAddress()
        );
        packageToUpdate.setId(packageId);
        packageToUpdate.setShipmentStatus(Package.ShipmentStatus.AT_TEMPORARY_POST_OFFICE);
        packageToUpdate.getPostOffices().add(postOffice);
        this.packageRepository.save(packageToUpdate);
    }

    @Override
    public void shipPackageFromTempPostOffice(long packageId) {
        Package packageToShip = this.packageRepository.findById(packageId)
                .orElseThrow(() -> new PackageNotFoundException(packageId));
        packageToShip.setShipmentStatus(Package.ShipmentStatus.SHIPPED);
        this.packageRepository.save(packageToShip);
    }

    @Override
    public void receivePackageByReceiver(long packageId) {
        Package packageToReceive = this.packageRepository.findById(packageId)
                .orElseThrow(() -> new PackageNotFoundException(packageId));
        packageToReceive.setShipmentStatus(Package.ShipmentStatus.RECEIVED);
        this.packageRepository.save(packageToReceive);
    }
}
