package com.project.stickhero;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

class TestRunner{
    public static void main(String[] args) {
        Result result= JUnitCore.runClasses(ForJunit.class);
        for(Failure failure: result.getFailures()){
            System.out.println(failure.toString());
        }
    }
}

public class ForJunit {
    @Test
    public void test(){
        int a=2;
        assertEquals(a,Data.heartCounter);


    }


}
