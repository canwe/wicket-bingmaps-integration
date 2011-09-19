// Wicket Namespace
var Wicket;
if (!Wicket) {
    Wicket = {}
}
else if (typeof Wicket != "object") { throw new Error("Wicket already exists but is not an object"); }

Wicket.bingmaps = {}

function WicketBingMap(id, options){
    Wicket.bingmaps[id] = this;

    this.map = new Microsoft.Maps.Map(document.getElementById(id), options);

    this.listeners = {};
    this.overlays = {};
    this.overlaysOptions = {};
    this.myLayer = {};  //clustering
    this.pushpins = {};
    this.lockEvent = {};

    this.onEvent = function(callBack, params){
        params['center'] = this.map.getCenter();
        params['bounds'] = this.map.getBounds();
        params['northwest'] = this.map.getBounds().getNorthwest();
        params['southeast'] = this.map.getBounds().getSoutheast();
        params['zoom'] = this.map.getZoom();
        params['currentMapType'] = this.getMapTypeString(this.map.getMapTypeId());

        for (var key in params) {
            callBack = callBack + '&' + key + '=' + params[key];
        }

        wicketAjaxGet(callBack, function(){}, function(){});
    }

    this.addListener = function(event, callBack){
        var self = this;

        var callbackFunction = function(e) {
            var params = {};
            for (var p = 0; p < arguments.length; p++) {
                if (arguments[p] != null) {
                    params['argument' + p] = arguments[p];
                }
            }

            //http://stackoverflow.com/questions/27509/detecting-an-undefined-object-property-in-javascript
            if (!(typeof e === "undefined") && e.targetType == "map") {
              try {
                var point = new Microsoft.Maps.Point(e.getX(), e.getY());
                var loc = e.target.tryPixelToLocation(point);
                params['location'] = loc;
              } catch (err) {
                //Handle errors here
              }
            }

            self.onEvent(callBack, params);
        };

        if (event == "rightclick") {
            addDisableRightClickContextMenu(this.map.getRootElement()); //disable right click menu
        }
        Microsoft.Maps.Events.addHandler(this.map, event, callbackFunction);
    }

    //http://social.msdn.microsoft.com/Forums/en-US/vemapcontroldev/thread/43d94f56-14d6-40a5-a621-c059d649474d/
    var addDisableRightClickContextMenu = function(element){
		function init() {
		    //Disable default double right click behavior
			element.onselectstart = new Function ("return false");
			element.oncontextmenu = new Function ("return false");
		}
		init();
	};

    this.addOverlayListener = function(overlayID, event){
        var self = this;
        var overlay = this.overlays[overlayID];

        //var overlayListenersId = '' + overlayID + ':' + event;
        //http://www.artlebedev.ru/tools/technogrette/js/likbez/
        if (!isArray(this.listeners[overlayID])) {
            this.listeners[overlayID] = new Array();
        }

        var handler = function(e) {
            var params = {};
            for (var p = 0; p < arguments.length; p++) {
                if (arguments[p] != null) {
                    params['argument' + p] = arguments[p];
                }
            }

            if (!(typeof e === "undefined") && e.targetType == 'pushpin') {
              try {
                var pinLoc = e.target.getLocation();
                params['overlay.location'] = pinLoc;
              } catch (err) {
                //Handle errors here
              }
            } else if (!(typeof e.entity === "undefined")){
              //http://www.garzilla.net/vemaps/Draggable-Push-Pins-with-Bing-Maps-7.aspx
              //especially for drag events
              try {
                var pinLoc = e.entity.getLocation();
                params['overlay.location'] = pinLoc;
                var visible = e.entity.getVisible();
                params['overlay.visible'] = visible;
              } catch (err) {
                //Handle errors here
              }
            }

            params['overlay.overlayId'] = overlay.overlayId;
            params['overlay.event'] = event;

            self.onEvent(self.overlayListenerCallbackUrl, params);
        }
        var ovListenerId = Microsoft.Maps.Events.addHandler(overlay, event, handler);
        //http://alljs.ru/articles/array/manipulations
        this.listeners[overlayID].push({'event': event, 'ovListenerId': ovListenerId, 'handler': handler});
    }

    this.clearOverlayListeners = function(overlayID, event){
        var self = this;
        var overlay = this.overlays[overlayID];

        if (isArray(this.listeners[overlayID])) {
            var idsToRemove = new Array();
            this.listeners[overlayID].forEach(function(object, index) {
                if (object['event'] == event) {
                    Microsoft.Maps.Events.removeHandler(object['ovListenerId']);
                    idsToRemove.push(index);
                }
            });
            idsToRemove.forEach(function(object, index) {
                self.listeners[overlayID].splice(object, 1);
            });
            //this.listeners[overlayListenersId].length = 0;
        }
    }

    this.getMapTypeString = function(mapType){
        switch (mapType) {
            case Microsoft.Maps.MapTypeId.aerial:
                return 'Microsoft.Maps.MapTypeId.aerial';
                break;
            case Microsoft.Maps.MapTypeId.auto:
                return 'Microsoft.Maps.MapTypeId.auto';
                break;
            case Microsoft.Maps.MapTypeId.birdseye:
                return 'Microsoft.Maps.MapTypeId.birdseye';
                break;
            case Microsoft.Maps.MapTypeId.collinsBart:
                return 'Microsoft.Maps.MapTypeId.collinsBart';
                break;
            case Microsoft.Maps.MapTypeId.mercator:
                return 'Microsoft.Maps.MapTypeId.mercator';
                break;
            case Microsoft.Maps.MapTypeId.ordnanceSurvey:
                return 'Microsoft.Maps.MapTypeId.ordnanceSurvey';
                break;
            case Microsoft.Maps.MapTypeId.road:
                return 'Microsoft.Maps.MapTypeId.road';
                break;
            default:
                return 'unknown';
                break;
        };
    }

    this.setMapType = function(mapType){
        this.map.setMapType(mapType);
    }

    this.setView = function(viewOptions){
        this.map.setView(viewOptions);
    }

    this.addOverlay = function(overlayId, overlay){
        var self = this;
        //alert(odump(this.overlaysOptions[overlayId]));
        this.overlays[overlayId] = overlay;
        overlay.overlayId = overlayId;
        //overlay.toString = function(){
        //    return overlayId;
        //};

        //http://stackoverflow.com/questions/1789945/javascript-string-contains
        if (overlay.toString().indexOf("Pushpin") != -1) {
            this.pushpins[overlayId] = overlay;
        }

        try {
            var actions = overlay.getActions();
            if (isArray(actions)) {
                actions.forEach(function(object, index) {
                    var s = object.eventHandler;
                    if ((''+ s).charAt(0) == '?') {
                    object.eventHandler = function() {
                        wicketAjaxGet(s, function(){}, function(){});
                    };
                    }
                });
            }
        } catch (err) {
            //Handle errors here
        }

        if(isEmpty(this.myLayer) || overlay.toString().indexOf("Pushpin") == -1) {
            this.map.entities.push(overlay);
        }
    }

    this.addOverlays = function(elements){
        var self = this;
        elements.forEach(function(object, index) {
            //alert(odump(object));
            self.overlays[object.overlayId] = object.overlay;
            object.overlay.overlayId = object.overlayId;

            //http://stackoverflow.com/questions/1789945/javascript-string-contains
            if (object.overlay.toString().indexOf("Pushpin") != -1) {
                self.pushpins[object.overlayId] = object.overlay;
            }

            try {
                var actions = object.overlay.getActions();
                if (isArray(actions)) {
                    actions.forEach(function(object1, index1) {
                        var s = object1.eventHandler;
                        if ((''+ s).charAt(0) == '?') {
                        object1.eventHandler = function() {
                            wicketAjaxGet(s, function(){}, function(){});
                        };
                        }
                    });
                }
            } catch (err) {
                //Handle errors here
            }

            if(isEmpty(self.myLayer) || object.overlay.toString().indexOf("Pushpin") == -1) {
                self.map.entities.push(object.overlay);
            }
        });
    }

    this.resolveOverlay = function(overlayId){
       return this.overlays[overlayId];
    }

    this.removeOverlay = function(overlayId){
        if (this.overlays[overlayId] != null) {
            this.map.entities.remove(this.overlays[overlayId]);

            this.overlays[overlayId] = null;
            this.pushpins[overlayId] = null;
            this.overlaysOptions[overlayId] = null;
        }
    }

    this.clearOverlays = function() {
        for(var prop in this.overlays) {
            this.map.entities.remove(this.overlays[prop]);
        }
        this.overlays = {};
        this.pushpins = {};
        this.overlaysOptions = {};
        //this.map.entities.clear();
    }

    this.dispose = function(){
        this.map.dispose();
    }

    // When the mouse is used, the cancelEvent function will
    // get called. Setting the handled property to true will
    // disable the mousemove event, which disables panning.
    function cancelEvent(e)
    {
        e.handled = true;
    }

    this.lockMap = function() {
        // Attach an event handler for a mousemove event.
        this.lockEvent = Microsoft.Maps.Events.addHandler(this.map, "mousemove", cancelEvent);
    }

    this.unlockMap = function() {
        // Remove an event handler for a mousemove event.
        Microsoft.Maps.Events.removeHandler(this.lockEvent);
    }

    this.initializeClustering = function() {
       var self = this;
       self.myLayer = new ClusteredEntityCollection(self.map, {
	            singlePinCallback: self.createPin,
	            clusteredPinCallback: self.createClusteredPin
	        });
       //self.myLayer.BringLayerToFront();
    }

    this.createPin = function (data) {
	    var pin = data.Pushpin || new Microsoft.Maps.Pushpin(data._LatLong, data.Options);

        pin.title = "Single Location";
	    pin.description = "GridKey: " + data.GridKey;
        return pin;
	}

	this.createClusteredPin = function (cluster, latlong, opts) {
	    var opts1 = isEmpty(opts) ? { text: '+' } : opts;
	    opts1.text = '' + cluster.length;
	    var pin = new Microsoft.Maps.Pushpin(latlong, opts1);
        pin.title = "Cluster";
	    pin.description = "GridKey: " + cluster[0].GridKey + "<br/>Cluster Size: " + cluster.length + "<br/>Zoom in for more details.";
        return pin;
    }

    this.placeClusteredPushpins = function() {
        var data = []; var i = 0;
        for(var prop in this.pushpins)
        {
            //data.push(this.pushpins[prop]);
            data.push(new ExampleDataModel("Point: " + i ++,
                                           this.pushpins[prop],
                                           this.pushpins[prop].getLocation().latitude,
                                           this.pushpins[prop].getLocation().longitude,
                                           this.overlaysOptions[prop]));
        }
        //alert(odump(data));
        this.myLayer.SetData(data);
    }

    /*
     * Example data model that may be returned from a custom web service.
     */
     var ExampleDataModel = function (name, pushpin, latitude, longitude, options) {
        this.Name = name;
        this.Pushpin = pushpin;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.Options = options;
     };

};

