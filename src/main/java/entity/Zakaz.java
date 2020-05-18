package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "zakaz")
public class Zakaz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_zakaz;

    // @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // @JoinColumn(name = "id_profile")
    // private Profile id_profile;

    @JoinColumn(name = "id_profile")
    private int id_profile;

    @Column(name = "zakazDate")
    private String zakazDate;

    @Column(name = "zakazTime")
    private String zakazTime;

    @Column(name = "place", length = 250)
    private String place;

    @Column(name = "aim", length = 250)
    private String aim;

    @Column(name = "price", length = 250)
    private String price;

    @Column(name = "status", length = 25)
    private String status;

    public Zakaz() {
    }

    public int getId_zakaz() {
	return id_zakaz;
    }

    public int getId_profile() {
	return id_profile;
    }

    public void setId_profile(int id_profile) {
	this.id_profile = id_profile;
    }

    public String getZakazDate() {
	return zakazDate;
    }

    public void setZakazDate(String zakazDate) {
	this.zakazDate = zakazDate;
    }

    public String getZakazTime() {
	return zakazTime;
    }

    public void setZakazTime(String zakazTime) {
	this.zakazTime = zakazTime;
    }

    public String getPlace() {
	return place;
    }

    public void setPlace(String place) {
	this.place = place;
    }

    public String getAim() {
	return aim;
    }

    public void setAim(String aim) {
	this.aim = aim;
    }

    public String getPrice() {
	return price;
    }

    public void setPrice(String price) {
	this.price = price;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    @Override
    public String toString() {
	return "Zakaz [id_zakaz=" + id_zakaz + ", id_profile=" + id_profile + ", zakazDate=" + zakazDate
		+ ", zakazTime=" + zakazTime + ", place=" + place + ", aim=" + aim + ", price=" + price + ", status="
		+ status + "]";
    }

}
