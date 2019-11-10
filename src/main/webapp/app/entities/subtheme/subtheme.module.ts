import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared';
import {
  SubthemeComponent,
  SubthemeDetailComponent,
  SubthemeUpdateComponent,
  SubthemeDeletePopupComponent,
  SubthemeDeleteDialogComponent,
  subthemeRoute,
  subthemePopupRoute
} from './';

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
  entryComponents: [SubthemeComponent, SubthemeUpdateComponent, SubthemeDeleteDialogComponent, SubthemeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearningPatternsSubthemeModule {}
