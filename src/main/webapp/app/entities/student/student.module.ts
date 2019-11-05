import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared/shared.module';
import { StudentComponent } from './student.component';
import { StudentDetailComponent } from './student-detail.component';
import { StudentUpdateComponent } from './student-update.component';
import { StudentDeletePopupComponent, StudentDeleteDialogComponent } from './student-delete-dialog.component';
import { studentRoute, studentPopupRoute } from './student.route';

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
  entryComponents: [StudentDeleteDialogComponent]
})
export class LearningPatternsStudentModule {}
