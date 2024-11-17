import cytoscape from "https://cdnjs.cloudflare.com/ajax/libs/cytoscape/3.30.2/cytoscape.esm.min.mjs";

function renderSilo(ele) {
  // Icon path is assumed to be of 32x32 in this example. You may auto calculate this if you wish.
  const iconPath = "M24 20.993V24H0v-2.996A14.977 14.977 0 0112.004 15c4.904 0 9.26 2.354 11.996 5.993zM16.002 8.999a4 4 0 11-8 0 4 4 0 018 0z";
  const iconColor = '#ffffff';
  const size = 512; // may need to calculate this yourself
  const iconResize = 494; // adjust this for more "padding" (bigger number = more smaller icon)

  const width = size;
  const height = size;
  const scale = (size - iconResize) / size;
  const iconTranslate = iconResize / 2 / scale;
  const backgroundColor = `rgb(127,173,242,1)`;

  const svg = `<svg xmlns="http://www.w3.org/2000/svg" width="${width}" height="${height}">
      <path d="M290.135,268.067v201.267c0,4.71,3.823,8.533,8.533,8.533s8.533-3.823,8.533-8.533V285.134l2.5,2.5 c1.664,1.664,3.849,2.5,6.033,2.5s4.369-0.836,6.033-2.5c3.337-3.337,3.337-8.73,0-12.066l-17.058-17.067h-0.009l-48.7-48.7 v-30.601c0-4.71-3.823-8.533-8.533-8.533s-8.533,3.823-8.533,8.533v25.6H76.802c-2.261,0-4.437,0.896-6.033,2.5l-51.2,51.191 c0,0.008,0,0.008-0.009,0.008L2.502,275.568c-3.336,3.336-3.336,8.73,0,12.066c3.337,3.336,8.73,3.336,12.066,0l2.5-2.5v184.201 c0,4.71,3.823,8.533,8.533,8.533c4.71,0,8.533-3.823,8.533-8.533V268.067l46.2-46.199h163.601L290.135,268.067z"
            fill="${iconColor}" transform="scale(${scale}) translate(${iconTranslate}, ${iconTranslate})">
      </path>
      <path d="M59.735,494.934h-51.2c-4.71,0-8.533,3.823-8.533,8.533s3.823,8.533,8.533,8.533h51.2c4.71,0,8.533-3.823,8.533-8.533 S64.446,494.934,59.735,494.934z"
            fill="${iconColor}" transform="scale(${scale}) translate(${iconTranslate}, ${iconTranslate})">
      </path>
      <path d="M431.14,50.578c-3.96-2.569-9.25-1.442-11.802,2.509c-2.569,3.959-1.442,9.242,2.509,11.802 c14.857,9.643,26.598,23.996,33.058,40.414c1.323,3.354,4.54,5.41,7.945,5.41c1.041,0,2.099-0.188,3.123-0.597 c4.386-1.724,6.545-6.682,4.813-11.068C462.918,79.071,449.214,62.303,431.14,50.578z"
            fill="${iconColor}" transform="scale(${scale}) translate(${iconTranslate}, ${iconTranslate})">
      </path>
      <path d="M233.653,324.908c-0.99-0.41-2.082-0.64-3.226-0.64h-0.026H93.869h-0.026c-1.143,0-2.236,0.23-3.226,0.64 c-1.016,0.418-1.963,1.041-2.782,1.86c-0.819,0.819-1.442,1.766-1.86,2.782c-0.41,0.99-0.64,2.082-0.64,3.226v0.026v136.533 c0,3.447,2.082,6.562,5.265,7.885c3.183,1.314,6.852,0.589,9.301-1.852l62.234-62.234l62.234,62.234 c1.63,1.63,3.814,2.5,6.033,2.5c1.101,0,2.21-0.213,3.268-0.649c3.183-1.323,5.265-4.437,5.265-7.885V332.801v-0.026 c0-1.143-0.23-2.236-0.64-3.226c-0.418-1.016-1.041-1.963-1.86-2.782C235.616,325.949,234.669,325.326,233.653,324.908z M102.402,448.735v-95.334l47.667,47.667L102.402,448.735z M114.468,341.334h95.334l-47.667,47.667L114.468,341.334z M221.869,448.735l-47.667-47.667l47.667-47.667V448.735z"
            fill="${iconColor}" transform="scale(${scale}) translate(${iconTranslate}, ${iconTranslate})">
      </path>
      <path d="M375.469,0.001c-75.281,0-136.533,61.252-136.533,136.533v8.533c0,4.71,3.823,8.533,8.533,8.533h145.067v315.733 c0,4.71,3.823,8.533,8.533,8.533c4.71,0,8.533-3.823,8.533-8.533v-8.533h51.2v8.533c0,4.71,3.823,8.533,8.533,8.533 c4.71,0,8.533-3.823,8.533-8.533V145.068c0-4.71-3.823-8.533-8.533-8.533H256.002c0-65.877,53.589-119.467,119.467-119.467 s119.467,53.589,119.467,119.467v332.8c0,4.71,3.823,8.533,8.533,8.533s8.533-3.823,8.533-8.533v-332.8 C512.002,61.253,450.75,0.001,375.469,0.001z M409.602,153.601h51.2v34.133h-51.2V153.601z M409.602,204.801h51.2v34.133h-51.2 V204.801z M409.602,256.001h51.2v34.133h-51.2V256.001z M409.602,307.201h51.2v34.133h-51.2V307.201z M409.602,358.401h51.2 v34.133h-51.2V358.401z M409.602,409.601h51.2v34.133h-51.2V409.601z"
            fill="${iconColor}" transform="scale(${scale}) translate(${iconTranslate}, ${iconTranslate})">
      </path>
      <path d="M503.469,494.934h-17.067c-4.71,0-8.533,3.823-8.533,8.533s3.823,8.533,8.533,8.533h17.067 c4.71,0,8.533-3.823,8.533-8.533S508.179,494.934,503.469,494.934z"
            fill="${iconColor}" transform="scale(${scale}) translate(${iconTranslate}, ${iconTranslate})">
      </path>
      <path d="M452.269,494.934h-358.4c-4.71,0-8.533,3.823-8.533,8.533s3.823,8.533,8.533,8.533h358.4c4.71,0,8.533-3.823,8.533-8.533 S456.979,494.934,452.269,494.934z"
            fill="${iconColor}" transform="scale(${scale}) translate(${iconTranslate}, ${iconTranslate})">
      </path>
      
    </svg>`;

  return {
    //svg: 'data:image/svg+xml;base64,' + btoa(svg),
    svg: "none",
    background: backgroundColor,
    width: 8,
    height: 8,
  };
}

