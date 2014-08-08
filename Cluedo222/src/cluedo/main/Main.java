package cluedo.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cluedo.ui.Board;
import cluedo.ui.CluedoFrame;

public class Main {

	public static void main(String[] args) {
		new CluedoFrame(new Board());
	}

}
