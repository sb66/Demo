package com.tutorial;

import com.tutorial.DemoI;

import java.io.IOException;

public class NoOpDemo implements DemoI {
    public boolean test(String message, int size) throws IOException {return false;};

    public void  display(String message, int size) throws IOException {};

    public void display() {};
}
