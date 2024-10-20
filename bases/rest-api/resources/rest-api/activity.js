import cytoscape from "https://cdnjs.cloudflare.com/ajax/libs/cytoscape/3.30.2/cytoscape.esm.min.mjs";

function initCytoscape(data)
{
var cy = cytoscape({

  container: document.getElementById('activity-container'), // container to render in

  elements: data,

  style: [ // the stylesheet for the graph
    {
      selector: 'node',
      style: {
        'background-color': '#666',
        'label': 'data(id)'
      }
    },

    {
      selector: 'edge',
      style: {
        'width': 1,
        'line-color': '#ccc',
        'target-arrow-color': '#ccc',
        'target-arrow-shape': 'triangle',
        'curve-style': 'bezier'
      }
    }
  ],

  layout: {
    name: 'cose'
  }

});


}

var xmlHttp = new XMLHttpRequest();
xmlHttp.onreadystatechange = function() {
  if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
    initCytoscape(JSON.parse(xmlHttp.responseText));
}
xmlHttp.open("GET", "/silo/activity/", true); // true for asynchronous
xmlHttp.send(null);
