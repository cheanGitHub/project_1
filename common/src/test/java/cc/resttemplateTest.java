package cc;

import org.apache.commons.io.IOUtils;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class resttemplateTest {
    public static void main(String[] args) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        final String APPLICATION_PDF = "application/pdf";
        HttpHeaders headers = new HttpHeaders();


        try {
//            List list = new ArrayList<>();
//            list.add(MediaType.valueOf(APPLICATION_PDF));
//            headers.setAccept(list);
            System.out.println("111");
            InputStream result = restTemplate.execute("https://dlied4.myapp.com/myapp/1104466820/cos.release-40109/10040714_com.tencent.tmgp.sgame_a734461_1.53.1.10_wxvoiq.apk",
                    HttpMethod.GET, null, new ResponseExtractor<InputStream>() {
                        @Override
                        public InputStream extractData(ClientHttpResponse clientHttpResponse) throws IOException {
                            //File ret = File.createTempFile("download", "tmp");
                            //StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                            // return ret;

                            System.out.println("111 111");
                            InputStream body = clientHttpResponse.getBody();
                            System.out.println("111 2");

                            InputStream inputStream = null;
                            OutputStream outputStream = null;


                            File file = new File("D:\\JavaTools\\IdeaProjects\\project_1\\wz.apk");
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            outputStream = new FileOutputStream(file);
//                            int len = 0;
//                            byte[] buf = new byte[1024];
//                            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
//                                outputStream.write(buf, 0, len);
//                                System.out.println("333");
//                            }
//                            outputStream.flush();
//                            System.out.println("444");


                            IOUtils.copy(body, outputStream, 4 * 1024);



                            inputStream.close();
                            outputStream.close();

                            return body;
                        }
                    });

            System.out.println("111 111 111");


//            ResponseEntity<byte[]> response = restTemplate.exchange(
//                    url,
//                    HttpMethod.GET,
//                    new HttpEntity<byte[]>(headers),
//                    byte[].class);
//
//            byte[] result = response.getBody();
//
//            inputStream = new ByteArrayInputStream(result);

        } finally {
//            if (inputStream != null) {
//                inputStream.close();
//            }
//            if (outputStream != null) {
//                outputStream.close();
//            }
        }

    }


}
