import java.util.*;
import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.regex.*;
import java.net.MalformedURLException;
import java.io.IOException;
// Testing
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.nio.charset.Charset;
// Testing


public class FinalProject {
  public static void main(String[] args) throws MalformedURLException, IOException{
    boolean appRun = true;
    Deque<String> email = new ArrayDeque<String>();
    Deque<String> unvisitedLinks = new ArrayDeque<String>();
    Deque<String> tomatometer = new ArrayDeque<String>();
    
    int arrayRun = 1;
    //Scanner userURL = new Scanner(System.in);
    //System.out.println("Enter a URL: ");
    //unvisitedLinks.add(userURL.next());
    
    // Testing
    HttpURLConnection connection = null;
    System.out.println("Enter a movie to search:");
    Scanner userSearch = new Scanner(System.in);
    String temp = userSearch.nextLine();
    temp = temp.replaceAll(" ","-");
    String googleSearch = "http://www.google.com/search?q=" + temp + "-movie-reviews";
    
    Document doc = Jsoup.connect(googleSearch).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11").get();
    Element links = doc.select("a[href*=/m/]").first();
    Element metaLinks = doc.select("a[href*=metacritic.com/movie/]").first();
    Element ebertLinks = doc.select("a[href*=rogerebert.com/reviews/]").first();
    Element avLinks = doc.select("a[href*=film.avclub.com/]").first();
    Element empireLinks = doc.select("a[href*=empireonline.com/movies/]").first();
    Element slantLinks = doc.select("a[href*=slantmagazine.com/film/review/").first();
    
    String linkString = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"; //http://stackoverflow.com/questions/163360/regular-expression-to-match-urls-in-java
    Pattern linkPattern = Pattern.compile(linkString, Pattern.CASE_INSENSITIVE);
    Matcher linkMatch;
    String rottenLink = null;
    
    if(links != null){
    rottenLink = links.attr("abs:href"); // Rotten Tomatoes Link
    linkMatch = linkPattern.matcher(rottenLink);
    if(linkMatch.find()){
      System.out.println(rottenLink);
      doc = Jsoup.connect(rottenLink).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11").get();
      Element rottenScore = doc.select("#tomato_meter_link > span.meter-value.superPageFontColor > span").first();
      String rottenRating = rottenScore.html();
      System.out.println(rottenRating);
    }
    }
    
    if(metaLinks != null){
    String metacriticLink = metaLinks.attr("abs:href"); // Metacritic Link
    linkMatch = linkPattern.matcher(metacriticLink);
    if(linkMatch.find()){
      System.out.println(metacriticLink);
      doc = Jsoup.connect(metacriticLink).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11").get();
      Element metaScore = doc.select("#nav_to_metascore > div:nth-child(2) > div.distribution > div.score.fl > a > div").first();
      String metaRating = metaScore.html();
      System.out.println(metaRating);
    }
    }
    
    if(ebertLinks != null){
    String ebertLink = ebertLinks.attr("abs:href"); // Roger Ebert Link
    linkMatch = linkPattern.matcher(ebertLink);
    if(linkMatch.find()){
    System.out.println(ebertLink);
    doc = Jsoup.connect(ebertLink).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11").get();
    Element ebertScore = doc.select("#review > div.wrapper > div > section > article > header > p > span > span").first();
    String ebertRating = ebertScore.html();
    ebertRating = ebertRating.replaceAll("<i class=\"icon-star-full\"></i>","★"); // ? = full star
    ebertRating = ebertRating.replaceAll("<i class=\"icon-star-half\"></i>","½"); // ! = half a star
    System.out.println(ebertRating);
    }
    }
    
    //String avLink = avLinks.attr("abs:href"); // AV Club Link
    //System.out.println(avLink);
    
    if(empireLinks != null){
    String empireLink = empireLinks.attr("abs:href"); // Empire Link
    linkMatch = linkPattern.matcher(empireLink);
    if(linkMatch.find()){
    System.out.println(empireLink);
    doc = Jsoup.connect(empireLink).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11").get();
    Elements empireScore = doc.select("body > main > article > div > div > div.epsilon.col.w-2-3.article__body > div.no-marg.subtitle > div > span"); // using selector syntax
    String empireRating = empireScore.html();
    System.out.println(empireRating); // ratings gonna be ? which indicates a star for each ?
    }
    }
    
    //info pulling
    if(rottenLink != null){
    doc = Jsoup.connect(rottenLink).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11").get();
    
    Element titlePull = doc.select("#movie-title").first();
    String title = titlePull.text();
    System.out.println(title);
    
    Elements summaryPull = doc.select("#movieSynopsis");
    String summary = summaryPull.html();
    System.out.println();
    System.out.println("Movie Info");
    System.out.println(summary);
    System.out.println();
    
    
    Elements mpaaRating = doc.select("#mainColumn > section.panel.panel-rt.panel-box.movie_info.media > div > div.panel-body.content_body > ul > li:nth-child(1) > div.meta-value");
    String rated = "Rated: " + mpaaRating.html();
    System.out.println(rated);
    
    Elements genrePull = doc.select("#mainColumn > section.panel.panel-rt.panel-box.movie_info.media > div > div.panel-body.content_body > ul > li:nth-child(2) > div.meta-value");
    String genre = "Genre: " + genrePull.text();
    System.out.println(genre);
    
    Elements directorPull = doc.select("#mainColumn > section.panel.panel-rt.panel-box.movie_info.media > div > div.panel-body.content_body > ul > li:nth-child(3) > div.meta-value");
    String director = "Directed By: " + directorPull.text();
    System.out.println(director);
    
    Elements writerPull = doc.select("#mainColumn > section.panel.panel-rt.panel-box.movie_info.media > div > div.panel-body.content_body > ul > li:nth-child(4) > div.meta-value");
    String writer = "Written By: " + writerPull.text();
    System.out.println(writer);
    
    Elements releasePull = doc.select("#mainColumn > section.panel.panel-rt.panel-box.movie_info.media > div > div.panel-body.content_body > ul > li.meta-row.clearfix.js-theater-release-dates > div.meta-value > time");
    String release = "Released: " + releasePull.html();
    System.out.println(release);
    }
    
    
    /*
    String link = unvisitedLinks.peek();
    InputStream url = null;
    URL myURL = new URL(link);
    String pageToken = null;
    String rottenScore = "superPageFontColor\"><span>[0-9][0-9]</span>"; //rotten tomatoes
    String patternString = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"; //http://stackoverflow.com/questions/8204680/java-regex-email
    String linkString = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"; //http://stackoverflow.com/questions/163360/regular-expression-to-match-urls-in-java
    Pattern scorePattern = Pattern.compile(rottenScore, Pattern.CASE_INSENSITIVE); //tomatoes
    Pattern href = Pattern.compile("\"([^\"]*)\""); //http://stackoverflow.com/questions/1473155/how-to-get-data-between-quotes-in-java
    Scanner urlReader = new Scanner(myURL.openStream());
    
    while(arrayRun == 1){
    while(urlReader.hasNext()){
      pageToken = urlReader.next();
      Matcher scoreMatch = scorePattern.matcher(pageToken);

      if(scoreMatch.find()) // tomatoes
      {
        tomatometer.add(pageToken);
      }
  }
    arrayRun = 0;
    
    
    System.out.println();

    System.out.println("Tomatometer: ");
    System.out.println(tomatometer);
    // [superPageFontColor"><span>94</span>%</span> Movie: Blue Velvet
    // [superPageFontColor"><span>88</span>%</span> Movie: Titanic
}*/
  }
private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}