package JOOQ.Data;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Checkpoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Parcel extends no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel {

    private static final long serialVersionUID = 1L;

    private Long parcelId;
    private Long parcelVersion;
    private Double weight;
    private Integer weightClass;
    private Long shipmentId;
    private List<String> checkpoints;

    public Parcel() {}

    public Parcel(Parcel value) {
        this.parcelId = value.parcelId;
        this.parcelVersion = value.parcelVersion;
        this.weight = value.weight;
        this.weightClass = value.weightClass;
        this.shipmentId = value.shipmentId;
        this.checkpoints = new ArrayList<>(value.checkpoints);
    }

    public Parcel(
            Long parcelId,
            Long parcelVersion,
            Double weight,
            Integer weightClass,
            Long shipmentId
    ) {
        this.parcelId = parcelId;
        this.parcelVersion = parcelVersion;
        this.weight = weight;
        this.weightClass = weightClass;
        this.shipmentId = shipmentId;
        this.checkpoints = new ArrayList<>();
    }

    /**
     * Getter for the list of checkpoints.
     */
    public List<String> getCheckpoints() {
        return checkpoints;
    }

    /**
     * Setter for the list of checkpoints.
     */
    public void setCheckpoints(List<String> checkpoints) {
        this.checkpoints = checkpoints;
    }

    /**
     * Add a checkpoint to the list.
     */
    public void addCheckpoint(Checkpoint checkpoint) {
        checkpoints.add(String.valueOf(checkpoint));
    }

    /**
     * Get the current checkpoint assigned to the parcel.
     */
    public String getCurrentCheckpoint() {
        if (checkpoints.isEmpty()) {
            return null;
        }
        return checkpoints.get(checkpoints.size() - 1);
    }

    /**
     * Get all the checkpoints assigned to the parcel.
     */
    public List<String> getAllCheckpoints() {
        return new ArrayList<>(checkpoints);
    }

    /**
     * Getter for <code>public.parcel.parcel_id</code>.
     */
    public Long getParcelId() {
        return this.parcelId;
    }

    /**
     * Setter for <code>public.parcel.parcel_id</code>.
     */
    public Parcel setParcelId(Long parcelId) {
        this.parcelId = parcelId;
        return this;
    }

    /**
     * Getter for <code>public.parcel.parcel_version</code>.
     */
    public Long getParcelVersion() {
        return this.parcelVersion;
    }

    /**
     * Setter for <code>public.parcel.parcel_version</code>.
     */
    public Parcel setParcelVersion(Long parcelVersion) {
        this.parcelVersion = parcelVersion;
        return this;
    }

    /**
     * Getter for <code>public.parcel.weight</code>.
     */
    public Double getWeight() {
        return this.weight;
    }

    /**
     * Setter for <code>public.parcel.weight</code>.
     */
    public Parcel setWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    /**
     * Getter for <code>public.parcel.weight_class</code>.
     */
    public Integer getWeightClass() {
        return this.weightClass;
    }


    public Long getShipmentId() {
        return shipmentId;
    }

    public Parcel setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
        return this;
    }

    public Parcel setWeightClass(Integer weightClass) {
        this.weightClass = weightClass;
        return this;
    }
}


