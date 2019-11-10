import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ITeacher, Teacher } from 'app/shared/model/teacher.model';
import { TeacherService } from './teacher.service';

@Component({
  selector: 'jhi-teacher-update',
  templateUrl: './teacher-update.component.html'
})
export class TeacherUpdateComponent implements OnInit {
  isSaving: boolean;
  teacherCreationDateDp: any;
  teacherModifiedDateDp: any;

  editForm = this.fb.group({
    id: [],
    teacherFirstName: [],
    teacherLastName: [],
    teacherEmail: [],
    teacherPassword: [],
    teacherCreatedBy: [],
    teacherCreationDate: [],
    teacherModifiedBy: [],
    teacherModifiedDate: []
  });

  constructor(protected teacherService: TeacherService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ teacher }) => {
      this.updateForm(teacher);
    });
  }

  updateForm(teacher: ITeacher) {
    this.editForm.patchValue({
      id: teacher.id,
      teacherFirstName: teacher.teacherFirstName,
      teacherLastName: teacher.teacherLastName,
      teacherEmail: teacher.teacherEmail,
      teacherPassword: teacher.teacherPassword,
      teacherCreatedBy: teacher.teacherCreatedBy,
      teacherCreationDate: teacher.teacherCreationDate,
      teacherModifiedBy: teacher.teacherModifiedBy,
      teacherModifiedDate: teacher.teacherModifiedDate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const teacher = this.createFromForm();
    if (teacher.id !== undefined) {
      this.subscribeToSaveResponse(this.teacherService.update(teacher));
    } else {
      this.subscribeToSaveResponse(this.teacherService.create(teacher));
    }
  }

  private createFromForm(): ITeacher {
    return {
      ...new Teacher(),
      id: this.editForm.get(['id']).value,
      teacherFirstName: this.editForm.get(['teacherFirstName']).value,
      teacherLastName: this.editForm.get(['teacherLastName']).value,
      teacherEmail: this.editForm.get(['teacherEmail']).value,
      teacherPassword: this.editForm.get(['teacherPassword']).value,
      teacherCreatedBy: this.editForm.get(['teacherCreatedBy']).value,
      teacherCreationDate: this.editForm.get(['teacherCreationDate']).value,
      teacherModifiedBy: this.editForm.get(['teacherModifiedBy']).value,
      teacherModifiedDate: this.editForm.get(['teacherModifiedDate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeacher>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
