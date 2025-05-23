package group4.backend.entities;

import jakarta.persistence.*;
/**
 * Class representing source extra features table in our database. responsible for holding feature and source_id.
 *
 * Note: This documentation was generated with the assistance of AI.
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
    @MapsId("sourceId")
    @JoinColumn(name = "source_id", referencedColumnName = "source_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_source_id_2"))
    private Source sourceId;

    /**
     * Default constructor required by JPA.
     */
    public SourceExtraFeatures() {
    }

    /**
     * Constructs a new SourceExtraFeatures with the specified feature and source.
     *
     * @param feature  the extra feature to be associated
     * @param sourceId the source to be associated
     */
    public SourceExtraFeatures(ExtraFeatures feature, Source sourceId) {
        this.id = new SourceExtraFeaturesId(feature.getFeature(), sourceId.getSourceId());
        this.feature = feature;
        this.sourceId = sourceId;
    }

    /**
     * Gets the composite identifier of this mapping.
     *
     * @return the composite identifier
     */
    public SourceExtraFeaturesId getId() {
        return this.id;
    }

    /**
     * Sets the composite identifier of this mapping.
     *
     * @param id the composite identifier to set
     */
    public void setId(SourceExtraFeaturesId id) {
        this.id = id;
    }

    /**
     * Gets the extra feature associated with this mapping.
     *
     * @return the extra feature
     */
    public ExtraFeatures getFeature() {
        return feature;
    }

    /**
     * Sets the extra feature for this mapping.
     *
     * @param feature the extra feature to set
     */
    public void setFeature(ExtraFeatures feature) {
        this.feature = feature;
    }

    /**
     * Gets the source associated with this mapping.
     *
     * @return the source
     */
    public Source getSourceId() {
        return sourceId;
    }

    /**
     * Sets the source for this mapping.
     *
     * @param sourceId the source to set
     */
    public void setSourceId(Source sourceId) {
        this.sourceId = sourceId;
    }
}
