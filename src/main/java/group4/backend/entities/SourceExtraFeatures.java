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
    @JoinColumn(name = "feature", referencedColumnName = "feature", foreignKey = @ForeignKey(name = "FK_feature"))
    private ExtraFeatures feature;

    @ManyToOne
    @MapsId("sourceId")
    @JoinColumn(name = "source_id", referencedColumnName = "source_id", foreignKey = @ForeignKey(name = "FK_source_id_2"))
    private Source sourceID;

    public SourceExtraFeatures() {}

    public SourceExtraFeatures(ExtraFeatures feature, Source source) {
        this.id = new SourceExtraFeaturesId(feature.getFeature(), source.getSourceID());
        this.feature = feature;
        this.sourceID = source;
    }

    public SourceExtraFeaturesId getId() {
        return this.id;
    }

    public void setId(SourceExtraFeaturesId id) {
        this.id = id;
    }

    /**
     * returns feature
     * @return string feature
     */
    public ExtraFeatures getFeature() { return feature; }

    /**
     * sets the feature to a new feature
     * @param feature the feature
     */
    public void setFeature(ExtraFeatures feature) {this.feature = feature; }

    /**
     * returns source id
     * @return int source id
     */
    public Source getSourceID() { return sourceID; }

    /**
     * sets the source id to a new source id
     * @param sourceID the source id
     */
    public void setSourceID(Source sourceID) {this.sourceID = sourceID; }


}
