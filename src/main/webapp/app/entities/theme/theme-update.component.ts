import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITheme, Theme } from 'app/shared/model/theme.model';
import { ThemeService } from './theme.service';
import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from 'app/entities/subject/subject.service';

@Component({
  selector: 'jhi-theme-update',
  templateUrl: './theme-update.component.html'
})
export class ThemeUpdateComponent implements OnInit {
  isSaving: boolean;

  subjects: ISubject[];
  themeCreationDateDp: any;
  themeModifiedDateDp: any;

  editForm = this.fb.group({
    id: [],
    themeName: [],
    themeDescription: [],
    themeCreatedBy: [],
    themeCreationDate: [],
    themeModifiedBy: [],
    themeModifiedDate: [],
    subject: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected themeService: ThemeService,
    protected subjectService: SubjectService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ theme }) => {
      this.updateForm(theme);
    });
    this.subjectService
      .query()
      .subscribe((res: HttpResponse<ISubject[]>) => (this.subjects = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(theme: ITheme) {
    this.editForm.patchValue({
      id: theme.id,
      themeName: theme.themeName,
      themeDescription: theme.themeDescription,
      themeCreatedBy: theme.themeCreatedBy,
      themeCreationDate: theme.themeCreationDate,
      themeModifiedBy: theme.themeModifiedBy,
      themeModifiedDate: theme.themeModifiedDate,
      subject: theme.subject
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const theme = this.createFromForm();
    if (theme.id !== undefined) {
      this.subscribeToSaveResponse(this.themeService.update(theme));
    } else {
      this.subscribeToSaveResponse(this.themeService.create(theme));
    }
  }

  private createFromForm(): ITheme {
    return {
      ...new Theme(),
      id: this.editForm.get(['id']).value,
      themeName: this.editForm.get(['themeName']).value,
      themeDescription: this.editForm.get(['themeDescription']).value,
      themeCreatedBy: this.editForm.get(['themeCreatedBy']).value,
      themeCreationDate: this.editForm.get(['themeCreationDate']).value,
      themeModifiedBy: this.editForm.get(['themeModifiedBy']).value,
      themeModifiedDate: this.editForm.get(['themeModifiedDate']).value,
      subject: this.editForm.get(['subject']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITheme>>) {
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
}
