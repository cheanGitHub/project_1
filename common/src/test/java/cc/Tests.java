package cc;

import com.cc.httpClient.HttpClientHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Tests {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.cc.annoprocessor.Tests.class);

    public static void main(String[] args) {

    }

    @Test
    public void testHttpClientPost() {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("param1", "p1");
        urlParams.put("param2", "p2");

//        params.put("body", "{\n" +
//                "\t\"body1\" : \"b1\",\n" +
//                "\t\"name\" : \"name1\",\n" +
//                "\t\"password\" : \"password1\"\n" +
//                "}");

//        params.put("body1", "b1");
//        params.put("name", "name1");
//        params.put("password", "password1");

        String body = "{\n" +
                "\t\"body1\" : \"b1\",\n" +
                "\t\"name\" : \"name1\",\n" +
                "\t\"password\" : \"password1\"\n" +
                "}";


        Map<String, String> headers = new HashMap<>();
        headers.put("header1", "h1");
        headers.put("header2", "h2");
        headers.put("Content-Type", "application/json");


        String s = HttpClientHelper.httpClientPost("http://127.0.0.1:8080/hello", headers, urlParams, body, "UTF-8");
        System.out.println(s);
    }

    @Test
    public void testMuttiWord() {
        LOGGER.info("ret = " + doWork());
    }

    private String doWork() {
        LOGGER.info("start");

        new Thread(new MyRunnable()).start();

        return "bb";
    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            int n = 10;
            while (n > 0) {
                LOGGER.info("working = " + n--);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("working = end");
            }
        }
    }

}
