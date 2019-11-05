import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared/shared.module';
import { SubthemeComponent } from './subtheme.component';
import { SubthemeDetailComponent } from './subtheme-detail.component';
import { SubthemeUpdateComponent } from './subtheme-update.component';
import { SubthemeDeletePopupComponent, SubthemeDeleteDialogComponent } from './subtheme-delete-dialog.component';
import { subthemeRoute, subthemePopupRoute } from './subtheme.route';

const ENTITY_STATES = [...subthemeRoute, ...subthemePopupRoute];

@NgModule({
  imports: [LearningPatternsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SubthemeComponent,
    SubthemeDetailComponent,
    SubthemeUpdateComponent,
    SubthemeDeleteDialogComponent,
    SubthemeDeletePopupComponent
  ],
  entryComponents: [SubthemeDeleteDialogComponent]
})
export class LearningPatternsSubthemeModule {}
