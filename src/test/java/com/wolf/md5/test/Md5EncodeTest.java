package com.wolf.md5.test;


import com.wolf.bean.WFMd5PasswordEncoder;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5EncodeTest {


    @Test
    public  void testMd5Encode(){
        PasswordEncoder passwordEncoder = new WFMd5PasswordEncoder();
        System.out.println("----" + passwordEncoder.encode("123456") + "----");
    }


}
