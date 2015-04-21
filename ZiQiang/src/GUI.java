

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI extends JFrame implements ActionListener,ListSelectionListener{
	private JButton start = new JButton("start");
	private JList list = new JList();
	private JPanel left = new JPanel();
	private JPanel right = new JPanel();
	private JTextArea article = new JTextArea();
	private ArrayList<ZiQiang> results = null; 
//	private static CountDownLatch countDownLatch = new CountDownLatch(threadCount);
	private class WindowCloser extends WindowAdapter{
		public void windowClosing(WindowEvent we){
			System.exit(0);
		}
	}
	
	public GUI(){
		super("ziqiang");
		setSize(1000,1000);
		setup();
		addWindowListener(new WindowCloser());
		list.addListSelectionListener(this);
		pack();
		start.addActionListener(this);
//		list.addActionListener(this);
//		list.addMouseListener(this);
	
		show();
		
	}
	private void setup(){
//		list.setFixedCellWidth(500);
		list.setAutoscrolls(true);
		article.setAlignmentX(CENTER_ALIGNMENT);
		setLayout(new FlowLayout());
//		list.set
//		list.setMaximumSize(getMaximumSize());
		left.setLayout(new BorderLayout());
		left.add("North",start);
		left.add("Center",list);
		right.add(article);
//		setLayout(new GridLayout(2,1));
		add(left);
		add(right);
		article.setText("hehehahoqiao");
		
			
		
	}
	private void changedGUI(){
		
	}
	private void getZQ(){
		String url = "http://www.ziqiang.net";
	       //init the static content
	    StringBuffer content = ContentGetter.getAllContent(url);
	       //get the results
	    ArrayList<ZiQiang> myZiQiang = ContentGetter.getZQAllUrl();
	       //
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == start){
			ContentGetter.getInfo();
			results = ContentGetter.getAllResults();
//			ArrayList<ZiQiang> c = ContentGetter.allResults;
			 try {
					ContentGetter.countDownLatch.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			for(int i = 0;i <= results.size();i++){
//				ZiQiang currentZQ = results.get(i);
//				if(!("nothing".equals(currentZQ.getTitle())))
//					list.add(currentZQ.getTitle());
//			}
			DefaultListModel m=new DefaultListModel();
//			int size = results.size();
			for(int i = 0;i < results.size();i++){
				ZiQiang currentZQ = results.get(i);
				if(!("nothing".equals(currentZQ.getTitle())))
					m.addElement(currentZQ.getTitle());
			}
			list.setModel(m);
			
		}
		else if(ae.getSource() == list){
			
		}
	}
	
	public static void main(String[] args){
		GUI gui = new GUI();
	}
	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
//		int index = e.getFirstIndex();
//		int firstIndex = e.getFirstIndex();
//		int lastIndex = e.getLastIndex();
		String content = null;
		String selected = list.getSelectedValue().toString();
		for(int i = 0;i<results.size();i++){
			if(results.get(i).getTitle().equals(selected)){
				content = "梗概：\n"+results.get(i).getSummary()+"\n\n\n\n"+results.get(i).getContent();
			}
		}
		article.setText(content);
//		String content = "梗概：\n"+results.get(index).getSummary()+"\n\n\n\n"+results.get(index).getContent();
//		article.setText(content);
	}
	
	

}
