import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IActivity, Activity } from 'app/shared/model/activity.model';
import { ActivityService } from './activity.service';
import { ISubtheme } from 'app/shared/model/subtheme.model';
import { SubthemeService } from 'app/entities/subtheme';

@Component({
  selector: 'jhi-activity-update',
  templateUrl: './activity-update.component.html'
})
export class ActivityUpdateComponent implements OnInit {
  isSaving: boolean;

  subthemes: ISubtheme[];
  activityCreationDateDp: any;
  activityModifiedDateDp: any;

  editForm = this.fb.group({
    id: [],
    activityName: [],
    activityDescription: [],
    activityDuration: [],
    activityUtility: [],
    activityReqsId: [],
    activityCreatedBy: [],
    activityCreationDate: [],
    activityModifiedBy: [],
    activityModifiedDate: [],
    subtheme: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activityService: ActivityService,
    protected subthemeService: SubthemeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ activity }) => {
      this.updateForm(activity);
    });
    this.subthemeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISubtheme[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISubtheme[]>) => response.body)
      )
      .subscribe((res: ISubtheme[]) => (this.subthemes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(activity: IActivity) {
    this.editForm.patchValue({
      id: activity.id,
      activityName: activity.activityName,
      activityDescription: activity.activityDescription,
      activityDuration: activity.activityDuration,
      activityUtility: activity.activityUtility,
      activityReqsId: activity.activityReqsId,
      activityCreatedBy: activity.activityCreatedBy,
      activityCreationDate: activity.activityCreationDate,
      activityModifiedBy: activity.activityModifiedBy,
      activityModifiedDate: activity.activityModifiedDate,
      subtheme: activity.subtheme
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const activity = this.createFromForm();
    if (activity.id !== undefined) {
      this.subscribeToSaveResponse(this.activityService.update(activity));
    } else {
      this.subscribeToSaveResponse(this.activityService.create(activity));
    }
  }

  private createFromForm(): IActivity {
    return {
      ...new Activity(),
      id: this.editForm.get(['id']).value,
      activityName: this.editForm.get(['activityName']).value,
      activityDescription: this.editForm.get(['activityDescription']).value,
      activityDuration: this.editForm.get(['activityDuration']).value,
      activityUtility: this.editForm.get(['activityUtility']).value,
      activityReqsId: this.editForm.get(['activityReqsId']).value,
      activityCreatedBy: this.editForm.get(['activityCreatedBy']).value,
      activityCreationDate: this.editForm.get(['activityCreationDate']).value,
      activityModifiedBy: this.editForm.get(['activityModifiedBy']).value,
      activityModifiedDate: this.editForm.get(['activityModifiedDate']).value,
      subtheme: this.editForm.get(['subtheme']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivity>>) {
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

  trackSubthemeById(index: number, item: ISubtheme) {
    return item.id;
  }
}
