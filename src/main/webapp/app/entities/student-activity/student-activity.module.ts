import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared';
import {
  StudentActivityComponent,
  StudentActivityDetailComponent,
  StudentActivityUpdateComponent,
  StudentActivityDeletePopupComponent,
  StudentActivityDeleteDialogComponent,
  studentActivityRoute,
  studentActivityPopupRoute
} from './';

const ENTITY_STATES = [...studentActivityRoute, ...studentActivityPopupRoute];

@NgModule({
  imports: [LearningPatternsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StudentActivityComponent,
    StudentActivityDetailComponent,
    StudentActivityUpdateComponent,
    StudentActivityDeleteDialogComponent,
    StudentActivityDeletePopupComponent
  ],
  entryComponents: [
    StudentActivityComponent,
    StudentActivityUpdateComponent,
    StudentActivityDeleteDialogComponent,
    StudentActivityDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearningPatternsStudentActivityModule {}
