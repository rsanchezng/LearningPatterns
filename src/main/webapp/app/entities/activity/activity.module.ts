import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared';
import {
  ActivityComponent,
  ActivityDetailComponent,
  ActivityUpdateComponent,
  ActivityDeletePopupComponent,
  ActivityDeleteDialogComponent,
  activityRoute,
  activityPopupRoute
} from './';

const ENTITY_STATES = [...activityRoute, ...activityPopupRoute];

@NgModule({
  imports: [LearningPatternsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ActivityComponent,
    ActivityDetailComponent,
    ActivityUpdateComponent,
    ActivityDeleteDialogComponent,
    ActivityDeletePopupComponent
  ],
  entryComponents: [ActivityComponent, ActivityUpdateComponent, ActivityDeleteDialogComponent, ActivityDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearningPatternsActivityModule {}
