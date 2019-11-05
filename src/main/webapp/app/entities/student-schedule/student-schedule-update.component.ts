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
import { IStudentSchedule, StudentSchedule } from 'app/shared/model/student-schedule.model';
import { StudentScheduleService } from './student-schedule.service';
import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from 'app/entities/group/group.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';

@Component({
  selector: 'jhi-student-schedule-update',
  templateUrl: './student-schedule-update.component.html'
})
export class StudentScheduleUpdateComponent implements OnInit {
  isSaving: boolean;

  groups: IGroup[];

  students: IStudent[];
  scheduleCreationDateDp: any;
  scheduleModifiedDateDp: any;

  editForm = this.fb.group({
    id: [],
    scheduleCreatedBy: [],
    scheduleCreationDate: [],
    scheduleModifiedBy: [],
    scheduleModifiedDate: [],
    group: [],
    student: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected studentScheduleService: StudentScheduleService,
    protected groupService: GroupService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ studentSchedule }) => {
      this.updateForm(studentSchedule);
    });
    this.groupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGroup[]>) => response.body)
      )
      .subscribe((res: IGroup[]) => (this.groups = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(studentSchedule: IStudentSchedule) {
    this.editForm.patchValue({
      id: studentSchedule.id,
      scheduleCreatedBy: studentSchedule.scheduleCreatedBy,
      scheduleCreationDate: studentSchedule.scheduleCreationDate,
      scheduleModifiedBy: studentSchedule.scheduleModifiedBy,
      scheduleModifiedDate: studentSchedule.scheduleModifiedDate,
      group: studentSchedule.group,
      student: studentSchedule.student
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const studentSchedule = this.createFromForm();
    if (studentSchedule.id !== undefined) {
      this.subscribeToSaveResponse(this.studentScheduleService.update(studentSchedule));
    } else {
      this.subscribeToSaveResponse(this.studentScheduleService.create(studentSchedule));
    }
  }

  private createFromForm(): IStudentSchedule {
    return {
      ...new StudentSchedule(),
      id: this.editForm.get(['id']).value,
      scheduleCreatedBy: this.editForm.get(['scheduleCreatedBy']).value,
      scheduleCreationDate: this.editForm.get(['scheduleCreationDate']).value,
      scheduleModifiedBy: this.editForm.get(['scheduleModifiedBy']).value,
      scheduleModifiedDate: this.editForm.get(['scheduleModifiedDate']).value,
      group: this.editForm.get(['group']).value,
      student: this.editForm.get(['student']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentSchedule>>) {
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

  trackGroupById(index: number, item: IGroup) {
    return item.id;
  }

  trackStudentById(index: number, item: IStudent) {
    return item.id;
  }
}
