<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="utf-8"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>날씨</title>
<script src = "http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<!-- weather widget start -->
<center>
<div id="m-booked-custom-widget-3469">
    <div class="weather-customize" style="width:1250px;">
        <div class="booked-weather-custom-160 color-009f5d" style="width:1250px;" id="width5">
            <div class="booked-weather-custom-160-date">天气, 03 八月</div>
            <div class="booked-weather-custom-160-main more"> <a target="_blank"
                    href="https://ibooked.cn/weather/harbin-19199" class="booked-weather-custom-160-city"> 哈尔滨天气 </a> <a
                    target="_blank" class="booked-wzs-bottom-custom-160" href="https://www.booked.net/"><img
                        src="//s.bookcdn.com/images/letter/s5.gif" alt="www.booked.net" /></a>
                <div class="booked-weather-custom-160-degree booked-weather-custom-C wmd18"><span><span
                            class="plus">+</span>29</span></div>
                <div class="booked-weather-custom-details">
                    <p><span>高: <strong><span class="plus">+</span>29<sup>°</sup></strong></span><span> 低: <strong><span
                                    class="plus">+</span>23<sup>°</sup></strong></span></p>
                    <p>湿度: <strong>69%</strong></p>
                    <p>风力风向: <strong>S - 15 KPH</strong></p>
                </div>
            </div>
            <div class="booked-weather-custom-160-main more"> <a target="_blank"
                    href="https://ibooked.cn/weather/beijing-18391" class="booked-weather-custom-160-city"> 北京天气 </a>
                <div class="booked-weather-custom-160-degree booked-weather-custom-C wmd01"><span><span
                            class="plus">+</span>39</span></div>
                <div class="booked-weather-custom-details">
                    <p><span>高: <strong><span class="plus">+</span>39<sup>°</sup></strong></span><span> 低: <strong><span
                                    class="plus">+</span>33<sup>°</sup></strong></span></p>
                    <p>湿度: <strong>28%</strong></p>
                    <p>风力风向: <strong>WNW - 11 KPH</strong></p>
                </div>
            </div>
            <div class="booked-weather-custom-160-main more"> <a target="_blank"
                    href="https://ibooked.cn/weather/hangzhou-18525" class="booked-weather-custom-160-city"> 杭州天气 </a>
                <div class="booked-weather-custom-160-degree booked-weather-custom-C wmd03"><span><span
                            class="plus">+</span>35</span></div>
                <div class="booked-weather-custom-details">
                    <p><span>高: <strong><span class="plus">+</span>35<sup>°</sup></strong></span><span> 低: <strong><span
                                    class="plus">+</span>30<sup>°</sup></strong></span></p>
                    <p>湿度: <strong>59%</strong></p>
                    <p>风力风向: <strong>SE - 18 KPH</strong></p>
                </div>
            </div>
            <div class="booked-weather-custom-160-main more"> <a target="_blank"
                    href="https://ibooked.cn/weather/chengdu-18975" class="booked-weather-custom-160-city"> 成都天气 </a>
                <div class="booked-weather-custom-160-degree booked-weather-custom-C wmd18"><span><span
                            class="plus">+</span>33</span></div>
                <div class="booked-weather-custom-details">
                    <p><span>高: <strong><span class="plus">+</span>33<sup>°</sup></strong></span><span> 低: <strong><span
                                    class="plus">+</span>26<sup>°</sup></strong></span></p>
                    <p>湿度: <strong>56%</strong></p>
                    <p>风力风向: <strong>SE - 9 KPH</strong></p>
                </div>
            </div>
        </div>
    </div>
</div>
</center>
<script
    type="text/javascript"> var css_file = document.createElement("link"); css_file.setAttribute("rel", "stylesheet"); css_file.setAttribute("type", "text/css"); css_file.setAttribute("href", 'https://s.bookcdn.com/css/weather.css?v=0.0.1'); document.getElementsByTagName("head")[0].appendChild(css_file); function setWidgetData(data) { if (typeof (data) != 'undefined' && data.results.length > 0) { for (var i = 0; i < data.results.length; ++i) { var objMainBlock = document.getElementById('m-booked-custom-widget-3469'); if (objMainBlock !== null) { var copyBlock = document.getElementById('m-bookew-weather-copy-' + data.results[i].widget_type); objMainBlock.innerHTML = data.results[i].html_code; if (copyBlock !== null) objMainBlock.appendChild(copyBlock); } } } else { alert('data=undefined||data.results is empty'); } } </script>
<script type="text/javascript" charset="UTF-8"
    src="https://widgets.booked.net/weather/info?action=get_weather_info&ver=6&cityID=19199,18391,18525,18975&type=2&scode=2&ltid=3458&domid=&anc_id=6958&cmetric=1&wlangID=17&color=009f5d&wwidth=1250&header_color=ffffff&text_color=333333&link_color=08488D&border_form=1&footer_color=ffffff&footer_text_color=333333&transparent=0"></script>
<!-- weather widget end -->
</body>
</html>