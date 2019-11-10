import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared';
import {
  StudentComponent,
  StudentDetailComponent,
  StudentUpdateComponent,
  StudentDeletePopupComponent,
  StudentDeleteDialogComponent,
  studentRoute,
  studentPopupRoute
} from './';

const ENTITY_STATES = [...studentRoute, ...studentPopupRoute];

@NgModule({
  imports: [LearningPatternsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StudentComponent,
    StudentDetailComponent,
    StudentUpdateComponent,
    StudentDeleteDialogComponent,
    StudentDeletePopupComponent
  ],
  entryComponents: [StudentComponent, StudentUpdateComponent, StudentDeleteDialogComponent, StudentDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearningPatternsStudentModule {}
