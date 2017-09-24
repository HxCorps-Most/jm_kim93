var page = require('webpage').create();
var url = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EC%A0%95%EB%AA%A8";
var jqueryUrl = "http://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js";

// Event listener - 콘솔 메세지 대기
page.onConsoleMessage = function(msg, lineNum, sourceId) {
    console.log('CONSOLE: ' + msg);
};


page.open(url, function(status) {
    if (status === "success") {
        page.includeJs(jqueryUrl, function() {
            page.evaluate(function() {
                // 연관 검색어
                console.log("Parcing from related search keyword result : ");
                // #nx_related_keywords > dl > dd.lst_relate > ul > li
                var related_data = $("dd.lst_relate > ul > li");
                for(var i = 0 ; i<related_data.length ; i++) {
                    console.log($(related_data[i]).text());
                }
                console.log("");

                // 실시간 검색어 1
                console.log("News Topic result : ");
                // #nxfr_htp > div.realtime_srch._aside_news_tab > ol:nth-child(2) > li:nth-child(1) > a > span > span
                var realtime_new_topic = $("div.realtime_srch._aside_news_tab > ol > li");
                for(var i = 0 ; i<(realtime_new_topic.length/2) ; i++) {
                    console.log($(realtime_new_topic[i]).find("span.tit").text());
                }
                console.log("");
                
                // 실시간 검색어 2
                console.log("Single Lady Hot Topic result : ");
                // #nxfr_ugrank > div.realtime_srch > ol > li:nth-child(3) > a > span > span
                var single_topic = $("#nxfr_ugrank > div.realtime_srch > ol > li");
                for(var i = 0 ; i<(single_topic.length) ; i++) {
                    console.log($(single_topic[i]).find("span.tit").text());
                }
                console.log("");
                
            });
            phantom.exit(0);
        });
    } else {
      phantom.exit(1);
    }
});
