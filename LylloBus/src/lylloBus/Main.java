package lylloBus;

import javax.swing.JFrame;

import controllers.Controller;
import models.Model;
import views.View;

public class Main extends JFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(model, controller);
	}

}
