import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared';
import {
  StudentScheduleComponent,
  StudentScheduleDetailComponent,
  StudentScheduleUpdateComponent,
  StudentScheduleDeletePopupComponent,
  StudentScheduleDeleteDialogComponent,
  studentScheduleRoute,
  studentSchedulePopupRoute
} from './';

const ENTITY_STATES = [...studentScheduleRoute, ...studentSchedulePopupRoute];

@NgModule({
  imports: [LearningPatternsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StudentScheduleComponent,
    StudentScheduleDetailComponent,
    StudentScheduleUpdateComponent,
    StudentScheduleDeleteDialogComponent,
    StudentScheduleDeletePopupComponent
  ],
  entryComponents: [
    StudentScheduleComponent,
    StudentScheduleUpdateComponent,
    StudentScheduleDeleteDialogComponent,
    StudentScheduleDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearningPatternsStudentScheduleModule {}
