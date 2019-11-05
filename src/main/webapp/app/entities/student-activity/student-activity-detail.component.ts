import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentActivity } from 'app/shared/model/student-activity.model';

@Component({
  selector: 'jhi-student-activity-detail',
  templateUrl: './student-activity-detail.component.html'
})
export class StudentActivityDetailComponent implements OnInit {
  studentActivity: IStudentActivity;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ studentActivity }) => {
      this.studentActivity = studentActivity;
    });
  }

  previousState() {
    window.history.back();
  }
}
