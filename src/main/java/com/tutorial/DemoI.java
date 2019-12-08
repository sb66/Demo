package com.tutorial;

import java.io.IOException;

public interface DemoI {
    boolean test(String message, int size) throws IOException;

    void  display(String message, int size) throws IOException;

    void display();
}
