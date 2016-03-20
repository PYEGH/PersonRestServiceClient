import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.representation.Form;

public class JerseyClient {
	private static final String APP_URL = "http://localhost:8089/jaxrs2_v1/personRestService";
	private static final String URL = "http://localhost:8089/jaxrs2_v1/personRestService/";
	private static final String PERSONS_XML_URL = URL + "xml/";
	private static final String PERSONS_JSON_URL = URL + "json/";
	private static final String CREATE_PERSON_URL = URL + "create";
	private static final String UPDATE_PERSON_URL = URL + "update";
	private static final String DELETE_PERSON_URL = URL + "delete/";

	private static final String PERSONS_XML_MSG = "GET all in XML:\n";
	private static final String PERSONS_JSON_MSG = "GET all in JSON:\n";
	private static final String PERSON_XML_MSG = "GET one in XML:\n";
	private static final String PERSON_JSON_MSG = "GET one in JSON:\n";

	private static final String PARAM_FIRST_NAME = "firstName";
	private static final String PARAM_LAST_NAME = "lastName";
	private static final String PARAM_LOGIN = "login";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_ID = "id";

	public static void main(String[] args) {
		new JerseyClient().demo();
	}

	private void demo() {
		Client client = Client.create();
		client.setFollowRedirects(true);
		WebResource resource = client.resource(APP_URL);

		getAllPersonsDemo(resource, client);
		postDemo(resource, client);
		getOneDemo(resource, client);
		putDemo(resource, client);
		deleteDemo(resource, client);
	}

	private void getAllPersonsDemo(WebResource resource, final Client client) {
		// GET all persons as XML
		resource = client.resource(PERSONS_XML_URL);
		String response = resource.accept(MediaType.APPLICATION_XML_TYPE).get(
				String.class);
		report(PERSONS_XML_MSG, response);

		// GET all JSON
		resource = client.resource(PERSONS_JSON_URL);
		response = resource.accept(MediaType.APPLICATION_JSON_TYPE).get(
				String.class);
		report(PERSONS_JSON_MSG, response);
	}

	private void getOneDemo(WebResource resource, final Client client) {
		final int userId = 1;
		resource = client.resource(PERSONS_XML_URL + userId);
		String response = resource.accept(MediaType.APPLICATION_XML_TYPE).get(
				String.class);
		report(PERSON_XML_MSG, response);

		resource = client.resource(PERSONS_JSON_URL + userId);
		response = resource.accept(MediaType.APPLICATION_JSON_TYPE).get(
				String.class);
		report(PERSON_JSON_MSG, response);
	}

	private void postDemo(WebResource resource, final Client client) {
		Form form = new Form();
		form.add(PARAM_FIRST_NAME, "Test_FName");
		form.add(PARAM_LAST_NAME, "Test_LName");
		form.add(PARAM_LOGIN, "Test_login");
		form.add(PARAM_EMAIL, "test_email@epam.com");

		resource = client.resource(CREATE_PERSON_URL);
		String response = resource
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_XML_TYPE)
				.post(String.class, form);
		report("POST:\n", response);
	}

	private void putDemo(WebResource resource, final Client client) {
		Form form = new Form();
		form.add(PARAM_ID, 1);
		form.add(PARAM_FIRST_NAME, "PYE_UPD");
		form.add(PARAM_LAST_NAME, "PYE_UPD");
		form.add(PARAM_LOGIN, "PYE_UPD_login");
		form.add(PARAM_EMAIL, "PYE_UPD_email@epam.com");

		resource = client.resource(UPDATE_PERSON_URL);
		String response = resource
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.put(String.class, form);
		report("PUT:\n", response);
	}

	private void deleteDemo(WebResource resource, final Client client) {
		resource = client.resource(DELETE_PERSON_URL + 3);

		String response = resource.accept(MediaType.TEXT_PLAIN_TYPE).delete(
				String.class);
		report("DELETE:\n", response);
	}

	private void report(String msg, String response) {
		System.out.println("\n" + msg + response);
	}
}
