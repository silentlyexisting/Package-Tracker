package com.tracker.code.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import java.util.List;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "parcels")
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private Parcel.ParcelType parcelType;
    @NonNull
    private String receiverIndex;
    @NonNull
    private String receiverAddress;
    @NonNull
    private String receiverName;
    @NonNull
    private ShipmentStatus shipmentStatus;
    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<PostOffice> postOffices;

    public enum ParcelType {
        LETTER,
        PACKAGE,
        PARCEL,
        POST_CARD
    }

    public enum ShipmentStatus {
        REGISTRATED,
        AT_TEMPORARY_POST_OFFICE,
        SHIPPED,
        RECEIVED,
    }
}
