package model;

import jakarta.persistence.*;

@Entity
@Table(name = "city", schema = "public")
public class City {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "city_id")
    private long cityId;
    @Column(name = "city_name")
    private String cityName;

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
