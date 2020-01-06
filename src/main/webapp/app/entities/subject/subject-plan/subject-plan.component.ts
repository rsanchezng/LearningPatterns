import { Component, OnInit } from '@angular/core';
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

@Component({
  selector: 'jhi-subject-plan',
  templateUrl: './subject-plan.component.html'
})
export class SubjectPlanComponent implements OnInit {
  planForm: FormGroup;
  subject: ISubject;
  themes: ITheme[];
  subthemes: ISubtheme[];
  activities: IActivity[];

  nodeDataArray = [
    { key: '1', name: 'Data Structures & Algorithms' },
    { key: '2', parent: '1', name: 'Trees' },
    { key: '3', parent: '1', name: 'Sorting' },
    { key: '4', parent: '2', name: 'Binary' },
    { key: '5', parent: '2', name: 'Red-Black' },
    { key: '6', parent: '3', name: 'Quicksort' },
    { key: '7', parent: '3', name: 'Tree Sort' },
    { key: '8', parent: '4', name: 'A1' },
    { key: '9', parent: '4', name: 'A2' },
    { key: '10', parent: '4', name: 'A3' },
    { key: '11', parent: '5', name: 'A4' },
    { key: '12', parent: '5', name: 'A5' },
    { key: '13', parent: '5', name: 'A6' },
    { key: '14', parent: '6', name: 'A7' },
    { key: '15', parent: '6', name: 'A8' },
    { key: '16', parent: '6', name: 'A9' },
    { key: '17', parent: '7', name: 'A10' },
    { key: '18', parent: '7', name: 'A11' },
    { key: '19', parent: '7', name: 'A12' }
  ];

  public model: go.Model = new go.TreeModel(this.nodeDataArray);

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
      studentMinGrade: []
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

  generatePlan() {
    console.log(this.themes);
    console.log(this.subthemes);
    console.log(this.activities);
    window.alert('Testing! Check the console for more details.');
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
