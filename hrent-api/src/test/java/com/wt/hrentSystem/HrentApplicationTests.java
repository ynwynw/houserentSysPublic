package com.wt.hrentSystem;

import com.wt.hrentSystem.common.entity.User;
import com.wt.hrentSystem.common.service.UserService;
import com.wt.hrentSystem.common.util.MD5Utils;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.hrent.entity.Tag;
import com.wt.hrentSystem.hrent.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
public class HrentApplicationTests {

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Test
    void contextLoads() {
        try {
            System.out.println(MD5Utils.EncoderByMd5("123456"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findUser(){

    }

}
