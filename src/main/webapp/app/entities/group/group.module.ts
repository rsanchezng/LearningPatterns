import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared/shared.module';
import { GroupComponent } from './group.component';
import { GroupDetailComponent } from './group-detail.component';
import { GroupUpdateComponent } from './group-update.component';
import { GroupDeletePopupComponent, GroupDeleteDialogComponent } from './group-delete-dialog.component';
import { groupRoute, groupPopupRoute } from './group.route';

const ENTITY_STATES = [...groupRoute, ...groupPopupRoute];

@NgModule({
  imports: [LearningPatternsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [GroupComponent, GroupDetailComponent, GroupUpdateComponent, GroupDeleteDialogComponent, GroupDeletePopupComponent],
  entryComponents: [GroupDeleteDialogComponent]
})
export class LearningPatternsGroupModule {}
