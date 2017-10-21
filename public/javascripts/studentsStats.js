// Load the Visualization API and the piechart package.
google.charts.load('current', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawChart);

function drawChart() {

    var jsonData = $.ajax({
      url: "http://localhost:8080/stats/globalPresenceAverage",
      dataType: "json",
      async: false
      }).responseText;

    // Create our data table out of JSON data loaded from server.
    var data = new google.visualization.DataTable(jsonData);

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('globalPresenceAverage'));
    chart.draw(data, {width: 400, height: 300});

    var jsonData = $.ajax({
      url: "http://localhost:8080/stats/globalGradesAverage",
      dataType: "json",
      async: false
      }).responseText;

    // Create our data table out of JSON data loaded from server.
    var data = new google.visualization.DataTable(jsonData);

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('globalGradesAverage'));
    chart.draw(data, {width: 400, height: 300});


    var jsonData = $.ajax({
        url: "http://localhost:8080/stats/finalGradesAverage",
        dataType: "json",
        async: false
        }).responseText;

    // Create our data table out of JSON data loaded from server.
    var data = new google.visualization.DataTable(jsonData);

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('finalGradesAverage'));
    chart.draw(data, {width: 400, height: 300});


/*    var jsonData = $.ajax({
        url: "http://localhost:8080/stats/subjectGradesAverage",
        dataType: "json",
        async: false
        }).responseText;

    // Create our data table out of JSON data loaded from server.
    var data = new google.visualization.arrayToDataTable(jsonData);
    var options = {
      width: 800,
      height: 300
      axes: {
        x: {
          0: { side: 'bottom', label: 'Nazwa przedmiotu'} // Top x-axis.
        }
      },
      bar: { groupWidth: "90%" }
    };

    var chart = new google.charts.ColumnChart(document.getElementById('subjectGradesAverage'));
    chart.draw(data, {width: 400, height: 300});*/


       // Define the chart to be drawn.
       var data = google.visualization.arrayToDataTable([
            ['Nazwa', 'Srednia', {role: 'style'}],
            ['Matematyka',  3.5, '#76A7FA'],
            ['Język Angielski',  4.3, '#76A7FA'],
            ['Historia',  3.88, '#76A7FA'],
            ['Biologia',  4.55, '#76A7FA'],
            ['Geografia',  3.9, '#76A7FA'],
            ['Język Polski',  5.0, '#76A7FA'],
            ['WF',  5.2, '#76A7FA']
          ]);

       var options = {
          width: 800,
          height: 300
       };

       // Instantiate and draw the chart.
       var chart = new google.visualization.ColumnChart(document.getElementById('subjectGradesAverage'));
       chart.draw(data, options);

}