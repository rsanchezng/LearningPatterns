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
    { key: 'Alpha', color: 'lightblue' },
    { key: 'Beta', color: 'orange' },
    { key: 'Gamma', color: 'lightgreen' },
    { key: 'Delta', color: 'pink' }
  ];

  public diagramLinkData: Array<go.ObjectData> = [
    { key: -1, from: 'Alpha', to: 'Beta' },
    { key: -2, from: 'Alpha', to: 'Gamma' },
    { key: -3, from: 'Beta', to: 'Beta' },
    { key: -4, from: 'Gamma', to: 'Delta' },
    { key: -5, from: 'Delta', to: 'Alpha' }
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
