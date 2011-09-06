<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!--<script charset="UTF-8" type="text/javascript" src="http://ecn.dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=7.0">-->
        <!--</script>-->
        <!--<script>-->
            <!--function GetMap()-->
            <!--{-->
                <!--var mapOptions = {-->
                    <!--credentials: "AjP8SpGkklw0ikJfHaIwn7b-Csg-5ekMyumxo_sG_EgObGXaxw34JyCnAxNCMp4j",-->
                    <!--mapTypeId: Microsoft.Maps.MapTypeId.aerial-->
                <!--}-->
                <!--var map = new Microsoft.Maps.Map(document.getElementById("mapDiv"), mapOptions);-->
            <!--}-->

        <!--</script>-->
    </head>
    <body onload="GetMap();">
      <!--<div id='mapDiv' style="position:absolute; width:400px; height:400px;"></div>-->

      <table>
        <tr><td align="right"><a href="simple">simple</a></td><td> - Simply Bing Maps on Wicket.</td></tr>
        <tr><td align="right"><a href="listen">listen</a></td><td> - Listen to the map.</td></tr>
        <tr><td align="right"><a href="marker">marker</a></td><td> - Add some marker to the map.</td></tr>
        <tr><td align="right"><a href="custompoint">custom marker</a></td><td> - Custom Marker on the map.</td></tr>
        <tr><td align="right"><a href="refreshpoint">refresh marker</a></td><td> - Refresh marker with AJAX on the map.</td></tr>
        <tr><td align="right"><a href="markerListener">marker listener</a></td><td> - Listen to a marker on the map.</td></tr>
        <tr><td align="right"><a href="markerListenerAdvanced">more marker listener</a></td><td> - Listen to a marker on the map & toggle this behavior.</td></tr>
        <tr><td align="right"><a href="many">many</a></td><td> - How many maps do you need?</td></tr>
        <tr><td align="right"><a href="info">info</a></td><td> - Info Windows on the map.</td></tr>
        <tr><td align="right"><a href="clustering">clustering</a></td><td> - Pushpins clustering on the map.</td></tr>
      </table>
    </body>
</html>