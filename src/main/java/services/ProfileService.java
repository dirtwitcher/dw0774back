package services;

import java.util.List;

import daoImpl.ProfileImpl;
import entity.Profile;

public class ProfileService {

    private ProfileImpl profileImpl = new ProfileImpl();

    public ProfileService() {
    }

    public void createProfile(Profile obj) {
	profileImpl.create(obj);
    }

    public void updateProfile(Profile obj) {
	profileImpl.update(obj);
    }

    public void deleteProfile(Profile obj) {
	profileImpl.delete(obj);
    }

    public Profile findProfileByName(String name) {
	return profileImpl.findByName(name);
    }

    public Profile findProfileById(int id) {
	return profileImpl.findById(id);
    }

    public List<Profile> findAllProfile() {
	return profileImpl.findAll();
    }

}
