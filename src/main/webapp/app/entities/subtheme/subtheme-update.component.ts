import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISubtheme, Subtheme } from 'app/shared/model/subtheme.model';
import { SubthemeService } from './subtheme.service';
import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from 'app/entities/theme/theme.service';

@Component({
  selector: 'jhi-subtheme-update',
  templateUrl: './subtheme-update.component.html'
})
export class SubthemeUpdateComponent implements OnInit {
  isSaving: boolean;

  themes: ITheme[];
  subthemeCreationDateDp: any;
  subthemeModifiedDateDp: any;

  editForm = this.fb.group({
    id: [],
    subthemeName: [],
    subthemeDescription: [],
    subthemeCreatedBy: [],
    subthemeCreationDate: [],
    subthemeModifiedBy: [],
    subthemeModifiedDate: [],
    theme: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected subthemeService: SubthemeService,
    protected themeService: ThemeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ subtheme }) => {
      this.updateForm(subtheme);
    });
    this.themeService
      .query()
      .subscribe((res: HttpResponse<ITheme[]>) => (this.themes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(subtheme: ISubtheme) {
    this.editForm.patchValue({
      id: subtheme.id,
      subthemeName: subtheme.subthemeName,
      subthemeDescription: subtheme.subthemeDescription,
      subthemeCreatedBy: subtheme.subthemeCreatedBy,
      subthemeCreationDate: subtheme.subthemeCreationDate,
      subthemeModifiedBy: subtheme.subthemeModifiedBy,
      subthemeModifiedDate: subtheme.subthemeModifiedDate,
      theme: subtheme.theme
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const subtheme = this.createFromForm();
    if (subtheme.id !== undefined) {
      this.subscribeToSaveResponse(this.subthemeService.update(subtheme));
    } else {
      this.subscribeToSaveResponse(this.subthemeService.create(subtheme));
    }
  }

  private createFromForm(): ISubtheme {
    return {
      ...new Subtheme(),
      id: this.editForm.get(['id']).value,
      subthemeName: this.editForm.get(['subthemeName']).value,
      subthemeDescription: this.editForm.get(['subthemeDescription']).value,
      subthemeCreatedBy: this.editForm.get(['subthemeCreatedBy']).value,
      subthemeCreationDate: this.editForm.get(['subthemeCreationDate']).value,
      subthemeModifiedBy: this.editForm.get(['subthemeModifiedBy']).value,
      subthemeModifiedDate: this.editForm.get(['subthemeModifiedDate']).value,
      theme: this.editForm.get(['theme']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubtheme>>) {
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

  trackThemeById(index: number, item: ITheme) {
    return item.id;
  }
}
