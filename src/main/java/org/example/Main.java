package org.example;

import View.AppView;
import model.Items;

import java.io.IOException;

import static model.App.AllFromDisplayNames;


public class Main {
    public static void main(String[] args) throws IOException {
        (new AppView()).run();
    }
}