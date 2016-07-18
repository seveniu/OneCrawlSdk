/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seveniu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    //    public static void main(String[] args) throws Exception {
//        ConsumerServer.start(9999, new Consumer() {
//            @Override
//            public boolean has(String url) {
//                return false;
//            }
//
//            @Override
//            public void done(Node node) {
//                System.out.println(node);
//            }
//
//            @Override
//            public void statistic(TaskStatistic statistic) {
//                System.out.println(statistic);
//            }
//
//            @Override
//            public List<Task> getTasks() {
//                return null;
//            }
//        });
//
//
//    }
    public static void main(String[] args) {

        String html = "<div class=\"article-top-box clearfix\"> \n" +
                " <div class=\"icon_container\"> \n" +
                "  <div class=\"yhjingxuan\">\n" +
                "   <span>优惠精选</span>\n" +
                "  </div> \n" +
                " </div> \n" +
                " <a isconvert=\"1\" itemprop=\"url\" data-url=\"http://go.smzdm.com/91aa51aa22d4c37e/ca_aa_yh_163_6248599_491_1933_165\" href=\"http://go.smzdm.com/91aa51aa22d4c37e/ca_aa_yh_163_6248599_491_1933_165\" class=\"pic-Box\" target=\"_blank\" rel=\"nofollow\" onclick=\"change_direct_url(this);  gtmAddToCart({'name':'MI 小米 Note 移动联通4G 智能手机 白色','id':'6248599' , 'price':'1099','brand':'MI/小米' ,'mall':'京东', 'category':'电脑数码/手机通讯/智能手机/安卓手机','metric1':'1099','dimension10':'jd.com','dimension9':'youhui','dimension11':'8阶价格','dimension12':'京东','dimension20':'无','dimension32':'先发后审','dimension25':'491'})\">[[0]]</a> \n" +
                " <!--article-right--> \n" +
                " <div class=\"article-right\"> \n" +
                "  <h1 class=\"article_title \"> 0点开抢： <em itemprop=\"name\"> MI 小米 Note 移动联通4G 智能手机 白色 </em> <em itemprop=\"offers\" itemscope itemtype=\"http://schema.org/Offer\"> \n" +
                "    <meta itemprop=\"priceCurrency\" content=\"CNY\"> <em itemprop=\"price\" style=\"display:none\">1099</em> <span class=\"red\">&nbsp;&nbsp;&nbsp;1099元</span></em> </h1> \n" +
                "  <div class=\"article-meta-box\"> \n" +
                "   <div class=\"article_meta\"> \n" +
                "    <span class=\"ellipsis author\">爆料人：烧麦XM</span> \n" +
                "    <span>时间：07-17 23:50</span> \n" +
                "   </div> \n" +
                "   <div class=\"article_meta\"> \n" +
                "    <span class=\"tags\">标签： <a href=\"http://www.smzdm.com/mall/jd/\">京东</a> <a href=\"http://www.smzdm.com/fenlei/anzhouji/\">安卓手机</a> <a itemprop=\"brand\" itemscope itemtype=\"http://schema.org/Brand\" href=\"http://pinpai.smzdm.com/1933/\">MI/小米</a> <a href=\"http://www.smzdm.com/youhui/\">优惠精选</a> </span> \n" +
                "   </div> \n" +
                "   <div class=\"clearfix article_link no-gonglue\"> \n" +
                "    <div class=\"buy\"> \n" +
                "     <a href=\"http://go.smzdm.com/91aa51aa22d4c37e/ca_aa_yh_163_6248599_491_1933_165\" data-url=\"\" target=\"_blank\" rel=\"nofollow\" onclick=\"change_direct_url(this);  gtmAddToCart({'name':'MI 小米 Note 移动联通4G 智能手机 白色','id':'6248599' , 'price':'1099','brand':'MI/小米' ,'mall':'京东', 'category':'电脑数码/手机通讯/智能手机/安卓手机','metric1':'1099','dimension10':'jd.com','dimension9':'youhui','dimension11':'8阶价格','dimension12':'京东','dimension20':'无','dimension32':'先发后审','dimension25':'491'})\">直达链接<i>&gt;</i></a> \n" +
                "    </div>  \n" +
                "    <div class=\"mobile-go-buy mobile-link\"> \n" +
                "     <a href=\"\">微信扫码购买</a> \n" +
                "     <div class=\"more-app-go-buy\"> \n" +
                "      <div class=\"more-app-bg\"> \n" +
                "       <span class=\"arrow-left\"><em></em></span> \n" +
                "       <div id=\"qrcode\"></div> \n" +
                "      </div> \n" +
                "     </div> \n" +
                "    </div>  \n" +
                "   </div> \n" +
                "  </div> \n" +
                "  <!--article-right--> \n" +
                " </div> \n" +
                " <!--item-box--> \n" +
                " <div class=\"item-box item-preferential\"> \n" +
                "  <div class=\"inner-block\">\n" +
                "   <strong>小米Note普通版入手好价。</strong>\n" +
                "  </div> \n" +
                "  <div class=\"baoliao-block\">\n" +
                "   <p>小米Note整体外观延续金属边框大屏风潮，机身厚度仅为6.95毫米、重量161克，显示屏幕尺寸则达到5.7英寸，屏占比很高。采用高通骁龙801处理器 + 3GB RAM + 16GB ROM。而在外观设计方面，小米Note还采用前2.5D、后3D曲面康宁大猩猩3玻璃，质感和手感都提升明显。标配版1080p屏，加入了左右手单手操作功能，缩小后的显示区域可根据用手习惯归于屏幕的左下或者右下角，并且提供3.7、4、4.5英寸3个不同大小可选，对于小手用户来说不啻福音。400万前置 +&nbsp;1300万后置摄像头，其中前置镜头提供2μm大像素提升自拍效果，而后置配备OIS光学防抖，双色温闪光灯。另外小米Note这枚后置摄像头与机身保持水平，没有突出。</p>\n" +
                "  </div> \n" +
                "  <div class=\"baoliao-block news_content\"> \n" +
                "   <dl class=\"list_catalogue\" style=\"display: none;\"> \n" +
                "    <dd></dd> \n" +
                "   </dl> \n" +
                "   <p itemprop=\"description\"><a itemprop=\"description\" href=\"http://go.smzdm.com/91aa51aa22d4c37e/ca_aa_yh_163_6248599_491_1933_165\" target=\"_blank\" rel=\"nofollow\" onclick=\"gtmAddToCart({'name':'MI 小米 Note 移动联通4G 智能手机 白色','id':'6248599' , 'price':'1099','brand':'MI/小米' ,'mall':'京东', 'category':'电脑数码/手机通讯/智能手机/安卓手机','metric1':'1099','dimension10':'jd.com','dimension9':'youhui','dimension11':'8阶价格','dimension12':'京东','dimension20':'无','dimension32':'先发后审','dimension25':'491'});\">京东</a>将会0点特价至1099元，并且白条支付还可6期免息。其具体的版本是联通合约版（不含合约计划），网络上支持移动联通双4G，也就是与普通双网版本无异。而一千元出头的价格来购买去年的旗舰产品，还是蛮划算的。<br></p> \n" +
                "  </div> \n" +
                "  <div class=\"baoliao-block\"> \n" +
                "   <p>值友“烧麦XM”爆料原文：小米 Note 联通合约版 白色 联通4G手机 双卡双待 不含合约计划，现价1299元。7.18日零点开始，低至1099元！历史低价！7.11-7.20打白条6期免息，12期息费5折！ 限时限量赠送手机U盘，赠完即止！</p> \n" +
                "  </div> \n" +
                " </div> \n" +
                " <!--item-box end--> \n" +
                " <!--item-box--> \n" +
                " <div class=\"item-box\"> \n" +
                "  <div class=\"inner-block\"> \n" +
                "   <!--banner_scroll--> \n" +
                "   <div class=\"banner_scroll\"> \n" +
                "    <!--内容展示区域--> \n" +
                "    <div class=\"imgContent\"> \n" +
                "     <div class=\"bigImgContent\">\n" +
                "      <a class=\"aimgcon\">[[1]]</a>\n" +
                "     </div> \n" +
                "     <a class=\"imgpn img-prev\">上一张</a> \n" +
                "     <a class=\"imgpn img-next\">下一张</a> \n" +
                "    </div> \n" +
                "    <div class=\"smallImgScroll\"> \n" +
                "     <ul class=\"smallImgList\"> \n" +
                "      <li class=\"thisimg\"> <span></span> <a href=\"\" rel=\"http://y.zdmimg.com/201512/25/567cac4a720a2.jpeg_d480.jpg\">[[2]]</a> </li> \n" +
                "      <li> <span></span> <a href=\"\" rel=\"http://y.zdmimg.com/201512/25/567cac4a67237.jpeg_d480.jpg\">[[3]]</a> </li> \n" +
                "      <li> <span></span> <a href=\"\" rel=\"http://y.zdmimg.com/201512/25/567cac4a6e5d3.jpeg_d480.jpg\">[[4]]</a> </li> \n" +
                "     </ul> \n" +
                "    </div> \n" +
                "   </div> \n" +
                "   <!--banner_scroll end--> \n" +
                "  </div> \n" +
                "  <!--wiki-container start--> \n" +
                "  <div class=\"wiki-container\"> \n" +
                "   <div class=\"wiki-info\"> \n" +
                "    <a class=\"\" href=\"http://wiki.smzdm.com/p/lp0y41/\" target=\"_blank\">MI 小米 Note 16G 移动联通双4G 双卡双待 手机 </a> \n" +
                "   </div> \n" +
                "   <div class=\"its-worth\" onclick=\"do_support('lp0y41',this);\" id=\"wiki_support\" data-id=\"lp0y41\"> \n" +
                "    <i class=\"icon-zan2\"></i>&nbsp;&nbsp;\n" +
                "    <span class=\"worth-count\" data-num=\"24\">值得买&nbsp;24</span> \n" +
                "    <span class=\"worth-info\">已点值</span> \n" +
                "    <span class=\"scoreWorth\">+1</span> \n" +
                "   </div> \n" +
                "   <hr> \n" +
                "   <ul class=\"wiki-card\"> \n" +
                "    <li> <a href=\"http://wiki.smzdm.com/p/lp0y41/yuanchuang/\" target=\"_blank\"> \n" +
                "      <div class=\"wiki-num \">\n" +
                "        3 \n" +
                "      </div> \n" +
                "      <div>\n" +
                "       原创\n" +
                "      </div> </a> </li> \n" +
                "    <li class=\"wiki-divide\"></li> \n" +
                "    <li> <a href=\"http://wiki.smzdm.com/p/lp0y41/dianping/\" target=\"_blank\"> \n" +
                "      <div class=\"wiki-num \">\n" +
                "        1 \n" +
                "      </div> \n" +
                "      <div>\n" +
                "       点评\n" +
                "      </div> </a> </li> \n" +
                "    <li class=\"wiki-divide\"></li> \n" +
                "    <li> <a href=\"http://wiki.smzdm.com/p/lp0y41/jiage/\" target=\"_blank\"> \n" +
                "      <div class=\"wiki-num \">\n" +
                "        7 \n" +
                "      </div> \n" +
                "      <div>\n" +
                "       历史优惠\n" +
                "      </div> </a> </li> \n" +
                "    <li class=\"wiki-divide\"></li> \n" +
                "    <li> \n" +
                "     <div class=\"wiki-num wiki-grey\">\n" +
                "       0 \n" +
                "     </div> \n" +
                "     <div>\n" +
                "      资讯\n" +
                "     </div> </li> \n" +
                "    <li class=\"wiki-divide\"></li> \n" +
                "    <li> \n" +
                "     <div class=\"wiki-num wiki-grey\">\n" +
                "       0 \n" +
                "     </div> \n" +
                "     <div>\n" +
                "      众测\n" +
                "     </div> </li> \n" +
                "    <li class=\"wiki-divide\"></li> \n" +
                "    <li> \n" +
                "     <div class=\"wiki-num wiki-grey\">\n" +
                "      0\n" +
                "     </div> \n" +
                "     <div>\n" +
                "      二手\n" +
                "     </div> </li> \n" +
                "   </ul> \n" +
                "   <div class=\"wiki-footer\"> \n" +
                "    <div class=\"wiki-footer-left\">\n" +
                "     更多内容查看商品百科\n" +
                "    </div> \n" +
                "    <a target=\"_blank\" href=\"http://wiki.smzdm.com/\" onclick=\"dataLayer.push({'event':'优惠详情页_百科介绍_图片','文章标题':'MI 小米 Note 16G 移动联通双4G 双卡双待 手机 ','文章编号':'256522'})\" class=\"wiki-footer-right\"> [[5]] </a> \n" +
                "   </div> \n" +
                "  </div> \n" +
                "  <!--wiki-container end--> \n" +
                " </div> \n" +
                " <input type=\"hidden\" id=\"isDetail\"> \n" +
                " <input type=\"hidden\" id=\"channelID\" value=\"1\"> \n" +
                " <input type=\"hidden\" id=\"articleID\" value=\"6248599\"> \n" +
                " <!--item-box end--> \n" +
                "</div>";
        final Pattern IMG_TEMP_PLACEHOLDER = Pattern.compile("(\\[\\[(\\d+)\\]\\])");
        Matcher m = IMG_TEMP_PLACEHOLDER.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String index = m.group(2);
            System.out.println(index);
//            Result result = results.get(index);
//            String placeholder = result.getStorageName();
            String placeholder = "***********************";
            if (placeholder == null || placeholder.isEmpty()) {
                continue;
            }
            try {
                m.appendReplacement(sb, placeholder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        m.appendTail(sb);
        System.out.println(sb.toString());
    }


}
