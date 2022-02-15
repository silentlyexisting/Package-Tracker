<a href="https://codeclimate.com/github/silentlyexisting/Parcel-Tracker-MediaSoft-Test/maintainability"><img src="https://api.codeclimate.com/v1/badges/d91b499737f5fab63ffb/maintainability" /></a>
<a href="https://codeclimate.com/github/silentlyexisting/Parcel-Tracker-MediaSoft-Test/test_coverage"><img src="https://api.codeclimate.com/v1/badges/d91b499737f5fab63ffb/test_coverage" /></a>

# Parcel Tracker | Почтовый Трекер
#[Test Task For MediaSoft Java EE](https://drive.google.com/file/d/1obl6-j36xkLszszRPnj0enWxPX-nzzuQ/view) 
 
## API
API available for viewing at: build/openapi.json

## POST: /api/v1.0/shipments/registrate
> Registrate a parcel in the postal system (tracker)
```
{
    "parcelType": "POST_CARD",
    "receiverIndex": "60606",
    "receiverAddress": "1415 University Drive, Chicago",
    "receiverName": "Michael E. Reed",
    "postOffice": {
        "index": "10013",
        "name": "Start Post Office",
        "address": "4517 Hoffman Avenue, New York"
    }
}
```

## GET:  /api/v1.0/shipments/{parcelId}
> Get a specific parcel by its id
```
{
    "id": 4,
    "parcelType": "POST_CARD",
    "receiverIndex": "60606",
    "receiverAddress": "1415 University Drive, Chicago",
    "receiverName": "Michael E. Reed",
    "shipmentStatus": "REGISTRATED",
    "postOffices": [
        {
            "id": 7,
            "index": "10013",
            "name": "Start Post Office",
            "address": "4517 Hoffman Avenue, New York"
        }
    ]
}
```

## PATCH: /api/v1.0/shipments/{parcelId}/temp-office
> Update the status (location) of the parcel to a temporary post office by id
```
{
  "index": "15201",
  "name": "Some Temporary Post Office",
  "address": "3521 Michigan Avenue"
}
```
## GET: response body with added temporary post office
```
{
    "id": 4,
    "parcelType": "POST_CARD",
    "receiverIndex": "60606",
    "receiverAddress": "1415 University Drive, Chicago",
    "receiverName": "Michael E. Reed",
    "shipmentStatus": "AT_TEMPORARY_POST_OFFICE",
    "postOffices": [
        {
            "id": 7,
            "index": "10013",
            "name": "Start Post Office",
            "address": "4517 Hoffman Avenue, New York"
        },
        {
            "id": 8,
            "index": "15201",
            "name": "Some Temporary Post Office",
            "address": "3521 Michigan Avenue"
        }
    ]
}
```

## PATCH: /api/v1.0/shipments/{parcelId}/shipped
> Update the status of the parcel to shipped by id

## PATCH: /api/v1.0/shipments/{parcelId}/received
> Update the status of the parcel to received by id
