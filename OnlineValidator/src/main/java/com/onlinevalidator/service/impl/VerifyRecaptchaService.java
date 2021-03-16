package com.onlinevalidator.service.impl;

import com.onlinevalidator.model.enumerator.ChiaveConfigurazioneEnum;
import com.onlinevalidator.service.ConfigurazioneServiceInterface;
import com.onlinevalidator.service.VerifyRecaptchaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

@Service
public class VerifyRecaptchaService implements VerifyRecaptchaInterface {

	private String url;
	private String secret;
	private String userAgent;

	@Autowired
	private ConfigurazioneServiceInterface configurazioneService;

	@PostConstruct
	public void init() {

		// Inizializzazione dei parametri ReCaptcha
		logInfo("Inizializzazione parametri utilizzati per la verifica ReCaptcha...");
		this.url = configurazioneService.readValue(ChiaveConfigurazioneEnum.G_RECAPTCHA_URL);
		this.secret = configurazioneService.readValue(ChiaveConfigurazioneEnum.G_RECAPTCHA_SECRET);
		this.userAgent = configurazioneService.readValue(ChiaveConfigurazioneEnum.G_RECAPTCHA_USER_AGENT);
	}

	@Override
	public boolean verify(String gRecaptchaResponse) {

		if (StringUtils.isEmpty(gRecaptchaResponse)) {

			// Logging e restituzione dell'errore
			logWarn("Nessuna risposta ReCaptcha ricevuta, impossibile validare l'input");
			return false;
		}

		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", userAgent);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + secret + "&response="
					+ gRecaptchaResponse;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//parse JSON response and return 'success' value
			JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();

			return jsonObject.getBoolean("success");
		} catch (Exception e) {
			logError("Si è verificato un errore durante la verifica del captcha: {}", e.getMessage(), e);
			return false;
		}
	}

}