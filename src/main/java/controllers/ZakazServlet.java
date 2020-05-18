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

import org.hibernate.Session;

import com.google.gson.Gson;

import app.HibernateSessionFactory;
import entity.Profile;
import entity.Zakaz;
import services.ProfileService;
import services.ZakazService;

public class ZakazServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ZakazServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	@SuppressWarnings("unused")
	class ZakazProfile {
	    String FIO = null;
	    String callNumber = null;
	    String email = null;

	    String zakazDate = null;
	    String zakazTime = null;
	    String place = null;
	    String aim = null;
	    String price = null;
	    String status = null;
	}

	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	response.setHeader("Access-Control-Allow-Methods", "GET");

	// System.out.println("Enter zakaz doGet");

	Gson gson = new Gson();
	boolean check = false;

	if (request.getParameter("action").equals("profile")) {
	    String login = request.getParameter("login");
	    String password = request.getParameter("password");
	    Integer id = Integer.parseInt(request.getParameter("id_profile"));

	    Session session = HibernateSessionFactory.getSessionFactory().openSession();

	    @SuppressWarnings("unchecked")
	    List<Zakaz> zakazes = session.createNativeQuery(
		    "select zakaz.id_zakaz, zakaz.aim, zakaz.place, zakaz.price, zakaz.status, zakaz.zakazDate, zakaz.zakazTime from zakaz inner join profile on profile.id_profile = zakaz.id_profile where profile.id_profile="
			    + id + " and profile.login=\'" + login + "\' and profile.password=\'" + password + "\'")
		    .list();

	    // response
	    String json = gson.toJson(zakazes);
	    response.getWriter().write(json);
	    check = true;
	    zakazes.clear();
	}

	if (request.getParameter("action").equals("getZakaz")) {
	    Session session = HibernateSessionFactory.getSessionFactory().openSession();

	    @SuppressWarnings("unchecked")
	    List<ZakazProfile> zakazProfile = session.createNativeQuery(
		    "select profile.FIO, profile.callNumber, profile.email, zakaz.aim, zakaz.place, zakaz.price, zakaz.status, zakaz.zakazDate, zakaz.zakazTime from profile inner join zakaz on profile.id_profile = zakaz.id_profile")
		    .list();
	    session.close();

	    // response
	    String json = gson.toJson(zakazProfile);
	    response.getWriter().write(json);
	    check = true;
	    zakazProfile.clear();
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

	// System.out.println("Enter zakaz doPost");

	Zakaz zakaz = null;

	ProfileService profileService = new ProfileService();

	String login = request.getParameter("login");
	String password = request.getParameter("password");
	boolean check = false;

	List<Profile> profileList = profileService.findAllProfile();

	Gson gson = new Gson();

	for (Profile myUser : profileList) {
	    if ((myUser.getLogin().equals(login)) && (myUser.getPassword().equals(password))) {

		@SuppressWarnings("rawtypes")
		Enumeration en = request.getParameterNames();
		while (en.hasMoreElements()) {
		    en.nextElement(); // login
		    en.nextElement(); // password
		    zakaz = gson.fromJson((String) en.nextElement(), Zakaz.class);
		}

		if (!zakaz.getStatus().equals("Не к спеху") && !zakaz.getStatus().equals("В томном ожидании")
			&& !zakaz.getStatus().equals("Очень, очень надо")) {
		    zakaz.setStatus("Не к спеху"); // secure
		}

		zakaz.setId_profile(myUser.getId_profile()); // secure

		ZakazService zakazService = new ZakazService();
		zakazService.createZakaz(zakaz);

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

	// System.out.println("Enter zakaz doPut");

	String login = request.getParameter("login");
	String password = request.getParameter("password");
	boolean check = false;

	Gson gson = new Gson();

	ProfileService profileService = new ProfileService();
	List<Profile> profileList = profileService.findAllProfile();

	for (Profile myUser : profileList) {
	    if ((myUser.getLogin().equals(login)) && (myUser.getPassword().equals(password))) {

		String body = inputStreamToString(request.getInputStream());
		Zakaz zakaz = gson.fromJson(body, Zakaz.class);

		Session session = HibernateSessionFactory.getSessionFactory().openSession();

		@SuppressWarnings("unchecked")
		List<Zakaz> zakazCheck = session.createNativeQuery(
			"select zakaz.id_zakaz, zakaz.aim, zakaz.place, zakaz.price, zakaz.status, zakaz.zakazDate, zakaz.zakazTime from zakaz inner join profile on profile.id_profile = zakaz.id_profile where zakaz.id_zakaz="
				+ zakaz.getId_zakaz() + " and profile.login=\'" + login + "\' and profile.password=\'"
				+ password + "\'")
			.list();
		session.close();

		if (!zakazCheck.isEmpty()) {
		    zakaz.setId_profile(myUser.getId_profile()); // secure

		    ZakazService zakazService = new ZakazService();
		    zakazService.updateZakaz(zakaz);

		    // response
		    String json = gson.toJson("good response");
		    response.getWriter().write(json);
		    check = true;
		}
		zakazCheck.clear();
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

	// System.out.println("Enter zakaz doDelete");

	Integer id = Integer.parseInt(request.getParameter("id_zakaz"));
	String login = request.getParameter("login");
	String password = request.getParameter("password");
	boolean check = false;

	Gson gson = new Gson();

	Session session = HibernateSessionFactory.getSessionFactory().openSession();
	@SuppressWarnings("unchecked")
	List<Zakaz> zakazCheck = session.createNativeQuery(
		"select zakaz.id_zakaz, zakaz.aim, zakaz.place, zakaz.price, zakaz.status, zakaz.zakazDate, zakaz.zakazTime from zakaz inner join profile on profile.id_profile = zakaz.id_profile where zakaz.id_zakaz="
			+ id + " and profile.login=\'" + login + "\' and profile.password=\'" + password + "\'")
		.list();
	session.close();

	if (!zakazCheck.isEmpty()) {
	    ZakazService zakazService = new ZakazService();
	    Zakaz zakaz = zakazService.findZakazById(id);
	    zakazService.deleteZakaz(zakaz);

	    // response
	    String json = gson.toJson("good response");
	    response.getWriter().write(json);
	    check = true;
	}

	zakazCheck.clear();

	if (check == false) {
	    String json = gson.toJson("bad response");
	    response.getWriter().write(json);
	}
    }

}
