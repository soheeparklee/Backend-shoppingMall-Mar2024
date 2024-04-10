package org.shoppingMall.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.shoppingMall.config.certification.EmailCertificationConfig.generateRandomNumber;

@Service
@RequiredArgsConstructor
public class EmailCertificationService {
    private final JavaMailSender mailSender;
//    private final RedisUtil redisUtil;

    private final int authNumber= generateRandomNumber(100000, 999999); // config 에 미리 만들어둔 메서드
    @Value("${email.address}")
    private String emailAddress; // email-config에 설정한 자신의 이메일 주소를 입력
    public String joinEmail(String email) {
        String setFrom= emailAddress;
        String toMail= email;
        String title= "[인증]OOO사이트 가입 인증번호";
        String content=
                "회원가입 창으로 돌아가 인증 번호를 정확히 입력해주세요." + 	//html 형식으로 작성 !
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" ; //이메일 내용 삽입
        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

    @Transactional
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message= mailSender.createMimeMessage(); //JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try{
            MimeMessageHelper helper= new MimeMessageHelper(message, true, "utf-8"); //이메일 메시지와 관련된 설정을 수행합니다.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom); //이메일의 발신자 주소 설정
            helper.setTo(toMail); //이메일의 수신자 주소 설정
            helper.setSubject(title); //이메일의 제목을 설정
            helper.setText(content, true);
            mailSender.send(message); //이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
        }catch(MessagingException e){ //이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            e.printStackTrace(); //e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드

        }
    }
}
