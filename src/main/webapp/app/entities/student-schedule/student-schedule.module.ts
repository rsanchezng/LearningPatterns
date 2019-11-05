import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared/shared.module';
import { StudentScheduleComponent } from './student-schedule.component';
import { StudentScheduleDetailComponent } from './student-schedule-detail.component';
import { StudentScheduleUpdateComponent } from './student-schedule-update.component';
import { StudentScheduleDeletePopupComponent, StudentScheduleDeleteDialogComponent } from './student-schedule-delete-dialog.component';
import { studentScheduleRoute, studentSchedulePopupRoute } from './student-schedule.route';

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
  entryComponents: [StudentScheduleDeleteDialogComponent]
})
export class LearningPatternsStudentScheduleModule {}
