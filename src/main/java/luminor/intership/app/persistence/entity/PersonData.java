package luminor.intership.app.persistence.entity;

import lombok.Data;

/**
 * @author Klaudijus Simokaitis
 * */
@Data
public class PersonData {
    String status;
    String country;
    String countryCode;
    String region;
    String regionName;
    String city;
    String zip;
    String lat;
    String lon;
    String timezone;
    String isp;
    String org;
    String as;
    String query;
}
