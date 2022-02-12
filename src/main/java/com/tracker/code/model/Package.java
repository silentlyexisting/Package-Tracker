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
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private PackageType packageType;
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

    public enum PackageType {
        LETTER,
        PACKAGE,
        PARCEL,
        POST_CARD
    }

    public enum ShipmentStatus {
        REGISTERED,
        AT_TEMPORARY_POST_OFFICE,
        SHIPPED,
        RECEIVED,
    }
}
