package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_profile;

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "id_profile", cascade =
    // CascadeType.ALL)
    // private List<Zakaz> zakazList;

    @Column(name = "login", length = 25)
    private String login;

    @Column(name = "password", length = 25)
    private String password;

    @Column(name = "FIO", length = 135)
    private String FIO;

    @Column(name = "callNumber", length = 25)
    private String callNumber;

    @Column(name = "email", length = 150)
    private String email;

    // @Column(name = "reit")
    // private double reit;

    // @Column(name = "foto")
    // @Lob(type = LobType.BLOB)
    // private byte[] image;

    public Profile() {
    }

    // public List<Zakaz> getZakazList() {
    // return zakazList;
    // }

    // public void setZakazList(List<Zakaz> zakazList) {
    // this.zakazList = zakazList;
    // }

    public int getId_profile() {
	return id_profile;
    }

    public void setId_profile(int id_profile) {
	this.id_profile = id_profile;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getFIO() {
	return FIO;
    }

    public void setFIO(String fIO) {
	FIO = fIO;
    }

    public String getCallNumber() {
	return callNumber;
    }

    public void setCallNumber(String callNumber) {
	this.callNumber = callNumber;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    @Override
    public String toString() {
	return "Profile [id_profile=" + id_profile + ", login=" + login + ", password=" + password + ", FIO=" + FIO
		+ ", callNumber=" + callNumber + ", email=" + email + "]";
    }

}
