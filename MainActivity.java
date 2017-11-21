package jeongari.com.android_parsing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;


public class MainActivity extends AppCompatActivity {

    TextView textview;
    Button getActionBtn;
    Runnable task;
    String html;

    String givenUrl = "http://news.joins.com/article/22123706";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        textview = (TextView) findViewById(R.id.textview);
        getActionBtn = (Button) findViewById(R.id.getActionBtn);
        getActionBtn.setOnClickListener(mClickListener);


        task = new Runnable(){

            public void run(){

                html = getHtmltoText(givenUrl);

            }

        };

    }
    // 루프 하나에 대한 작업
    //

    // html파일을 순회하여 유효한 element를 만날 때마다 TextView를 동적 생성
    // tag마다 다르게 분기를 만들어서 다른 속성변환을 수행
    // 만든 Textview를 Layout에 추가


    View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            Thread thread = new Thread(task);
            thread.start();

            try{

                thread.join();  // 쓰레드 작업 끝날때까지 다른 작업들은 대기

            }catch(Exception e){



            }
            List<String> obj = Arrays.asList(html.split(","));
            textview.setText(obj.get(0));


        }
    };


    public String getHtmltoText(String sourceUrlString) {
        List<Element> contents = null;
        List<Element> contents2 = null;
        String content ="";
        String htmlContent = "";
        String temp = "";
        String temp2 = "";
        Element element = null;


        try {

            URL sUrl = new URL(sourceUrlString);
            InputStream is = sUrl.openStream();
            Source source= new Source(new InputStreamReader(is,"utf-8"));


            // 전체 소스 구문을 분석한다.
            source.fullSequentialParse();
            // HTML markup에서 text contents만 가져와서 스트링으로 변환한다.
            contents=source.getAllElements(HTMLElementName.BODY);
            htmlContent = source.getAllElements(HTMLElementName.BODY).toString();

            // article div 안의 태그들을 모두 contents List에 넣기
            contents = source.getAllElementsByClass("article").get(0).getAllElements();
            for (int i = 0 ; i < contents.size() ; i++)
            {
                // Element를 하나씩 보며 표현해야 할 텍스트인지 확인
                element = contents.get(i);
                // 주석 코드는 무시
                if (element.getStartTag().getName().equals("!--")) continue;
                // 속성을 하나도 갖지 않는 태그는 무시
                else if (element.getAttributes().isEmpty() == true) continue;
                // class 속성을 갖는 태그에 한하여 class 속성값 확인
                else if (element.getAttributeValue("class") != null) {
                    // 기사 제목
                    if (element.getAttributeValue("class").equals("subject")) {
                        content = content + element.getContent().toString() + ",";
                    }
                    // 기사 출처와 작성 날짜
                    else if(element.getAttributeValue("class").equals("source_date")) {
                        for(int j = 0 ; j < element.getChildElements().size() ; j++) {
                            content = content + element.getChildElements().get(j).getContent().toString() + ",";
                        }
                    }
                    // 기사 본문
                    else if(element.getAttributeValue("class").equals("article_body")) {
                        contents2 = element.getChildElements();
                        int tempSize = element.getChildElements().size();

//                        for (int j = 0 ; j < element.getChildElements().size() ; j++ ) {
//                            if ()
//                        }
                        temp = element.getContent().toString();
                        // tag가 특수 문단 내용을 갖는 태그인지 확인한 뒤, tagName을 그대로 , 써서 추가

                        //

                        // caption, ab_sub_headingline,
                        // 이 경우는 <> 와 </> 구분하여
                        // 그 다음에 태그 제거
                        int start = temp.indexOf("<");
                        int end = temp.indexOf(">");

                        temp = temp.replaceAll("<br>","");
                        temp = temp.replaceAll("<br/>","");
                        temp = temp.replaceAll("&nbsp;","");
                        temp2 = temp;
                        temp = temp.replaceAll("^<div","");
                        temp = temp.replaceAll("</div>","");
                        temp = temp.replaceAll("^<img","");
                        temp = temp.replaceAll("^<span","");
                        temp = temp.replaceAll("<span>","");
                        temp = temp.replaceAll("^<p","");
                        temp = temp.replaceAll("</p>","");
                        temp = temp.replaceAll("<strong>","");
                        temp = temp.replaceAll("</strong>","");
                        temp = temp.replaceAll("^<li","");
                        temp = temp.replaceAll("</li>","");
                        temp = temp.replaceAll("^<ul","");
                        temp = temp.replaceAll("</ul>","");
                        temp = temp.replaceAll("^<a","");
                        temp = temp.replaceAll("</a>","");
                        content = content + temp + ",";
                    }

                }

            }

            int tempSize = contents.size();

//            for(int i =0; i<contents.size();i++)
//            {
//                Element element = (Element)contents.get(i);
//                String txt = element.getAttributes().toString();
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        return htmlContent;
        return content;
    }
    // 필요 영역
    // tag로 div 먼저 가져오
    // article_head
    // article_body
    // article_foot

    // 저걸 다 가져온다 한들, 저걸 어찌 TextView로 각기 만든단말인가
    // string으로 가져오되, 구분자 따위를 써서 element마다 분리시킨 뒤
    // list로 다시 만들고

    // 그걸 구분하여 list 크기만큼 textview를 만든 다음
    // 각각에 대해 layout에 추가하고 속성 부여한다





    public List<Element> getHtmltoList (String sourceUrlString) {
    List<Element> contents = null;
    String content ="";
    String temp = "";

        try {

        URL sUrl = new URL(sourceUrlString);
        InputStream is = sUrl.openStream();
        Source source= new Source(new InputStreamReader(is,"utf-8"));

        // 전체 소스 구문을 분석한다.
        source.fullSequentialParse();
        // HTML markup에서 text contents만 가져와서 스트링으로 변환한다.
        contents = source.getAllElements(HTMLElementName.BODY).get(0).getAllElements();

        for (int i = 0 ; i < contents.size() ; i++)
        {
            Element element = contents.get(i);
            String elementTag = element.getStartTag().getName();

            switch (elementTag)
            {
                case "body" : break;
                case "div" :
                    List<Element> divContents = element.getChildElements();
//                        int temp = divContents.size();
                    for (int j = 0 ; j < divContents.size() ; j++)
                    {
                        Element innerElement = divContents.get(j);
                        List<Element> innerDivContents = innerElement.getChildElements();
                        if (innerDivContents.size() < 1) temp = innerElement.getContent().toString();
                        int tempInt = contents.size();
                    }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return contents;
    }
}
