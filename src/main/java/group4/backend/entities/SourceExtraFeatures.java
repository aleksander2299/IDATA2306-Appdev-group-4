package group4.backend.entities;

import jakarta.persistence.*;
/**
 * class representing source extra features table in our database. responsible for holding feature and source_id.
 */
@Entity
@Table(name = "source_extra_features")
public class SourceExtraFeatures {

    @Id
    @Column(name = "feature", nullable = false)
    private String feature;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_id", nullable = false)
    private int sourceID;

    /**
     * returns feature
     * @return string feature
     */
    public String getFeature() { return feature; }

    /**
     * sets the feature to a new feature
     * @param feature the feature
     */
    public void setFeature(String feature) {this.feature = feature; }

    /**
     * returns source id
     * @return int source id
     */
    public int getSourceID() { return sourceID; }

    /**
     * sets the source id to a new source id
     * @param sourceID the source id
     */
    public void setSourceID(int sourceID) {this.sourceID = sourceID; }


}
