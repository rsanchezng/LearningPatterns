import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISubject, Subject } from 'app/shared/model/subject.model';
import { SubjectService } from './subject.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher/teacher.service';

@Component({
  selector: 'jhi-subject-update',
  templateUrl: './subject-update.component.html'
})
export class SubjectUpdateComponent implements OnInit {
  isSaving: boolean;

  teachers: ITeacher[];
  subjectCreationDateDp: any;
  subjectModifiedDateDp: any;

  editForm = this.fb.group({
    id: [],
    subjectName: [],
    subjectDescription: [],
    subjectCredits: [],
    subjectCreatedBy: [],
    subjectCreationDate: [],
    subjectModifiedBy: [],
    subjectModifiedDate: [],
    teacher: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected subjectService: SubjectService,
    protected teacherService: TeacherService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ subject }) => {
      this.updateForm(subject);
    });
    this.teacherService
      .query()
      .subscribe((res: HttpResponse<ITeacher[]>) => (this.teachers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(subject: ISubject) {
    this.editForm.patchValue({
      id: subject.id,
      subjectName: subject.subjectName,
      subjectDescription: subject.subjectDescription,
      subjectCredits: subject.subjectCredits,
      subjectCreatedBy: subject.subjectCreatedBy,
      subjectCreationDate: subject.subjectCreationDate,
      subjectModifiedBy: subject.subjectModifiedBy,
      subjectModifiedDate: subject.subjectModifiedDate,
      teacher: subject.teacher
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const subject = this.createFromForm();
    if (subject.id !== undefined) {
      this.subscribeToSaveResponse(this.subjectService.update(subject));
    } else {
      this.subscribeToSaveResponse(this.subjectService.create(subject));
    }
  }

  private createFromForm(): ISubject {
    return {
      ...new Subject(),
      id: this.editForm.get(['id']).value,
      subjectName: this.editForm.get(['subjectName']).value,
      subjectDescription: this.editForm.get(['subjectDescription']).value,
      subjectCredits: this.editForm.get(['subjectCredits']).value,
      subjectCreatedBy: this.editForm.get(['subjectCreatedBy']).value,
      subjectCreationDate: this.editForm.get(['subjectCreationDate']).value,
      subjectModifiedBy: this.editForm.get(['subjectModifiedBy']).value,
      subjectModifiedDate: this.editForm.get(['subjectModifiedDate']).value,
      teacher: this.editForm.get(['teacher']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubject>>) {
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

  trackTeacherById(index: number, item: ITeacher) {
    return item.id;
  }
}
