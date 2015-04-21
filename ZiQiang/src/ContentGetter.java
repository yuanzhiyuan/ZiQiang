


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yuanHao
 */
public class ContentGetter {
	
//	private static boolean []lock = {true,true,true};
	//there are three child thread
	private static int threadCount = 3;
	//the counter is used to block the main thread
	public static CountDownLatch countDownLatch = new CountDownLatch(threadCount);
    public static StringBuffer content = new StringBuffer();
    private static final String ZQ_NEWS_URL = "http://news.ziqiang.net/article";
    private static final String ZQ_MIAO_URL = "http://miao.ziqiang.net/article";
    //private static final String ZQ_EDU_URL  = "http://edu.ziqiang.net/drama";
    //private static final String ZQ_BBS_URL  = "http://bbs.ziqiang.net/forum.php?";
    private static final String ZQ_XGB_URL  = "http://xgb.whu.edu.cn/article";
    public static ArrayList<ZiQiang> allResults = new ArrayList<ZiQiang>();
    public static ArrayList<ZiQiang> getAllResults(){
    	return allResults;
    }
    /*public ContentGetter(){
        this.content = getAllContent("http://www.ziqiang.net");
    }*/
    /*public ContentGetter(String url){
        this.content = getAllContent(url);
    }*/
    //the method is used to get all content of a url
    public static StringBuffer getAllContent(String url) {
    	//use for store the content of url
    	System.out.println("start to get content of"+url);
    	long start = new Date().getTime();
        StringBuffer contentxxx = new StringBuffer();
        BufferedReader in = null;
        try {
        	//create a URL object
            URL theUrl = new URL(url);
            //create a connection with the url
            URLConnection connection = theUrl.openConnection();
            //set timeout 3s
            connection.setReadTimeout(3000);
          //connect
            connection.connect();
            //buffer the input stream with utf-8
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            //loop the line of buffer
            String line = new String();
            while((line=in.readLine()) != null){
                contentxxx.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{//close the buffer
            try{
                if(in != null){
                    in.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        long end = new Date().getTime();
        long total = end - start;
        System.out.println("end to get content of "+url+" time:"+total/1000);
        return contentxxx;
    }
    //the method is used to get a ziqiang object
    private static ArrayList<ZiQiang> getZQSomeUrl(String content, String patternStr, String some){
    	System.out.println("start to get "+some+"url");
    	long start = new Date().getTime();
    	//init a ZiQiang to store the return  
        ArrayList<ZiQiang> results = new ArrayList<ZiQiang>(); 
        //create a pattern
        Pattern pattern = Pattern.compile(patternStr);
        //create a matcher
        Matcher matcher = pattern.matcher(content);
        //create a bool to judge if one single match is success
        boolean isFind = matcher.find();
        //loop until is the match is fail
        while(isFind){
        	//get all matched links of the page
        	//matcher.group(0) is the whole string 
        	//matcher.group(1) is the string in the ()
            results.add(new ZiQiang(some+matcher.group(1)));
            //everytime it runs, the match point goes forward a step
            isFind = matcher.find();
            
        }
        long end = new Date().getTime();
        long total = end - start;
        System.out.println("end to get "+some+" url"+ " time: "+total/1000);
        
        return results;
    }
    //this method is used to get all ZiQiang
    public static ArrayList<ZiQiang> getZQAllUrl(){
//        String theContent = new String(content);
        //allResults is used to store all results
//        ArrayList<ZiQiang> allResults = new ArrayList<ZiQiang>();
        //匹配自强新闻板块
//        String patternNewsStr = "<a" + ".+?" + "href=\"" + ZQ_NEWS_URL + "(/\\d+/)" + "\"" + ".+?>";
       //匹配自强毛线社区和原创武大板块
//        String patternMiaoStr = "<a" + ".+?" + "href=\"" + ZQ_MIAO_URL + "(/\\d+/)" + "\"" + ".+?>";
        //匹配学霸天下板块
        //String patternEduStr =  "<a" + ".+?" + "href=\"" + ZQ_EDU_URL + "(/\\d+/)" + "\"" + ".+?>";
        //匹配自强茶馆模块
        //String patternBbsStr =  "<a" + ".+?" + "href=\"" + ZQ_BBS_URL + "(.+?)" + "\"" + ".+?>";
        //匹配自强学院思政板块
//        String patternXgbStr =  "<a" + ".+?" + "href=\"" + ZQ_XGB_URL + "(/\\d+)" + "\"" + ".+?>";
    	//the first thread used for ziqiang news
    	//it is a inner class
        new Thread(new Runnable(){
        //调用匹配函数，结果加入allResult
        //add news channel
        public void run(){
        	//pattern for news url
        	String patternNewsStr = "<a" + ".+?" + "href=\"" + ZQ_NEWS_URL + "(/\\d+/)" + "\"" + ".+?>";
        	//add all news ziqiang into allResults
        	allResults.addAll(getZQSomeUrl(new String(content),patternNewsStr,ZQ_NEWS_URL));
        	//counter decrease one
        	countDownLatch.countDown();
        	}
        }).start();
        //the second thread
        new Thread(new Runnable(){
        	public void run(){
        		//pattern for miao
        		 String patternMiaoStr = "<a" + ".+?" + "href=\"" + ZQ_MIAO_URL + "(/\\d+/)" + "\"" + ".+?>";
        		 //add all miao ziqiang into allResults
        		 allResults.addAll(getZQSomeUrl(new String(content),patternMiaoStr,ZQ_MIAO_URL));
        		//counter decrease one
        		countDownLatch.countDown();
        	}
        }).start();
        //the third thread
        new Thread(new Runnable(){
        	
        	
        	
        	public void run(){
        		//pattern for xgb url
        		String patternXgbStr =  "<a" + ".+?" + "href=\"" + ZQ_XGB_URL + "(/\\d+)" + "\"" + ".+?>";
        		//add the xgb ziqiang into allResults
        		allResults.addAll(getZQSomeUrl(new String(content),patternXgbStr,ZQ_XGB_URL));
        		//counter decrease one
        		countDownLatch.countDown();
        		}
        	}).start();
        return allResults;
        
        
        
        
    }
    public static void getInfo(){
    	String url = "http://www.ziqiang.net";
        //init the static content
        content = ContentGetter.getAllContent(url);
        //get the results
        ContentGetter.getZQAllUrl();
        //
        
    	
    }
    public static void main(String args[]){
    	long start = new Date().getTime();
    	//the target url
//       String url = "http://www.ziqiang.net";
//       //init the static content
//       content = ContentGetter.getAllContent(url);
//       //get the results
//       ArrayList<ZiQiang> myZiQiang = ContentGetter.getZQAllUrl();
//       //
    	getInfo();
       
       //block the main thread 
       //If I don't block the main thread,the main thread will go along with the three child thread, 
       //there will be no ZiQiang object before it write into disk.
       try {
		countDownLatch.await();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     //write to your hard disk
       for(ZiQiang ziqiang : allResults){
    	   System.out.println("writing "+ziqiang.getTitle()+"into hard disk");
           String fileContent = "梗概：\n"+ziqiang.getSummary()+"\n\n\n\n"+ziqiang.getContent();
           MyWriter.writeIntoFile("C:/hehe/"+ziqiang.getTitle()+".txt",fileContent, false);
           System.out.println("end writing "+ziqiang.getTitle()+"into hard disk");
       }
       long end = new Date().getTime();
       long total = end - start;
       System.out.println("-------------------------------------------------------------");
       System.out.println("totle time:"+total/1000);
       //write to the mysql db 
       //MyWriter.writeIntoDatabase(myZiQiang, "root", "002899", "test", "ziqiang");
      
       //Class.forName("com.mysql.jdbc.Driver").newInstance();
    }

}