function renderUnlinkedActor(ele) {
  // Icon path is assumed to be of 32x32 in this example. You may auto calculate this if you wish.
  const iconPath = "M24 20.993V24H0v-2.996A14.977 14.977 0 0112.004 15c4.904 0 9.26 2.354 11.996 5.993zM16.002 8.999a4 4 0 11-8 0 4 4 0 018 0z";
  const iconColor = `#a8acb1`;

  const size = 12; // may need to calculate this yourself
  const iconResize = 0; // adjust this for more "padding" (bigger number = more smaller icon)
  const width = size;
  const height = size;
  const scale = (size / 24);
  const backgroundColor = `#f3f4f6`;

  const missingSvg = `<svg height="${height}" transform="scale(${scale})" width="${width}" version="1.1" id="_x32_" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 512 512" xml:space="preserve" fill="${iconColor}"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <style type="text/css"> .st0{fill:${iconColor};} </style> <g> <path class="st0" d="M396.138,85.295c-13.172-25.037-33.795-45.898-59.342-61.03C311.26,9.2,280.435,0.001,246.98,0.001 c-41.238-0.102-75.5,10.642-101.359,25.521c-25.962,14.826-37.156,32.088-37.156,32.088c-4.363,3.786-6.824,9.294-6.721,15.056 c0.118,5.77,2.775,11.186,7.273,14.784l35.933,28.78c7.324,5.864,17.806,5.644,24.875-0.518c0,0,4.414-7.978,18.247-15.88 c13.91-7.85,31.945-14.173,58.908-14.258c23.517-0.051,44.022,8.725,58.016,20.717c6.952,5.941,12.145,12.594,15.328,18.68 c3.208,6.136,4.379,11.5,4.363,15.574c-0.068,13.766-2.742,22.77-6.603,30.442c-2.945,5.729-6.789,10.813-11.738,15.744 c-7.384,7.384-17.398,14.207-28.634,20.479c-11.245,6.348-23.365,11.932-35.612,18.68c-13.978,7.74-28.77,18.858-39.701,35.544 c-5.449,8.249-9.71,17.686-12.416,27.641c-2.742,9.964-3.98,20.412-3.98,31.071c0,11.372,0,20.708,0,20.708 c0,10.719,8.69,19.41,19.41,19.41h46.762c10.719,0,19.41-8.691,19.41-19.41c0,0,0-9.336,0-20.708c0-4.107,0.467-6.755,0.917-8.436 c0.773-2.512,1.206-3.14,2.47-4.668c1.29-1.452,3.895-3.674,8.698-6.331c7.019-3.946,18.298-9.276,31.07-16.176 c19.121-10.456,42.367-24.646,61.972-48.062c9.752-11.686,18.374-25.758,24.323-41.968c6.001-16.21,9.242-34.431,9.226-53.96 C410.243,120.761,404.879,101.971,396.138,85.295z"></path> <path class="st0" d="M228.809,406.44c-29.152,0-52.788,23.644-52.788,52.788c0,29.136,23.637,52.772,52.788,52.772 c29.136,0,52.763-23.636,52.763-52.772C281.572,430.084,257.945,406.44,228.809,406.44z"></path> </g> </g></svg>`;


  var background = null;
  background = 'data:image/svg+xml;base64,' + btoa(missingSvg);

  return {
    //svg: 'data:image/svg+xml;base64,' + btoa(svg),
    //svg: "url('https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80')",
    svg: background,

    background: backgroundColor,
    width: 32,
    height: 32,
  };
}


