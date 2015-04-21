


import java.util.ArrayList;
import java.util.Date;
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


//the class is used to describe a ziqiang article
public class ZiQiang {
	//the url of an article
    private String url = new String();
    //the title of an article
    private String title = new String();
    //the summary of an article
    private String summary = new String();
    //the content of an article
    private String content = new String();
    //the html of an article page
    private StringBuffer htmlContent = new StringBuffer();
    //get the url
    public String getUrl(){
        return url;
    }
    //get the title
    public String getTitle(){
        return title;
    }
    //get the summary
    public String getSummary(){
        return summary;
    }
    //get the content
    public String getContent(){
        return content;
    }
    //get the html
    public StringBuffer getHtmlContent(){
        return htmlContent;
    }
    
    //use the url to init a object
    public ZiQiang(String url) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       System.out.println("creating a ZiQiang object url is"+url);
       long start = new Date().getTime();
    	this.url = url;
       //url is not null
       if(url != null){
    	   //use the method in ContentGetter to get the htmlContent
           this.htmlContent = ContentGetter.getAllContent(this.url);
           //there are two different pattern in all the ZiQiang channel, so I use two different pattern
           //to match the information
           //pattern for a title
           String articletitlePattern1 = "<h1 id=\"articletitle\">" + "(.+?)" + "</h1>";
           //pattern for a summary
           String summaryPattern1 = "<div id=\"summary\">" + "(.+?)" + "</div>";
           //pattern for a content
           String contentPattern1 = "<div class=\"content\">" + "(.+?)" + "</div>";
           //pattern for another title
           String articletitlePattern2 = "<div class=\"newstitle\">(.+?)</div>";
           //pattern for another summary
           String summaryPattern2 = "<div class=\"navcontent\">(.+?)</div>";
           //pattern for another content
           String contentPattern2 = "<div class=\"newscontent\">(.+?)</div>";
           
          /* ArrayList<String> titlePattern = new ArrayList<String>();
           ArrayList<String> summaryPattern = new ArrayList<String>();
           ArrayList<String> contentPattern = new ArrayList<String>();
           */
           //get the title with method catchTarget
           String title1 = catchTarget(articletitlePattern1);
         //get another title with method catchTarget
           String title2 = catchTarget(articletitlePattern2);
         //get the summary with method catchTarget
           String summary1 = catchTarget(summaryPattern1);
         //get another summary with method catchTarget
           String summary2 = catchTarget(summaryPattern2);
         //get the content with method catchTarget
           String content1 = catchTarget(contentPattern1);
         //get another content with method catchTarget
           String content2 = catchTarget(contentPattern2);
           //there must be at least a nothing in the two title
           //I want the one which is not nothing
           //get the right title
           this.title = getClear("nothing".equals(title1)?title2:title1);
           //get the right summary
           this.summary = getClear("nothing".equals(summary1)?summary2:summary1);
           //get the right content
           this.content = getClear("nothing".equals(content1)?content2:content1);
           long end = new Date().getTime();
           long total = end - start;
           System.out.println("end creating a ZiQiang object url is "+url + "time: "+total/1000);
       }
    }
    //the output string of the object
    public String toString(){
        String result = new String();
        result += "\n";
        result += url;
        result += "\n";
        result += title;
        result += "\n";
        result += summary;
        result += "\n";
        result += content;
        return result;
    }
    //this method is used to catch information with given pattern 
    private String catchTarget(String target){
        
    	//store the result
        String result = new String();
        //create the pattern
        Pattern pattern = Pattern.compile(target);
        //create the matcher
        Matcher matcher = pattern.matcher(htmlContent);
        //System.out.println(ContentGetter.content);
        boolean isFind = matcher.find();
        result = "nothing";
        //get the matched information
        if(isFind){
            result = matcher.group(1);
        }
        
        return result;
        
    }
    //this method is used to clear the unnecessary information 
    //such as some html tags and some space
    private String getClear(String str){
        if(str != null){
        	//replace <p> with \n
            String clearedStr = str.replaceAll("</p>", "\n");
            //replace <br> with \n
            clearedStr = clearedStr.replaceAll("<br/>", "\n");
            //replace anything like &??; with " "
            clearedStr = clearedStr.replaceAll("&.*?;", " ");
           // clearedStr = clearedStr.replaceAll("&rdquo"," ");
            //clear other html tags
            clearedStr = clearedStr.replaceAll("<.*?>", "");
            return clearedStr;
        }
        //if the str is null return nothing
        return "nothing";
    }
    
}
