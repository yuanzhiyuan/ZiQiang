

import javax.swing.*;

public class textarea extends JFrame {
	private JTextArea article = new JTextArea();
	public textarea(){
		add(article);
		setSize(500,500);
		show();
		article.setText("asfasfasfasfasfasgagsdgsdgsdhdhjdrhdrjdrgsegeshdfgsdgs");
		article.setAlignmentX(250);
		article.setAlignmentY(250);
		
		article.setLocation(200, 200);
	}
	public static void main(String[] argc){
		new textarea();
	}
	
}
