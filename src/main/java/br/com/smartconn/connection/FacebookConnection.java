package br.com.smartconn.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.smartconn.Profile;
import br.com.smartconn.mine.Notification;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Henrique Nascimento
 *
 * @version 1.0.0
 */
public class FacebookConnection {

    private final String login;
    private final String password;

    public FacebookConnection(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Profile connectAndRetrieveProfile() throws IOException {

        Connection.Response response;

        final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";

        response = Jsoup.connect("https://m.facebook.com/login/async/?refsrc=https%3A%2F%2Fm.facebook.com%2F&lwv=101")
                .userAgent(userAgent)
                .method(Connection.Method.POST).data("email", this.login).data("pass", this.password)
                .followRedirects(true).execute();

        Map<String, String> cookies = response.cookies();



        Document docHome = Jsoup.connect("https://m.facebook.com/home.php")
                .userAgent(userAgent)
                .cookies(cookies).get();

        Profile profile = new Profile();

        // setName
        String name = docHome.body().getElementsByClass("_36e0").get(0).text();
        profile.setName(name);
//
//        Elements ellles = doc.body().select("div#MNotificationFlyoutContent").get(0).children().get(0).children();
//        Elements els = docHome.body().getElementsByAttribute("data-store").get(5).children().get(1).children().get(0).children().get(0).children();
//        Elements elsio = docHome.body().getElementsByClass("_747.inner");
//        List<Notification> notifications = new ArrayList<Notification>();
//
//
//        Elements datas = doc.body().getElementsByClass("_7k7 inner");
//        Elements elements = docHome.body().getAllElements();
//        Elements ell = doc.body().select("ol._7k7.inner");
//
//
//        els.get(5).children().forEach(children -> {
//            children.getElementsByClass("c").forEach(n -> {
//                Notification not = new Notification();
//                not.setWhen(n.getElementsByClass("mfss").get(0).text());
//                notifications.add(not);
//                not.setBody(n.text());
//            });
//        });

//        Elements els = docHome.body().getElementsByAttribute("data-store");
//        List<Notification> notifications = new ArrayList<>();
//
//        //        profile.setName(docHome.body().getElementsByClass("_52ja").get(0).text());
//        els.get(6).children().forEach(children -> {
//            children.getElementsByClass("c").forEach(n -> {
//                Notification not = new Notification();
//                not.setWhen(n.getElementsByClass("mfss").get(0).text());
//                notifications.add(not);
//                not.setBody(n.text());
//            });
//        });


        // henrique

        Document docNotifications = Jsoup.connect("https://m.facebook.com/notifications")
                .userAgent(userAgent)
                .cookies(cookies).get();

        Elements elements = docNotifications.getElementsByClass("_55x2");
        Elements ele = elements.get(0).children();

        List<Notification> notifications = new ArrayList<>();

        for (Element element: ele) {
            if (element == ele.get(0))
                continue;

            Notification not = new Notification();
            try{
                not.setBody(element.getElementsByClass("c").get(0).children().get(0).text());
                not.setWhen(element.getElementsByClass("mfss").text());
            }catch (Exception e){
                continue;
            }

            notifications.add(not);
        }

        String txtNot = ele.get(1).getElementsByClass("c").get(0).children().get(0).text();
        String txtwhen = ele.get(1).getElementsByClass("mfss").text();
        String txtEle = ele.get(1).text();


        profile.setNotifications(notifications);
        return profile;
    }

}
