import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISubject } from 'app/shared/model/subject.model';
import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from 'app/entities/theme/theme.service';
import { ISubtheme } from 'app/shared/model/subtheme.model';
import { SubthemeService } from 'app/entities/subtheme/subtheme.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';
import * as go from 'gojs';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { DataSyncService } from 'gojs-angular';

@Component({
  selector: 'jhi-subject-plan',
  templateUrl: './subject-plan.component.html',
  styleUrls: ['./subject-plan.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SubjectPlanComponent implements OnInit {
  planForm: FormGroup;
  subject: ISubject;
  themes: ITheme[];
  subthemes: ISubtheme[];
  activities: IActivity[];

  public initDiagram(): go.Diagram {
    const $ = go.GraphObject.make;
    const dia = $(go.Diagram, {
      'undoManager.isEnabled': true,
      model: $(go.GraphLinksModel, {
        linkKeyProperty: 'key'
      })
    });

    dia.nodeTemplate = $(
      go.Node,
      'Auto',
      {
        toLinkable: true,
        fromLinkable: true
      },
      $(go.Shape, 'RoundedRectangle', { stroke: null }, new go.Binding('fill', 'color')),
      $(go.TextBlock, { margin: 8 }, new go.Binding('text', 'key'))
    );

    return dia;
  }

  public diagramNodeData: Array<go.ObjectData> = [
    { key: 'Data Structures & Algorithms', color: 'orange' },
    { key: 'Trees', color: 'red' },
    { key: 'Sorting', color: 'red' },
    { key: 'Binary', color: 'lightblue' },
    { key: 'Red-Black', color: 'lightblue' },
    { key: 'Quicksort', color: 'lightblue' },
    { key: 'Tree Sort', color: 'lightblue' },
    { key: 'A1', color: 'green' },
    { key: 'A2', color: 'green' },
    { key: 'A3', color: 'green' },
    { key: 'A4', color: 'green' },
    { key: 'A5', color: 'green' },
    { key: 'A6', color: 'green' },
    { key: 'A7', color: 'green' },
    { key: 'A8', color: 'green' },
    { key: 'A9', color: 'green' },
    { key: 'A10', color: 'green' },
    { key: 'A11', color: 'green' },
    { key: 'A12', color: 'green' }
  ];

  public diagramLinkData: Array<go.ObjectData> = [
    { key: -1, from: 'Data Structures & Algorithms', to: 'Trees' },
    { key: -2, from: 'Data Structures & Algorithms', to: 'Sorting' },
    { key: -3, from: 'Trees', to: 'Binary' },
    { key: -4, from: 'Trees', to: 'Red-Black' },
    { key: -5, from: 'Sorting', to: 'Quicksort' },
    { key: -6, from: 'Sorting', to: 'Tree Sort' },
    { key: -7, from: 'Binary', to: 'A1' },
    { key: -8, from: 'Binary', to: 'A2' },
    { key: -9, from: 'Binary', to: 'A3' },
    { key: -10, from: 'Red-Black', to: 'A4' },
    { key: -11, from: 'Red-Black', to: 'A5' },
    { key: -12, from: 'Red-Black', to: 'A6' },
    { key: -13, from: 'Quicksort', to: 'A7' },
    { key: -14, from: 'Quicksort', to: 'A8' },
    { key: -15, from: 'Quicksort', to: 'A9' },
    { key: -16, from: 'Tree Sort', to: 'A10' },
    { key: -17, from: 'Tree Sort', to: 'A11' },
    { key: -18, from: 'Tree Sort', to: 'A12' }
  ];

  public diagramDivClassName: string = 'myDiagramDiv';
  public diagramModelData = { prop: 'value' };

  public diagramModelChange = function(changes: go.IncrementalData) {
    this.diagramNodeData = DataSyncService.syncNodeData(changes, this.diagramNodeData);
    this.diagramLinkData = DataSyncService.syncLinkData(changes, this.diagramLinkData);
    this.diagramModelData = DataSyncService.syncModelData(changes, this.diagramModelData);
  };

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected jhiAlertService: JhiAlertService,
    protected themeService: ThemeService,
    protected subthemeService: SubthemeService,
    protected activityService: ActivityService,
    private formBuilder: FormBuilder
  ) {
    this.planForm = this.formBuilder.group({
      studentCredits: [],
      studentMinGrade: [],
      selectedActivities: new FormArray([])
    });
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subject }) => {
      this.subject = subject;
    });

    // This is my attempt to have the asynchronous functions to work synchronously.

    this.themeService
      .query({
        'subject.equals': this.subject.id
      })
      .subscribe(
        (themeres: HttpResponse<ITheme[]>) => {
          this.themes = themeres.body;
          const themesIds = this.themes.map(({ id }) => id);
          this.subthemeService
            .query({
              'theme.in': themesIds
            })
            .subscribe(
              (subthemeres: HttpResponse<ISubtheme[]>) => {
                this.subthemes = subthemeres.body;
                const subthemesIds = this.subthemes.map(({ id }) => id);
                this.activityService
                  .query({
                    'subtheme.in': subthemesIds
                  })
                  .subscribe(
                    (activityres: HttpResponse<IActivity[]>) => {
                      this.activities = activityres.body;
                      this.addActivities();
                    },
                    (activityres: HttpErrorResponse) => this.onError(activityres.message)
                  );
              },
              (subthemeres: HttpErrorResponse) => this.onError(subthemeres.message)
            );
        },
        (themeres: HttpErrorResponse) => this.onError(themeres.message)
      );
  }

  previousState() {
    window.history.back();
  }

  addActivities() {
    this.activities.forEach((a, i) => {
      const control = new FormControl(i === 0);
      (this.planForm.controls.selectedActivities as FormArray).push(control);
    });
  }

  generatePlan() {
    const selectedActivitiesIds = this.planForm.value.selectedActivities
      .map((v, i) => (v ? this.activities[i].id : null))
      .filter(v => v !== null);
    console.log(selectedActivitiesIds);
    console.log(this.planForm.value.studentMinGrade);
    console.log(this.planForm.value.studentCredits);
    window.alert('Testing! Check the console for more details.');
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
