package services;

import java.util.List;

import daoImpl.ZakazImpl;
import entity.Zakaz;

public class ZakazService {

    private ZakazImpl zakazImpl = new ZakazImpl();

    public ZakazService() {
    }

    public void createZakaz(Zakaz obj) {
	zakazImpl.create(obj);
    }

    public void updateZakaz(Zakaz obj) {
	zakazImpl.update(obj);
    }

    public void deleteZakaz(Zakaz obj) {
	zakazImpl.delete(obj);
    }

    public Zakaz findZakazById(int id) {
	return zakazImpl.findById(id);
    }

    public Zakaz findZakazByName(String name) {
	return zakazImpl.findByName(name);
    }

    public List<Zakaz> findAllZakaz() {
	return zakazImpl.findAll();
    }

}
