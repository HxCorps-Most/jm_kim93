
/*
$(".lst_relate > ul > li").each(function() {
            var data = $(this);
            var content = document.getElementById("result");
            content = content + data.text() + "(" + data.attr("href") + ")" + "<br>";
            console.log(content);

*/

var request = require('request'),
    cheerio = require('cheerio');

var url = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=정모";

//var url = "https://dobest.io/";

request(url, function(error, response, body) {  
  if (error) throw error;
 //   console.log(body);

    var $ = cheerio.load(body);
    console.log($);
/*
    var postElements = $(".lst_relate > ul > li");
    postElements.each(function() {
        var postTitle = $(this).text();
        var postUrl = $(this).attr("href");

        console.log(postTitle + postUrl + " ");

  });

*/
});

/*
    var postElements = $("section.posts article.post");
    postElements.each(function() {
        var postTitle = $(this).find("h1").text();
        var postUrl = $(this).find("h1 a").attr("href");
*/