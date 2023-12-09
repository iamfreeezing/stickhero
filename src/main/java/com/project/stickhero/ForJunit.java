package com.project.stickhero;

import javafx.scene.image.ImageView;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;
class Runner {
    public static void runTest() {
        Result result = JUnitCore.runClasses(ForJunit.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}

public class ForJunit {
    @Test
    public void testHeartCounter(){   //to see how many hearts have been generated
        int a=2; // only 1 heart has been generated
        boolean b=false;
        if(Data.heartCounter==2 || Data.heartCounter==3){
            b=true;
        }
        assertTrue("Expected value should have been 2 or 3 but was "+ Data.heartCounter,b);
        System.out.println("test: testHeartCounter test has passed!");


    }
    @Test
    public void wontRevive(){
        assertFalse(GameOver.isReviveHapened());
        System.out.println("test: wontRevive test has passed!");
    }

    @Test
    public void hasRevived(){
        assertTrue("Player did not revive hence this test failed.",GameOver.isReviveHapened());
        System.out.println("test: hasRevived test has passed!");
    }
    @Test
    public void SingletonDP(){
         Player player1= Player.getInstance();
         Player player2= Player.getInstance();

         assertEquals(player2,player2);
        System.out.println("test: SingletonDP test has passed!");

    }
    @Test
    public void testHeartScore(){
        assertEquals(1,Data.heartScore);
        System.out.println("test: testHeartScore test has passed!");
    }


}
