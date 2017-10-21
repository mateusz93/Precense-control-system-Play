<!-- Add Google Maps -->
<script src="http://maps.googleapis.com/maps/api/js"></script>
<script>
var myCenter = new google.maps.LatLng(51.746066, 19.455357);

function initialize() {
    var mapProp = {
        center:myCenter,
        zoom:14,
        scrollwheel:false,
        draggable:false,
        mapTypeId:google.maps.MapTypeId.ROADMAP
    };

    var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);

    var marker = new google.maps.Marker({
        position:myCenter,
    });

    marker.setMap(map);
}

google.maps.event.addDomListener(window, 'load', initialize);
</script>