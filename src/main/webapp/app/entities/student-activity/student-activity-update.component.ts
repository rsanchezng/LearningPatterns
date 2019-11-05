import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IStudentActivity, StudentActivity } from 'app/shared/model/student-activity.model';
import { StudentActivityService } from './student-activity.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';

@Component({
  selector: 'jhi-student-activity-update',
  templateUrl: './student-activity-update.component.html'
})
export class StudentActivityUpdateComponent implements OnInit {
  isSaving: boolean;

  activities: IActivity[];

  students: IStudent[];
  activityStartDateDp: any;
  activityEndDateDp: any;
  studentActivityGradeDateDp: any;
  studentActivityCreatedDateDp: any;
  studentActivityModifiedDateDp: any;
  studentActivityModifiedByDp: any;

  editForm = this.fb.group({
    id: [],
    activityStartDate: [],
    activityEndDate: [],
    activityGrade: [],
    studentActivityGradeDate: [],
    studentActivityCreatedDate: [],
    studentActivityCreatedBy: [],
    studentActivityModifiedDate: [],
    studentActivityModifiedBy: [],
    activity: [],
    student: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected studentActivityService: StudentActivityService,
    protected activityService: ActivityService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ studentActivity }) => {
      this.updateForm(studentActivity);
    });
    this.activityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IActivity[]>) => mayBeOk.ok),
        map((response: HttpResponse<IActivity[]>) => response.body)
      )
      .subscribe((res: IActivity[]) => (this.activities = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(studentActivity: IStudentActivity) {
    this.editForm.patchValue({
      id: studentActivity.id,
      activityStartDate: studentActivity.activityStartDate,
      activityEndDate: studentActivity.activityEndDate,
      activityGrade: studentActivity.activityGrade,
      studentActivityGradeDate: studentActivity.studentActivityGradeDate,
      studentActivityCreatedDate: studentActivity.studentActivityCreatedDate,
      studentActivityCreatedBy: studentActivity.studentActivityCreatedBy,
      studentActivityModifiedDate: studentActivity.studentActivityModifiedDate,
      studentActivityModifiedBy: studentActivity.studentActivityModifiedBy,
      activity: studentActivity.activity,
      student: studentActivity.student
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const studentActivity = this.createFromForm();
    if (studentActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.studentActivityService.update(studentActivity));
    } else {
      this.subscribeToSaveResponse(this.studentActivityService.create(studentActivity));
    }
  }

  private createFromForm(): IStudentActivity {
    return {
      ...new StudentActivity(),
      id: this.editForm.get(['id']).value,
      activityStartDate: this.editForm.get(['activityStartDate']).value,
      activityEndDate: this.editForm.get(['activityEndDate']).value,
      activityGrade: this.editForm.get(['activityGrade']).value,
      studentActivityGradeDate: this.editForm.get(['studentActivityGradeDate']).value,
      studentActivityCreatedDate: this.editForm.get(['studentActivityCreatedDate']).value,
      studentActivityCreatedBy: this.editForm.get(['studentActivityCreatedBy']).value,
      studentActivityModifiedDate: this.editForm.get(['studentActivityModifiedDate']).value,
      studentActivityModifiedBy: this.editForm.get(['studentActivityModifiedBy']).value,
      activity: this.editForm.get(['activity']).value,
      student: this.editForm.get(['student']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentActivity>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackActivityById(index: number, item: IActivity) {
    return item.id;
  }

  trackStudentById(index: number, item: IStudent) {
    return item.id;
  }
}
