package group4.backend.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * A composite identifier class for the source_extra_features table representing the relationship
 * between sources and their extra features.
 * <p>
 * This class implements Serializable and is used as an embeddable composite key.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
@Embeddable
public class SourceExtraFeaturesId implements Serializable {

  @Column(name = "feature")
  private String feature;

  @Column(name = "source_id")
  private int sourceId;

  /**
   * Default constructor required by JPA.
   */
  public SourceExtraFeaturesId() {
  }

  /**
   * Constructs a new SourceExtraFeaturesId with specified feature and source ID.
   *
   * @param feature  the feature identifier
   * @param sourceId the source identifier
   */
  public SourceExtraFeaturesId(String feature, int sourceId) {
    this.feature = feature;
    this.sourceId = sourceId;
  }

  /**
   * Gets the feature identifier.
   *
   * @return the feature string
   */
  public String getFeature() {
    return feature;
  }

  /**
   * Sets the feature identifier.
   *
   * @param feature the feature to set
   */
  public void setFeature(String feature) {
    this.feature = feature;
  }

  /**
   * Gets the source identifier.
   *
   * @return the source ID
   */
  public int getSourceId() {
    return sourceId;
  }

  /**
   * Sets the source identifier.
   *
   * @param sourceId the source ID to set
   */
  public void setSourceId(int sourceId) {
    this.sourceId = sourceId;
  }

  /**
   * Compares this SourceExtraFeaturesId with another object for equality.
   *
   * @param o the object to compare with
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SourceExtraFeaturesId that = (SourceExtraFeaturesId) o;
    return this.sourceId == that.sourceId && Objects.equals(this.feature, that.feature);
  }

  /**
   * Generates a hash code for this SourceExtraFeaturesId.
   *
   * @return the hash code value
   */
  @Override
  public int hashCode() {
    return Objects.hash(feature, sourceId);
  }
}
