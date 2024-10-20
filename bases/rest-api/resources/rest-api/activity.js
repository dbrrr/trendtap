import cytoscape from "https://cdnjs.cloudflare.com/ajax/libs/cytoscape/3.30.2/cytoscape.esm.min.mjs";

function renderNode(ele) {
  // Icon path is assumed to be of 32x32 in this example. You may auto calculate this if you wish.
  const iconPath = "M24 20.993V24H0v-2.996A14.977 14.977 0 0112.004 15c4.904 0 9.26 2.354 11.996 5.993zM16.002 8.999a4 4 0 11-8 0 4 4 0 018 0z";
  const iconColor = '#ffffff';
  const size = 24; // may need to calculate this yourself
  const iconResize = 0; // adjust this for more "padding" (bigger number = more smaller icon)

  const width = size;
  const height = size;
  const scale = (size - iconResize) / size;
  const iconTranslate = iconResize / 2 / scale;
  const backgroundColor = `#33362F`;

  const svg = `<svg xmlns="http://www.w3.org/2000/svg" width="${width}" height="${height}">
      <rect x="0" y="0" width="${width}" height="${height}" fill="${backgroundColor}"></rect>
      <path d="${iconPath}" fill="${iconColor}" transform="scale(${scale}) translate(${iconTranslate}, ${iconTranslate}) "></path>
    </svg>`;

  return {
    svg: 'data:image/svg+xml;base64,' + btoa(svg),
    width,
    height,
  };
}


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
        'background-image': (ele) => renderNode(ele).svg,
        width: (ele) => renderNode(ele).width,
        height: (ele) => renderNode(ele).height,
        'label': 'data(label)'
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
