import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISubject } from 'app/shared/model/subject.model';
import { ITheme } from 'app/shared/model/theme.model';
import { ISubtheme } from 'app/shared/model/subtheme.model';
import { IActivity } from 'app/shared/model/activity.model';

@Component({
  selector: 'jhi-subject-plan',
  templateUrl: './subject-plan.component.html'
})
export class SubjectPlanComponent implements OnInit {
  subject: ISubject;
  themes: ITheme[];
  subthemes: ISubtheme[];
  activities: IActivity[];

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subject }) => {
      this.subject = subject;
    });
  }

  previousState() {
    window.history.back();
  }
}
