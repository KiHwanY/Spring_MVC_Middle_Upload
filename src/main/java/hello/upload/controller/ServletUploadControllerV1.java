package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v1")
public class ServletUploadControllerV1 {

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        log.info("request={}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName={}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts={}", parts);
//      request.getParts(): multipart/form-data 전송 방식에서 각각 나누어진 부분을 받아서 확인할 수 있다.

        return "upload-form";
//      실행해보면 logging.level.org.apache.coyote.http11 옵션을 통한 로그에서
//      multipart/form-data 방식으로 전송된 것을 확인할 수 있다.
    }

//    결과 로그
//    Content-Type: multipart/form-data; boundary=----xxxx
//------xxxx
//    Content-Disposition: form-data; name="itemName"
//    Spring
//------xxxx
//    Content-Disposition: form-data; name="file"; filename="test.data"
//    Content-Type: application/octet-stream
//    sdklajkljdf...

//    [멀티파트 사용 옵션]
//
//    업로드 사이즈 제한
//    spring.servlet.multipart.max-file-size=1MB
//    spring.servlet.multipart.max-request-size=10MB
//
//    큰 파일을 무제한 업로드하게 둘 수는 없으므로 업로드 사이즈를 제한할 수 있다.
//    사이즈를 넘으면 예외(SizeLimitExceededException)가 발생한다.
//    max-file-size : 파일 하나의 최대 사이즈, 기본 1MB
//    max-request-size : 멀티파트 요청 하나에 여러 파일을 업로드 할 수 있는데, 그 전체 합이다. 기본 10MB
//
    /*
    *   [spring.servlet.multipart.enabled 끄기]
        spring.servlet.multipart.enabled=false
        *
        [결과 로그]
        request=org.apache.catalina.connector.RequestFacade@xxx
        itemName=null
        parts=[]
        *
        *
        멀티파트는 일반적인 폼 요청인 application/x-www-form-urlencoded 보다 훨씬 복잡하다.
        spring.servlet.multipart.enabled 옵션을 끄면 서블릿 컨테이너는 멀티파트와 관련된 처리를 하지
        않는다.
        그래서 결과 로그를 보면 request.getParameter("itemName") , request.getParts() 의 결과가
        비어있다.
        *
        [spring.servlet.multipart.enabled 켜기]
        spring.servlet.multipart.enabled=true (기본 true)
        *
        이 옵션을 켜면 스프링 부트는 서블릿 컨테이너에게 멀티파트 데이터를 처리하라고 설정한다. 참고로 기본
        값은 true 이다.
        *
        request=org.springframework.web.multipart.support.StandardMultipartHttpServletR
        equest
        itemName=Spring
        parts=[ApplicationPart1, ApplicationPart2]
        *
        request.getParameter("itemName") 의 결과도 잘 출력되고, request.getParts() 에도 요청한 두
        가지 멀티파트의 부분 데이터가 포함된 것을 확인할 수 있다. 이 옵션을 켜면 복잡한 멀티파트 요청을
        처리해서 사용할 수 있게 제공한다.
        *
        로그를 보면 HttpServletRequest 객체가 RequestFacade
        StandardMultipartHttpServletRequest 로 변한 것을 확인할 수 있다.
    * 
    * */
}
