import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared';
import {
  ThemeComponent,
  ThemeDetailComponent,
  ThemeUpdateComponent,
  ThemeDeletePopupComponent,
  ThemeDeleteDialogComponent,
  themeRoute,
  themePopupRoute
} from './';

const ENTITY_STATES = [...themeRoute, ...themePopupRoute];

@NgModule({
  imports: [LearningPatternsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ThemeComponent, ThemeDetailComponent, ThemeUpdateComponent, ThemeDeleteDialogComponent, ThemeDeletePopupComponent],
  entryComponents: [ThemeComponent, ThemeUpdateComponent, ThemeDeleteDialogComponent, ThemeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearningPatternsThemeModule {}