function renderActor(ele) {
  // Icon path is assumed to be of 32x32 in this example. You may auto calculate this if you wish.
  const iconPath = "M24 20.993V24H0v-2.996A14.977 14.977 0 0112.004 15c4.904 0 9.26 2.354 11.996 5.993zM16.002 8.999a4 4 0 11-8 0 4 4 0 018 0z";
  const iconColor = `rgb(127,173,242,1)`;

  const size = 32; // may need to calculate this yourself
  const iconResize = 0; // adjust this for more "padding" (bigger number = more smaller icon)
  const width = size;
  const height = size;
  const scale = (size / 24);
  const backgroundColor = `rgb(222,233,255,1)`;

  const svg = `<svg xmlns="http://www.w3.org/2000/svg" width="${width}" height="${height}">
      <path d="${iconPath}" fill="${iconColor}" transform="scale(${scale})"></path>
    </svg>`;


  var background = null;

  if (ele.data("backgroundImage")) {
    background =  "url(" + ele.data("backgroundImage") + ")";
  } else {
    background = 'data:image/svg+xml;base64,' + btoa(svg);
  }

  return {
    //svg: 'data:image/svg+xml;base64,' + btoa(svg),
    //svg: "url('https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80')",
    svg: background,

    background: backgroundColor,
    width: 32,
    height: 32,
  };
}

function renderNode(ele) {
  if (ele.data("nodeType") == "silo") {
    return renderSilo(ele);
  }
  if (!ele.data("linked")) {
    return renderUnlinkedActor(ele);
  }
  return renderActor(ele);
}

let cy = null;

