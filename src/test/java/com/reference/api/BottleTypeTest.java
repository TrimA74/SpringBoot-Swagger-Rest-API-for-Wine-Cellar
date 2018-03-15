package com.reference.api;

import com.reference.api.models.BottleType;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Test;

import javax.naming.Name;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class BottleTypeTest {

    @Test
    public void should_200_On_Existing_BottleType() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + 8080 + "/bottletype/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void should_200_On_BottleType_To_Validate() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + 8080 + "/bottletype/getBottleToValidate").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void should_200_On_Updating_BottleType() throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + 8080 + "/bottletype/update/").toURI());
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        BottleType bottleType = new BottleType("Bordeaux inférieur", false);
        bottleType.id((long) 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(bottleType);

        request.setEntity(new StringEntity(json));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }
}
