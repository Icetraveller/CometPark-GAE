<html><head>       <script type="text/javascript" src="/_ah/channel/jsapi"></script></head><body><h2>Parking Spot 1 Status changes:</h2><div id='content'>  gvufhiweuhf</div><div id='time'>  12:30PM</div><table style="width:300px" id='spotsTable><tr>  <td>Spots</td>  <td>Status</td>		  </tr><tr>  <td>Spot 1</td>  <td id='1'>Occupied</td>		</tr><tr>  <td>Spot 2</td>  <td id='2'>Occupied</td>		</tr></table><script type='text/javascript'>    onMessage = function(m) {              var msg = document.getElementById('content');      msg.innerHTML = String(m.data);      var currentdate = new Date();      var dateText = document.getElementById('time');      dateText.innerHTML = currentdate.getHours() + ":" + currentdate.getMinutes();         }    onOpened = function() {        var msg = document.getElementById('content');      msg.innerHTML = 'onOpened';      };    openChannel = function() {      var msg = document.getElementById('content');      msg.innerHTML = 'openChannel';        var token = '{{ token }}';        var channel = new goog.appengine.Channel(token);        var handler = {          'onopen': onOpened,          'onmessage': onMessage,          'onerror': function() {var msg = document.getElementById('content');            msg.style.background = "green";},          'onclose': function() {            var msg = document.getElementById('content');            msg.style.background = "red";        }        };        var socket = channel.open(handler);        socket.onopen = onOpened;        socket.onmessage = onMessage;    }    initialize = function() {        openChannel();         var msg = document.getElementById('content');      msg.innerHTML = 'openChannel';    }    setTimeout(initialize, 100);</script></body></html>