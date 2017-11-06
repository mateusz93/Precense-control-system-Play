package services;

import exceptions.IncorrectCaptchaException;
import lombok.val;
import org.springframework.util.StringUtils;
import play.db.ebean.Transactional;
import play.mvc.Http;

import javax.inject.Singleton;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

@Transactional
@Singleton
class CaptchaService {

    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String RECAPTCHA_SECRET_KEY = "6LfTixkUAAAAAGIo_LcFUg46PsxCvOyZuSeDJ_Ns";
    private static final String RECAPTCHA_USER_AGENT = "Mozilla/5.0";

    private static final String IS_SUCCESS = "success";
    private static final String POST_METHOD = "POST";
    private static final String USER_AGENT = "User-Agent";
    private static final String ACCEPT_LANGUAGE = "Accept-Language";

    boolean verify() throws IOException, IncorrectCaptchaException {
        val requestBody = Http.Context.current().request().body().asFormUrlEncoded();
        String reCaptchaResponse = requestBody.get("g-recaptcha-response")[0];

        if (StringUtils.isEmpty(reCaptchaResponse)) {
            throw new IncorrectCaptchaException("recaptcha.incorrect");
        }

        URL obj = new URL(RECAPTCHA_URL);
        HttpsURLConnection con = getHttpsURLConnection(obj);

        String postParams = constructParams(reCaptchaResponse);

        sendPostRequest(con, postParams);
        String response = getResponse(con);

        JsonReader jsonReader = Json.createReader(new StringReader(response));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        return jsonObject.getBoolean(IS_SUCCESS);
    }

    private String constructParams(String reCaptchaResponse) {
        return "secret=" + RECAPTCHA_SECRET_KEY + "&response=" + reCaptchaResponse;
    }

    private String getResponse(HttpsURLConnection con) throws IOException {
        StringBuilder response = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private void sendPostRequest(HttpsURLConnection con, String postParams) throws IOException {
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();
    }

    private HttpsURLConnection getHttpsURLConnection(URL obj) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod(POST_METHOD);
        con.setRequestProperty(USER_AGENT, RECAPTCHA_USER_AGENT);
        con.setRequestProperty(ACCEPT_LANGUAGE, "en-US,en;q=0.5");
        return con;
    }
}
