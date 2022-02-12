package com.tracker.code.service;

import com.tracker.code.exception.ParcelNotFoundException;
import com.tracker.code.dto.ParcelDto;
import com.tracker.code.dto.PostOfficeDto;
import com.tracker.code.model.Parcel;

public interface ParcelService {
    void registerShipment(ParcelDto parcelDto);
    Parcel findParcelById(long parcelId) throws ParcelNotFoundException;
    void arriveTempPostOffice(PostOfficeDto postOfficeDto, long parcelId);
    void shipParcelFromTempPostOffice(long parcelId);
    void receiveParcelByReceiver(long parcelId);
}