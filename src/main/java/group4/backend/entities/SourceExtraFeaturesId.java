package group4.backend.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SourceExtraFeaturesId implements Serializable {

  @Column(name = "feature")
  private String feature;

  @Column(name = "source_id")
  private int sourceId;

  public SourceExtraFeaturesId() {}

  public SourceExtraFeaturesId(String feature, int sourceId) {
    this.feature = feature;
    this.sourceId = sourceId;
  }

  public String getFeature() {
    return feature;
  }

  public void setFeature(String feature) {
    this.feature = feature;
  }

  public int getSourceId() {
    return sourceId;
  }

  public void setSourceId(int sourceId) {
    this.sourceId = sourceId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SourceExtraFeaturesId that = (SourceExtraFeaturesId) o;
    return this.sourceId == that.sourceId && Objects.equals(this.feature, that.feature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(feature, sourceId);
  }
}
