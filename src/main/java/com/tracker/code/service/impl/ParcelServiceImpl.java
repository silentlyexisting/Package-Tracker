package com.tracker.code.service.impl;

import com.tracker.code.exception.ParcelNotFoundException;
import com.tracker.code.dto.ParcelDto;
import com.tracker.code.dto.PostOfficeDto;
import com.tracker.code.model.Parcel;
import com.tracker.code.model.PostOffice;
import com.tracker.code.repository.ParcelRepository;
import com.tracker.code.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelServiceImpl implements ParcelService {
    @Autowired
    private ParcelRepository parcelRepository;

    @Override
    public void registerShipment(ParcelDto parcelDto) {
        PostOffice postOffice = new PostOffice(
                parcelDto.getPostOffice().getIndex(),
                parcelDto.getPostOffice().getName(),
                parcelDto.getPostOffice().getAddress()
        );

        Parcel parcel = new Parcel(
                parcelDto.getParcelType(),
                parcelDto.getReceiverIndex(),
                parcelDto.getReceiverAddress(),
                parcelDto.getReceiverName(),
                parcelDto.getShipmentStatus(),
                List.of(postOffice)

        );
        parcelRepository.save(parcel);
    }

    @Override
    public Parcel findParcelById(long parcelId) {
        return parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(parcelId));
    }

    @Override
    public void arriveTempPostOffice(PostOfficeDto postOfficeDto, long parcelId) {
        Parcel parcel = this.parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(parcelId));

        PostOffice postOffice = new PostOffice(
                postOfficeDto.getIndex(),
                postOfficeDto.getName(),
                postOfficeDto.getAddress()
        );
        parcel.setId(parcelId);
        parcel.setShipmentStatus(Parcel.ShipmentStatus.AT_TEMPORARY_POST_OFFICE);
        parcel.getPostOffices().add(postOffice);
        this.parcelRepository.save(parcel);
    }

    @Override
    public void shipParcelFromTempPostOffice(long parcelId) {
        Parcel parcelToShip = this.parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(parcelId));
        parcelToShip.setShipmentStatus(Parcel.ShipmentStatus.SHIPPED);
        this.parcelRepository.save(parcelToShip);
    }

    @Override
    public void receiveParcelByReceiver(long parcelId) {
        Parcel parcelToReceive = this.parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(parcelId));
        parcelToReceive.setShipmentStatus(Parcel.ShipmentStatus.RECEIVED);
        this.parcelRepository.save(parcelToReceive);
    }
}
