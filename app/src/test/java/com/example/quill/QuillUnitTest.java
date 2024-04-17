package com.example.quill;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.quill.database.entities.User;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class QuillUnitTest {

    User defaultUser;
    String defaultUserName = "defaultUserName";
    String defaultPassword = "defaultPassword";


    @Before
    public void setup(){
        defaultUser = new User(defaultUserName,defaultPassword);
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void userTest(){
        defaultUser = null;
        assertNull(defaultUser);
        defaultUser=  new User("testUserName","testPassWord");
        assertNotNull(defaultUser);
    }

    @Test
    public void getUserName(){
       assertEquals(defaultUserName, defaultUser.getUsername());
    }

}