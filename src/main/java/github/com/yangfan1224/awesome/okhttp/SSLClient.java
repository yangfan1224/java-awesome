package github.com.yangfan1224.awesome.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class SSLClient {
    private  static OkHttpClient okHttpClient = new OkHttpClient();

    private  static OkHttpClient client = okHttpClient.newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public static OkHttpClient trustAllSslClient(OkHttpClient client, SSLSocketFactory trustAllSslSocketFactory,
                                                 X509TrustManager x509TrustManager) {

        OkHttpClient.Builder builder = client.newBuilder();
        builder.sslSocketFactory(trustAllSslSocketFactory, x509TrustManager);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return builder.build();
    }

    public static void main(String [] args) {
        // Load CAs from an InputStream
// (could be from a resource or ByteArrayInputStream or ...)
        CertificateFactory cf = null;
        Certificate ca;
        InputStream caInput = null;
        InputStream keyStoreInput = null;
        KeyStore keyStore = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            caInput = new BufferedInputStream(new FileInputStream("server/server.cert"));
            ca = cf.generateCertificate(caInput);
            System.out.println("CA= " + ((X509Certificate) ca).getSubjectDN());
            String keyStoreType = KeyStore.getDefaultType();
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStoreInput = new BufferedInputStream(new FileInputStream("server/server.jks"));
            keyStore.load(keyStoreInput, "123456".toCharArray());
            keyStore.setCertificateEntry("ca", ca);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                caInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

// Create a KeyStore containing our trusted CAs

        try {
            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, null);
            X509TrustManager x509TrustManager = (X509TrustManager) trustManagers[0];
            OkHttpClient sslClient = trustAllSslClient(client, sslContext.getSocketFactory(), x509TrustManager);
            String url = "https://foxth.com:8443/v1/accounts/sms/code/+8618627992565";
            Request request = new Request.Builder().url(url).header("X-Forwarded-For","192.168.0.108").get().build();
            try (Response response = sslClient.newCall(request).execute()) {
                System.out.println(response.code());
                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            if (keyStoreInput != null) {
                try {
                    keyStoreInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
