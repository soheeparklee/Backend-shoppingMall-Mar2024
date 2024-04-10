package org.shoppingMall.config.security;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {

    @Test
    void jasypt(){
        String url= "jdbc:mariadb://localhost:3306/BackEndProject_2_verSoh";
//        String url= "jdbc:mariadb://localhost:3306/BackEndProject_2_verSoh?useUnicode=true&characterEncoding=UTF-8\n";
        String username= "root";
        String password= "cesarpoo";

        String encryptUrl= jasyptEncrypt(url);
        String encryptUsername= jasyptEncrypt(username);
        String encryptPassword= jasyptEncrypt(password);

        System.out.println("encryptUrl : " + encryptUrl);
        System.out.println("encryptUrl : " + encryptUsername);
        System.out.println("encryptPassword : " + encryptPassword);

        Assertions.assertThat(url).isEqualTo(jasyptDecrypt(encryptUrl));
    }

    private String jasyptEncrypt(String input){
        String key= "cesarpoo";
        StandardPBEStringEncryptor encryptor= new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.encrypt(input);
    }

    private String jasyptDecrypt(String input){
        String key= "cesarpoo";
        StandardPBEStringEncryptor encryptor= new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }


}
