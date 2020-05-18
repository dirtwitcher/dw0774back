package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import app.HibernateSessionFactory;
import entity.Profile;
import services.ProfileService;

@SuppressWarnings("deprecation")
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ProfileServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	response.setHeader("Access-Control-Allow-Methods", "GET");

	// System.out.println("Enter profile doGet");

	String login = request.getParameter("login");
	String password = request.getParameter("password");
	boolean check = false;

	Gson gson = new Gson();

	ProfileService profileService = new ProfileService();
	List<Profile> profileList = profileService.findAllProfile();

	for (Profile myUser : profileList) {
	    if ((myUser.getLogin().equals(login)) && (myUser.getPassword().equals(password))) {
		if (request.getParameter("action").equals("auth")) {
		    String json = gson.toJson(myUser.getId_profile());
		    response.getWriter().write(json);
		    check = true;
		} else if (request.getParameter("action").equals("profile")) {
		    Session session = HibernateSessionFactory.getSessionFactory().openSession();
		    Profile profile = session.get(Profile.class, myUser.getId_profile());

		    // response
		    String json = gson.toJson(profile);
		    response.getWriter().write(json);
		    check = true;
		}
	    }
	}

	if (check == false) {
	    String json = gson.toJson("bad response");
	    response.getWriter().write(json);
	}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	response.setHeader("Access-Control-Allow-Methods", "POST");

	// System.out.println("Enter profile doPost");

	Profile profile = null;

	Gson gson = new Gson();
	@SuppressWarnings("rawtypes")
	Enumeration en = request.getParameterNames();

	while (en.hasMoreElements()) {
	    profile = gson.fromJson((String) en.nextElement(), Profile.class);
	}

	ProfileService profileService = new ProfileService();
	List<Profile> profileList = profileService.findAllProfile();

	boolean check = false;

	for (Profile myUser : profileList) {
	    String userLogin = myUser.getLogin();
	    if (profile.getLogin().equals(userLogin)) {
		check = true;
	    }
	}

	if (check == true) {
	    String json = gson.toJson("bad post");
	    response.getWriter().write(json);
	} else {
	    profileService.createProfile(profile);
	    String json = gson.toJson("good post");
	    response.getWriter().write(json);
	}
    }

    private static String inputStreamToString(InputStream inputStream) {
	String result = "";
	Scanner scanner = new Scanner(inputStream, "UTF-8");
	if (scanner.hasNext())
	    result = scanner.useDelimiter("\\A").next();
	else
	    result = "";
	scanner.close();
	return result;
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	response.setHeader("Access-Control-Allow-Methods", "PUT");

	// System.out.println("Enter profile doPut");

	String login = request.getParameter("login");
	String password = request.getParameter("password");
	boolean check = false;

	Gson gson = new Gson();

	ProfileService profileService = new ProfileService();
	List<Profile> profileList = profileService.findAllProfile();

	for (Profile myUser : profileList) {
	    if ((myUser.getLogin().equals(login)) && (myUser.getPassword().equals(password))) {

		String body = inputStreamToString(request.getInputStream());
		Profile profile = gson.fromJson(body, Profile.class);

		profile.setId_profile(myUser.getId_profile()); // secure

		profileService.updateProfile(profile);

		// response
		String json = gson.toJson(profile);
		response.getWriter().write(json);
		check = true;
	    }
	}

	if (check == false) {
	    String json = gson.toJson("bad response");
	    response.getWriter().write(json);
	}
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	response.setHeader("Access-Control-Allow-Methods", "DELETE");

	// System.out.println("Enter profile doDelete");

	String login = request.getParameter("login");
	String password = request.getParameter("password");

	boolean check = false;

	ProfileService profileService = new ProfileService();
	List<Profile> profileList = profileService.findAllProfile();

	Gson gson = new Gson();

	for (Profile myUser : profileList) {
	    if ((myUser.getLogin().equals(login)) && (myUser.getPassword().equals(password))) {

		Profile profile = profileService.findProfileById(myUser.getId_profile());
		profileService.deleteProfile(profile);

		Session sessionDel = HibernateSessionFactory.getSessionFactory().openSession();
		Transaction txn = sessionDel.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = sessionDel
			.createNativeQuery("delete from zakaz where id_profile=" + myUser.getId_profile());
		query.executeUpdate();
		txn.commit();
		sessionDel.close();

		// response
		String json = gson.toJson("good response");
		response.getWriter().write(json);
		check = true;

	    }
	}

	if (check == false) {
	    String json = gson.toJson("bad response");
	    response.getWriter().write(json);
	}
    }

}
