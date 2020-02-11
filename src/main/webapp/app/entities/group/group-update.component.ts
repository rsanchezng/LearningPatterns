import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IGroup, Group } from 'app/shared/model/group.model';
import { GroupService } from './group.service';
import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from 'app/entities/subject/subject.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher/teacher.service';

@Component({
  selector: 'jhi-group-update',
  templateUrl: './group-update.component.html'
})
export class GroupUpdateComponent implements OnInit {
  isSaving: boolean;

  subjects: ISubject[];

  teachers: ITeacher[];
  groupCreationDateDp: any;
  groupModifiedDateDp: any;

  editForm = this.fb.group({
    id: [],
    groupCreatedBy: [],
    groupCreationDate: [],
    groupModifiedBy: [],
    groupModifiedDate: [],
    subject: [],
    teacher: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected groupService: GroupService,
    protected subjectService: SubjectService,
    protected teacherService: TeacherService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ group }) => {
      this.updateForm(group);
    });
    this.subjectService
      .query()
      .subscribe((res: HttpResponse<ISubject[]>) => (this.subjects = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.teacherService
      .query()
      .subscribe((res: HttpResponse<ITeacher[]>) => (this.teachers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(group: IGroup) {
    this.editForm.patchValue({
      id: group.id,
      groupCreatedBy: group.groupCreatedBy,
      groupCreationDate: group.groupCreationDate,
      groupModifiedBy: group.groupModifiedBy,
      groupModifiedDate: group.groupModifiedDate,
      subject: group.subject,
      teacher: group.teacher
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const group = this.createFromForm();
    if (group.id !== undefined) {
      this.subscribeToSaveResponse(this.groupService.update(group));
    } else {
      this.subscribeToSaveResponse(this.groupService.create(group));
    }
  }

  private createFromForm(): IGroup {
    return {
      ...new Group(),
      id: this.editForm.get(['id']).value,
      groupCreatedBy: this.editForm.get(['groupCreatedBy']).value,
      groupCreationDate: this.editForm.get(['groupCreationDate']).value,
      groupModifiedBy: this.editForm.get(['groupModifiedBy']).value,
      groupModifiedDate: this.editForm.get(['groupModifiedDate']).value,
      subject: this.editForm.get(['subject']).value,
      teacher: this.editForm.get(['teacher']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroup>>) {
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

  trackSubjectById(index: number, item: ISubject) {
    return item.id;
  }

  trackTeacherById(index: number, item: ITeacher) {
    return item.id;
  }
}
