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

    public String getFeature() { return feature; }

    public void setFeature() {this.feature = feature; }
}
