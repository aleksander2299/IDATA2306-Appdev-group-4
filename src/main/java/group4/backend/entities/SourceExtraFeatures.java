package group4.backend.entities;

import jakarta.persistence.*;
/**
 * class representing source extra features table in our database. responsible for holding feature and source_id.
 */
@Entity
@Table(name = "source_extra_features")
public class SourceExtraFeatures {

    @EmbeddedId
    private SourceExtraFeaturesId id;

    @ManyToOne
    @MapsId("feature")
    @JoinColumn(name = "feature", referencedColumnName = "feature", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_feature"))
    private ExtraFeatures feature;

    @ManyToOne
    @MapsId("sourceID")
    @JoinColumn(name = "source_id", referencedColumnName = "source_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_source_id_2"))
    private Source sourceID;

    public SourceExtraFeatures() {}

    public SourceExtraFeatures(ExtraFeatures feature, Source sourceID) {
        this.id = new SourceExtraFeaturesId(feature.getFeature(), sourceID.getSourceID());
        this.feature = feature;
        this.sourceID = sourceID;
    }

    public SourceExtraFeaturesId getId() {
        return this.id;
    }

    public void setId(SourceExtraFeaturesId id) {
        this.id = id;
    }

    public ExtraFeatures getFeature() { return feature; }

    public void setFeature(ExtraFeatures feature) {
        this.feature = feature;
    }

    public Source getSourceID() { return sourceID; }

    public void setSourceID(Source sourceID) {
        this.sourceID = sourceID;
    }
}
