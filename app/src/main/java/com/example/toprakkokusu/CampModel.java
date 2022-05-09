package com.example.toprakkokusu;

public class CampModel {
    private String campName, explanation, location,photo,comment;
    private Boolean wc,paid,transport,facility,park,water, wildAnimal,sea,fire,network,trekking,wood;

    public CampModel(String campName, String explanation, String location, String photo, String comment,
                     Boolean wc, Boolean paid, Boolean transport, Boolean facility, Boolean park,
                     Boolean water, Boolean wildAnimal, Boolean sea, Boolean fire, Boolean network, Boolean trekking, Boolean wood) {
        this.campName = campName;
        this.explanation = explanation;
        this.location = location;
        this.photo = photo;
        this.comment = comment;
        this.wc = wc;
        this.paid = paid;
        this.transport = transport;
        this.facility = facility;
        this.park = park;
        this.water = water;
        this.wildAnimal = wildAnimal;
        this.sea = sea;
        this.fire = fire;
        this.network = network;
        this.trekking = trekking;
        this.wood = wood;
    }

    public CampModel(String campName, String explanation, Boolean wc, Boolean paid, Boolean transport, Boolean facility) {
        this.campName = campName;
        this.explanation = explanation;
        this.wc = wc;
        this.paid = paid;
        this.transport = transport;
        this.facility = facility;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getWc() {
        return wc;
    }

    public void setWc(Boolean wc) {
        this.wc = wc;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getTransport() {
        return transport;
    }

    public void setTransport(Boolean transport) {
        this.transport = transport;
    }

    public Boolean getFacility() {
        return facility;
    }

    public void setFacility(Boolean facility) {
        this.facility = facility;
    }

    public Boolean getPark() {
        return park;
    }

    public void setPark(Boolean park) {
        this.park = park;
    }

    public Boolean getWater() {
        return water;
    }

    public void setWater(Boolean water) {
        this.water = water;
    }

    public Boolean getWildAnimal() {
        return wildAnimal;
    }

    public void setWildAnimal(Boolean wildAnimal) {
        this.wildAnimal = wildAnimal;
    }

    public Boolean getSea() {
        return sea;
    }

    public void setSea(Boolean sea) {
        this.sea = sea;
    }

    public Boolean getFire() {
        return fire;
    }

    public void setFire(Boolean fire) {
        this.fire = fire;
    }

    public Boolean getNetwork() {
        return network;
    }

    public void setNetwork(Boolean network) {
        this.network = network;
    }

    public Boolean getTrekking() {
        return trekking;
    }

    public void setTrekking(Boolean trekking) {
        this.trekking = trekking;
    }

    public Boolean getWood() {
        return wood;
    }

    public void setWood(Boolean wood) {
        this.wood = wood;
    }
}
