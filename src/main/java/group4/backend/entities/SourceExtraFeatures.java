package group4.backend.entities;

import jakarta.persistence.*;
/**
 * class representing source extra features table in our database. responsible for holding feature.
 */
@Entity
@Table(name = "source_extra_features")
public class SourceExtraFeatures {

    @Id
    @Column(name = "feature", nullable = false)
    private String feature;

    @Id
    @Column(name = "source_id", nullable = false)
    private int sourceID;

    public String getFeature() { return feature; }

    public void setFeature(String feature) {this.feature = feature; }

    public int getSourceID() { return sourceID; }

    public void setSourceID(int sourceID) {this.sourceID = sourceID; }


}
