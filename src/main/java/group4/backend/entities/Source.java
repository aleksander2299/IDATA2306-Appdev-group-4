package group4.backend.entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * class representing source table in our database. responsible for holding source_id, source_name and location_type.
 */
@Entity
@Table(name = "source")
public class Source implements Comparable<Source>{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_id", nullable = false)
    private Integer sourceId;

    @Column(name = "source_name", nullable = false)
    private String sourceName;

    @Column(name = "location_type", nullable = false)
    private String locationType;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @OneToMany(mappedBy = "sourceId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SourceExtraFeatures> sourceExtraFeatures;


    /**
     * returns source id
     * @return int source id
     */
    public Integer getSourceId() { return sourceId; }


    /**
     * sets the source id to a new source id
     * @param sourceId the source id
     */
    public void setSourceId(int sourceId) {this.sourceId = sourceId; }

    /**
     * returns source name
     * @return string source name
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * sets the source name to a new source name
     * @param sourceName the source name
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * returns location type
     * @return string location type
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * sets the location type to a location type
     * @param locationType the new location type
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    /**
     * returns city
     * @return string city
     */
    public String getCity() {
        return city;
    }

    /**
     * sets the city to a city
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * returns country
     * @return string country
     */
    public String getCountry() {
        return country;
    }

    /**
     * sets the country to a country
     * @param country the new country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int compareTo(Source other) {
        return Integer.compare(this.sourceId, other.sourceId);
    }
}
