import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared/shared.module';
import { TeacherComponent } from './teacher.component';
import { TeacherDetailComponent } from './teacher-detail.component';
import { TeacherUpdateComponent } from './teacher-update.component';
import { TeacherDeletePopupComponent, TeacherDeleteDialogComponent } from './teacher-delete-dialog.component';
import { teacherRoute, teacherPopupRoute } from './teacher.route';

const ENTITY_STATES = [...teacherRoute, ...teacherPopupRoute];

@NgModule({
  imports: [LearningPatternsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TeacherComponent,
    TeacherDetailComponent,
    TeacherUpdateComponent,
    TeacherDeleteDialogComponent,
    TeacherDeletePopupComponent
  ],
  entryComponents: [TeacherDeleteDialogComponent]
})
export class LearningPatternsTeacherModule {}
