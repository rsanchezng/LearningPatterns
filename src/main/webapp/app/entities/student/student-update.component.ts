import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
  isSaving: boolean;
  studentCreationDateDp: any;
  studentModifiedDateDp: any;

  editForm = this.fb.group({
    id: [],
    studentFirstName: [],
    studentLastName: [],
    studentEmail: [],
    studentPassword: [],
    studentCredits: [],
    studentCreatedBy: [],
    studentCreationDate: [],
    studentModifiedBy: [],
    studentModifiedDate: []
  });

  constructor(protected studentService: StudentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);
    });
  }

  updateForm(student: IStudent) {
    this.editForm.patchValue({
      id: student.id,
      studentFirstName: student.studentFirstName,
      studentLastName: student.studentLastName,
      studentEmail: student.studentEmail,
      studentPassword: student.studentPassword,
      studentCredits: student.studentCredits,
      studentCreatedBy: student.studentCreatedBy,
      studentCreationDate: student.studentCreationDate,
      studentModifiedBy: student.studentModifiedBy,
      studentModifiedDate: student.studentModifiedDate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id']).value,
      studentFirstName: this.editForm.get(['studentFirstName']).value,
      studentLastName: this.editForm.get(['studentLastName']).value,
      studentEmail: this.editForm.get(['studentEmail']).value,
      studentPassword: this.editForm.get(['studentPassword']).value,
      studentCredits: this.editForm.get(['studentCredits']).value,
      studentCreatedBy: this.editForm.get(['studentCreatedBy']).value,
      studentCreationDate: this.editForm.get(['studentCreationDate']).value,
      studentModifiedBy: this.editForm.get(['studentModifiedBy']).value,
      studentModifiedDate: this.editForm.get(['studentModifiedDate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>) {
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
