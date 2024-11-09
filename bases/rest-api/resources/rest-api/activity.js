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
  const backgroundColor = `#c14732`;

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
    svg: 'data:image/svg+xml;base64,' + btoa(svg),
    background: backgroundColor,
    width: 32,
    height: 32,
  };
}

function renderActor(ele) {
  // Icon path is assumed to be of 32x32 in this example. You may auto calculate this if you wish.
  const iconPath = "M24 20.993V24H0v-2.996A14.977 14.977 0 0112.004 15c4.904 0 9.26 2.354 11.996 5.993zM16.002 8.999a4 4 0 11-8 0 4 4 0 018 0z";
  const iconColor = '#ffffff';
  const size = 24; // may need to calculate this yourself
  const iconResize = 0; // adjust this for more "padding" (bigger number = more smaller icon)
  const width = size;
  const height = size;
  const scale = (size - iconResize) / size;
  const iconTranslate = iconResize / 2 / scale;
  const backgroundColor = `#3265c3`;
  const svg = `<svg xmlns="http://www.w3.org/2000/svg" width="${width}" height="${height}">
      <rect x="0" y="0" width="${width}" height="${height}" fill="${backgroundColor}"></rect>
      <path d="${iconPath}" fill="${iconColor}" transform="scale(${scale}) translate(${iconTranslate}, ${iconTranslate}) "></path>
    </svg>`;
  return {
    svg: 'data:image/svg+xml;base64,' + btoa(svg),
    background: backgroundColor,
    width: 24,
    height: 24,
  };
}

function renderNode(ele) {
  if (ele.data("nodeType") == "silo") {
    return renderSilo(ele);
  }
  return renderActor(ele);
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
        'background-color': (ele) => renderNode(ele).background,
        'background-image': (ele) => renderNode(ele).svg,
        'border': '1px solid black',
        width: (ele) => renderNode(ele).width,
        height: (ele) => renderNode(ele).height,
        'label': 'data(label)',
        'font-size': '5px',
        'font-weight': '900',
        "color": "rgb(67,56,202)",
        "text-background-color": "rgb(238,242,255)",
        "text-background-opacity": "1",
        "text-background-shape": "roundrectangle",
        "text-border-color": "rgb(59,130,246,0.5)",
        'text-background-padding': '1.5px',
        "text-border-width": 0.4,
        "text-border-opacity": 0.5,
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
