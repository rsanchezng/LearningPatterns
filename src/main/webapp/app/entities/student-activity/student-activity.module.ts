import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared/shared.module';
import { StudentActivityComponent } from './student-activity.component';
import { StudentActivityDetailComponent } from './student-activity-detail.component';
import { StudentActivityUpdateComponent } from './student-activity-update.component';
import { StudentActivityDeletePopupComponent, StudentActivityDeleteDialogComponent } from './student-activity-delete-dialog.component';
import { studentActivityRoute, studentActivityPopupRoute } from './student-activity.route';

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
  entryComponents: [StudentActivityDeleteDialogComponent]
})
export class LearningPatternsStudentActivityModule {}
