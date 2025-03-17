package group4.backend.entities;

import jakarta.persistence.*;


/**
 * class representing provider table in database model. a provider is one that lists room from sources.
 */
@Entity
@Table(name = "provider")
public class Provider {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id", nullable = false)
    private int providerId;

    @Column(name = "provider_name", nullable = false)
    private String providerName;

    /**
     * gets provider
     * @return the int provider_id primary key
     */
    public int getProviderId() {
        return providerId;
    }

    /**
     * sets provider_id to new provider_id
     * @param providerId the new providerId
     */
    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }


    /**
     * gets provider name
     * @return the string providerName
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * sets providerName
     * @param providerName the new name for providerName to be set to
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}
