var page = require('webpage').create();
var url = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EC%A0%95%EB%AA%A8";
var jqueryUrl = "http://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js";

page.onConsoleMessage = function(msg, lineNum, sourceId) {
    console.log('CONSOLE: ' + msg);
};

page.open(url, function(status) {
    if (status === "success") {
        page.includeJs(jqueryUrl, function() {
            page.evaluate(function() {
                // lastest version on the web
                console.log("Parcing from related search keyword result : ");
                
                // #nx_related_keywords > dl > dd.lst_relate > ul
                var related_data = $("dd.lst_relate ul");
                console.log(related_data.text());

                // #nxfr_htp > div.realtime_srch._aside_news_tab > ol:nth-child(2) > li:nth-child(1) > a > span > span
                var realtime_new_topic = $("div.realtime_srch._aside_news_tab > ol > li > a");
                console.log(realtime_new_topic.text());
                
            });
            phantom.exit(0);
        });
    } else {
      phantom.exit(1);
    }
});
