package com.tracker.code.dto;

import com.tracker.code.model.Parcel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParcelDto {
    private Parcel.ParcelType parcelType;
    private String receiverIndex;
    private String receiverAddress;
    private String receiverName;
    private Parcel.ShipmentStatus shipmentStatus;
    private PostOfficeDto postOffice;
}


