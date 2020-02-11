import { Component, Input, OnInit } from '@angular/core';
import * as go from 'gojs';

const $ = go.GraphObject.make;

@Component({
  selector: 'jhi-learning-diagram',
  templateUrl: './learning-diagram.component.html',
  styleUrls: ['./learning-diagram.component.scss']
})
export class LearningDiagramComponent implements OnInit {
  public diagram: go.Diagram = null;

  @Input()
  public model: go.Model;

  constructor() {}

  ngOnInit() {
    this.diagram = $(go.Diagram, 'learningDiagramDiv', {
      layout: $(go.TreeLayout, {}),
      'undoManager.isEnabled': true
    });

    this.diagram.nodeTemplate = $(
      go.Node,
      'Auto',
      $(
        go.Shape,
        'RoundedRectangle',
        {
          name: 'SHAPE',
          portId: '',
          fromLinkable: true,
          toLinkable: true,
          cursor: 'pointer',
          fill: 'lightblue',
          stroke: null
        },
        new go.Binding('fill', '', function(node) {
          const levelColors = ['orange', 'red', 'lightblue', 'green'];
          let color = node.findObject('SHAPE').fill;
          const dia: go.Diagram = node.diagram;
          if (dia && dia.layout.network) {
            dia.layout.network.vertexes.each(function(v: go.TreeVertex) {
              if (v.node && v.node.key === node.data.key) {
                const level: number = v.level % levelColors.length;
                color = levelColors[level];
              }
            });
          }
          return color;
        }).ofObject()
      ),
      $(go.TextBlock, { margin: 12, stroke: 'white', font: 'bold 16px sans-serif' }, new go.Binding('text', 'name'))
    );

    this.diagram.linkTemplate = $(go.Link, { routing: go.Link.Orthogonal, corner: 10 }, $(go.Shape), $(go.Shape, { toArrow: 'Standard' }));

    this.diagram.model = this.model;
  }
}