function isArray(o) {
    return {}.toString.call(o) == '[object Array]';
};

function toStr(obj) {
    var str = ""; //variable which will hold property values
    for(var prop in obj)
    {
        str += prop + " value :" + obj[prop] + "\n"; //Concatenate prop and its value from object
    }
    return str;
};

//http://stackoverflow.com/questions/4994201/is-object-empty
function isEmpty(obj) {
    if (typeof obj === "undefined") return true;
    // Speed up calls to hasOwnProperty
    var hasOwnProperty = Object.prototype.hasOwnProperty;

    // Assume if it has a length property with a non-zero value
    // that that property is correct.
    if (obj.length && obj.length > 0)    return false;

    for (var key in obj) {
        if (hasOwnProperty.call(obj, key))    return false;
    }

    return true;
};

//http://icodesnippet.com/snippet/javascript/dump-object-properties-recursive
//http://refactormycode.com/codes/226-recursively-dump-an-object
function odump(object, depth, max){
  depth = depth || 0;
  max = max || 2;

  if (depth > max)
    return false;

  var indent = "";
  for (var i = 0; i < depth; i++)
    indent += "  ";

  var output = "";
  for (var key in object){
    output += "\n" + indent + key + ": ";
    switch (typeof object[key]){
      case "object": output += odump(object[key], depth + 1, max); break;
      case "function": output += "function"; break;
      default: output += object[key]; break;
    }
  }
  return output;
};
