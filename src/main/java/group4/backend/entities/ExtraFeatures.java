package group4.backend.entities;

import jakarta.persistence.*;
/**
 * class representing extra features table in our database. responsible for holding feature.
 */
@Entity
@Table(name = "extra_features")
public class ExtraFeatures {

    @Id
    @Column(name = "feature", nullable = false)
    private String feature;
    
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
}
