import cytoscape from "https://cdnjs.cloudflare.com/ajax/libs/cytoscape/3.30.2/cytoscape.esm.min.mjs";

var cy = cytoscape({

  container: document.getElementById('activity-container'), // container to render in

  elements: [ // list of graph elements to start with
    { // node a
      data: {id: 'Rafaa' }
    },
    {
      data: {id: 'Fredrik'}
    },
    {
      data: {id: 'Luca'}
    },
    {
      data: {id: 'Elia'}
    },
    {
      data: {id: 'Dave'}
    },
    {
      data: {id: 'Erik'}
    },
    {
      data: {id: 'Mel'}
    },
    {
      data: {id: 'Meeting 1'}
    },
    {
      data: {id: 'Meeting 2'}
    },
    {
      data: {id: 'Elia'}
    },
    { // edge ab
      data: { id: 'Friends1', source: 'Rafaa', target: 'Meeting 1'}
    },
    { // edge ab
      data: { id: 'Friends2', source: 'Fredrik', target: 'Meeting 1'}
    },
    { // edge ab
      data: { id: 'Friends3', source: 'Dave', target: 'Meeting 1'}
    },
    { // edge ab
      data: { id: 'Friends4', source: 'Dave', target: 'Meeting 2'}
    },
    { // edge ab
      data: { id: 'Friends5', source: 'Erik', target: 'Meeting 2'}
    },
    { // edge ab
      data: { id: 'Friends6', source: 'Mel', target: 'Meeting 2'}
    },
    { // edge ab
      data: { id: 'Friends7', source: 'Luca', target: 'Meeting 1'}
    },
    { // edge ab
      data: { id: 'Friends8', source: 'Elia', target: 'Meeting 1'}
    }
  ],

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
    name: 'random'
  }

});

var r = cy.$("#Rafaa");
var e = cy.$("#Elia");

let current = r;

/**
setInterval(() => {
    current = (current === e) ? r : e;
    cy.animate({
        center: { eles: current}, // Center on the node
        padding: 50,
        zoom: 2},
        {duration: 250 }
        );
    console.log(current); // Replace with whatever action you want to perform
  }, 5000);

*/