function initCytoscape(data)
{
cy = cytoscape({

  container: document.getElementById('activity-container'), // container to render in

  elements: data,

  style: [

    // the stylesheet for the graph
    {
      selector: 'node',
      style: {
        'background-color': (ele) => renderNode(ele).background,
        'background-fit': "cover",
        'background-image': (ele) => renderNode(ele).svg,
        width: (ele) => renderNode(ele).width,
        height: (ele) => renderNode(ele).height,
        'label': 'data(label)',
        'font-size': '8px',
        'font-weight': '500',
        "color": "rgb(17,24,39,1)",
        "text-background-color": "#fff",
        "text-background-opacity": "1",
        "text-background-shape": "roundrectangle",
        "text-border-color": "rgb(229,231,235,1)",
        'text-background-padding': '3px',
        "text-border-width": 1,
        "text-border-opacity": 1,
      }
    },

    {
      selector: 'edge',
      style: {
        'width': 2,
        'line-color': `rgb(222,233,255,1)`,
        'target-arrow-color': '#ccc',
        'target-arrow-shape': 'none',
        'curve-style': 'straight-triangle'
      }
    },

    {
      selector: 'node.highlighted',
      style: {
        'border-width': "1px",
        'border-color': "#f6af3b",
      }
    },

    {
      selector: 'edge.highlighted',
      style: {
        'line-color': "#f6af3b",
      }
    }

  ],

  layout: {
    name: 'cose',
    animate: false,
    fit: true
  },

  maxZoom: 1.8,
  minZoom: 0.45,

});


}

function clearHighlightedElements() {
  // Remove previous highlights if needed
  cy.elements('.highlighted').removeClass('highlighted');
}

function center(nodeId) {
  const position = cy.getElementById(nodeId).position();

  const zoom = cy.zoom();
  const pan = cy.pan();

  const renderedNodePosition = {
    x: position.x * zoom + pan.x,
    y: position.y * zoom + pan.y
  };

  const panOffset = {
    x: 300 - renderedNodePosition.x,
    y: 280 - renderedNodePosition.y
  };

  cy.animate({
    pan: {x: pan.x + panOffset.x,
          y: pan.y + panOffset.y}
  }, {
    duration: 250
  });

  const canvas = document.getElementById('graphCanvas');

  // Function to draw a text box at a Cytoscape node position
  function drawTextBoxAtNode(nodeId) {
    // Get the position of the node
    const node = cy.getElementById(nodeId);
    const position = node.renderedPosition();
    const canvasRect = canvas.getBoundingClientRect();
    var div = document.getElementById("silo-detail-floating-window");
    div.classList.remove("hidden");
    div.style.top = canvasRect.top + position.y - 200 + "px";
    div.style.left = canvasRect.left + position.x + "px";
  }

  cy.on('render', () => {
    drawTextBoxAtNode(nodeId);
  });

}

window.clearCytoscapeFocus = function() {
  cy.off('render');
}

const nonScrollableDiv = document.getElementById('silo-detail-floating-window');

// Prevent scrolling entirely when hovering over the div
nonScrollableDiv.addEventListener('wheel', (event) => {
  event.preventDefault();
  cy.container().dispatchEvent(new WheelEvent('wheel', event));
}, { passive: false });

function highlightElements(nodeIds, edgeIds) {
  // Remove previous highlights if needed
  clearHighlightedElements();

  // Highlight nodes
  nodeIds.forEach(id => {
    cy.getElementById(id).addClass('highlighted').incomers().addClass('highlighted');
    cy.getElementById(id).connectedEdges().addClass('highlighted');
  });

  // Highlight edges
  edgeIds.forEach(id => {
    cy.getElementById(id).addClass('highlighted');
  });
}

var xmlHttp = new XMLHttpRequest();
xmlHttp.onreadystatechange = function() {
  if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
    initCytoscape(JSON.parse(xmlHttp.responseText));
}
xmlHttp.open("GET", "/silo/activity/", true); // true for asynchronous
xmlHttp.send(null);

const listElements = document.querySelectorAll('.siloItem');

// Loop through each element and add event listeners
listElements.forEach((element) => {
  element.addEventListener('mouseenter', () => {
    highlightElements([element.id], [])
  });

  element.addEventListener('mouseleave', () => {
    clearHighlightedElements();
  });

  element.addEventListener('click', () => {
    center(element.id);
  });
});

(function() {
  const canvas = document.getElementById('graphCanvas');
  const context = canvas.getContext('2d');

  // resize the canvas to fill browser window dynamically
  window.addEventListener('resize', resizeCanvas, false);

  function resizeCanvas() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
  }

  resizeCanvas();

})();
