import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentSchedule } from 'app/shared/model/student-schedule.model';

@Component({
  selector: 'jhi-student-schedule-detail',
  templateUrl: './student-schedule-detail.component.html'
})
export class StudentScheduleDetailComponent implements OnInit {
  studentSchedule: IStudentSchedule;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ studentSchedule }) => {
      this.studentSchedule = studentSchedule;
    });
  }

  previousState() {
    window.history.back();
  }
}
