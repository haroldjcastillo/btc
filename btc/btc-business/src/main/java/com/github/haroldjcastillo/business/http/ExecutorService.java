package com.github.haroldjcastillo.business.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import com.github.haroldjcastillo.business.config.HttpResponse;

public class ExecutorService {

	final static CloseableHttpClient client = HttpConnector.INSTANCE.getHttpClient();

	public static final HttpResponse get(final String uri) throws IOException {

		final HttpResponse httpResponse = new HttpResponse();
		final HttpContext context = HttpClientContext.create();
		final HttpGet httpget = new HttpGet(uri);
		final CloseableHttpResponse response = client.execute(httpget, context);

		try {
			final HttpEntity entity = response.getEntity();
			try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
				final byte[] buffer = new byte[0xFFFF];
				for (int len; (len = entity.getContent().read(buffer)) != -1;)
					os.write(buffer, 0, len);
				os.flush();
				byte[] content = os.toByteArray();
				httpResponse.setCode(response.getStatusLine().getStatusCode());
				httpResponse.setMessage(response.getStatusLine().getReasonPhrase());
				httpResponse.setContent(content);
				httpResponse.setContentType(entity.getContentType().getValue());
			}
		} finally {
			response.close();
		}

		return httpResponse;
	}

}
